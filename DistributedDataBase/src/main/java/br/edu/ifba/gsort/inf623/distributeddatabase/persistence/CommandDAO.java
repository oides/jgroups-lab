package br.edu.ifba.gsort.inf623.distributeddatabase.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import br.edu.ifba.gsort.inf623.distributeddatabase.entity.Command;

public class CommandDAO {

	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/";
	private static final String USER = "postgres";
	private static final String PASS = "postgres";

	private Connection connection;
	private PreparedStatement preparedStatement;
	private Statement statement;
	
	private static final String SQL_INSERT_COMMAND = "INSERT INTO command(ordem, comando) VALUES (?, ?)";
	
	public CommandDAO(String databaseName) {
		
		super();		
		try {
			
			Class.forName(JDBC_DRIVER);
			
			connection = DriverManager.getConnection(DB_URL + databaseName, USER, PASS);
			statement = connection.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
 	}
	
	public void salvarComando(Command command) {
		try {
			
			preparedStatement = connection.prepareStatement(SQL_INSERT_COMMAND);
			
			preparedStatement.setLong(1, command.getId());
			preparedStatement.setString(2, command.getCommand());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean executeStatement(String sql) {
		try {
			
			statement.execute(sql);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}

	public List<Command> obterComandos() {
		
		List<Command> listaComandos = new LinkedList<Command>();
		
		try {
			
			ResultSet rs = statement.executeQuery("select ordem, comando from command");
			
			while (rs.next()) {
	            listaComandos.add(new Command(rs.getLong("ordem"), rs.getString("comando")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaComandos;
	}

}
