package database.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Utilities {
	public final String oracleUrlPrefix="jdbc:oracle:thin:@";
	public final String postgresUrlPrefix="jdbc:postgresql://";
	public String createUrl(String dbms,Authentification db) throws Exception {
		if(oracleUrlPrefix.contains(dbms)) {
			return this.oracleUrl(db);
		}else if(postgresUrlPrefix.contains(dbms)){
			System.out.println(this.postgresUrl(db));
			return this.postgresUrl(db);
		}
		throw new Exception("No url matched");
	}
	
	public String oracleUrl(Authentification db) {
		return this.oracleUrlPrefix+db.getHost()+":"+db.getPort()+":"+db.getDbname();
	}
	
	public String postgresUrl(Authentification db) {
		return this.postgresUrlPrefix+db.getHost()+":"+db.getPort()+"/"+db.getDbname();
	}
	public static Field[] getAnnotatedFields(Class object, Class annotation) {
		Field[] fields = object.getDeclaredFields();
		ArrayList<Field> annotatedFields = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.isAnnotationPresent(annotation)) {
				annotatedFields.add(field);
			}
		}
		return annotatedFields.toArray(new Field[annotatedFields.size()]);
	}
}
