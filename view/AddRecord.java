package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddRecord {
	JFrame frm;
	JPanel panel,panel1;
	
	public AddRecord() {
		frm=new JFrame("�����Ϣ");
		panel=new JPanel();
		panel1=new JPanel();
		frm.setBounds(400,300,1000,300);
		panel.setLayout(null);
		JLabel lab=new JLabel("ѡ��");
		lab.setBounds(100, 10, 100, 30);
		lab.setFont(new Font("����", Font.BOLD, 30));
		JLabel lab1=new JLabel("֧�ֶ�:");
		lab1.setBounds(10, 50, 100, 30);
		lab1.setFont(new Font("����", Font.BOLD, 30));
		JTextField sup=new JTextField(10);
		sup.setBounds(120, 50, 100, 30);
		JLabel lab2=new JLabel("���Ŷ�:");
		lab2.setBounds(10, 90, 100, 30);
		lab2.setFont(new Font("����", Font.BOLD, 30));
		JTextField conf=new JTextField(10);
		conf.setBounds(120, 90, 100, 30);
		panel.add(lab);
		panel.add(lab1);
		panel.add(sup);
		panel.add(lab2);
		panel.add(conf);
		
		panel1.setLayout(new FlowLayout());
		JButton but=new JButton("��ʼ");
		panel1.add(but);
		JButton but1=new JButton("���");
		panel1.add(but1);		
		
		frm.setLayout(new BorderLayout());
		frm.add(panel, BorderLayout.CENTER);
		frm.add(panel1, BorderLayout.SOUTH);
		frm.validate();
		frm.setVisible(true);
		frm.setResizable(false);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String args[]) {
		new AddRecord();
	}
}
