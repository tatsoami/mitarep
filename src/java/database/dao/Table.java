package database.dao;

import java.util.ArrayList;

public class Table {
	private String name;
	private ArrayList<String> fields;
	private String sequenceName;
	private int pkIndex;
	public Table() {
		// TODO Auto-generated constructor stub
	}
	public Table(String name, ArrayList<String> fields,String sequenceName,int pkIndex) {
		super();
		this.name = name;
		this.fields = fields;
		this.sequenceName=sequenceName;
		this.pkIndex=pkIndex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getFields() {
		return fields;
	}
	public void setFields(ArrayList<String> fields) {
		this.fields = fields;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public int getPkIndex() {
		return pkIndex;
	}
	public void setPkIndex(int pkIndex) {
		this.pkIndex = pkIndex;
	}
	
	
	
}
