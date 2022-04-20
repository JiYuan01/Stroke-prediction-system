package aaa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DBHelper {
	/* ����driver,url,user,pwd�� */
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false";
	private static String user = "root", pwd = "123456";
	private static Connection con;
    /*������������*/
	static {
		try {
			Class.forName(driver);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    /*ִ����ɾ�Ĳ���*/
	public static void executeUpdate(String sql) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			Statement cmd = con.createStatement();
			cmd.executeUpdate(sql);
		} catch (Exception ex) {
		 ex.printStackTrace();
		}
	}
	/*ִ�в�ѯ����*/
	public static ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			Statement cmd = con.createStatement();
			rs = cmd.executeQuery(sql);
		} catch (Exception ex) { 
			ex.printStackTrace();
		}
		return rs;
	}
	/*����ѯ����������б���ʽ����*/
	public static List<Object[]> executeQueryList(String sql) {
		List<Object[]> list=new ArrayList<Object[]>();
		try {
		    con=DriverManager.getConnection(url,user,pwd);
			Statement cmd=con.createStatement();
			ResultSet rs=cmd.executeQuery(sql);
			ResultSetMetaData rsMeta=  rs.getMetaData();//Ԫ����
			int colsCount=rsMeta.getColumnCount();
			while(rs.next()) {
				Object[] row=new Object[colsCount];
				for(int i=0;i<colsCount;i++) {
					row[i]=rs.getObject(i+1);
				}
				list.add(row);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
   /*ִ�йر����ݿ����Ӳ���*/
	public static void closeConnection() {
		try {
           if(!con.isClosed())
			con.close();
		  } catch (Exception ex) {
			  ex.printStackTrace();
		}
	}
}

