package aaa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DBHelper {
	/* 定义driver,url,user,pwd等 */
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false";
	private static String user = "root", pwd = "123456";
	private static Connection con;
    /*加载驱动程序*/
	static {
		try {
			Class.forName(driver);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    /*执行增删改操作*/
	public static void executeUpdate(String sql) {
		try {
			con = DriverManager.getConnection(url, user, pwd);
			Statement cmd = con.createStatement();
			cmd.executeUpdate(sql);
		} catch (Exception ex) {
		 ex.printStackTrace();
		}
	}
	/*执行查询操作*/
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
	/*将查询操作结果以列表形式返回*/
	public static List<Object[]> executeQueryList(String sql) {
		List<Object[]> list=new ArrayList<Object[]>();
		try {
		    con=DriverManager.getConnection(url,user,pwd);
			Statement cmd=con.createStatement();
			ResultSet rs=cmd.executeQuery(sql);
			ResultSetMetaData rsMeta=  rs.getMetaData();//元数据
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
   /*执行关闭数据库连接操作*/
	public static void closeConnection() {
		try {
           if(!con.isClosed())
			con.close();
		  } catch (Exception ex) {
			  ex.printStackTrace();
		}
	}
}

