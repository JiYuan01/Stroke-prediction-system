package aaa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bayesian {
	public static String[] conAttribute= new String[]{"age","avg_glucose_level","bmi"};//����������
	public static String predictAttribute = "stroke";//Ԥ������
	public static String [] predictAttributeKind = new String[]{"1","0"};//Ԥ���������
	public static String tableName="trainData";//���ݿ�������
	public static String split = ":";
	
	public static String predict(String... params) {//�������n���ֶκͶ�Ӧ���Խ���Ԥ��
		List<ArrayList<Object>> predict_probs = new ArrayList<ArrayList<Object>>();
		//����һ����ά�б����������������Ӧ����Ȼ����*�������
		Double[] probs = new Double[predictAttributeKind.length];
		for(int i=0;i<probs.length;i++) {//����Բ�ͬ��������Ȼ����
			probs[i]=1.0;
			for(int j=0;j<params.length;j++) {//�ж��Ƿ�����������
				boolean flag = false;
				if(conAttribute.length>0) {
					for(int k=0;k<conAttribute.length;k++) {
						if(params[j].contains(conAttribute[k])) {
							flag=true;
						}
					}
				}
				if(flag==true) {//�����������Ը���
					probs[i]=probs[i]*calculateConProb(params[j], predictAttribute+split+predictAttributeKind[i]);
				}
				else {//������ɢ���Ը���
					probs[i]=probs[i]*calculateDisProb(params[j], predictAttribute+split+predictAttributeKind[i]);
				}
			}
			probs[i]=probs[i]*calculateProb(predictAttribute,predictAttributeKind[i]); //����Ӧ���������
			ArrayList<Object> p = new ArrayList<Object>();
			p.add(predictAttribute+"="+predictAttributeKind[i]);
			p.add(probs[i]); 
			predict_probs.add(p); //���б���������ֵ�����Ӧ��(��Ȼ����*�������)ֵ
		}
		for(ArrayList<Object> p:predict_probs) {//������ֵ�����Ӧ��(��Ȼ����*�������)ֵ
			System.out.println(p);
		}
		double max_prob=0.0;
		String predict_kind=null;
		for(ArrayList<Object> p:predict_probs) {//��������(��Ȼ����*�������)�����Ǹ������Ϊ���Ԥ������
			if((double) p.get(1)>max_prob) {
				max_prob=(double)p.get(1);
				predict_kind=(String) p.get(0);
			}
		}
		return predict_kind;
	}

	//���ñ�Ҷ˹��ʽP(A|B)=P(AB)|P(B)������ɢ���Եĸ���
	public static double calculateDisProb(String factor1,String factor2) {//��ɢ���Ը���
		String a[] = factor1.split(split);
		String b[] = factor2.split(split);
		String sql1 = "select count(*) from "+tableName+" where "+a[0]+"='"+a[1]+"' and "+b[0]+"='"+b[1]+"'";
		String sql2 = "select count(*) from "+tableName+" where "+b[0]+"='"+b[1]+"'";
		
		int count1=0,count2=0;
		ResultSet rs1 = DBHelper.executeQuery(sql1);
		ResultSet rs2 = DBHelper.executeQuery(sql2);
		try {
			if(rs1.next()) {
				count1=rs1.getInt(1);//����P(AB)
			}
			rs1.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(rs2.next()) {
				count2=rs2.getInt(1);//����P(B)
			}
			rs2.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		double prob = (count1+0.0)/(count2+0.0);
		return prob;
	}

	//������̬�ֲ������������Եĸ���
	public static Double calculateConProb(String factor1,String factor2) {//�������Ը���
		String a[] = factor1.split(split);
		String b[] = factor2.split(split);
		double avg=0,vari=0;
		//����ƽ��ֵ
		String sql1 = "SELECT AVG("+a[0]+") FROM "+tableName+" WHERE "+b[0]+"='"+b[1]+"'";
		ResultSet rs1 = DBHelper.executeQuery(sql1);
		try {
			if(rs1.next()) {
				avg=rs1.getDouble(1);
			}
			rs1.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		//���㷽��
		String sql2 = "SELECT AVG(POW(("+a[0]+"-"+avg+"),2)) FROM "+tableName+" WHERE "+b[0]+"='"+b[1]+"'";
		ResultSet rs2 = DBHelper.executeQuery(sql2);
		try {
			if(rs2.next()) {
				vari=rs2.getDouble(1);
			}
			rs2.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		//������̬�ֲ��������
		double prob = 1/(Math.sqrt(2*Math.PI*vari))*Math.pow(Math.E,-Math.pow(Double.parseDouble(a[1])-avg, 2)/(2*vari));	
		return prob;
	}

	//����ĳ�����Ե�ֵ���ָ���
	public static double calculateProb(String kind,String value) {
		double prob = 0.0;
		String sql1 = "SELECT COUNT(*)/(SELECT COUNT(*) FROM "+tableName+") FROM "+tableName+" WHERE "+kind+"='"+value+"'";
		ResultSet rs = DBHelper.executeQuery(sql1);
		try {
			if(rs.next()) {
				prob=rs.getDouble(1);
			}
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return prob;
	}
	public static void main(String[] args) {
		System.out.println("Ԥ����Ϊ��"+predict("gender:Female","age:60",
					"hypertension:1","heart_disease:1","ever_married:1",
					"work_type:Private","Residence_type:Urban","avg_glucose_level:250",
					"bmi:22.5","smoking_status:often"));

	}
}

