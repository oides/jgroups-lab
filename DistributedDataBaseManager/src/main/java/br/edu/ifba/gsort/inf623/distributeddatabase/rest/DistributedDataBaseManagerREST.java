package br.edu.ifba.gsort.inf623.distributeddatabase.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.edu.ifba.gsort.inf623.distributeddatabase.entity.Command;
import br.edu.ifba.gsort.inf623.distributeddatabase.jgroup.DistributedDataBaseGroup;
import br.edu.ifba.gsort.inf623.distributeddatabase.persistence.CommandDAO;

@Path("database")
public class DistributedDataBaseManagerREST {

	@Inject
	private DistributedDataBaseGroup distributedDataBaseGroup;
	
	@Inject
	private CommandDAO commandDAO;
	
	@GET
	@Path("connection/open")
	public String open() throws Exception {
		
		this.distributedDataBaseGroup.connect();
		
		return "Connection opened!";
		
	}

	@GET
	@Path("connection/close")
	public String close() throws Exception {
		
		this.distributedDataBaseGroup.disconnect();
		
		return "Connection closed!";
		
	}

	@GET
	@Path("command/{command}")
	public String insert(@PathParam("command") String cmd) throws Exception {
		
		Command command = this.saveCommand(cmd);
		
		this.distributedDataBaseGroup.sendMessage(command);
		
		return command.getId() + "-" + command.getCommand();
		
	}

	private Command saveCommand(String cmd) {
		
		Command command = new Command(cmd);
		
		if (!command.getCommand().equals("desligar")) {
			return commandDAO.create(command);
		} else {
			return command;
		}
		
	}

}