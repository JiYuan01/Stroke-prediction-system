package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import dao.RecordDao;
import apriori.Apriori;
import bayesian.Bayesian;

@SuppressWarnings("serial")
public class ManagerFrm extends JFrame implements ActionListener {
	private JTabbedPane p;//主面板采用卡片布局
	private JTable table;
	private JButton[] button=new JButton[5];
	private JTextField text;
	private JTextField[] text1=new JTextField[4];
	private JLabel[] label=new JLabel[8];
	@SuppressWarnings("rawtypes")
	private JComboBox[] jcb=new JComboBox[15];
	private JTextArea[] area=new JTextArea[6];
	private JPanel pNorth,pSouth,panel1,panel2,panel3;
	private JPanel[] panel=new JPanel[10];
	private JSplitPane splitPane,splitPane1;
	public String[][] rows=null;
	public String[] cols1= {"id","gender","age","hypertension","heart_disease","ever_married","work_type","Residence_type","avg_glucose_level","bmi","smoking_status","stroke"};
	public String[] cols= {"编号","性别","年龄","高血压","心脏病","结过婚","工作类型","住宅类型","平均葡萄糖水平","身体质量指数","是否吸烟","中风"};	
	public RecordDao record=new RecordDao();
	
	public static void main(String args[]) {//主方法，调用窗体生成构造方法
		new ManagerFrm();
	}
	
