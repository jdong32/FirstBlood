package mywindow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.io.BufferedReader;  
import java.io.FileInputStream;  
import java.io.FileReader;  
import java.io.IOException;  
import java.io.InputStreamReader;
import java.io.*;
import java.io.File;
import  javax.swing.*;  
import java.awt.Container; 
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyWindow{
    public static void readTxtFile(String filePath){
        try{
            int num = 0;
            File file=new File(filePath);
            if(file.isFile() && file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("GBK")));
                String line;
                FirstClass first[] = new FirstClass[30]; 
                while((line = br.readLine()) != null){
                    //定义正则表达式
                    String outstart = "=+慈溪同城外币往账流水=+";//往帐数据开始
                    String instart = "=+慈溪同城外币来账流水=+";//来张数据开始
                    String end = "\\d\\s+record\\(s\\)\\sselected";//读取数据库输出，可以根据第一个数字判断程序有没有读全数据使程序更健壮
                    String outentry = "^(\\d+\\s+){4}"
                            + "\\S+\\s+"
                            + "(\\d+\\s+){2}"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+\\s)"
                            + "(\\d+\\s+)"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+)";//往帐条目\\d为数值\\S为非空白\\s为空白{}里表示重复几次+表示重复一次或多次*表示0次或多次
                    String inentry = "^(\\d+\\s+){3}"
                            + "\\S+\\s+"
                            + "(\\d+\\s+){2}"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+\\s+)"
                            + "(\\d+\\s+)"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+)";//往帐条目\\d为数值\\S为非空白\\s为空白{}里表示重复几次+表示重复一次或多次*表示0次或多次
                    
                    //为正则建立Pattern
                    Pattern outp = Pattern.compile(outstart, Pattern.CASE_INSENSITIVE);
                    Pattern inp = Pattern.compile(instart, Pattern.CASE_INSENSITIVE);
                    Pattern endp = Pattern.compile(end, Pattern.CASE_INSENSITIVE);
                    Pattern outentryp = Pattern.compile(outentry, Pattern.CASE_INSENSITIVE);
                    Pattern inentryp = Pattern.compile(inentry, Pattern.CASE_INSENSITIVE);
                    
                    //为正则建立Matcher，之后用matcher的group函数抓取匹配的各个数值
                    Matcher outm = outp.matcher(line);
                    Matcher inm = inp.matcher(line);
                    Matcher endm;
                    
                    if (outm.find()) {//来帐数据开始
                        Matcher outentrym;
                        while (true) {//循环读来帐条目
                            String outline = br.readLine();
                            outentrym = outentryp.matcher(outline);
                            if (outentrym.find()) {//判断为条目
                                if((outentrym.group(6).trim().equals("0009501"))||(outentrym.group(6).trim().equals("0009507"))){
                                    FirstClass temp = new FirstClass();
                                    temp.name1 = outentrym.group(4).trim();
                                    temp.name2 = outentrym.group(8).trim();
                                    temp.money = Long.parseLong(outentrym.group(11).trim());
                                    first[num] = temp;
                                    num++;
                                }
                            } else {
                                endm = endp.matcher(outline);
                                if (endm.find()) {
                                        break;
                                }
                            }
                        }
                        System.out.println();
                    } else if (inm.find()) {//往帐数据开始
                        //your job here :)
                        Matcher inentrym;
                        while(true){//循环读取往帐条目
                            String inline = br.readLine();
                            inentrym = inentryp.matcher(inline);
                            if(inentrym.find()){
                                if(inentrym.group(6).trim().equals("0009501")||inentrym.group(6).trim().equals("0009507")){
                                    FirstClass temp = new FirstClass();
                                    temp.name1 = inentrym.group(4).trim();
                                    temp.name2 = inentrym.group(8).trim();
                                    temp.money = Long.parseLong(inentrym.group(11).trim());
                                    first[num] = temp;
                                    num++;
                                }                       
                            }
                            else{
                                endm = endp.matcher(inline);
                                if(endm.find()){
                                    break;
                                }
                            }
                        }
                    }
                }
                //将FirstClass数组写入文件
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void main(String argv[]){
        MyWindow step = new MyWindow();
	step.readTxtFile("D:\\Documents and Settings\\桌面\\cx\\小包数据\\原始数据\\cxls_wb_jrnl_20130604.txt");
    }         
}

