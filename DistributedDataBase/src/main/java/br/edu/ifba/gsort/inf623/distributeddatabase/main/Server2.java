package br.edu.ifba.gsort.inf623.distributeddatabase.main;

import br.edu.ifba.gsort.inf623.distributeddatabase.DistributedDataBase;

public class Server2 {

	public static void main(String[] args) throws Exception {
	
		DistributedDataBase distributedDataBase = new DistributedDataBase("jgroup2");
		distributedDataBase.start();
		
	}
	
}
