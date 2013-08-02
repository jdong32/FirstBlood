package FirstBlood;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;



public class myWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static String txtFilePath;
	public static String txtDirPath;
	public static String txtFileName;
<<<<<<< HEAD:src/FirstBlood/myWindow.java
	//public static Rate rate;
=======
	public static Rate rate;
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
    public static String rateE = "1.3060";
    public static String rateH = "0.1289";
    public static String rateJ = "0.01006";
    
//	public static String rate = "1.3060";
	public static String inforin[] = new String[5];

	JButton Select;
	JButton button0;
	JButton button1;
	JButton button2;
	JButton cleardata;
	JButton rollback;
	JButton setrate;
	JButton modifydata;
	JButton migratedata;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JLabel label6;
	JLabel copyright;
	JTextField textfield;
<<<<<<< HEAD:src/FirstBlood/myWindow.java
=======
	JTextField ratefield1;
	JTextField ratefield2;
	JTextField ratefield3;
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
	JPanel p;
	JPanel maincontainer;
	JPanel daiXiaoWei;
	JPanel footer;
	JFileChooser fc = new JFileChooser();
	TextArea area;
	ImageIcon background;
	ImageIcon trash;
	ImageIcon undo;
	JPanel backdrop;
	ImageIcon rateicon;
	ImageIcon migrateicon;
	ImageIcon modifyicon;

	public myWindow() {

		background = new ImageIcon(
				myWindow.class.getResource("/images/pic.jpg"));
		JLabel label = new JLabel(background);
		label.setBounds(20, 20, background.getIconWidth(),
				background.getIconHeight());

		p = new JPanel(); // 建立一个面板
		p.setBackground(Color.WHITE);
		this.getContentPane().add(p);// 把面板添加到框架
		p.setLayout(null);
		p.add(label);
		Font myFont = new Font("黑体", Font.PLAIN, 13);
		Font myFont1 = new Font("黑体", Font.PLAIN, 11);

		label1 = new JLabel("同户名统计软件");
		label1.setFont(new Font("黑体", Font.BOLD, 14));
		label1.setBounds(175, 43, 200, 20);
		p.add(label1); // 添加空白文本框
		
		maincontainer = new JPanel();
		maincontainer.setBackground(Color.decode("#CCFF99"));
		
		maincontainer.setBorder(BorderFactory.createEtchedBorder());
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		maincontainer.setSize(300, 170);
		maincontainer.setLocation(20, 160);
=======
		maincontainer.setSize(300, 295);
		maincontainer.setLocation(20, 90);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		maincontainer.setLayout(null);

		daiXiaoWei = new JPanel();
		daiXiaoWei.setBorder(BorderFactory.createEtchedBorder());
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		daiXiaoWei.setBounds(20, 100, 300, 50);
		daiXiaoWei.setLayout(null);
		daiXiaoWei.setBackground(Color.decode("#EFEFEF"));
		p.add(daiXiaoWei);

		
		setrate = new JButton("更改汇率");
		setrate.setFont(myFont1);
		setrate.setBounds(20, 10, 80, 30);
		setrate.setBackground(Color.WHITE);
		setrate.setToolTipText("设置国际汇率");
		daiXiaoWei.add(setrate);
		setrate.addActionListener(this);
		
		modifydata = new JButton("更改数据");
		modifydata.setFont(myFont1);
		modifydata.setBounds(110, 10, 80, 30);
		modifydata.setBackground(Color.WHITE);
		modifydata.setToolTipText("更改数据");
		daiXiaoWei.add(modifydata);
		modifydata.addActionListener(this);
		
		migratedata = new JButton("迁移数据");
		migratedata.setFont(myFont1);
		migratedata.setBounds(200, 10, 80, 30);
		migratedata.setBackground(Color.WHITE);
		migratedata.setToolTipText("迁移数据");
		daiXiaoWei.add(migratedata);
		migratedata.addActionListener(this);
		
		textfield = new JTextField(24);
		textfield.setFont(myFont1);
		textfield.setBounds(20, 25, 180, 30);
=======
		daiXiaoWei.setBounds(20, 20, 260, 133);
		daiXiaoWei.setLayout(null);
		daiXiaoWei.setBackground(Color.decode("#EFEFEF"));
		
		label0 = new JLabel("欧元对美元汇率:");
		label0.setFont(myFont1);
		label0.setBounds(10, 10, 130, 30);
		
		daiXiaoWei.add(label0);

		ratefield1 = new JTextField(rateE);
		ratefield1.setFont(myFont1);
		ratefield1.setSize(60, 30);
		ratefield1.setLocation(110, 10);
		daiXiaoWei.add(ratefield1);
		
		label5 = new JLabel("港币对美元汇率:");
		label5.setFont(myFont1);
		label5.setBounds(10, 50, 130, 30);
		daiXiaoWei.add(label5);

		ratefield2 = new JTextField(rateH);
		ratefield2.setFont(myFont1);
		ratefield2.setSize(60, 30);
		ratefield2.setLocation(110, 50);
		daiXiaoWei.add(ratefield2);
		
		label6 = new JLabel("日元对美元汇率:");
		label6.setFont(myFont1);
		label6.setBounds(10, 90, 130, 30);
		daiXiaoWei.add(label6);

		ratefield3 = new JTextField(rateJ);
		ratefield3.setFont(myFont1);
		ratefield3.setSize(60, 30);
		ratefield3.setLocation(110, 90);
		daiXiaoWei.add(ratefield3);

		button0 = new JButton("确定");
		button0.setBackground(Color.WHITE);
		button0.setFont(myFont);
		button0.setSize(70, 30);
		button0.setLocation(180, 90);
		daiXiaoWei.add(button0);
		button0.addActionListener(this);
		
		maincontainer.add(daiXiaoWei);
		textfield = new JTextField(24);
		textfield.setFont(myFont1);
		textfield.setBounds(20, 165, 180, 30);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		maincontainer.add(textfield);

		Select = new JButton("浏览");
		Select.setBackground(Color.WHITE);
		Select.setFont(myFont);
		Select.setSize(70, 30);
//		Select.setLocation(220, 130);
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		Select.setLocation(210, 25);
=======
		Select.setLocation(210, 165);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		maincontainer.add(Select);
		Select.addActionListener(this);

		button1 = new JButton("生成初步Excel文件");
		button1.setBackground(Color.WHITE);
		button1.setFont(myFont);
		button1.setSize(265, 30);
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		button1.setLocation(20, 70);
=======
		button1.setLocation(20, 210);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		maincontainer.add(button1);
		button1.addActionListener(this);

		button2 = new JButton("生成最终Excel文件");
		button2.setBackground(Color.WHITE);
		button2.setFont(myFont);
		button2.setSize(265, 30);
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		button2.setLocation(20, 110);
=======
		button2.setLocation(20, 250);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		maincontainer.add(button2);
		button2.addActionListener(this);
		
		p.add(maincontainer);
		
		footer = new JPanel();
		footer.setBackground(Color.decode("#99CCFF"));
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		footer.setBounds(20, 340, 300, 50);
=======
		footer.setBounds(20, 390, 300, 50);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		footer.setBorder(BorderFactory.createEtchedBorder());
		footer.setLayout(null);

		
		trash = new ImageIcon(myWindow.class.getResource("/images/trash.gif"));
		trash.setDescription("清空历史记录");
		undo = new ImageIcon(myWindow.class.getResource("/images/undo.gif"));
		undo.setDescription("取消上一次操作");
		
		cleardata = new JButton(trash);
		cleardata.setBounds(40, 10, 100, 30);
		cleardata.setBackground(Color.WHITE);
		cleardata.setToolTipText("清空历史记录");
		
		footer.add(cleardata);
		cleardata.addActionListener(this);
		
		rollback = new JButton(undo);
		rollback.setBounds(160, 10, 100, 30);
		rollback.setBackground(Color.WHITE);
		rollback.setToolTipText("取消上一次操作");
		footer.add(rollback);
		rollback.addActionListener(this);
		
		p.add(footer);
		
		copyright = new JLabel("Copyright © 2013, ZJRC Cixi Team, All Rights Reserved");
		copyright.setFont(new Font("Times", Font.PLAIN, 10));
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		copyright.setBounds(40, 404, 300, 20);
=======
		copyright.setBounds(40, 450, 300, 20);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		p.add(copyright);
		
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == setrate) {
			new SetRateWindow(this);
		} else if (e.getSource() == migratedata) {
			if (txtFilePath != null) {
				new MigrateDataWindow(this);
			} else {
				JOptionPane.showMessageDialog(null, "请选择文件路径", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		} else if (e.getSource() == modifydata) {
			if (txtFilePath != null) {
				new ModifyDataWindow(this);
			} else {
				JOptionPane.showMessageDialog(null, "请选择文件路径", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		
		// 当按下浏览按钮，打开一个文件选择，文本框显示文件路径
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		else if (e.getSource() == Select) {
=======
		if (e.getSource() == button0) {
			rateE = ratefield1.getText();
			rateH = ratefield2.getText();
			rateJ = ratefield3.getText();
			System.out.println(rateJ);
		} else if (e.getSource() == Select) {
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
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
				readTxtFile.Read(txtFilePath, txtDirPath, inforin);
				
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
				String date = stemp.replaceAll("\\D", "");
				String srcPath = txtDirPath + "初步文件.xls";
				String destPath = txtDirPath + "同户名统计.xls";
				Step2.zuzhang(srcPath, destPath, date);
				File destFile2 = new File(txtDirPath + "同户名统计.xls");
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
		
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		myWindow frame = new myWindow();
<<<<<<< HEAD:src/FirstBlood/myWindow.java
		frame.setSize(360, 465);
=======
		frame.setSize(360, 510);
>>>>>>> d5660dccc92597cee249146cce78275fd6f6dd2b:PROJECT_FIRST/src/FirstBlood/myWindow.java
		frame.setLocation(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
