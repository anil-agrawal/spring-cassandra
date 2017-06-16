package com.san.test;

import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;;

public class TestJDBC {
	private Cluster cluster;
	private Session session;

	static String ip = "192.168.99.100";
	static String keySpaceName = "test1";
	static String table = "damru";

	public static void main(String[] args) {
		TestJDBC obj = new TestJDBC();
		try {
			obj.connectToCluster(ip);
			// obj.createKeySpace(keySpaceName);
			obj.session = obj.getSession(keySpaceName);
			// obj.createTable(table);
			for (int i = 10; i < 20; i++) {
				obj.insertData(table, i, table + " name new " + i, table + " address " + i);
			}
			obj.testSelectQuery(table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			obj.cluster.close();
		}
	}

	void connectToCluster(String ipAddress) {
		if (cluster == null) {
			Cluster.Builder builder = Cluster.builder();
			builder = builder.addContactPoint(ipAddress);
			cluster = builder.build();
		}
	}

	Session getSession(String keySpace) {
		Session session = null;
		if (cluster != null) {
			session = cluster.connect(keySpace);
		}
		return session;
	}

	void createKeySpace(String keySpace) {
		if (cluster != null) {
			String query = "CREATE KEYSPACE " + keySpace + " WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};";
			Session session = cluster.connect();
			session.execute(query);
			session.execute("USE " + keySpace);
			System.out.println("Keyspace '" + keySpace + "' created");
		}
	}

	void testSelectQuery(String tableName) {
		String query = "select * from " + tableName + " ;";
		ResultSet rs = session.execute(query);
		List<Row> rows = rs.all();
		if (rows.isEmpty()) {
			System.out.println("No rows found in table " + tableName);
		}
		for (Row row : rows) {
			System.out.println("ID : " + row.getInt(0) + ", Name : " + row.getString(1) + ", Address : " + row.getString(2));
		}
		System.out.println("Rows fetched from table '" + tableName + "'");
	}

	void createTable(String tableName) {
		String query = "create table " + tableName + " (id int primary key, name text, address text);";
		session.execute(query);
		System.out.println("Table '" + tableName + "' created");
	}

	void insertData(String tableName, int id, String name, String address) {
		String query = "insert into " + tableName + " (id, name, address) values(" + id + ", '" + name + "', '" + address + "');";
		session.execute(query);
		System.out.println("Row inserted in table '" + tableName + "'");
	}
}
