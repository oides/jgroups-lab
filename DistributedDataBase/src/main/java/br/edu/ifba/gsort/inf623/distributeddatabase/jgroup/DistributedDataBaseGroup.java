package br.edu.ifba.gsort.inf623.distributeddatabase.jgroup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import br.edu.ifba.gsort.inf623.distributeddatabase.entity.Command;
import br.edu.ifba.gsort.inf623.distributeddatabase.persistence.CommandDAO;

public class DistributedDataBaseGroup extends ReceiverAdapter implements Serializable {

	private JChannel channel;
	private String dataBaseName;
	private CommandDAO commandDAO;

	public DistributedDataBaseGroup(String dataBaseName) throws Exception {
		
		this.channel = new JChannel();
		this.dataBaseName = dataBaseName;
		this.commandDAO = new CommandDAO(dataBaseName);
		
	}

	public void connect() throws Exception {
		
		this.channel.setReceiver(this);
		this.channel.connect("DataBaseCluster");
		this.channel.getState(null, 10000);
		
	}

	public void close() throws Exception {
		
	    this.channel.close();
		
	}
	
	@Override
	public void viewAccepted(View new_view) {
		
		System.out.println("NEW VIEW [" + new_view + "]");
		
	}
	
	public void receive(Message msg) {

		Command command = (Command) msg.getObject();
		
		if (command.getCommand().equals("desligar")) {
			
			try {
				this.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		// Executando comando recebido, e em caso de sucesso, salvando o comando no historico...
		if (commandDAO.executeStatement(command.getCommand())) {
			commandDAO.salvarComando(command);
		}
		
		logCommand(msg, command);
	}

	public void getState(OutputStream output) throws Exception {
		
		Util.objectToStream(commandDAO.obterComandos(), new DataOutputStream(output));
		
	}

	public void setState(InputStream input) throws Exception {
		
		List<Command> comandosAtuais = this.atualizarListaComandosAtuais(input);
		
		List<Command> comandosLocais = commandDAO.obterComandos();
		
		for (Command command : comandosAtuais) {
			
			if (!comandosLocais.contains(command)) {
				commandDAO.executeStatement(command.getCommand());
				commandDAO.salvarComando(command);
			}
			
		}

	}

	private List<Command> atualizarListaComandosAtuais(InputStream input) throws Exception {
		return (List<Command>) Util.objectFromStream(new DataInputStream(input));
	}

	private void logCommand(Message msg, Command command) {
		String line = "[" + msg.getSrc() + "][" + command.getId() + "/" + command.getCommand() + "]";
		System.out.println(line);
	}
	
}