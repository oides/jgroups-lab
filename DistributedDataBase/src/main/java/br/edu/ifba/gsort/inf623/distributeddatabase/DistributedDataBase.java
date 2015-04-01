package br.edu.ifba.gsort.inf623.distributeddatabase;

import br.edu.ifba.gsort.inf623.distributeddatabase.jgroup.DistributedDataBaseGroup;

public class DistributedDataBase {

	private DistributedDataBaseGroup distributedDataBaseGroup;

	public DistributedDataBase(String dataBaseName) throws Exception {
		
		super();
		
		this.distributedDataBaseGroup = new DistributedDataBaseGroup(dataBaseName);
		
	}

	public void start() throws Exception {

		this.distributedDataBaseGroup.connect();

	}

}
