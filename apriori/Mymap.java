package apriori;
import java.util.LinkedList;
import java.util.List;

public class  Mymap{//�Զ����map�࣬һ��������һ��Ƶ����Լ���֧�ֶȼ���
    public List<String> li=new LinkedList<>();
    public int count;

    public Mymap(List<String> li,int count){//���캯��  �½�һ������
        this.li = li;
        this.count = count;
    }

    public int getcount(){//���صõ���ǰƵ�����֧�ֶȼ��� 
        return count;
    }

    public boolean isListEqual(List<String> in){//�жϴ����Ƶ����Ƿ�ͱ�Ƶ�����ͬ
        if(in.size()!=li.size())//���жϴ�С�Ƿ���ͬ
            return false;
        else 
            for(int i=0;i<in.size();i++)//���������Ƶ������ж��Ƿ�����Ԫ�ض������ڱ�Ƶ�����
                if(!li.contains(in.get(i)))
                    return false;    
        return true;//�������Ƶ�����С��ͬ��ͬʱ��Ƶ������������Ƶ���������Ԫ�أ����ʾ����Ƶ�������ȵģ�����Ϊ��
    }
}