//public class MyWindow {
//    public static void main(String args[])
//    throws IOException
//    {
//        FileInputStream fl = new FileInputStream("D:\\Documents and Settings\\桌面\\cx\\小包数据\\原始数据\\cxls_wb_jrnl_20130603.txt");
//        InputStreamReader isr  = new InputStreamReader(fl,"GBK");
//        BufferedReader br = new BufferedReader(isr);
//        String line;
//        String[] arrs = new String[20];
//        while((line = br.readLine())!=null){
//            String temp = line.replace("	", " ");
//            String temp = line.replace("	", " ");
//            System.out.println(line);
//            arrs = temp.split("\\s+");
//            System.out.println(temp + "\t\t\t\t\t"+ arrs.length);
//            //arrs = temp.split("\\s+");
//        }       
//    }    




//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//
//
//public class MyWindow {
//	
//    public static void readTxtFile(String filePath){
//    	int num = 0;
//        try {
//                String encoding="GBK";
//                File file=new File(filePath);
//                if(file.isFile() && file.exists()){ //判断文件是否存在
//                    InputStreamReader read = new InputStreamReader(
//                    new FileInputStream(file),encoding);//考虑到编码格式
//                    BufferedReader bufferedReader = new BufferedReader(read);
//                    //String lineTxt = null;
//                    String line = null;
//                    while((line = bufferedReader.readLine()) != null ){
//                    	if(line.startsWith("0"))  {
//                    		String temp = line.replace("	", " ");
//                    		//String stringarray[] = line.split("\\s+");
//                                String stringarray[] = temp.split(" ");
//                    		String [] array = new String [20];
//                    		FirstClass first[] = new FirstClass[30];  
//                    		       
//                    		int i = 0;
//                    		
//                    		
//                            for(String stemp: stringarray) { //将text文件内容 读入字符串数组
//                            	if(stemp.equals("") == false) { //跳过空格
//                            		array[i] = stemp;                                               	  
//                            	    i++;
//                            	}
//                            }
//                            if(array[8].equals("0009501") || array[8].equals("0009507")) {
//                                                           
//                                FirstClass ff = new FirstClass();
//                                if(!(array[10].charAt(0) >= 'A' && array[10].charAt(7) <= 'Z')){
//                                	ff.name1 = array[7];
//                                	ff.name2 = array[10];
//                                	ff.money = Long.parseLong(array[11].replace(".", ""));
//                                	first[num] = ff;
//                                	System.out.println(first[num].name1);
//                                	System.out.println(first[num].name2);
//                                	System.out.println(first[num].money);
//                                	num++;
//                                	
//                                }
//                            	
//                            }
//                    	
//                    	}
//                    	
//                     	if(line.startsWith("1"))  {
//                    		//String temp = lineTxt.replace("	", " ");
//                                //String temp = lineTxt.replace("	", " ");
//                                String temp = line.replace("	", " ");
//                                //String temu = lineTxt.replace(" ", " ");
//                    		//String stringarray[] = temp.split("\\s+");
//                                String stringarray[] = temp.split(" ");
//                    		String [] array = new String [20];
//                    		FirstClass first[] = new FirstClass[30];  
//                    		       
//                    		int i = 0;
//                    		
//                    		
//                            for(String stemp: stringarray) { //将text文件内容 读入字符串数组
//                            	if(stemp.equals("") == false) { //跳过空格
//                            		array[i] = stemp;                                               	  
//                            	    i++;
//                            	}
//                            }
//                            if(array[7].equals("0009501") || array[7].equals("0009507")) {
//                                                           
//                                FirstClass ff = new FirstClass();
//                                if(!(array[9].charAt(0) >= 'A' && array[9].charAt(0) <= 'Z')){
//                                	ff.name1 = array[6];
//                                	ff.name2 = array[9];
//                                	ff.money = Long.parseLong(array[10].replace(".", ""));
//                                	first[num] = ff;
//                                	System.out.println(first[num].name1);
//                                	System.out.println(first[num].name2);
//                                	System.out.println(first[num].money);
//                                	num++;
//                                	
//                                }
//                           	
//                            }
//                           
//                    	
//                    	}
//                    }
//                    read.close();
//        }else{
//            System.out.println("找不到指定的文件");
//        }
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }
//     
//    }
//     
//    public static void main(String argv[]){
//        String filePath = "D:\\Documents and Settings\\桌面\\cx\\小包数据\\原始数据\\cxls_wb_jrnl_20130603.txt";
////      "res/";
//        readTxtFile(filePath);       
//    }
//
//}

















