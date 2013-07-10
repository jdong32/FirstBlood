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
	    
        p = new JPanel(); // ����һ�����
        this.getContentPane().add(p);// �������ӵ����
        p.add(label);
        Font myFont=new Font("����",Font.BOLD,18);
        Font myFont1=new Font("����",Font.BOLD,16);
        //label1.setOpaque(false);
        label1 = new JLabel("ͬ����ͳ�����",JLabel.CENTER);
        label1.setFont(myFont);
        p.add(label1); //��ӿհ��ı���   
        label1 = new JLabel("                                                               ");
        p.add(label1); //��ӿհ��ı���   
        textfield = new JTextField(15);
        textfield.setFont(myFont1);
        p.add(textfield); // ��һ���ı�����ӵ����
        Select = new JButton("���");
        Select.setFont(myFont);
        p.add(Select); // ��һ�������ť��ӵ����
        Select.addActionListener(this);        
        label2 = new JLabel("                                                                ");
        p.add(label2);             
        button1 = new JButton("���ɳ���Excel�ļ�");
        button1.setFont(myFont);
        
        p.add(button1);        
        button1.addActionListener(this);        
        label3 = new JLabel("                                                                ");
        p.add(label3);         
        button2 = new JButton("��������Excel�ļ�");
        button2.setFont(myFont);
        p.add(button2);
        button2.addActionListener(this);  
    }

public void actionPerformed(ActionEvent e) {
	
        // �����������ť����һ���ļ�ѡ���ı�����ʾ�ļ�·��
	    
        if (e.getSource() == Select) {        	
            int intRetVal = fc.showOpenDialog(this);            
            if (intRetVal == JFileChooser.APPROVE_OPTION) {            	
                textfield.setText(fc.getSelectedFile().getPath());
            	txtFilePath = fc.getSelectedFile().getAbsolutePath();
            	txtFileName = fc.getSelectedFile().getName();
            	txtDirPath = txtFilePath.replaceAll(txtFileName, "");
            	try {
	                String encoding="GBK";
	                File file=new File(txtDirPath + "�ؼ���.txt");
	                if(file.isFile() && file.exists()){           //�ж��ļ��Ƿ����
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);         //���ǵ������ʽ
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
	            System.out.println("�Ҳ���ָ�������ļ���");
	            JOptionPane.showMessageDialog(null, "�Ҳ���ָ�������ļ���",
	          			"��ʾ",JOptionPane.INFORMATION_MESSAGE);  
	        }
	        } catch (Exception ee) {
	            System.out.println("��ȡ�����ļ����ݳ���");
	            JOptionPane.showMessageDialog(null, "��ȡ�����ļ����ݳ���",
	          			"��ʾ",JOptionPane.INFORMATION_MESSAGE);  
	            ee.printStackTrace();
	        }	     
	      }            
        }
        
        if (e.getSource() == button1) { 
            
        	// ��һ��
        	// �����°�ť������һ��������Excel�ļ�
        
        	readTxtFile.Read(txtFilePath,txtDirPath,inforin,inforout);
        	File destFile = new File(txtDirPath + "�����ļ�.xls");
        	if(destFile.exists()){
        		JOptionPane.showMessageDialog(null, "����Excel�ļ�������",
              			"��ʾ",JOptionPane.INFORMATION_MESSAGE);  
        	}
   	
        }         
        
        if (e.getSource() == button2) { 
            
        	// �ڶ���
        	// �����°�ť������һ�����յ�Excel�ļ�
        	
            String stemp = fc.getSelectedFile().getName();
            String date = stemp.replaceAll("(?<!\\d)\\D", "");
            String srcPath = txtDirPath + "�����ļ�.xls";
            String destPath = txtDirPath + "�����ļ�.xls";
            Step2.zuzhang(srcPath, destPath, date);
            File destFile2 = new File(txtDirPath + "�����ļ�.xls");
        	if(destFile2.exists()){
        		JOptionPane.showMessageDialog(null, "����Excel�ļ�������",
              			"��ʾ",JOptionPane.INFORMATION_MESSAGE);  
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

