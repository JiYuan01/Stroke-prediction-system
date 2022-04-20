package apriori;


public class PreDao {
	
	public boolean isPre(){ //�ж��Ƿ�Ԥ�����
		String sql="select Residence_type from stroke_data_new where Residence_type = 'urban' or Residence_type = 'rural'";
		if(SQLHelper.executeQueryList(sql).size() == 0)
			return true;
		else
			return false;
	}
	
	public boolean isDiscretization(){ //�ж��Ƿ���ɢ����
		String sql="select age from stroke_data_new where age like '%-%'";
		if(SQLHelper.executeQueryList(sql).size() == 0)
			return false;
		else
			return true;
	}
	
	public int deleteMissingValue(){ //ɾ��ȱʧ����
		String sql="delete from stroke_data_new where bmi = 'N/A' or smoking_status ='unknown'";
		return SQLHelper.executeUpdate(sql);
	}
	
	public int deleteOutlier(){ //ɾ����Ⱥ��
		String sql="delete from stroke_data_new where convert(float,bmi)>=70 or convert(float,age)<10 or convert(float,age)>90";
		return SQLHelper.executeUpdate(sql);
	}
	
	public int standardGender(){ //�淶���Ա�Ϊ0��1,1ΪŮ��0Ϊ��
		String sql="update stroke_data_new set gender = " + 
				   "(CASE when gender = 'Male' THEN '0' "+
				    "when gender = 'Female' THEN '1' "+
				    "when gender = '1' THEN '1' "+
				    "when gender = '0' THEN '0' END)";
		return SQLHelper.executeUpdate(sql);
	}
		
	public int standardResidence(){ //�淶��ס��Ϊ0��1,1Ϊ����0Ϊ���
		String sql="update stroke_data_new set Residence_type = " + 
				   "(CASE when Residence_type = 'urban' THEN '1' "+
				   "when Residence_type = 'rural' THEN '0' "+
				   "when Residence_type = '1' THEN '1' "+
				   "when Residence_type = '0' THEN '0' END)";
		return SQLHelper.executeUpdate(sql);
	}
	//ֻ�ڹ���aprioriʱ��ɢ������
	public int discretizationAge(){ //��ɢ������
		String sql="update stroke_data_new set age = " + 
				"(CASE when convert(float,age)>=10 and convert(float,age)<=29 THEN '10-29' " + 
				"when convert(float,age)>=30 and convert(float,age)<=49 THEN '30-49' " + 
				"when convert(float,age)>=50 and convert(float,age)<=69 THEN '50-69' " + 
				"when convert(float,age)>=70 and convert(float,age)<=90 THEN '70-90' END)";
		return SQLHelper.executeUpdate(sql);
	}
	
	public int discretizationAvg_glucose_level(){ //��ɢ��ƽ��������ˮƽ
		String sql= "update stroke_data_new set avg_glucose_level = " + 
				"(CASE when convert(float,avg_glucose_level)<=100 THEN '0-100'" + 
				" when convert(float,avg_glucose_level) >=101 and convert(float,avg_glucose_level)<=200 THEN '101-200'" + 
				" when convert(float,avg_glucose_level) >=201 and convert(float,avg_glucose_level)<=300 THEN '201-300' END)"; 
		return SQLHelper.executeUpdate(sql);
	}
	
	public int discretizationBMI(){ //��ɢ��bmi
		String sql="update stroke_data_new set bmi = " + 
				"(CASE when convert(float,bmi)<=20 THEN 'low'" + 
				" when convert(float,bmi) >20 and convert(float,bmi)<=25 THEN 'normal'" + 
				" else 'high' END)";
		return SQLHelper.executeUpdate(sql);
	}
}
