package apriori;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class Apriori{

	public static int times=0;   //迭代次数
	private static double MIN_SUPPORT = 0.1; //默认最小支持度
	private static double MIN_CONFIDENCE = 0.7; //默认最小置信度
	private static boolean endTag = false;//循环状态，迭代标识
	static List<List<String>> record = new ArrayList<List<String>>();//初始数据集
	public List<List<String>> frequentItemset=new ArrayList<>();//存储所有的频繁项集
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static List<Mymap> map = new ArrayList();//存放频繁项集和对应的支持度
   
	public Apriori(double MIN_SUPPORT,double MIN_CONFIDENCE) {
		PreDao dao = new PreDao();//预处理操作类
		if(!dao.isPre()) {  //未经过预处理的进行预处理
			dao.deleteMissingValue();//删除缺失数据
			dao.deleteOutlier();//删除离群点
			dao.standardGender();//规范化性别为0或1,0为男,1为女
			dao.standardResidence(); //规范化住处为0或1,1为城镇，0为乡村
		}
		if(!dao.isDiscretization()) {  //未经过离散化的进行离散化
			dao.discretizationAge();
			dao.discretizationAvg_glucose_level();
			dao.discretizationBMI();
		}
		Apriori.MIN_CONFIDENCE = MIN_CONFIDENCE;
		Apriori.MIN_SUPPORT = MIN_SUPPORT;
	}
	
	public Apriori() {
		PreDao dao = new PreDao();//预处理操作类
		if(!dao.isPre()) {  //未经过预处理的进行预处理
			dao.deleteMissingValue();//删除缺失数据
			dao.deleteOutlier();//删除离群点
			dao.standardGender();//规范化性别为0或1,0为男,1为女
			dao.standardResidence(); //规范化住处为0或1,1为城镇，0为乡村
		}
		if(!dao.isDiscretization()) {  //未经过离散化的进行离散化
			dao.discretizationAge();
			dao.discretizationAvg_glucose_level();
			dao.discretizationBMI();
		}
	}
	
    //数据库中读取数据
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

    //获得C1候选集
    private static List<List<String>> findFirstCandidate() {
        List<List<String>> tableList = new ArrayList<List<String>>();
        HashSet<String> hs  = new HashSet<String>();//新建一个hash表，存放所有的不同的数据内容
        for (int i = 0; i<record.size(); i++) //遍历所有的数据集，找出所有的不同的数据存放到hs中
            for(int j=0;j<record.get(i).size();j++)
                hs.add(record.get(i).get(j));          
        Iterator<String> itr = hs.iterator();//迭代器，让hs迭代
        while(itr.hasNext()){
            List<String> tempList = new ArrayList<String>();
            String Item = (String) itr.next();
            tempList.add(Item);   //将每一种数据存放到一个List<String>中
            tableList.add(tempList);//所有的list<String>存放到一个大的list中
        }
        return tableList;//返回C1的候选集
    }
    
    /**
     **从当前频繁项集自连接求下一次候选集
     */
    @SuppressWarnings("unchecked")
	private static List<List<String>> getNextCandidate(List<List<String>> FrequentItemset) {
        List<List<String>> nextCandidateItemset = new ArrayList<List<String>>();
        for (int i=0; i<FrequentItemset.size(); i++){ //扫描当前频繁集的所有项目
            HashSet<String> hsSet = new HashSet<String>();
            HashSet<String> hsSettemp = new HashSet<String>();
            for (int k=0; k< FrequentItemset.get(i).size(); k++)//扫描频繁集第i行每个元素
                hsSet.add(FrequentItemset.get(i).get(k));//hsSet中加入频繁集第i行
            int hsLength_before = hsSet.size();//hsSet添加之后行数据之前的长度
            hsSettemp=(HashSet<String>) hsSet.clone();//浅复制了hsSet
            //频繁集第i行与第j行(j>i)连接   每次添加且只添加一个元素组成新的一行
            for(int h=i+1; h<FrequentItemset.size(); h++){ //从当前第i行的下一行开始
                hsSet=(HashSet<String>) hsSettemp.clone();//每次做连接的hasSet保持不变，就是第i行
                for(int j=0; j< FrequentItemset.get(h).size();j++)//遍历第h行
                    hsSet.add(FrequentItemset.get(h).get(j));//把第h行加入hsSet
                int hsLength_after = hsSet.size();//加入第h行后的hsSet长度
                //如果heSet加入数据前后不相等，表示添加了1个新的元素    同时判断其不是候选集中已经存在的一项
                if(hsLength_before+1 == hsLength_after && isnotHave(hsSet,nextCandidateItemset)){
                    Iterator<String> itr = hsSet.iterator();//迭代hsSet
                    List<String> tempList = new ArrayList<String>();
                    while(itr.hasNext()){//把这一个hsSet的结果写入tempList中
                        String Item = (String) itr.next();
                        tempList.add(Item);
                    }
                    nextCandidateItemset.add(tempList);//把所有tempList写入nextCandidateItemset
                }
            }
        }
        return nextCandidateItemset;
    }

    /**
     * 判断新添加元素形成的候选集是否在新的候选集中
     */
    private static boolean isnotHave(HashSet<String> hsSet, List<List<String>> nextCandidateItemset) {//判断hsset是不是candidateitemset中的一项
        List<String> tempList = new ArrayList<String>();
        Iterator<String> itr = hsSet.iterator();
        while(itr.hasNext()){//将hsSet转换为List<String>,迭代所有hsSet中的项
            String Item = (String) itr.next();
            tempList.add(Item);
        }
        for(int i=0; i<nextCandidateItemset.size();i++)//遍历candidateitemset，看其中是否有和templist相同的一项
            if(tempList.equals(nextCandidateItemset.get(i)))
                return false;
        return true;
    }

    /**
     * 由Ck候选集剪枝得到Lk项频繁集
     */
    private static List<List<String>> getSupprotedItemset(List<List<String>> CandidateItemset) { //对所有的候选集支持度计数
        boolean end = true;//是否结束频繁集标志
        List<List<String>> supportedItemset = new ArrayList<List<String>>();
        for(int i = 0; i < CandidateItemset.size(); i++){ //逐行扫描候选集
            int count = countFrequent1(CandidateItemset.get(i));//统计第i行的出现次数
            //因为是int乘法运算，避免取整情况,所以record-1
            if (count >= MIN_SUPPORT * (record.size()-1)){ //达到最小支持度的项（频繁项）
                supportedItemset.add(CandidateItemset.get(i));
                map.add(new Mymap(CandidateItemset.get(i),count));//存储当前频繁项集以及它的支持度计数
                end = false;//存在达到最小支持度的项
            }
        }
        endTag = end;//存在频繁项集则不会结束  如果end等于true 则无满足支持度的times项集 结束连接
        return supportedItemset;
    }

    /**
     * 统计record中出现list集合的个数
     */
    private static int countFrequent1(List<String> list) {//遍历所有数据集record，对每个候选集进行支持度计数
        int count =0;
        for(int i=0;i<record.size();i++){//从record的第一行开始遍历        
            boolean flag=true;
            for(int j=0;j<list.size();j++){
                String t=list.get(j);//list中的某个元素
                if(!record.get(i).contains(t)){//如果record中的某行数据集没有包含list中的任意元素           
                    flag = false;
                    break;//退出这一行，去record的下一行找
                }
            }
            if(flag)//如果record中的某行数据集包含list中的所有元素
            	count++;//支持度加一
        }
        return count;//返回支持度计数
    }
    
    //将新找到的频繁集加入所有频繁集的集合
    public boolean AddToFrequenceItem(List<List<String>> fre){
        for(int i=0;i<fre.size();i++)
        	frequentItemset.add(fre.get(i));
        return true;
    }
    
    public void useApriori(){ //apriori算法挖掘频繁集
        //获取C1项集
        List<List<String>> CandidateItemset = findFirstCandidate();
        //获取L1项集
        List<List<String>> FrequentItemset = getSupprotedItemset(CandidateItemset);
        AddToFrequenceItem(FrequentItemset);//添加到所有的频繁项集中
        //迭代过程
        times=2;
        while(endTag!=true){
            //连接操作，获取候选times项集
            List<List<String>> nextCandidateItemset = getNextCandidate(FrequentItemset);
            //计数操作，由候选k项集选择出频繁k项集
            List<List<String>> nextFrequentItemset = getSupprotedItemset(nextCandidateItemset);
            AddToFrequenceItem(nextFrequentItemset);//添加到所有的频繁项集中
            //下一次循环初值，当前频繁集
            FrequentItemset = nextFrequentItemset;
            times++;//迭代次数加一
        }
    }

    public List<List<String>> bubbleSort(){//根据关联规则置信度由大到小排序
        List<List<String>> rule = AssociationRulesMining(); //关联规则
    	for(int i=0;i<rule.size()-1;i++){//控制比较轮次，一共 n-1 趟
             for(int j=0;j<rule.size()-1-i;j++){//控制两个挨着的元素进行比较
                 if(Double.parseDouble(rule.get(j).get(1).substring(4))<Double.parseDouble(rule.get(j+1).get(1).substring(4))){
                 	List<String> temp = rule.get(j);
                     rule.set(j,rule.get(j+1));
                     rule.set(j+1,temp);
                 }
             }
         }
    	return rule;
    }
    
    public List<List<String>> AssociationRulesMining(){//关联规则挖掘
    	List<List<String>> result = new ArrayList<>();
    	for(int i=0;i<frequentItemset.size();i++) { //遍历频繁集
            List<String> tem=frequentItemset.get(i);//tem是每个频繁集
            if(tem.size()>1) {//大于L1的才能产生关联规则
                List<String> temclone=new ArrayList<>(tem);
                List<List<String>> AllSubset = getSubSet(temclone);//得到频繁项集tem的所有子集
                for (int j = 0; j < AllSubset.size(); j++) {//遍历一个频繁项集的所有元素
                    List<String> s1 = AllSubset.get(j);//第j个元素
                    List<String> s2 = gets2set(tem, s1);//除去第j个元素的剩余元素
                    if(!isAssociationRules(s1, s2, tem).isEmpty())
                    	result.add(isAssociationRules(s1, s2, tem));//判断是否关联规则，是就输出
                }
            }
        }
    	return result;
    }

    public static List<List<String>> getSubSet(List<String> set){ //得到set所有子集
        List<List<String>> result = new ArrayList<>(); //用来存放子集的集合，如{{},{1},{2},{1,2}}
        int length = set.size(); //set长度
        int num = length==0 ? 0 : 1<<(length); //子集个数，2的n次方，若集合set为空，num为0；若集合set有4个元素，那么num为16.
        //从1到2^n-2（[00...01]到[11...10]）不包括空集和原集合
        for(int i = 1; i < num-1; i++){
            List<String> subSet = new ArrayList<>();//一个子集
            int index = i;//本次第i次循环的2进制表示 每次循环把为1的项取出。比如i=6时，index=110，此时取出set的第二项，第三项
            for(int j = 0; j < length; j++){//遍历原集合的所有元素
                if((index & 1) == 1) //每次判断index最低位是否为1，为1则把原集合的第j个元素放到subset中
                    subSet.add(set.get(j));
                index >>= 1; //右移一位
            }
            result.add(subSet);//把子集存储起来
        }
        return result;
    }
    
    //判断是否为关联规则,并返回关联规则和置信度集合
    public static List<String> isAssociationRules(List<String> s1,List<String> s2,List<String> tem){
        List<String> result = new ArrayList<>();
    	double confidence=0;
        int counts1;//s1的支持度计数，分母
        int countTem;//tem的支持度，也就是s1Us2的支持度，分子
        if(s1.size()!=0&&s1!=null&&tem.size()!=0&&tem!=null){
            counts1= getCount(s1);
            countTem=getCount(tem);
            confidence=countTem*1.0/counts1;
            if(confidence>=MIN_CONFIDENCE){//满足最小置信度，可以作为关联规则
            	if(s2.toString().contains("[Stroke=1]")) {
            		result.add("关联规则："+s1.toString()+"=>>"+s2.toString());
            		result.add("置信度："+confidence);
            	}
            }
        }
        return result;
    }

    public static int getCount(List<String> in){//根据频繁项集得到其支持度计数
        int rt=0;
        for(int i=0;i<map.size();i++) {  //遍历所有频繁集
            Mymap tem=map.get(i);
            if(tem.isListEqual(in)) { //如果频繁项集中包含输入的in
                rt = tem.getcount();//输出其支持度
                return rt;
            }
        }
        return rt;
    }

    public static List<String> gets2set(List<String> tem, List<String> s1){//计算tem减去s1后的集合即为s2
        List<String> result=new ArrayList<>();
        for(int i=0;i<tem.size();i++){//去掉s1中的所有元素的剩余(元素)
            String t=tem.get(i);
            if(!s1.contains(t))
                result.add(t);
        }
        return  result;
    }
 
    public static void ShowData(List<List<String>> CandidateItemset){//显示出candidateitem中的所有的项集，只是写了这个方法，并没有使用到
        for(int i=0;i<CandidateItemset.size();i++){
            List<String> list = new ArrayList<String>(CandidateItemset.get(i));
            for(int j=0;j<list.size();j++)
                System.out.print(list.get(j)+" ");
            System.out.println();
        }
    }
    
}
