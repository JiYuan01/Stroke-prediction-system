package aaa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
	//查询所有联系人
	public static List<Person> queryPersons() {
		String sql = "select * from stroke01";
		List<Person> Person = new ArrayList<Person>();
		List<Object[]> list = DBHelper.executeQueryList(sql);
		for(Object[] li:list) {
			Person p = new Person();
			p.setId(li[0].toString());
			p.setGender(li[1].toString());
			p.setAge(li[2].toString());
			p.setHypertension(Integer.parseInt(li[3].toString()));
			p.setHeart_disease(Integer.parseInt(li[4].toString()));
			p.setEver_married(li[5].toString());
			p.setWork_type(li[6].toString());
			p.setResidence_type(li[7].toString());
			p.setAvg_glucose_level(li[8].toString());
			p.setBmi(li[9].toString());
			p.setSmoking_status(li[10].toString());
			p.setStroke(Integer.parseInt(li[11].toString()));
			Person.add(p);
		}
		return Person;
	}
	//查询联系人数量
	public static int queryPersonsNum() {
		String sql = "select count(*) from stroke01";
		ResultSet rs = DBHelper.executeQuery(sql);
		int num = 0;
		try {
			if(rs.next()) {
			num = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return num;
	}
}