//package mywindow;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import java.io.BufferedReader;  
//import java.io.FileInputStream;  
//import java.io.FileReader;  
//import java.io.IOException;  
//import java.io.InputStreamReader;
//import java.io.*;
//import java.io.File;
//import  javax.swing.*;  
//import java.awt.Container; 
//
//class Mid{
//    String PYRACCTNAME;
//    String PYEACCTNAME;
//    String AMOUNT;
//}
//
//
//public class MyWindow extends JFrame implements ActionListener {
//	private static final long serialVersionUID = 1L;
//    JButton button;
//    JButton Select;
//    JButton btnOK;
//    JTextField textfield;
//    JPanel p;
//    JFileChooser fc = new JFileChooser();
//    TextArea area;
//
//public MyWindow() {
//        p = new JPanel(); // 建立一个面板
//        this.getContentPane().add(p);// 把面板添加到框架
//        
//        textfield = new JTextField(10);
//        p.add(textfield); // 把一个文本框添加到面板
//        Select = new JButton("浏览");
//        p.add(Select); // 把一个浏览按钮添加到面板
//        Select.addActionListener(this);
//        
//        btnOK = new JButton("生成初步Excel文件");
//        p.add(btnOK);        
//        btnOK.addActionListener(this);
//        
//        button = new JButton("生成最终Excel文件");
//        p.add(button);
//        button.addActionListener(this);
//    }
//public void actionPerformed(ActionEvent e) {   
//        String path = null;
//        // 当按下选择按钮，打开一个文件选择，文本框显示文件路径
//        if (e.getSource() == Select) {
//            int intRetVal = fc.showOpenDialog(this);
//            if (intRetVal == JFileChooser.APPROVE_OPTION) {
//                textfield.setText(fc.getSelectedFile().getPath());
//                path = fc.getSelectedFile().getPath();
//            }
//        }        
//        else if (e.getSource() == btnOK) // 第一步// 当按下按钮，生成一个初步的Excel文件
//        {   
////            Mid[] midData = new Mid[100]; 
////            int midCount = 0;
////            try{
////                FileInputStream fl = new FileInputStream(path);
////                InputStreamReader isr;
////                try{
////                    isr = new InputStreamReader(fl,"GBK");
////                    BufferedReader br = new BufferedReader(isr);
////                    String line;
////                    String[] arrs = new String[20];
////
////                    while((line = br.readLine())!=null){
////                        String temp = line.replace("	", " ");
////                        arrs = temp.split("\\s+");
////                        if(arrs[0] == "0009501" || arrs[0] == "0009507"){
////                            midData[midCount].PYRACCTNAME = arrs[7];
////                            midData[midCount].PYEACCTNAME = arrs[10];
////                            midData[midCount].AMOUNT = arrs[arrs.length - 1];
////                            midCount++;
////                        }
////                        else if(arrs[4] == "0009501" || arrs[4] == "0009507"){
////                            midData[midCount].PYRACCTNAME = arrs[2];
////                            midData[midCount].PYEACCTNAME = arrs[5];
////                            midData[midCount].AMOUNT = arrs[arrs.length - 1];
////                            midCount++;
////                        }
////
////                    }
////                    br.close();
////                    isr.close();
////                    fl.close();
////                }catch(IOException i){
////                    System.out.println("IOExcption");
////                }
////
////            }catch(FileNotFoundException t){
////                System.out.println("File now found!");
////            }               
//        }         
//        else if (e.getSource() == button) {
//                String[] arrs = path.split("/");
//                String date = arrs[arrs.length - 1].replaceAll("(?<!\\d)\\D", "");
//            
//        	// 第二步
//        	// 当按下按钮，生成一个最终的Excel文件       	
//        }      
//    }
//    
//public static void main(String[] args) 
//{
//        MyWindow frame = new MyWindow();
//        
//        frame.setBackground(Color.yellow);
//        frame.setName("77");
//               
//        frame.setSize(200, 250);
//        frame.setLocation(600,300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
//}