	public static boolean isNumeric(String str) {//判断输入的字符串是不是数字
		for(int i=0;i<str.length();i++) {
			if(!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}
	
	public static float toNumeric(String str) {//将一个字符串转化为数字
		return Float.parseFloat(str);
	}
	
	public void initTable() {//初始化表格
		List<Object[]> list=record.queryRecord();
		rows=new String[list.size()][12];
		int r=0;
		for(Object[] o:list) {
			for(int i=0;i<12;i++)
				rows[r][i]=o[i].toString();
			r++;
		}
		table=new JTable(rows, cols) {//锁定第一列和最后一列不能编辑
			public boolean isCellEditable(int row, int column) { 
			 	if(column==0 || column==11)
			 		return false;
			 	return true;
			  }
		};
		table.setRowHeight(20);
		table.setPreferredScrollableViewportSize(new Dimension(800,600));
	}
	
	public void updateTable(String str) {//每次执行数据库修改操作后，需要更新表格
		List<Object[]> list=record.queryRecord(str);
		rows=new String[list.size()][12];
		int r=0;
		for(Object[] o:list) {
			for(int i=0;i<12;i++)
				rows[r][i]=o[i].toString();
			r++;
		}
		table=new JTable(rows, cols) {
			public boolean isCellEditable(int row, int column) { 
			 	if(column==0 || column==11)
			 		return false;
			 	return true;
			  }
		};
		table.setRowHeight(20);
		table.setPreferredScrollableViewportSize(new Dimension(800,600));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ManagerFrm() {//窗体构造方法
		this.setTitle("健康信息管理");
		this.setBounds(250,100,1450,800);
		text=new JTextField(5);
		text.setSize(60, 30);
		
//panel1内容，即信息管理面板内容
		String[] t={"","男","女"};
		jcb[0]=new JComboBox(t);
		jcb[0].setFont(new Font("宋体", Font.BOLD, 14));
		String[] t1={"","0","1"};
		jcb[1]=new JComboBox(t1);
		jcb[2]=new JComboBox(t1);
		jcb[7]=new JComboBox(t1);
		String[] t2={"","是","否"};
		jcb[3]=new JComboBox(t2);
		jcb[3].setFont(new Font("宋体", Font.BOLD, 14));
		String[] t3={"","private","self_employed","govt_job"};
		jcb[4]=new JComboBox(t3);
		jcb[4].setFont(new Font("Arial", Font.BOLD, 14));
		String[] t4={"","urban","rural"};
		jcb[5]=new JComboBox(t4);
		jcb[5].setFont(new Font("Arial", Font.BOLD, 14));
		String[] t5={"","often","formerly","occasionally","never"};
		jcb[6]=new JComboBox(t5);
		jcb[6].setFont(new Font("Arial", Font.BOLD, 14));
		button[0]=new JButton("查询");
		button[1]=new JButton("添加");
		button[2]=new JButton("删除");
		button[3]=new JButton("修改");
		button[4]=new JButton("恢复初始值");
		for(int i=0;i<5;i++) {
			button[i].setSize(100, 30);
			button[i].addActionListener(this);
		}
		pNorth=new JPanel();
		JLabel lb=new JLabel("编号:");
		lb.setFont(new Font("宋体", Font.BOLD, 14));
		pNorth.add(lb);
		pNorth.add(text);
		label[0]=new JLabel("性别:");
		label[1]=new JLabel("高血压:");
		label[2]=new JLabel("心脏病:");
		label[3]=new JLabel("结过婚:");
		label[4]=new JLabel("工作类型:");
		label[5]=new JLabel("住宅类型:");
		label[6]=new JLabel("是否吸烟:");
		label[7]=new JLabel("中风:");
		for(int i=0;i<8;i++){
			label[i].setSize(60, 30);
			label[i].setFont(new Font("宋体", Font.BOLD, 14));
			pNorth.add(label[i]);
			pNorth.add(jcb[i]);
		}
		pNorth.add(button[0]);
		pNorth.add(button[4]);
		pSouth=new JPanel();
		for(int i=1;i<4;i++)
			pSouth.add(button[i]);
		initTable();
		JScrollPane jsp=new JScrollPane(table);
		p=new JTabbedPane(JTabbedPane.TOP);
		panel1=new JPanel();
		panel1.setLayout(new BorderLayout());
		panel2=new JPanel();
		panel2.setLayout(new BorderLayout());
		panel3=new JPanel();
		panel3.setLayout(new BorderLayout());
		panel1.add(pNorth, BorderLayout.NORTH);
		panel1.add(jsp, BorderLayout.CENTER);
		panel1.add(pSouth, BorderLayout.SOUTH);
//panel2内容，即信息挖掘面板
		area[0]=new JTextArea();
		area[0].setSize(1200, 300);
		area[0].setLineWrap(true);
		area[1]=new JTextArea();
		area[1].setSize(300, 650);
		area[1].setLineWrap(true);
		area[2]=new JTextArea();
		area[2].setSize(900, 500);
		area[2].setLineWrap(true);
		area[0].setText("使用Apriori算法挖掘关联规则");
		area[0].setFont(new Font("宋体", Font.BOLD, 20));
		area[0].setEditable(false);
		area[1].setFont(new Font("Arial", Font.BOLD, 15));
		area[1].setEditable(false);
		area[2].setFont(new Font("宋体", Font.BOLD, 20));
		area[2].setEditable(false);
		for(int i=0;i<5;i++)
			panel[i]=new JPanel();
		panel[0].setLayout(null);
		JLabel lab=new JLabel("选项");
		lab.setBounds(120, 10, 100, 25);
		lab.setFont(new Font("宋体", Font.BOLD, 25));
		JLabel lab1=new JLabel("支持度:");
		lab1.setBounds(20, 70, 80, 20);
		lab1.setFont(new Font("宋体", Font.BOLD, 20));
		JTextField sup=new JTextField(15);
		sup.setBounds(100, 70, 150, 25);
		sup.setText("0.1");
		sup.setForeground(Color.gray);
		sup.addFocusListener(new FocusListener() {
			 public void focusGained(FocusEvent e) {
			        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
			                if (sup.getText().equals("0.1")){
			                	sup.setText("");     //将提示文字清空
			                	sup.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
			                }
			            }
			 
			            public void focusLost(FocusEvent e) {
			                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
			                if (sup.getText().equals("")){
			                	sup.setForeground(Color.gray); //将提示文字设置为灰色
			                	sup.setText("0.1");     //显示提示文字
			                }
			            }
		});
		JLabel lab2=new JLabel("置信度:");
		lab2.setBounds(20, 100, 80, 20);
		lab2.setFont(new Font("宋体", Font.BOLD, 20));
		JTextField conf=new JTextField(15);
		conf.setBounds(100, 100, 150, 25);
		conf.setText("0.7");
		conf.setForeground(Color.gray);
		conf.addFocusListener(new FocusListener() {
			 public void focusGained(FocusEvent e) {
			        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
			                if (conf.getText().equals("0.7")){
			                	conf.setText("");     //将提示文字清空
			                	conf.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
			                }
			            }
			 
			            public void focusLost(FocusEvent e) {
			                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
			                if (conf.getText().equals("")){
			                	conf.setForeground(Color.gray); //将提示文字设置为灰色
			                	conf.setText("0.7");     //显示提示文字
			                }
			            }
		});
		panel[0].add(lab);
		panel[0].add(lab1);
		panel[0].add(sup);
		panel[0].add(lab2);
		panel[0].add(conf);
		panel[1].setLayout(new FlowLayout());
		JButton but=new JButton("开始");
		but.addActionListener(new ActionListener() {//执行Apriori关联规则挖掘算法
			public void actionPerformed(ActionEvent e) {
				if(!"".equals(area[2].getText()))
					area[2].setText("");
				try {
					float support=(float)0.0;
					float confidence=(float)0.0;
					if("".equals(sup.getText()) && "".equals(conf.getText())) {
						support=(float)0.1;
						confidence=(float)0.7;
					}else {
						support=Float.parseFloat(sup.getText());
						confidence=Float.parseFloat(conf.getText());
					}
					if(support>=1 || support<=0 || confidence>=1 || confidence<=0) {
						JOptionPane.showMessageDialog(null, "输入有误，支持度和置信度的值都在0-1之间");
						return;
					}
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        Date date=new Date();
			        String time=format.format(date);
			        area[1].append(time+"----Apriori----s:"+support+" c:"+confidence+"\n");
			        
					/**********Apriori算法*****************/
					Apriori apriori=new Apriori(support, confidence); //输入支持度、置信度
					apriori.getRecord();//读取数据集
					apriori.useApriori();//调用Apriori算法获得频繁项集
					area[2].setText("关联规则：\n");
			        for (List<String> a: apriori.bubbleSort()) {
			        	area[2].append(a.toString());
			        	area[2].append("\n");
			        }
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(null, "输入有误，支持度和置信度的值都在0-1之间");
				}
			}
		});
		panel[1].add(but);
		JButton but1=new JButton("清空");
		but1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area[2].setText("");
			}
		});
		panel[1].add(but1);		
		panel[2].setLayout(new BorderLayout());
		panel[2].add(panel[0], BorderLayout.CENTER);
		panel[2].add(panel[1], BorderLayout.SOUTH);
		panel[3].setLayout(new BorderLayout());
		JLabel log=new JLabel("输出日志");
		log.setFont(new Font("宋体", Font.BOLD, 20));
		panel[3].add(log, BorderLayout.NORTH);
		panel[3].add(new JScrollPane(area[1]), BorderLayout.CENTER);
		splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel[2], panel[3]);
		splitPane.setDividerLocation(250);
		panel[4].setLayout(new BorderLayout());
		JLabel result=new JLabel("输出结果");
		result.setFont(new Font("宋体", Font.BOLD, 20));
		panel[4].add(result, BorderLayout.NORTH);
		panel[4].add(new JScrollPane(area[2]), BorderLayout.CENTER);
		panel2.add(new JScrollPane(area[0]), BorderLayout.NORTH);
		panel2.add(splitPane, BorderLayout.WEST);
		panel2.add(panel[4], BorderLayout.CENTER);
//panel3内容，即Bayes预测结果面板
		area[3]=new JTextArea();
		area[3].setSize(1200, 300);
		area[3].setLineWrap(true);
		area[4]=new JTextArea();
		area[4].setSize(300, 500);
		area[4].setLineWrap(true);
		area[5]=new JTextArea();
		area[5].setSize(900, 500);
		area[5].setLineWrap(true);
		area[3].setText("使用bayes分类预测");
		area[3].setFont(new Font("宋体", Font.BOLD, 20));
		area[3].setEditable(false);
		area[4].setFont(new Font("Arial", Font.BOLD, 15));
		area[4].setEditable(false);
		area[5].setEditable(false);
		area[5].setFont(new Font("宋体", Font.BOLD, 20));
		for(int i=5;i<10;i++)
			panel[i]=new JPanel();
		panel[6].setLayout(new FlowLayout());
		panel[5].setLayout(null);
		JLabel[] lab6=new JLabel[11];
		lab6[0]=new JLabel("性别：", SwingConstants.RIGHT);
		lab6[0].setBounds(0, 10, 140, 20);
		lab6[1]=new JLabel("年龄：", SwingConstants.RIGHT);
		lab6[1].setBounds(0, 40, 140, 20);
		lab6[2]=new JLabel("高血压：", SwingConstants.RIGHT);
		lab6[2].setBounds(0, 70, 140, 20);
		lab6[3]=new JLabel("心脏病：", SwingConstants.RIGHT);
		lab6[3].setBounds(0, 100, 140, 20);
		lab6[4]=new JLabel("是否结过婚：", SwingConstants.RIGHT);
		lab6[4].setBounds(0, 130, 140, 20);
		lab6[5]=new JLabel("工作类型：", SwingConstants.RIGHT);
		lab6[5].setBounds(0, 160, 140, 20);
		lab6[6]=new JLabel("住宅类型：", SwingConstants.RIGHT);
		lab6[6].setBounds(0, 190, 140, 20);
		lab6[7]=new JLabel("葡萄糖含量：", SwingConstants.RIGHT);
		lab6[7].setBounds(0, 220, 140, 20);
		lab6[8]=new JLabel("身高：", SwingConstants.RIGHT);
		lab6[8].setBounds(0, 250, 140, 20);
		lab6[9]=new JLabel("体重：", SwingConstants.RIGHT);
		lab6[9].setBounds(0, 280, 140, 20);
		lab6[9].setFont(new Font("宋体", Font.BOLD, 20));
		lab6[10]=new JLabel("是否吸烟：", SwingConstants.RIGHT);
		lab6[10].setBounds(0, 310, 140, 20);
		for(int i=0;i<11;i++) {
			lab6[i].setFont(new Font("宋体", Font.BOLD, 20));
			panel[5].add(lab6[i]);
		}
		
