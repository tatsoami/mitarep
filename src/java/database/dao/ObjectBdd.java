package database.dao;


import database.annotations.ExtendsIgnore;
import database.annotations.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ObjectBdd {
	@ExtendsIgnore
	private Authentification authentification=new Authentification("projet_baovola", "root", "garage_final","localhost", 5432);
	@ExtendsIgnore
	private Table table = null;
	@ExtendsIgnore
	private Object obj;
	@ExtendsIgnore
	private Queue<Integer> tempIndex = new LinkedList<>();

	public ObjectBdd() throws Exception {
		this.init();
	}
	public void init() throws Exception {
		this.obj = this;
		this.initTableName();
		this.initFieldsName();
		this.initSequenceName();
		this.initPkIndex();
	}
	
	public java.sql.Connection getConnection(boolean autocommit) throws Exception {
		if(this.authentification==null) {
			throw new Exception("Please set values to authentification classes.object via DbManager.setAthentificationData(String username,String password,String dbname,String host,int port)");
		}else{
			return new Connection().createConnection("postgres",this.authentification,autocommit);
		}
	}

	public Authentification getAuthentification() {
		return authentification;
	}

	public void setAuthentification(Authentification authentification) {
		this.authentification = authentification;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Queue<Integer> getTempIndex() {
		return tempIndex;
	}

	public void setTempIndex(Queue<Integer> tempIndex) {
		this.tempIndex = tempIndex;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table=table;
	}

	public String executeStringQuery(java.sql.Connection connection, String query) throws Exception {
		PreparedStatement ps = connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();
		String id = null;
		if(rs.next()) {
			id = String.valueOf(rs.getObject(1));
		}
		ps.close();
		return id;
	}

	private void initSequenceName() {
		Class clObj = this.obj.getClass();
		Sequence sequence = (Sequence) clObj.getAnnotation(Sequence.class);
		if(sequence!=null) {
			this.table.setSequenceName(sequence.value());
		}else{
			this.table.setSequenceName("seq"+this.table.getName());
		}
	}

	private int pkNumber() {
		Class clObj = this.obj.getClass();
		Field[] fields = clObj.getDeclaredFields();
		int countPk = 0;
		for (Field field : fields) {
			if (field.isAnnotationPresent(PrimaryKey.class)) {
				countPk++;
			}
		}
		return countPk;
	}

	private void pkControl() throws Exception {
		if (this.pkNumber() > 1) {
			throw new Exception("Table " + this.obj.getClass().getPackage().getName() + "\""
					+ this.obj.getClass().getSimpleName() + "\" contains more than 1 Primary Key");
		}
	}
	// method that get the field that have the desired name from an array of fields
	private Field getField(String fieldName, Field[] fields) {
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	// method that execute query from database and retrieve one number as a result
	public double executeQuery(String query) throws Exception {
		java.sql.Connection connection = this.getConnection(false);
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		double result = 0;
		if (resultSet.next()) {
			result = resultSet.getDouble(1);
		}
		resultSet.close();
		preparedStatement.close();
		connection.close();
		return result;
	}

	public int getPkValue() throws NoSuchFieldException, SecurityException, Exception {
		Field field=this.getField(this.getPkColumn(), this.getFields(this.getObj()));
		field.setAccessible(true);
		return (int) field.get(this);
	}
	
	
	public String getPkColumn() throws Exception {
		return this.getTable().getFields().get(this.getTable().getPkIndex());
	}
	
	private void initPkIndex() throws Exception {
		this.pkControl();
		Field[] fields = this.getFields(this.obj);
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(PrimaryKey.class)) {
				this.getTable().setPkIndex(i);
				return;
			}
		}
	}

	private void initTableName() {
		Class clObj = this.obj.getClass();
		this.setTable(new Table());
		if (clObj.isAnnotationPresent(Tablename.class)) {
			Tablename annotation = (Tablename) clObj.getAnnotation(Tablename.class);
			this.getTable().setName(annotation.value());
		} else {
			this.getTable().setName(clObj.getSimpleName());
		}
	}
	public Field[] getFields(Object obj) {
		// get caree's field Object and all of his parents
		Class c = obj.getClass();
		ArrayList<Field> fields = new ArrayList<>();
		while (c != null) {
			Field[] f = c.getDeclaredFields();
			for (Field field : f) {
				if(field.isAnnotationPresent(ExtendsIgnore.class)){
                                    continue;
                                }
                                fields.add(field);
			}
			c = c.getSuperclass();
		}
		return fields.toArray(new Field[fields.size()]);
	}
	private void initFieldsName() throws Exception {
		Field[] fields = this.getFields(this.obj);
		ArrayList<String> stringFields = new ArrayList<>();
		for (Field field : fields) {
			FieldName annotation = field.getAnnotation(FieldName.class);
			if (field.isAnnotationPresent(FieldName.class)) {
				stringFields.add(annotation.value());
			} else {
				stringFields.add(field.getName());
			}
		}
		this.getTable().setFields(stringFields);
	}

	private ArrayList<Object> values() throws IllegalArgumentException, IllegalAccessException {
		Object obj = this;
		Field[] fields = this.getFields(obj);
		ArrayList<Object> values = new ArrayList<>();
		for (Field field : fields) {
			field.setAccessible(true);
			values.add(field.get(obj));
		}
		return values;
	}

	private String saveQueryTemplate() {
		String sqlQuery = "insert into " + this.getTable().getName() + "(";
		Object value = null;
		int fieldnumber = this.getTable().getFields().size();
		String unknown = "";
		for (int i = 0; i < fieldnumber; i++) {
			value = this.getTable().getFields().get(i);
			sqlQuery += value;
			unknown += "?";
			if (i != fieldnumber - 1) {
				sqlQuery += ",";
				unknown += ",";
			}
		}
		sqlQuery += ") values(" + unknown + ")";
		return sqlQuery;
	}

	private String updateQueryTemplate() {
		String sqlQuery = "update " + this.getTable().getName() + " set ";
		Field[] fields = this.getFields(this.obj);
		for (int i = 0; i < fields.length; i++) {
			if (i != this.getTable().getPkIndex()) {
				sqlQuery += this.getTable().getFields().get(i) + "= ?";
				if (i != fields.length - 1) {
					sqlQuery += ",";
				}
				this.tempIndex.add(i);
			}
		}
		this.tempIndex.add(this.getTable().getPkIndex());
		sqlQuery=sqlQuery.substring(0, sqlQuery.length()-1);
		sqlQuery += " where " + this.getTable().getFields().get(this.getTable().getPkIndex()) + "=?";
		return sqlQuery;
	}

	private String deleteQueryTemplate(boolean all) {
		String sqlQuery = "delete from " + this.getTable().getName();
			if (all == false) {
			sqlQuery += " where " + this.getTable().getFields().get(this.getTable().getPkIndex()) + "= ?";
		}
		System.out.println(sqlQuery);
		return sqlQuery;
	}

	public String selectQueryTemplate() {
		String sqlQuery = "Select * from " + this.getTable().getName();
		return sqlQuery;
	}
	
	private String  selectByIdQueryTemplate() {
		return this.selectQueryTemplate()+" where "+this.getTable().getFields().get(this.getTable().getPkIndex())+"= ?";
	}

	public String nextSeqValue(java.sql.Connection con) throws SQLException {
		String sqlQuery = "Select nextval('" + this.getTable().getSequenceName() + "')";
		PreparedStatement stat = con.prepareStatement(sqlQuery);
		ResultSet res = stat.executeQuery();
		int seqValue = -1;
		while (res.next()) {
			seqValue = res.getInt(1);
		}
		res.close();
		stat.close();
		return Integer.toString(seqValue);
	}

	public int create(java.sql.Connection con) throws Exception {
		ArrayList<Object> values = this.values();
		String queryTemplate = this.saveQueryTemplate();
		System.out.println(queryTemplate);
		PreparedStatement pStat = con.prepareStatement(queryTemplate);
		int seqValue=0;
		try {
			java.sql.Connection con2 = this.getConnection(true);
			seqValue= Integer.parseInt(this.nextSeqValue(con2));
			String pkValue = String.valueOf((int)this.getPkValue());
			if(!pkValue.equals("0")) throw new RuntimeException("Primary key value is defined");
		} catch (SQLException|RuntimeException e) {
			seqValue= this.getPkValue();
		}
		Field[] fields = this.getFields(this.obj);
		for (int i = 0; i < values.size(); i++) {
			fields[i].setAccessible(true);
			if (this.getTable().getPkIndex() == i) {
				pStat.setObject(i + 1, seqValue);
				continue;
			}
			pStat.setObject(i + 1, values.get(i));
		}
		pStat.executeUpdate();
		pStat.close();
		return seqValue;
	}

	public void update(java.sql.Connection con) throws Exception {
		ArrayList<Object> values = this.values();
		String queryTemplate = updateQueryTemplate();
		System.out.println(queryTemplate);
		Class clObj = this.obj.getClass();
		Field[] fields = this.getFields(this.obj);
		PreparedStatement pStat = con.prepareStatement(queryTemplate);
		String fieldType = "";
		int index = 1;
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAnnotationPresent(PrimaryKey.class)) {
				fields[i].setAccessible(true);
				pStat.setObject(index, values.get(this.tempIndex.poll()));
				index++;
			}
		}
		pStat.setObject(index, this.obj.getClass().getMethod("getId").invoke(this.obj));
		pStat.executeUpdate();
		pStat.close();
	}

	public void delete(java.sql.Connection con,boolean all) throws SQLException, IllegalArgumentException, IllegalAccessException {
		ArrayList<Object> values = this.values();
		String sqlTemplate = this.deleteQueryTemplate(all);
		PreparedStatement pStat = con.prepareStatement(sqlTemplate);
		Class clObj = this.obj.getClass();
		Field[] fields = clObj.getDeclaredFields();
		String fieldType = fields[this.getTable().getPkIndex()].getType().getSimpleName();
		System.out.println(fieldType);
		pStat.setObject(1, values.get(this.getTable().getPkIndex()));
		pStat.executeUpdate();
		pStat.close();
	}
	
	public <T> ArrayList<T> findAll(java.sql.Connection con, String where)
			throws Exception {
		String query = this.selectQueryTemplate()+" "+where;
		System.out.println(query);
		PreparedStatement pStat = con.prepareStatement(query);
		ResultSet res = pStat.executeQuery();
		Field[] fields = this.getFields(this.getObj());
		ArrayList<T> results = new ArrayList<>();
		while (res.next()) {
			ObjectBdd copyObj = this.getClass().newInstance();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if(!fields[i].isAnnotationPresent(DbColumn.class)) {
					continue;
				}
				Object value=res.getObject(fields[i].getName());
				if (value instanceof BigDecimal) value=((BigDecimal) value).doubleValue();
				if(fields[i].getType().getSimpleName().equals("double") && value==null) value=0;
				fields[i].set(copyObj, value);
			}
			results.add((T)copyObj);
		}
		res.close();
		pStat.close();
		return results;
	}

	public ObjectBdd findOne(java.sql.Connection con,String where) throws Exception {
		ObjectBdd result=null;
		ArrayList<ObjectBdd> results=new ArrayList<>();
		String query = this.selectQueryTemplate()+" "+where+" limit 1";
		System.out.println(query);
		PreparedStatement pStat = con.prepareStatement(query);
		ResultSet res = pStat.executeQuery();
		Field[] fields = this.getClass().getDeclaredFields();
		while (res.next()) {
			ObjectBdd copyObj = this.getClass().newInstance();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if(!fields[i].isAnnotationPresent(DbColumn.class)) {
					continue;
				}
				fields[i].set(copyObj, res.getObject(i + 1));
			}
			results.add(copyObj);
		}
		res.close();
		pStat.close();
		if(results.size()<=0) {
			return null;
		}
		return results.get(0);
	}
}
