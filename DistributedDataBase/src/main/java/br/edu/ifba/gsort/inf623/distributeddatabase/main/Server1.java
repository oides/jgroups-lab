package br.edu.ifba.gsort.inf623.distributeddatabase.main;

import br.edu.ifba.gsort.inf623.distributeddatabase.DistributedDataBase;

public class Server1 {

	public static void main(String[] args) throws Exception {
	
		DistributedDataBase distributedDataBase = new DistributedDataBase("jgroup1");
		distributedDataBase.start();
		
	}
	
}
