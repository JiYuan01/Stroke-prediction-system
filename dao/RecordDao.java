package dao;

import java.util.List;

import dbutil.SQLHelper;

public class RecordDao {
	public int addRecord(String id,String gender,int age, int hypertension, int heart_disease, String ever_married,
			String work_type, String Residence_type, float avg_glucose_level, String bmi, String smoking_status, int stroke) {//添加记录
		String sql="insert into stroke_data1 values(?,?,?,?,?,?,?,?,?,?,?,?)";
		return SQLHelper.executeUpdateByParams(sql,id,gender,age,hypertension,heart_disease,ever_married,work_type,Residence_type,avg_glucose_level,bmi,smoking_status,stroke);
	}
	
	public int deleteRecord(String id) {//删除记录
		String sql="delete from stroke_data1 where id='"+id+"'";
		return SQLHelper.executeUpdate(sql);
	}
	
	public List<Object[]> queryRecord(){//整表查询
		String sql="select * from stroke_data1";
		return SQLHelper.executeQueryList(sql);
	}
	
	public List<Object[]> queryRecord(String str){//条件查询
		String sql="select * from stroke_data1 where 1=1 "+str;
		return SQLHelper.executeQueryList(sql);
	}
	
	public int updateRecord(String id, String col, String col_data) {//更新记录
		String sql="update stroke_data1 set "+col+"='"+col_data+"' where id='"+id+"'";
		return SQLHelper.executeUpdate(sql);
	}
	
	public static List<Object[]> userValidate(String user,String pwd){//验证用户
		String sql="select * from user_data where usr='"+user+"' and pwd='"+pwd+"' and usr_type='user'";
		return SQLHelper.executeQueryList(sql);
	}
	
	public static List<Object[]> managerValidate(String user,String pwd){//验证管理员
		String sql="select * from user_data where usr='"+user+"' and pwd='"+pwd+"' and usr_type='manager'";
		return SQLHelper.executeQueryList(sql);
	}
}
