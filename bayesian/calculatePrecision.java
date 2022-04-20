package aaa;

import java.util.List;

public class calculatePrecision {//使用留出法评估贝叶斯模型
	public static void main(String[] args) {
		int DataNum = PersonDao.queryPersonsNum();
		//将数据集按比例7:3随机分成训练集和测试集
		int trainNum = (int) (DataNum*0.7);
		String sql1 = "CREATE TABLE trainData AS (SELECT * FROM stroke01 ORDER BY RAND() LIMIT "+trainNum+")";
		String sql2 = "CREATE TABLE testData AS (SELECT * FROM stroke01 WHERE id NOT IN (SELECT id FROM trainData));";
		DBHelper.executeUpdate(sql1);
		DBHelper.executeUpdate(sql2);
		List<Object[]> testData = DBHelper.executeQueryList("select * from testData");
		System.out.println(testData.size());
		double count=0;
		for(Object[] data:testData) {
			
			String predResult = Bayesian.predict(
					"gender:" + data[1],"age:" + data[2],"hypertension:" + data[3]
					,"heart_disease:" + data[4],"ever_married:" + data[5],"work_type:" + data[6]
					,"Residence_type:" + data[7],"avg_glucose_level:" + data[8],"bmi:" + data[9]
					,"smoking_status:" + data[10]);
			System.out.println("实际结果："+"stroke="+data[11]);
			System.out.println("预测结果："+predResult);
			System.out.println("=========================");
			if((Integer.parseInt(data[11].toString())==1&&predResult.equals("stroke=1"))||
					(Integer.parseInt(data[11].toString())==0&&predResult.equals("stroke=0"))) {
				count++;
			}
		}
		System.out.println("预测准确率："+count/(testData.size()));
	}
}