		String[] t6={"男","女"};
		jcb[8]=new JComboBox(t6);
		jcb[8].setBounds(140, 10, 150, 25);
		jcb[8].setFont(new Font("宋体", Font.BOLD, 20));
		text1[0]=new JTextField();
		text1[0].setBounds(140, 40, 150, 25);
		text1[0].setFont(new Font("宋体", Font.BOLD, 20));
		String[] t7={"0","1"};
		jcb[9]=new JComboBox(t7);
		jcb[9].setBounds(140, 70, 150, 25);
		jcb[9].setFont(new Font("Arial", Font.BOLD, 20));
		jcb[10]=new JComboBox(t7);
		jcb[10].setBounds(140, 100, 150, 25);
		jcb[10].setFont(new Font("Arial", Font.BOLD, 20));
		String[] t8={"是","否"};
		jcb[11]=new JComboBox(t8);
		jcb[11].setBounds(140, 130, 150, 25);
		jcb[11].setFont(new Font("宋体", Font.BOLD, 20));
		String[] t9={"private","self_employed","govt_job"};
		jcb[12]=new JComboBox(t9);
		jcb[12].setBounds(140, 160, 150, 25);
		jcb[12].setFont(new Font("Arial", Font.BOLD, 20));
		String[] t10={"urban","rural"};
		jcb[13]=new JComboBox(t10);
		jcb[13].setBounds(140, 190, 150, 25);
		jcb[13].setFont(new Font("Arial", Font.BOLD, 20));
		text1[1]=new JTextField();
		text1[1].setBounds(140, 220, 150, 25);
		text1[1].setFont(new Font("宋体", Font.BOLD, 20));
		text1[2]=new JTextField();
		text1[2].setBounds(140, 250, 150, 25);
		text1[2].setFont(new Font("宋体", Font.BOLD, 20));
		text1[2].setText("单位:cm");
		text1[2].setForeground(Color.gray);
		text1[2].addFocusListener(new FocusListener() {	            
            public void focusGained(FocusEvent e) {
        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (text1[2].getText().equals("单位:cm")){
                	text1[2].setText("");     //将提示文字清空
                	text1[2].setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }
 
            public void focusLost(FocusEvent e) {
                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (text1[2].getText().equals("")){
                	text1[2].setForeground(Color.gray); //将提示文字设置为灰色
                	text1[2].setText("单位:cm");     //显示提示文字
                }
            }
 
        });
		text1[3]=new JTextField();
		text1[3].setBounds(140, 280, 150, 25);
		text1[3].setFont(new Font("宋体", Font.BOLD, 20));
		text1[3].setText("单位:kg");
		text1[3].setForeground(Color.gray);
		text1[3].addFocusListener(new FocusListener() {	            
            public void focusGained(FocusEvent e) {
        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (text1[3].getText().equals("单位:kg")){
                	text1[3].setText("");     //将提示文字清空
                	text1[3].setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }
 
            public void focusLost(FocusEvent e) {
                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (text1[3].getText().equals("")){
                	text1[3].setForeground(Color.gray); //将提示文字设置为灰色
                	text1[3].setText("单位:kg");     //显示提示文字
                }
            }
 
        });
		String[] t11={"often","formerly","occasionally","never"};
		jcb[14]=new JComboBox(t11);
		jcb[14].setBounds(140, 310, 150, 25);
		jcb[14].setFont(new Font("Arial", Font.BOLD, 20));
		for(int i=8;i<15;i++)
			panel[5].add(jcb[i]);
		for(int i=0;i<4;i++)
			panel[5].add(text1[i]);
		
		JButton but2=new JButton("开始");
		JButton but3=new JButton("清空");
		but2.addActionListener(new ActionListener() {//执行Bayes分类预测算法
			public void actionPerformed(ActionEvent e) {
				if(!"".equals(area[5].getText()))
					area[5].setText("");
		        String[] strs=new String[11];
				for(int i=0;i<strs.length;i++)
					strs[i]=null;
				
				strs[0]=(String)jcb[8].getSelectedItem();
				
				if("".equals(text1[0].getText())) {
					JOptionPane.showMessageDialog(null, "请输入年龄", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else if(!isNumeric(text1[0].getText())) {
					JOptionPane.showMessageDialog(null, "年龄输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else {
					strs[1]=text1[0].getText();
				}
				
				strs[2]=(String)jcb[9].getSelectedItem();
				strs[3]=(String)jcb[10].getSelectedItem();
				strs[4]=(String)jcb[11].getSelectedItem();
				strs[5]=(String)jcb[12].getSelectedItem();
				strs[6]=(String)jcb[13].getSelectedItem();
				
				if("".equals(text1[1].getText())) {
					JOptionPane.showMessageDialog(null, "请输入平均葡萄糖水平", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else if(!isNumeric(text1[1].getText())) {
					JOptionPane.showMessageDialog(null, "平均葡萄糖水平输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else {
					strs[7]=text1[1].getText();
				}
				
				if("".equals(text1[2].getText()) || "单位:cm".equals(text1[2].getText())) {
					JOptionPane.showMessageDialog(null, "请输入身高", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else if(!isNumeric(text1[2].getText())) {
					JOptionPane.showMessageDialog(null, "身高输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else if(toNumeric(text1[2].getText())>250) {
					JOptionPane.showMessageDialog(null, "身高输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else {
					strs[8]=text1[2].getText();
				}
				
				if("".equals(text1[3].getText()) || "单位:kg".equals(text1[3].getText())) {
					JOptionPane.showMessageDialog(null, "请输入体重", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else if(!isNumeric(text1[3].getText())) {
					JOptionPane.showMessageDialog(null, "体重输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else if(toNumeric(text1[3].getText())>100) {
					JOptionPane.showMessageDialog(null, "体重输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}else {
					strs[9]=text1[3].getText();
				}
				
				double s=Float.parseFloat(strs[9])/Math.pow(Float.parseFloat(strs[8])/100,2);
				strs[10]=(String)jcb[14].getSelectedItem();
				List<ArrayList<Object>> result=Bayesian.predict_kind("gender:"+strs[0],"age:"+strs[1],"hypertension:"+strs[2],"heart_disease:"+strs[3],"ever_married:"
							+strs[4],"work_type:"+strs[5],"Residence_type:"+strs[6],"avg_glucose_level:"+strs[7],"bmi:"+s,"smoking_status:"+strs[10]);
				System.out.println("gender:"+strs[0]+",age:"+strs[1]+",hypertension:"+strs[2]+",heart_disease:"+strs[3]+",ever_married:"
							+strs[4]+",work_type:"+strs[5]+",Residence_type:"+strs[6]+",avg_glucose_level:"+strs[7]+",bmi:"+s+",smoking_status:"+strs[10]);
				area[5].append("输出结果：\n");
				for(ArrayList<Object> p:result) {
					area[5].append("后验概率:");
					area[5].append(p.get(0).toString());
					area[5].append("====>");
					area[5].append(p.get(1).toString());
					area[5].append("\n");
				}
				area[5].append("预测结果："+Bayesian.predict_result("gender:"+strs[0],"age:"+strs[1],"hypertension:"+strs[2],"heart_disease:"+strs[3],"ever_married:"
							+strs[4],"work_type:"+strs[5],"Residence_type:"+strs[6],"avg_glucose_level:"+strs[7],"bmi:"+s,"smoking_status:"+strs[10]));
				
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date date=new Date();
		        String time=format.format(date);
		        area[4].append(time+"----Bayes\n");
				
			}
		});
		but3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area[5].setText("");
			}
		});
		panel[6].add(but2);
		panel[6].add(but3);
		panel[7].setLayout(new BorderLayout());
		panel[7].add(panel[5], BorderLayout.CENTER);
		panel[7].add(panel[6], BorderLayout.SOUTH);
		panel[8].setLayout(new BorderLayout());
		JLabel log1=new JLabel("输出日志");
		log1.setFont(new Font("宋体", Font.BOLD, 20));
		panel[8].add(log1, BorderLayout.NORTH);
		panel[8].add(new JScrollPane(area[4]), BorderLayout.CENTER);
		splitPane1=new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel[7], panel[8]);
		splitPane1.setDividerLocation(390);
		panel[9].setLayout(new BorderLayout());
		JLabel result1=new JLabel("输出结果");
		result1.setFont(new Font("宋体", Font.BOLD, 20));
		panel[9].add(result1, BorderLayout.NORTH);
		panel[9].add(new JScrollPane(area[5]), BorderLayout.CENTER);
		panel3.add(new JScrollPane(area[3]), BorderLayout.NORTH);
		panel3.add(splitPane1, BorderLayout.WEST);
		panel3.add(panel[9], BorderLayout.CENTER);
//将3个panel添加到主窗体中		
		p.add("信息管理", panel1);
		p.add("信息挖掘", panel2);
		p.add("健康预测", panel3);
		p.validate();
		this.add(p, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void actionPerformed(ActionEvent e) {//按钮监听事件
		if(e.getSource()==button[0]) {//查询按钮，用于多条件查询
			StringBuffer sb=new StringBuffer();
			if(!"".equals(text.getText().trim()))
				sb.append("and id='"+text.getText().trim()+"' ");
			if(!"".equals(jcb[0].getSelectedItem().toString()))
				sb.append("and gender like '"+jcb[0].getSelectedItem().toString()+"' ");
			if(!"".equals(jcb[1].getSelectedItem().toString()))
				sb.append("and hypertension='"+jcb[1].getSelectedItem().toString()+"' ");
			if(!"".equals(jcb[2].getSelectedItem().toString()))
				sb.append("and heart_disease='"+jcb[2].getSelectedItem().toString()+"' ");
			if(!"".equals(jcb[3].getSelectedItem().toString()))
				sb.append("and ever_married='"+jcb[3].getSelectedItem().toString()+"' ");
			if(!"".equals(jcb[4].getSelectedItem().toString()))
				sb.append("and work_type like '"+jcb[4].getSelectedItem().toString()+"' ");
			if(!"".equals(jcb[5].getSelectedItem().toString()))
				sb.append("and Residence_type like '"+jcb[5].getSelectedItem().toString()+"' ");
			if(!"".equals(jcb[6].getSelectedItem().toString()))
				sb.append("and smoking_status like '"+jcb[6].getSelectedItem().toString()+"' ");
			if(!"".equals(jcb[7].getSelectedItem().toString()))
				sb.append("and stroke like '"+jcb[7].getSelectedItem().toString()+"' ");
			System.out.println(sb.toString());
			updateTable(sb.toString());
			JScrollPane jsp=new JScrollPane(table);
			panel1.removeAll();
			panel1.add(pNorth, BorderLayout.NORTH);
			panel1.add(jsp, BorderLayout.CENTER);
			panel1.add(pSouth, BorderLayout.SOUTH);
			panel1.validate();
		}
		else if(e.getSource()==button[1]) {//添加按钮，用于添加一条记录
			JFrame frm=new JFrame("添加信息");
			JPanel panel=new JPanel();
			JPanel panel1=new JPanel();
			JPanel panel2=new JPanel();
			JTextField tmp=new JTextField(6);
			
			frm.setBounds(500,350,900,160);
			panel.add(new JLabel("编号"));
			panel.add(tmp);
			panel.add(new JLabel("性别:"));
			JComboBox temp=new JComboBox(new String[] {"男","女"});
			panel.add(temp);
			panel.add(new JLabel("年龄:"));
			JTextField tmp4=new JTextField(6);
			panel.add(tmp4);
			panel.add(new JLabel("高血压:"));
			JComboBox temp1=new JComboBox(new String[] {"0","1"});
			panel.add(temp1);
			panel.add(new JLabel("心脏病:"));
			JComboBox temp2=new JComboBox(new String[] {"0","1"});
			panel.add(temp2);
			panel.add(new JLabel("结过婚:"));
			JComboBox temp3=new JComboBox(new String[] {"是","否"});
			panel.add(temp3);
			panel.add(new JLabel("工作类型:"));
			JComboBox temp4=new JComboBox(new String[] {"private","self_employed","govt_job"});
			panel.add(temp4);
			panel2.add(new JLabel("住宅类型:"));
			JComboBox temp5=new JComboBox(new String[] {"urban","rural"});
			panel2.add(temp5);
			panel2.add(new JLabel("平均葡萄糖水平:"));
			JTextField tmp3=new JTextField(6);
			panel2.add(tmp3);
			panel2.add(new JLabel("身高:"));
			JTextField tmp1=new JTextField(6);
			tmp1.setText("单位:cm");
			tmp1.setForeground(Color.gray);
	        tmp1.addFocusListener(new FocusListener() {
	            public void focusGained(FocusEvent e) {
	        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
	                if (tmp1.getText().equals("单位:cm")){
	                	tmp1.setText("");     //将提示文字清空
	                	tmp1.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
	                }
	            }
	 
	            public void focusLost(FocusEvent e) {
	                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
	                if (tmp1.getText().equals("")){
	                	tmp1.setForeground(Color.gray); //将提示文字设置为灰色
	                	tmp1.setText("单位:cm");     //显示提示文字
	                }
	            }
	 
	        });
			panel2.add(tmp1);
			panel2.add(new JLabel("体重:"));
			JTextField tmp2=new JTextField(6);
			tmp2.setText("单位:kg");
			tmp2.setForeground(Color.gray);
	        tmp2.addFocusListener(new FocusListener() {
	            public void focusGained(FocusEvent e) {
	        //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
	                if (tmp2.getText().equals("单位:kg")){
	                	tmp2.setText("");     //将提示文字清空
	                	tmp2.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
	                }
	            }
	 
	            public void focusLost(FocusEvent e) {
	                 //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
	                if (tmp2.getText().equals("")){
	                	tmp2.setForeground(Color.gray); //将提示文字设置为灰色
	                	tmp2.setText("单位:kg");     //显示提示文字
	                }
	            }
	 
	        });
			panel2.add(tmp2);
			panel2.add(new JLabel("是否吸烟:"));
			JComboBox temp6=new JComboBox(new String[] {"often","formerly","occasionally","never"});
			panel2.add(temp6);
			panel2.add(new JLabel("中风:"));
			JComboBox temp7=new JComboBox(new String[] {"0","1"});
			panel2.add(temp7);
			JButton bt1=new JButton("确定");
			JButton bt2=new JButton("取消");
			bt1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String[] strs=new String[13];
					for(int i=0;i<strs.length;i++)
						strs[i]=null;
					if("".equals(tmp.getText())) {
						JOptionPane.showMessageDialog(null, "请输入编号", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!isNumeric(tmp.getText())) {
						JOptionPane.showMessageDialog(null, "编号输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else {
						strs[0]=tmp.getText();
					}
					
					strs[1]=(String)temp.getSelectedItem();
					
					if("".equals(tmp4.getText())) {
						JOptionPane.showMessageDialog(null, "请输入年龄", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!isNumeric(tmp4.getText())) {
						JOptionPane.showMessageDialog(null, "年龄输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else {
						strs[2]=tmp4.getText();
					}
					
					strs[3]=(String)temp1.getSelectedItem();
					strs[4]=(String)temp2.getSelectedItem();
					strs[5]=(String)temp3.getSelectedItem();
					strs[6]=(String)temp4.getSelectedItem();
					strs[7]=(String)temp5.getSelectedItem();
					
					if("".equals(tmp3.getText())) {
						JOptionPane.showMessageDialog(null, "请输入平均葡萄糖水平", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!isNumeric(tmp3.getText())) {
						JOptionPane.showMessageDialog(null, "平均葡萄糖水平输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else {
						strs[8]=tmp3.getText();
					}
					
					if("".equals(tmp1.getText()) || "单位:cm".equals(tmp1.getText())) {
						JOptionPane.showMessageDialog(null, "请输入身高", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!isNumeric(tmp1.getText())) {
						JOptionPane.showMessageDialog(null, "身高输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else if(toNumeric(tmp1.getText())>250) {
						JOptionPane.showMessageDialog(null, "身高输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else {
						strs[9]=tmp1.getText();
					}
					
					if("".equals(tmp2.getText()) || "单位:kg".equals(tmp2.getText())) {
						JOptionPane.showMessageDialog(null, "请输入体重", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!isNumeric(tmp2.getText())) {
						JOptionPane.showMessageDialog(null, "体重输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else if(toNumeric(tmp2.getText())>100) {
						JOptionPane.showMessageDialog(null, "体重输入有误", "消息提示框", JOptionPane.WARNING_MESSAGE);
						return;
					}else {
						strs[10]=tmp2.getText();
					}
					
					double s=Float.parseFloat(strs[10])/Math.pow(Float.parseFloat(strs[9])/100,2);
					strs[11]=(String)temp6.getSelectedItem();
					strs[12]=(String)temp7.getSelectedItem();
					
					int n=JOptionPane.showConfirmDialog(null, "确定添加吗？", "信息确认框", JOptionPane.YES_NO_OPTION);
					if(n==JOptionPane.YES_OPTION) {
						int r=record.addRecord(strs[0],strs[1],Integer.parseInt(strs[2]),Integer.parseInt(strs[3]),
							Integer.parseInt(strs[4]),strs[5],strs[6],strs[7],Float.parseFloat(strs[8]),String.format("%.1f", s),strs[11],Integer.parseInt(strs[12]));
						if(r>-1) {
							JOptionPane.showMessageDialog(null, "添加成功", "消息提示框", JOptionPane.WARNING_MESSAGE);
							frm.dispose();
						}
						else
							JOptionPane.showMessageDialog(null, "添加失败", "消息提示框", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
			bt2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frm.dispose();
				}
			});
			panel1.add(bt1);
			panel1.add(bt2);
			frm.add(panel, BorderLayout.NORTH);
			frm.add(panel2, BorderLayout.CENTER);
			frm.add(panel1, BorderLayout.SOUTH);
			frm.validate();
			frm.setVisible(true);
			frm.setResizable(false);
			frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		else if(e.getSource()==button[2]) {//删除按钮，用于删除一条记录
			int rowCount=table.getSelectedRow();
			if(rowCount>-1) {
				String rid=(String)table.getValueAt(rowCount, 0);
				record.deleteRecord(rid);
				initTable();
				JScrollPane jsp=new JScrollPane(table);
				panel1.removeAll();
				panel1.add(pNorth, BorderLayout.NORTH);
				panel1.add(jsp, BorderLayout.CENTER);
				panel1.add(pSouth, BorderLayout.SOUTH);
				panel1.validate();
			}
		}
		else if(e.getSource()==button[3]) {//修改按钮，用于修改一条记录
			int rowCount=table.getEditingRow();
			int colCount=table.getEditingColumn();
			if(rowCount>-1 && colCount>-1) {
				table.getCellEditor().stopCellEditing();
				String rid=(String)table.getValueAt(rowCount, 0);
				String r_update_col=cols1[colCount];
				String r_update=(String)table.getValueAt(rowCount, colCount);
				System.out.println(rid+"\t"+r_update_col+"\t"+r_update);
				record.updateRecord(rid,r_update_col,r_update);
			}
		}
		else if(e.getSource()==button[4]) {//恢复初始值，恢复全表查询
			initTable();
			JScrollPane jsp=new JScrollPane(table);
			panel1.removeAll();
			panel1.add(pNorth, BorderLayout.NORTH);
			panel1.add(jsp, BorderLayout.CENTER);
			panel1.add(pSouth, BorderLayout.SOUTH);
			panel1.validate();
		}
	}
}
