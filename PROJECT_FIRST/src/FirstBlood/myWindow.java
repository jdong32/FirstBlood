package FirstBlood;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

public class myWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static String txtFilePath;
	public static String txtDirPath;
	public static String txtFileName;
	public static String inforin[] = new String[5];
	public static String inforout[] = new String[5];    
    JButton Select;
    JButton button1;
    JButton button2;
    JLabel label0;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JTextField textfield;
    JPanel p;
    JFileChooser fc = new JFileChooser();    
    TextArea area;
    ImageIcon  background;
    JPanel backdrop;

public myWindow() {
	    background = new ImageIcon("./pic.jpg");
	    JLabel label = new JLabel(background);
	    label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
	    
        p = new JPanel(); // 建立一个面板
        this.getContentPane().add(p);// 把面板添加到框架
        p.add(label);
        Font myFont=new Font("楷体",Font.BOLD,18);
        Font myFont1=new Font("楷体",Font.BOLD,16);
        //label1.setOpaque(false);
        label1 = new JLabel("同名户统计软件",JLabel.CENTER);
        label1.setFont(myFont);
        p.add(label1); //添加空白文本框   
        label1 = new JLabel("                                                               ");
        p.add(label1); //添加空白文本框   
        textfield = new JTextField(15);
        textfield.setFont(myFont1);
        p.add(textfield); // 把一个文本框添加到面板
        Select = new JButton("浏览");
        Select.setFont(myFont);
        p.add(Select); // 把一个浏览按钮添加到面板
        Select.addActionListener(this);        
        label2 = new JLabel("                                                                ");
        p.add(label2);             
        button1 = new JButton("生成初步Excel文件");
        button1.setFont(myFont);
        
        p.add(button1);        
        button1.addActionListener(this);        
        label3 = new JLabel("                                                                ");
        p.add(label3);         
        button2 = new JButton("生成最终Excel文件");
        button2.setFont(myFont);
        p.add(button2);
        button2.addActionListener(this);  
    }

public void actionPerformed(ActionEvent e) {
	
        // 当按下浏览按钮，打开一个文件选择，文本框显示文件路径
	    
        if (e.getSource() == Select) {        	
            int intRetVal = fc.showOpenDialog(this);            
            if (intRetVal == JFileChooser.APPROVE_OPTION) {            	
                textfield.setText(fc.getSelectedFile().getPath());
            	txtFilePath = fc.getSelectedFile().getAbsolutePath();
            	txtFileName = fc.getSelectedFile().getName();
            	txtDirPath = txtFilePath.replaceAll(txtFileName, "");
            	try {
	                String encoding="GBK";
	                File file=new File(txtDirPath + "关键字.txt");
	                if(file.isFile() && file.exists()){           //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);         //考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    int num = 0;
	                    String stemp[] = new String[4];
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                        stemp[num] = lineTxt;
	                        num++;
	                    }
	                    read.close();
	                    String in[] = stemp[1].split("\\s+");
	                    String out[] = stemp[3].split("\\s+");
	                    inforin = in;
	                    inforout = out;
	                    System.out.println(inforout[0]);
	                    System.out.println(inforout[1]);	                  
	        }else{
	            System.out.println("找不到指定配置文件！");
	            JOptionPane.showMessageDialog(null, "找不到指定配置文件！",
	          			"提示",JOptionPane.INFORMATION_MESSAGE);  
	        }
	        } catch (Exception ee) {
	            System.out.println("读取配置文件内容出错！");
	            JOptionPane.showMessageDialog(null, "读取配置文件内容出错！",
	          			"提示",JOptionPane.INFORMATION_MESSAGE);  
	            ee.printStackTrace();
	        }	     
	      }            
        }
        
        if (e.getSource() == button1) { 
            
        	// 第一步
        	// 当按下按钮，生成一个初步的Excel文件
        
        	readTxtFile.Read(txtFilePath,txtDirPath,inforin,inforout);
        	File destFile = new File(txtDirPath + "初步文件.xls");
        	if(destFile.exists()){
        		JOptionPane.showMessageDialog(null, "初步Excel文件已生成",
              			"提示",JOptionPane.INFORMATION_MESSAGE);  
        	}
   	
        }         
        
        if (e.getSource() == button2) { 
            
        	// 第二步
        	// 当按下按钮，生成一个最终的Excel文件
        	
            String stemp = fc.getSelectedFile().getName();
            String date = stemp.replaceAll("(?<!\\d)\\D", "");
            String srcPath = txtDirPath + "初步文件.xls";
            String destPath = txtDirPath + "最终文件.xls";
            Step2.zuzhang(srcPath, destPath, date);
            File destFile2 = new File(txtDirPath + "最终文件.xls");
        	if(destFile2.exists()){
        		JOptionPane.showMessageDialog(null, "最终Excel文件已生成",
              			"提示",JOptionPane.INFORMATION_MESSAGE);  
        	}
        }        
    }
    
public static void main(String[] args) {
	
        myWindow frame = new myWindow();
        frame.setSize(300, 350);
        frame.setLocation(600,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


