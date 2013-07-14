package FirstBlood;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class myWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static String txtFilePath;
	public static String txtDirPath;
	public static String txtFileName;
	public static String rate = "1.3";
	public static String inforin[] = new String[5];
	public static String inforout[] = new String[5];
	JButton Select;
	JButton button0;
	JButton button1;
	JButton button2;
	JButton cleardata;
	JButton rollback;
	JLabel label0;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel copyright;
	JTextField textfield;
	JTextField ratefield;
	JPanel p;
	JPanel maincontainer;
	JPanel footer;
	JFileChooser fc = new JFileChooser();
	TextArea area;
	ImageIcon background;
	ImageIcon trash;
	ImageIcon undo;
	JPanel backdrop;

	public myWindow() {
		background = new ImageIcon(
				myWindow.class.getResource("pic.jpg"));
		JLabel label = new JLabel(background);
		label.setBounds(20, 20, background.getIconWidth(),
				background.getIconHeight());

		p = new JPanel(); // 建立一个面板
		p.setBackground(Color.WHITE);
		this.getContentPane().add(p);// 把面板添加到框架
		p.setLayout(null);
		p.add(label);
		Font myFont = new Font("黑体", Font.PLAIN, 13);
		Font myFont1 = new Font("黑体", Font.PLAIN, 13);

		label1 = new JLabel("同名户统计软件");
		label1.setFont(new Font("黑体", Font.BOLD, 14));
		label1.setBounds(175, 43, 200, 20);
		p.add(label1); // 添加空白文本框
		
		maincontainer = new JPanel();
		maincontainer.setBackground(Color.decode("#CCFF99"));
		
		maincontainer.setBorder(BorderFactory.createEtchedBorder());
		maincontainer.setSize(300, 200);
		maincontainer.setLocation(20, 90);
		maincontainer.setLayout(null);

		label0 = new JLabel("欧元对美元汇率:");
		label0.setFont(myFont1);
		label0.setBounds(20, 30, 130, 30);
		maincontainer.add(label0);

		ratefield = new JTextField("1.3");
		//ratefield.setText("1.3");
		ratefield.setFont(myFont1);
		ratefield.setSize(80, 30);
		ratefield.setLocation(130, 30);
		maincontainer.add(ratefield);

		button0 = new JButton("确定");
		button0.setBackground(Color.WHITE);
		button0.setFont(myFont);
		button0.setSize(70, 30);
		button0.setLocation(220, 30);
		maincontainer.add(button0);
		button0.addActionListener(this);

		textfield = new JTextField(24);
		textfield.setFont(myFont1);
		textfield.setBounds(20, 70, 190, 30);
		maincontainer.add(textfield);

		Select = new JButton("浏览");
		Select.setBackground(Color.WHITE);
		Select.setFont(myFont);
		Select.setSize(70, 30);
		Select.setLocation(220, 70);
		maincontainer.add(Select);
		Select.addActionListener(this);

		button1 = new JButton("生成初步Excel文件");
		button1.setBackground(Color.WHITE);
		button1.setFont(myFont);
		button1.setSize(265, 30);
		button1.setLocation(20, 115);
		maincontainer.add(button1);
		button1.addActionListener(this);

		button2 = new JButton("生成最终Excel文件");
		button2.setBackground(Color.WHITE);
		button2.setFont(myFont);
		button2.setSize(265, 30);
		button2.setLocation(20, 155);
		maincontainer.add(button2);
		button2.addActionListener(this);
		
		p.add(maincontainer);
		//p.setVisible(true);
		
		footer = new JPanel();
		footer.setBackground(Color.decode("#99CCFF"));
		footer.setBounds(20, 300, 300, 50);
		footer.setBorder(BorderFactory.createEtchedBorder());
		footer.setLayout(null);
		
		
		/*cleardata = new JButton("清除所有数据");
		cleardata.setFont(myFont);
		cleardata.setSize(135, 30);
		cleardata.setLocation(15, 10);
		footer.add(cleardata);
		cleardata.addActionListener(this);
		
		rollback = new JButton("取消上一次操作");
		rollback.setFont(myFont);
		rollback.setSize(135, 30);
		rollback.setLocation(150, 10);
		footer.add(rollback);
		rollback.addActionListener(this);*/
		
		trash = new ImageIcon(myWindow.class.getResource("trash.gif"));
		trash.setDescription("清空历史记录");
		undo = new ImageIcon(myWindow.class.getResource("undo.gif"));
		undo.setDescription("取消上一次操作");
		
		cleardata = new JButton(trash);
		cleardata.setBounds(40, 10, 100, 30);
		cleardata.setBackground(Color.WHITE);
		footer.add(cleardata);
		cleardata.addActionListener(this);
		
		rollback = new JButton(undo);
		rollback.setBounds(160, 10, 100, 30);
		rollback.setBackground(Color.WHITE);
		footer.add(rollback);
		rollback.addActionListener(this);
		
		p.add(footer);
		
		copyright = new JLabel("Copyright © 2013, ZJRC Cixi Team, All Rights Reserved");
		copyright.setFont(new Font("Times", Font.PLAIN, 10));
		copyright.setBounds(40, 365, 300, 20);
		p.add(copyright);
		
	}

	public void actionPerformed(ActionEvent e) {

		// 当按下浏览按钮，打开一个文件选择，文本框显示文件路径
		if (e.getSource() == button0) {
			rate = ratefield.getText();
			System.out.println(rate);
		} else if (e.getSource() == Select) {
			int intRetVal = fc.showOpenDialog(this);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				textfield.setText(fc.getSelectedFile().getPath());
				txtFilePath = fc.getSelectedFile().getAbsolutePath();
				txtFileName = fc.getSelectedFile().getName();
				txtDirPath = txtFilePath.replaceAll(txtFileName, "");
				readConfig.readread(txtDirPath);
			}

		}

		else if (e.getSource() == button1) {
			// 第一步
			// 当按下按钮，生成一个初步的Excel文件

			if (txtFilePath != null) {
				readTxtFile.Read(txtFilePath, txtDirPath, inforin, inforout);
				
				System.out.println("开始输出数据");
				for (int y = 0; y < 30; y++)
					System.out.println(readTxtFile.first[y].currtype + " "
							+ readTxtFile.first[y].name1 + " "
							+ readTxtFile.first[y].name2 + " " + " "
							+ readTxtFile.first[y].money);
				File destFile = new File(txtDirPath + "初步文件.xls");
				if (destFile.exists()) {
					JOptionPane.showMessageDialog(null, "初步Excel文件已生成", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "请选择文件路径", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		else if (e.getSource() == button2) {
			// 第二步
			// 当按下按钮，生成一个最终的Excel文件
			if (txtDirPath != null && (new File(txtDirPath + "初步文件.xls").exists())) {
				String stemp = fc.getSelectedFile().getName();
				String date = stemp.replaceAll("(?<!\\d)\\D", "");
				String srcPath = txtDirPath + "初步文件.xls";
				String destPath = txtDirPath + "最终文件.xls";
				Step2.zuzhang(srcPath, destPath, date);
				File destFile2 = new File(txtDirPath + "最终文件.xls");
				if (destFile2.exists()) {
					JOptionPane.showMessageDialog(null, "最终Excel文件已生成", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "初步Excel不存在", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		else if (e.getSource() == cleardata) {
			Step2.cleardata();
			JOptionPane.showMessageDialog(null, "历史文件已清空", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if (e.getSource() == rollback) {
			Step2.rollback();
			JOptionPane.showMessageDialog(null, "回滚成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		myWindow frame = new myWindow();
		frame.setSize(360, 430);
		frame.setLocation(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
