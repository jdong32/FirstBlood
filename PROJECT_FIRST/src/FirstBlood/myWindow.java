package FirstBlood;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class myWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static String txtFilePath;
	public static String txtDirPath;
	public static String txtFileName;
	public static String rate;
	public static String inforin[] = new String[5];
	public static String inforout[] = new String[5];
	JButton Select;
	JButton button0;
	JButton button1;
	JButton button2;
	JLabel label0;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	JTextField textfield;
	JTextField ratefield;
	JPanel p;
	JFileChooser fc = new JFileChooser();
	TextArea area;
	ImageIcon background;
	JPanel backdrop;

	public myWindow() {
		background = new ImageIcon(myWindow.class.getResource("../mywindow/pic.jpg"));
		JLabel label = new JLabel(background);
		label.setBounds(90, 20, background.getIconWidth(),background.getIconHeight());

		p = new JPanel(); // 建立�?��面板
		this.getContentPane().add(p);// 把面板添加到框架
		p.setLayout(null);
		p.add(label);
		Font myFont = new Font("楷体", Font.BOLD, 16);
		Font myFont1 = new Font("楷体", Font.BOLD, 16);

//		label1 = new JLabel("同名户统计软�?);
//		label1.setFont(myFont);
//		label1.setBounds(200, 40, 200, 20);
//		p.add(label1); // 添加空白文本�?

		label0 = new JLabel("欧元对美元汇�?");
		label0.setFont(myFont1);
		label0.setBounds(20, 105, 130, 30);
		p.add(label0);

		ratefield = new JTextField(10);
		ratefield.setFont(myFont1);
		ratefield.setSize(80, 30);
		ratefield.setLocation(150, 100);
		p.add(ratefield);

		button0 = new JButton("确定");
		button0.setFont(myFont);
		button0.setSize(80, 30);
		button0.setLocation(250, 100);
		p.add(button0);
		button0.addActionListener(this);

		textfield = new JTextField(24);
		textfield.setFont(myFont1);
		textfield.setBounds(20, 150, 210, 30);
		p.add(textfield);

		Select = new JButton("浏览");
		Select.setFont(myFont);
		Select.setSize(80, 30);
		Select.setLocation(250, 150);
		p.add(Select);
		Select.addActionListener(this);

		button1 = new JButton("生成初步Excel文件");
		button1.setFont(myFont);
		button1.setSize(310, 30);
		button1.setLocation(20, 200);
		p.add(button1);
		button1.addActionListener(this);

		button2 = new JButton("生成�?��Excel文件");
		button2.setFont(myFont);
		button2.setSize(310, 30);
		button2.setLocation(20, 250);
		p.add(button2);
		button2.addActionListener(this);
                
                File file=new File("../src/rate.txt");
                if(file.isFile() && file.exists()){
                    try{
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("../mywindow/rate.txt"), Charset.forName("GBK")));
                        rate = br.readLine();
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
	}

	public void actionPerformed(ActionEvent e) {

		// 当按下浏览按钮，打开�?��文件选择，文本框显示文件路径
		if (e.getSource() == button0) {
			rate = ratefield.getText();
			System.out.println(rate);
                        try{
                            File f = new File("../src/rate.txt");
                            FileWriter fw = new FileWriter(f);
                            fw.write("");
                            fw.write(rate);
                            fw.close();
                        }catch(Exception exp){
                            System.err.println(exp.getMessage());
                        }
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
			// 第一�?
			// 当按下按钮，生成�?��初步的Excel文件

			readTxtFile.Read(txtFilePath, txtDirPath, inforin, inforout);
			
			System.out.println("�?��输出数据");
			for (int y = 0; y < 30; y++)
				System.out.println(readTxtFile.first[y].currtype + " "
						+ readTxtFile.first[y].name1 + " "
						+ readTxtFile.first[y].name2 + " " + " "
						+ readTxtFile.first[y].money);
			File destFile = new File(txtDirPath + "初步文件.xls");
			if (destFile.exists()) {
				JOptionPane.showMessageDialog(null, "初步Excel文件已生�?, "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		else if (e.getSource() == button2) {
			// 第二�?
			// 当按下按钮，生成�?���?��的Excel文件

			String stemp = fc.getSelectedFile().getName();
			String date = stemp.replaceAll("(?<!\\d)\\D", "");
			String srcPath = txtDirPath + "初步文件.xls";
			String destPath = txtDirPath + "�?��文件.xls";
			Step2.zuzhang(srcPath, destPath, date);
			File destFile2 = new File(txtDirPath + "�?��文件.xls");
			if (destFile2.exists()) {
				JOptionPane.showMessageDialog(null, "�?��Excel文件已生�?, "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void main(String[] args) {

		myWindow frame = new myWindow();
                
                ImageIcon icon = new ImageIcon("../src/icon.jpg");
                frame.setIconImage(icon.getImage());
                frame.setTitle("ͬ������ϵͳͬ����ͳ������");
                frame.getContentPane().setBackground(Color.WHITE);
                
		frame.setSize(360, 370);
		frame.setLocation(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
