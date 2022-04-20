package apriori;

import java.util.ArrayList;
import java.util.List;

public class StrokeDao {
	public List<Stroke> queryAllNewStroke(){ //返回全部结果集
		List<Stroke> strlist=new ArrayList<Stroke>();
		String sql="select * from stroke_data_new";
		List<Object[]> list=SQLHelper.executeQueryList(sql);
		for(Object[] arr:list) {
			Stroke s=new Stroke();
			s.setId(arr[0].toString());
			s.setGender(arr[1].toString());
			s.setAge(arr[2].toString());
			s.setHypertension(Integer.parseInt(arr[3].toString()));
			s.setHeart_disease(Integer.parseInt(arr[4].toString()));
			s.setEver_married(arr[5].toString());
			s.setWork_type(arr[6].toString());
			s.setResidence_type(arr[7].toString());
			s.setAvg_glucose_level(arr[8].toString());
			s.setBmi(arr[9].toString());
			s.setSmoking_status(arr[10].toString());
			s.setStroke(Integer.parseInt(arr[11].toString()));
			strlist.add(s);
		}
		return strlist;
	}
	

	
}
