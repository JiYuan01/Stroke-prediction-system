package dbutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLHelper {
	private static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=HealthSystem", user="sa", pwd="001220";
	private static Connection conn=null;
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String sql) {
		ResultSet rs=null;
		try {
			conn=DriverManager.getConnection(url,user,pwd);
			Statement cmd=conn.createStatement();
			rs=cmd.executeQuery(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void closeConnection() {
		try {
			if(conn!=null && !conn.isClosed())
				conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int executeUpdate(String sql) {
		int r=0;
		try {
			conn=DriverManager.getConnection(url,user,pwd);
			Statement cmd=conn.createStatement();
			r=cmd.executeUpdate(sql);
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public static int executeUpdateByParams(String sql,Object... params) {
		int r=0;
		try {
			Connection conn=DriverManager.getConnection(url,user,pwd);
			PreparedStatement pcmd=conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				if(params[i] instanceof Integer)
					pcmd.setInt(i+1, new Integer(params[i].toString()));
				else if(params[i] instanceof Double)
					pcmd.setDouble(i+1, new Double(params[i].toString()));
				else if(params[i] instanceof Float)
					pcmd.setFloat(i+1, new Float(params[i].toString()));
				else if(params[i] instanceof String)
					pcmd.setString(i+1, params[i].toString());
				else
					pcmd.setObject(i+1, params[i]);
			}
			r=pcmd.executeUpdate();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public static List<Object[]> executeQueryList(String sql){
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			conn=DriverManager.getConnection(url,user,pwd);
			Statement cmd=conn.createStatement();
			ResultSet rs=cmd.executeQuery(sql);
			ResultSetMetaData rsMeta=rs.getMetaData();
			int colsCount=rsMeta.getColumnCount();
			while(rs.next()) {
				Object[] row=new Object[colsCount];
				for(int i=0;i<colsCount;i++)
					row[i]=rs.getObject(i+1);
				list.add(row);
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
