package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bayesian.Bayesian;

@SuppressWarnings("serial")
public class UserFrm extends JFrame implements ActionListener {
	private JLabel[] label=new JLabel[11];
	private JTextField[] text=new JTextField[4];
	@SuppressWarnings("rawtypes")
	private JComboBox[] jcb=new JComboBox[7];
	private JButton button;
	private JTextArea area;
	
	public static void main(String args[]) {//主方法，调用窗体构造方法
		new UserFrm();
	}
	
	public static boolean isNumeric(String str) {//判断输入的字符串是不是数字
		for(int i=0;i<str.length();i++) {
			if(!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}
	
	public static float toNumeric(String str) {//将指定字符串转化为数字
		return Float.parseFloat(str);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserFrm() {//窗体构造方法
		this.setTitle("健康信息检测");
		this.setBounds(700,100,500,750);
		this.getContentPane().setLayout(null);
		label[0]=new JLabel("性别：", SwingConstants.RIGHT);
		label[0].setBounds(50, 50, 150, 30);
		label[1]=new JLabel("年龄：", SwingConstants.RIGHT);
		label[1].setBounds(50, 90, 150, 30);
		label[2]=new JLabel("是否有高血压：", SwingConstants.RIGHT);
		label[2].setBounds(50, 130, 150, 30);
		label[3]=new JLabel("是否有心脏病：", SwingConstants.RIGHT);
		label[3].setBounds(50, 170, 150, 30);
		label[4]=new JLabel("是否结过婚：", SwingConstants.RIGHT);
		label[4].setBounds(50, 210, 150, 30);
		label[5]=new JLabel("工作类型：", SwingConstants.RIGHT);
		label[5].setBounds(50, 250, 150, 30);
		label[6]=new JLabel("住宅类型：", SwingConstants.RIGHT);
		label[6].setBounds(50, 290, 150, 30);
		label[7]=new JLabel("葡萄糖含量：", SwingConstants.RIGHT);
		label[7].setBounds(50, 330, 150, 30);
		label[8]=new JLabel("身高：", SwingConstants.RIGHT);
		label[8].setBounds(50, 370, 150, 30);
		label[9]=new JLabel("体重：", SwingConstants.RIGHT);
		label[9].setBounds(50, 410, 150, 30);
		label[9].setFont(new Font("宋体", Font.BOLD, 20));
		label[10]=new JLabel("是否吸烟：", SwingConstants.RIGHT);
		label[10].setBounds(50, 450, 150, 30);
		for(int i=0;i<11;i++) {
			label[i].setFont(new Font("宋体", Font.BOLD, 20));
			this.getContentPane().add(label[i]);
		}
		
		String[] t={"男","女"};
		jcb[0]=new JComboBox(t);
		jcb[0].setBounds(200, 50, 200,30);
		jcb[0].setFont(new Font("宋体", Font.BOLD, 20));
		this.getContentPane().add(jcb[0]);
		text[0]=new JTextField();
		text[0].setBounds(200, 90, 200, 30);
		text[0].setFont(new Font("宋体", Font.BOLD, 20));
		this.getContentPane().add(text[0]);
		String[] t1={"0","1"};
		jcb[1]=new JComboBox(t1);
		jcb[1].setBounds(200, 130, 200,30);
		jcb[1].setFont(new Font("Arial", Font.BOLD, 20));
		this.getContentPane().add(jcb[1]);
		jcb[2]=new JComboBox(t1);
		jcb[2].setBounds(200, 170, 200,30);
		jcb[2].setFont(new Font("Arial", Font.BOLD, 20));
		this.getContentPane().add(jcb[2]);
		String[] t2={"是","否"};
		jcb[3]=new JComboBox(t2);
		jcb[3].setBounds(200, 210, 200,30);
		jcb[3].setFont(new Font("宋体", Font.BOLD, 20));
		this.getContentPane().add(jcb[3]);
		String[] t3={"private","self_employed","govt_job"};
		jcb[4]=new JComboBox(t3);
		jcb[4].setBounds(200, 250, 200,30);
		jcb[4].setFont(new Font("Arial", Font.BOLD, 20));
		this.getContentPane().add(jcb[4]);
		String[] t4={"urban","rural"};
		jcb[5]=new JComboBox(t4);
		jcb[5].setBounds(200, 290, 200,30);
		jcb[5].setFont(new Font("Arial", Font.BOLD, 20));
		this.getContentPane().add(jcb[5]);
		text[1]=new JTextField();
		text[1].setBounds(200, 330, 200, 30);
		text[1].setFont(new Font("宋体", Font.BOLD, 20));
		this.getContentPane().add(text[1]);
		text[2]=new JTextField();
		text[2].setBounds(200, 370, 200, 30);
		text[2].setFont(new Font("宋体", Font.BOLD, 20));
		text[2].setText("单位:cm");
		text[2].setForeground(Color.gray);
		text[2].addFocusListener(new FocusListener() {	            
            public void focusGained(FocusEvent e) {
        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (text[2].getText().equals("单位:cm")){
                	text[2].setText("");     //将提示文字清空
                	text[2].setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }
 
            public void focusLost(FocusEvent e) {
                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (text[2].getText().equals("")){
                	text[2].setForeground(Color.gray); //将提示文字设置为灰色
                	text[2].setText("单位:cm");     //显示提示文字
                }
            }
 
        });
		this.getContentPane().add(text[2]);
		text[3]=new JTextField();
		text[3].setBounds(200, 410, 200, 30);
		text[3].setFont(new Font("宋体", Font.BOLD, 20));
		text[3].setText("单位:kg");
		text[3].setForeground(Color.gray);
		text[3].addFocusListener(new FocusListener() {	            
            public void focusGained(FocusEvent e) {
        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (text[3].getText().equals("单位:kg")){
                	text[3].setText("");     //将提示文字清空
                	text[3].setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }
 
            public void focusLost(FocusEvent e) {
                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (text[3].getText().equals("")){
                	text[3].setForeground(Color.gray); //将提示文字设置为灰色
                	text[3].setText("单位:kg");     //显示提示文字
                }
            }
 
        });
		getContentPane().add(text[3]);
		String[] t5={"often","formerly","occasionally","never"};
		jcb[6]=new JComboBox(t5);
		jcb[6].setBounds(200, 450, 200,30);
		jcb[6].setFont(new Font("Arial", Font.BOLD, 20));
		this.getContentPane().add(jcb[6]);
				
		button=new JButton("结果查询");
		button.setBounds(150, 500, 200, 40);
		button.addActionListener(this);
		this.getContentPane().add(button);
		
		area=new JTextArea();
		area.setBounds(50, 570, 400, 120);
		area.setLineWrap(true);
		area.setEditable(false);
		area.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.getContentPane().add(area);
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {//按钮监听函数，用于开始算法逻辑
		if(e.getSource()==button) {
			String[] strs=new String[11];
			for(int i=0;i<strs.length;i++)
				strs[i]=null;
			
			strs[0]=(String)jcb[0].getSelectedItem();
			
			if("".equals(text[0].getText())) {
				JOptionPane.showMessageDialog(null, "请输入年龄", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(!isNumeric(text[0].getText())) {
				JOptionPane.showMessageDialog(null, "年龄输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				strs[1]=text[0].getText();
			}
			
			strs[2]=(String)jcb[1].getSelectedItem();
			strs[3]=(String)jcb[2].getSelectedItem();
			strs[4]=(String)jcb[3].getSelectedItem();
			strs[5]=(String)jcb[4].getSelectedItem();
			strs[6]=(String)jcb[5].getSelectedItem();
			
			if("".equals(text[1].getText())) {
				JOptionPane.showMessageDialog(null, "请输入平均葡萄糖水平", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(!isNumeric(text[1].getText())) {
				JOptionPane.showMessageDialog(null, "平均葡萄糖水平输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				strs[7]=text[1].getText();
			}
			
			if("".equals(text[2].getText()) || "单位:cm".equals(text[2].getText())) {
				JOptionPane.showMessageDialog(null, "请输入身高", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(!isNumeric(text[2].getText())) {
				JOptionPane.showMessageDialog(null, "身高输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(toNumeric(text[2].getText())>250) {
				JOptionPane.showMessageDialog(null, "身高输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				strs[8]=text[2].getText();
			}
			
			if("".equals(text[3].getText()) || "单位:kg".equals(text[3].getText())) {
				JOptionPane.showMessageDialog(null, "请输入体重", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(!isNumeric(text[3].getText())) {
				JOptionPane.showMessageDialog(null, "体重输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(toNumeric(text[3].getText())>100) {
				JOptionPane.showMessageDialog(null, "体重输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				strs[9]=text[3].getText();
			}
			
			double s=Float.parseFloat(strs[9])/Math.pow(Float.parseFloat(strs[8])/100,2);
			strs[10]=(String)jcb[6].getSelectedItem();
			area.setFont(new Font("宋体", Font.BOLD, 16));
			area.setText("这是结果");
			area.append("\n");
			List<ArrayList<Object>> result=Bayesian.predict_kind("gender:"+strs[0],"age:"+strs[1],"hypertension:"+strs[2],"heart_disease:"+strs[3],"ever_married:"
						+strs[4],"work_type:"+strs[5],"Residence_type:"+strs[6],"avg_glucose_level:"+strs[7],"bmi:"+s,"smoking_status:"+strs[10]);
			System.out.println("gender:"+strs[0]+",age:"+strs[1]+",hypertension:"+strs[2]+",heart_disease:"+strs[3]+",ever_married:"
						+strs[4]+",work_type:"+strs[5]+",Residence_type:"+strs[6]+",avg_glucose_level:"+strs[7]+",bmi:"+s+",smoking_status:"+strs[10]);
			for(ArrayList<Object> p:result) {
				area.append("后验概率:");
				area.append(p.get(0).toString());
				area.append("====>");
				area.append(p.get(1).toString());
				area.append("\n");
			}
			area.append("预测结果："+Bayesian.predict_result("gender:"+strs[0],"age:"+strs[1],"hypertension:"+strs[2],"heart_disease:"+strs[3],"ever_married:"
						+strs[4],"work_type:"+strs[5],"Residence_type:"+strs[6],"avg_glucose_level:"+strs[7],"bmi:"+s,"smoking_status:"+strs[10]));
			
		}
	}
}
