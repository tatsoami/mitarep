package database.dao;

public class Authentification {
	private String username;
	private String password;
	private String dbname;
	private String host;
	private int port;
	public Authentification() {
		super();
	}
	public Authentification(String username, String password, String dbname, String host, int port) {
		super();
		this.username = username;
		this.password = password;
		this.dbname = dbname;
		this.host = host;
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
