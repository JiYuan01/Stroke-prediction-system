package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dao.RecordDao;

public class Login {
	public static void main(String args[]) {
		new LoginWin();
	}
}

class LoginWin implements ActionListener {//构造窗体的类
	private JFrame frame;
	private JLabel label,label1,label2;
	private JTextField text;
	private JPasswordField pwd;
	private JButton button;
	private ButtonGroup btgroup;
	private JRadioButton rdbutton,rdbutton1;
	
	LoginWin(){
		frame=new JFrame();
		frame.setTitle("健康系统登录");
		frame.setBounds(100, 100, 500, 400);
		frame.getContentPane().setLayout(null);
		
		label=new JLabel("中风预测系统");
		label.setBounds(130, 30,300,60);
		label.setFont(new Font("楷体_GB2312", Font.BOLD, 40));			
		label1=new JLabel("账户：");
		label1.setBounds(80, 130, 70, 40);
		label1.setFont(new Font("楷体_GB2312", Font.BOLD, 20));		
		label2=new JLabel("密码：");
		label2.setBounds(80 ,180 ,70 ,40);
		label2.setFont(new Font("楷体_GB2312", Font.BOLD, 20));
		frame.getContentPane().add(label);
		frame.getContentPane().add(label1);
		frame.getContentPane().add(label2);
		
		text=new JTextField();
		text.setBounds(150,130,260,40);
		text.setFont(new Font("楷体_GB2312", Font.BOLD, 20));
		pwd=new JPasswordField();
		pwd.setBounds(150,180,260,40);
		pwd.setFont(new Font("楷体_GB2312", Font.BOLD, 20));
		pwd.setEchoChar('*');
		frame.getContentPane().add(text);
		frame.getContentPane().add(pwd);
		
		btgroup=new ButtonGroup();
		rdbutton=new JRadioButton("用户");
		rdbutton1=new JRadioButton("管理员");
		rdbutton.setBounds(180, 230,90,20);
		rdbutton.setSelected(true);
		rdbutton1.setBounds(270, 230,90,20);
		btgroup.add(rdbutton);
		btgroup.add(rdbutton1);
		frame.getContentPane().add(rdbutton);
		frame.getContentPane().add(rdbutton1);
		
		button=new JButton("登录");
		button.setBounds(150,270,200,40);
		button.setBackground(Color.getColor("#00BFFF"));
		button.addActionListener(this);
		frame.getContentPane().add(button);
		
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {			
		if(rdbutton.isSelected()) {
			String usr=text.getText();
			String password=pwd.getText();
			
			if(RecordDao.userValidate(usr, password).size()>0) {
				JOptionPane.showMessageDialog(null, "用户登陆成功！");
				new UserFrm();
				frame.dispose();
			}else {
				JOptionPane.showMessageDialog(null, "账户或密码错误！");
			}
		}
		else if(rdbutton1.isSelected()) {
			String usr=text.getText();
			String password=pwd.getText();
			
//			if(usr.equals("root") & password.equals("1234")) {
//				JOptionPane.showMessageDialog(null, "管理员登陆成功！");
//				frame.dispose();
//				new ManagerFrm();
//			}			
			if(RecordDao.managerValidate(usr, password).size()>0) {
				JOptionPane.showMessageDialog(null, "管理员登陆成功！");
				new ManagerFrm();
				frame.dispose();
			}else {
				JOptionPane.showMessageDialog(null, "账户或密码错误！");
			}
		}
	}
}