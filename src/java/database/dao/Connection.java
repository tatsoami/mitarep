package database.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
	public Connection() {
		// TODO Auto-generated constructor stub
	}
	
	public java.sql.Connection createConnection(String dbms, Authentification db, boolean autocommit) throws Exception {
		java.sql.Connection con = null;
		// step1 load the driver class
		try {
			if(dbms.equals("oracle")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}else if(dbms.equals("postgres")){
				Class.forName("org.postgresql.Driver");
			}
			con = DriverManager.getConnection(new Utilities().createUrl(dbms, db), db.getUsername(), db.getPassword());
			con.setAutoCommit(autocommit);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
}
