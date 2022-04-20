package apriori;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class Apriori{

	public static int times=0;   //��������
	private static double MIN_SUPPORT = 0.1; //Ĭ����С֧�ֶ�
	private static double MIN_CONFIDENCE = 0.7; //Ĭ����С���Ŷ�
	private static boolean endTag = false;//ѭ��״̬��������ʶ
	static List<List<String>> record = new ArrayList<List<String>>();//��ʼ���ݼ�
	public List<List<String>> frequentItemset=new ArrayList<>();//�洢���е�Ƶ���
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static List<Mymap> map = new ArrayList();//���Ƶ����Ͷ�Ӧ��֧�ֶ�
   
	public Apriori(double MIN_SUPPORT,double MIN_CONFIDENCE) {
		PreDao dao = new PreDao();//Ԥ���������
		if(!dao.isPre()) {  //δ����Ԥ����Ľ���Ԥ����
			dao.deleteMissingValue();//ɾ��ȱʧ����
			dao.deleteOutlier();//ɾ����Ⱥ��
			dao.standardGender();//�淶���Ա�Ϊ0��1,0Ϊ��,1ΪŮ
			dao.standardResidence(); //�淶��ס��Ϊ0��1,1Ϊ����0Ϊ���
		}
		if(!dao.isDiscretization()) {  //δ������ɢ���Ľ�����ɢ��
			dao.discretizationAge();
			dao.discretizationAvg_glucose_level();
			dao.discretizationBMI();
		}
		Apriori.MIN_CONFIDENCE = MIN_CONFIDENCE;
		Apriori.MIN_SUPPORT = MIN_SUPPORT;
	}
	
	public Apriori() {
		PreDao dao = new PreDao();//Ԥ���������
		if(!dao.isPre()) {  //δ����Ԥ����Ľ���Ԥ����
			dao.deleteMissingValue();//ɾ��ȱʧ����
			dao.deleteOutlier();//ɾ����Ⱥ��
			dao.standardGender();//�淶���Ա�Ϊ0��1,0Ϊ��,1ΪŮ
			dao.standardResidence(); //�淶��ס��Ϊ0��1,1Ϊ����0Ϊ���
		}
		if(!dao.isDiscretization()) {  //δ������ɢ���Ľ�����ɢ��
			dao.discretizationAge();
			dao.discretizationAvg_glucose_level();
			dao.discretizationBMI();
		}
	}
	
    //���ݿ��ж�ȡ����
    public void getRecord() {
    	List<List<String>> dataList = new ArrayList<List<String>>();
        StrokeDao dao = new StrokeDao();
        dao.queryAllNewStroke();
        List<Stroke> allStroke = dao.queryAllNewStroke();
		for(Stroke s: allStroke) {			
			List<String> temp = new ArrayList<String>();		
			temp.add("Gender="+s.getGender());
			temp.add("Age="+s.getAge());
			temp.add("Hypertension="+String.valueOf(s.getHypertension()));
			temp.add("Heart_disease="+String.valueOf(s.getHeart_disease()));
			temp.add("Ever_married="+s.getEver_married());
			temp.add("Work_type="+s.getWork_type());
			temp.add("Residence_type="+s.getResidence_type());
			temp.add("Avg_glucose_level="+s.getAvg_glucose_level());
			temp.add("bmi="+s.getBmi());
			temp.add("Smoking_status="+s.getSmoking_status());
			temp.add("Stroke="+String.valueOf(s.getStroke()));
			dataList.add(temp);	
		}
		record = dataList;
    }

    //���C1��ѡ��
    private static List<List<String>> findFirstCandidate() {
        List<List<String>> tableList = new ArrayList<List<String>>();
        HashSet<String> hs  = new HashSet<String>();//�½�һ��hash��������еĲ�ͬ����������
        for (int i = 0; i<record.size(); i++) //�������е����ݼ����ҳ����еĲ�ͬ�����ݴ�ŵ�hs��
            for(int j=0;j<record.get(i).size();j++)
                hs.add(record.get(i).get(j));          
        Iterator<String> itr = hs.iterator();//����������hs����
        while(itr.hasNext()){
            List<String> tempList = new ArrayList<String>();
            String Item = (String) itr.next();
            tempList.add(Item);   //��ÿһ�����ݴ�ŵ�һ��List<String>��
            tableList.add(tempList);//���е�list<String>��ŵ�һ�����list��
        }
        return tableList;//����C1�ĺ�ѡ��
    }
    
    /**
     **�ӵ�ǰƵ�������������һ�κ�ѡ��
     */
    @SuppressWarnings("unchecked")
	private static List<List<String>> getNextCandidate(List<List<String>> FrequentItemset) {
        List<List<String>> nextCandidateItemset = new ArrayList<List<String>>();
        for (int i=0; i<FrequentItemset.size(); i++){ //ɨ�赱ǰƵ������������Ŀ
            HashSet<String> hsSet = new HashSet<String>();
            HashSet<String> hsSettemp = new HashSet<String>();
            for (int k=0; k< FrequentItemset.get(i).size(); k++)//ɨ��Ƶ������i��ÿ��Ԫ��
                hsSet.add(FrequentItemset.get(i).get(k));//hsSet�м���Ƶ������i��
            int hsLength_before = hsSet.size();//hsSet���֮��������֮ǰ�ĳ���
            hsSettemp=(HashSet<String>) hsSet.clone();//ǳ������hsSet
            //Ƶ������i�����j��(j>i)����   ÿ�������ֻ���һ��Ԫ������µ�һ��
            for(int h=i+1; h<FrequentItemset.size(); h++){ //�ӵ�ǰ��i�е���һ�п�ʼ
                hsSet=(HashSet<String>) hsSettemp.clone();//ÿ�������ӵ�hasSet���ֲ��䣬���ǵ�i��
                for(int j=0; j< FrequentItemset.get(h).size();j++)//������h��
                    hsSet.add(FrequentItemset.get(h).get(j));//�ѵ�h�м���hsSet
                int hsLength_after = hsSet.size();//�����h�к��hsSet����
                //���heSet��������ǰ����ȣ���ʾ�����1���µ�Ԫ��    ͬʱ�ж��䲻�Ǻ�ѡ�����Ѿ����ڵ�һ��
                if(hsLength_before+1 == hsLength_after && isnotHave(hsSet,nextCandidateItemset)){
                    Iterator<String> itr = hsSet.iterator();//����hsSet
                    List<String> tempList = new ArrayList<String>();
                    while(itr.hasNext()){//����һ��hsSet�Ľ��д��tempList��
                        String Item = (String) itr.next();
                        tempList.add(Item);
                    }
                    nextCandidateItemset.add(tempList);//������tempListд��nextCandidateItemset
                }
            }
        }
        return nextCandidateItemset;
    }

    /**
     * �ж������Ԫ���γɵĺ�ѡ���Ƿ����µĺ�ѡ����
     */
    private static boolean isnotHave(HashSet<String> hsSet, List<List<String>> nextCandidateItemset) {//�ж�hsset�ǲ���candidateitemset�е�һ��
        List<String> tempList = new ArrayList<String>();
        Iterator<String> itr = hsSet.iterator();
        while(itr.hasNext()){//��hsSetת��ΪList<String>,��������hsSet�е���
            String Item = (String) itr.next();
            tempList.add(Item);
        }
        for(int i=0; i<nextCandidateItemset.size();i++)//����candidateitemset���������Ƿ��к�templist��ͬ��һ��
            if(tempList.equals(nextCandidateItemset.get(i)))
                return false;
        return true;
    }

    /**
     * ��Ck��ѡ����֦�õ�Lk��Ƶ����
     */
    private static List<List<String>> getSupprotedItemset(List<List<String>> CandidateItemset) { //�����еĺ�ѡ��֧�ֶȼ���
        boolean end = true;//�Ƿ����Ƶ������־
        List<List<String>> supportedItemset = new ArrayList<List<String>>();
        for(int i = 0; i < CandidateItemset.size(); i++){ //����ɨ���ѡ��
            int count = countFrequent1(CandidateItemset.get(i));//ͳ�Ƶ�i�еĳ��ִ���
            //��Ϊ��int�˷����㣬����ȡ�����,����record-1
            if (count >= MIN_SUPPORT * (record.size()-1)){ //�ﵽ��С֧�ֶȵ��Ƶ���
                supportedItemset.add(CandidateItemset.get(i));
                map.add(new Mymap(CandidateItemset.get(i),count));//�洢��ǰƵ����Լ�����֧�ֶȼ���
                end = false;//���ڴﵽ��С֧�ֶȵ���
            }
        }
        endTag = end;//����Ƶ����򲻻����  ���end����true ��������֧�ֶȵ�times� ��������
        return supportedItemset;
    }

    /**
     * ͳ��record�г���list���ϵĸ���
     */
    private static int countFrequent1(List<String> list) {//�����������ݼ�record����ÿ����ѡ������֧�ֶȼ���
        int count =0;
        for(int i=0;i<record.size();i++){//��record�ĵ�һ�п�ʼ����        
            boolean flag=true;
            for(int j=0;j<list.size();j++){
                String t=list.get(j);//list�е�ĳ��Ԫ��
                if(!record.get(i).contains(t)){//���record�е�ĳ�����ݼ�û�а���list�е�����Ԫ��           
                    flag = false;
                    break;//�˳���һ�У�ȥrecord����һ����
                }
            }
            if(flag)//���record�е�ĳ�����ݼ�����list�е�����Ԫ��
            	count++;//֧�ֶȼ�һ
        }
        return count;//����֧�ֶȼ���
    }
    
    //�����ҵ���Ƶ������������Ƶ�����ļ���
    public boolean AddToFrequenceItem(List<List<String>> fre){
        for(int i=0;i<fre.size();i++)
        	frequentItemset.add(fre.get(i));
        return true;
    }
    
    public void useApriori(){ //apriori�㷨�ھ�Ƶ����
        //��ȡC1�
        List<List<String>> CandidateItemset = findFirstCandidate();
        //��ȡL1�
        List<List<String>> FrequentItemset = getSupprotedItemset(CandidateItemset);
        AddToFrequenceItem(FrequentItemset);//��ӵ����е�Ƶ�����
        //��������
        times=2;
        while(endTag!=true){
            //���Ӳ�������ȡ��ѡtimes�
            List<List<String>> nextCandidateItemset = getNextCandidate(FrequentItemset);
            //�����������ɺ�ѡk�ѡ���Ƶ��k�
            List<List<String>> nextFrequentItemset = getSupprotedItemset(nextCandidateItemset);
            AddToFrequenceItem(nextFrequentItemset);//��ӵ����е�Ƶ�����
            //��һ��ѭ����ֵ����ǰƵ����
            FrequentItemset = nextFrequentItemset;
            times++;//����������һ
        }
    }

    public List<List<String>> bubbleSort(){//���ݹ����������Ŷ��ɴ�С����
        List<List<String>> rule = AssociationRulesMining(); //��������
    	for(int i=0;i<rule.size()-1;i++){//���ƱȽ��ִΣ�һ�� n-1 ��
             for(int j=0;j<rule.size()-1-i;j++){//�����������ŵ�Ԫ�ؽ��бȽ�
                 if(Double.parseDouble(rule.get(j).get(1).substring(4))<Double.parseDouble(rule.get(j+1).get(1).substring(4))){
                 	List<String> temp = rule.get(j);
                     rule.set(j,rule.get(j+1));
                     rule.set(j+1,temp);
                 }
             }
         }
    	return rule;
    }
    
    public List<List<String>> AssociationRulesMining(){//���������ھ�
    	List<List<String>> result = new ArrayList<>();
    	for(int i=0;i<frequentItemset.size();i++) { //����Ƶ����
            List<String> tem=frequentItemset.get(i);//tem��ÿ��Ƶ����
            if(tem.size()>1) {//����L1�Ĳ��ܲ�����������
                List<String> temclone=new ArrayList<>(tem);
                List<List<String>> AllSubset = getSubSet(temclone);//�õ�Ƶ���tem�������Ӽ�
                for (int j = 0; j < AllSubset.size(); j++) {//����һ��Ƶ���������Ԫ��
                    List<String> s1 = AllSubset.get(j);//��j��Ԫ��
                    List<String> s2 = gets2set(tem, s1);//��ȥ��j��Ԫ�ص�ʣ��Ԫ��
                    if(!isAssociationRules(s1, s2, tem).isEmpty())
                    	result.add(isAssociationRules(s1, s2, tem));//�ж��Ƿ���������Ǿ����
                }
            }
        }
    	return result;
    }

    public static List<List<String>> getSubSet(List<String> set){ //�õ�set�����Ӽ�
        List<List<String>> result = new ArrayList<>(); //��������Ӽ��ļ��ϣ���{{},{1},{2},{1,2}}
        int length = set.size(); //set����
        int num = length==0 ? 0 : 1<<(length); //�Ӽ�������2��n�η���������setΪ�գ�numΪ0��������set��4��Ԫ�أ���ônumΪ16.
        //��1��2^n-2��[00...01]��[11...10]���������ռ���ԭ����
        for(int i = 1; i < num-1; i++){
            List<String> subSet = new ArrayList<>();//һ���Ӽ�
            int index = i;//���ε�i��ѭ����2���Ʊ�ʾ ÿ��ѭ����Ϊ1����ȡ��������i=6ʱ��index=110����ʱȡ��set�ĵڶ��������
            for(int j = 0; j < length; j++){//����ԭ���ϵ�����Ԫ��
                if((index & 1) == 1) //ÿ���ж�index���λ�Ƿ�Ϊ1��Ϊ1���ԭ���ϵĵ�j��Ԫ�طŵ�subset��
                    subSet.add(set.get(j));
                index >>= 1; //����һλ
            }
            result.add(subSet);//���Ӽ��洢����
        }
        return result;
    }
    
    //�ж��Ƿ�Ϊ��������,�����ع�����������Ŷȼ���
    public static List<String> isAssociationRules(List<String> s1,List<String> s2,List<String> tem){
        List<String> result = new ArrayList<>();
    	double confidence=0;
        int counts1;//s1��֧�ֶȼ�������ĸ
        int countTem;//tem��֧�ֶȣ�Ҳ����s1Us2��֧�ֶȣ�����
        if(s1.size()!=0&&s1!=null&&tem.size()!=0&&tem!=null){
            counts1= getCount(s1);
            countTem=getCount(tem);
            confidence=countTem*1.0/counts1;
            if(confidence>=MIN_CONFIDENCE){//������С���Ŷȣ�������Ϊ��������
            	if(s2.toString().contains("[Stroke=1]")) {
            		result.add("��������"+s1.toString()+"=>>"+s2.toString());
            		result.add("���Ŷȣ�"+confidence);
            	}
            }
        }
        return result;
    }

    public static int getCount(List<String> in){//����Ƶ����õ���֧�ֶȼ���
        int rt=0;
        for(int i=0;i<map.size();i++) {  //��������Ƶ����
            Mymap tem=map.get(i);
            if(tem.isListEqual(in)) { //���Ƶ����а��������in
                rt = tem.getcount();//�����֧�ֶ�
                return rt;
            }
        }
        return rt;
    }

    public static List<String> gets2set(List<String> tem, List<String> s1){//����tem��ȥs1��ļ��ϼ�Ϊs2
        List<String> result=new ArrayList<>();
        for(int i=0;i<tem.size();i++){//ȥ��s1�е�����Ԫ�ص�ʣ��(Ԫ��)
            String t=tem.get(i);
            if(!s1.contains(t))
                result.add(t);
        }
        return  result;
    }
 
    public static void ShowData(List<List<String>> CandidateItemset){//��ʾ��candidateitem�е����е����ֻ��д�������������û��ʹ�õ�
        for(int i=0;i<CandidateItemset.size();i++){
            List<String> list = new ArrayList<String>(CandidateItemset.get(i));
            for(int j=0;j<list.size();j++)
                System.out.print(list.get(j)+" ");
            System.out.println();
        }
    }
    
}
