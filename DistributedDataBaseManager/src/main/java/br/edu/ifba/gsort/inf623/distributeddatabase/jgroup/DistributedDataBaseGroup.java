package br.edu.ifba.gsort.inf623.distributeddatabase.jgroup;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import br.edu.ifba.gsort.inf623.distributeddatabase.entity.Command;

@SessionScoped
public class DistributedDataBaseGroup extends ReceiverAdapter implements Serializable {

	private JChannel channel;

	public DistributedDataBaseGroup() throws Exception {
		
		this.channel = new JChannel();
		
	}

	public void connect() throws Exception {
		
		channel.setReceiver(this);
		this.channel.connect("DataBaseCluster");
		
	}

	public void disconnect() throws Exception {
		
	    this.channel.disconnect();
		
	}
	
	public void close() throws Exception {
		
	    this.channel.close();
		
	}
	
	@Override
	public void viewAccepted(View new_view) {
		
		System.out.println("NEW VIEW [" + new_view + "]");
		
	}
	
	public void sendMessage(Command command) throws Exception {
		
		Message msg = new Message(null, null, command);
		this.channel.send(msg);
        
	}
	
}
