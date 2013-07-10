/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author diana
 */
//
import java.io.BufferedReader;  
import java.io.FileInputStream;   
import java.io.InputStreamReader;
import java.io.File;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number; 

import java.util.Date;



public class Test{
    public static void readTxtFile(String filePath, String[] limit){
        try{
            int num = 0;
            File file=new File(filePath);
            if(file.isFile() && file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("GBK")));
                String line;
                FirstClass first[] = new FirstClass[30];
                Set<String> limitString = new HashSet<String>();
                for(int s= 0;s<limit.length;s++){
                    limitString.add(limit[s]);
                }
                while((line = br.readLine()) != null){
                    //定义正则表达式
                    String outstart = "=+慈溪同城外币往账流水=+";//往帐数据开始
                    String instart = "=+慈溪同城外币来账流水=+";//来张数据开始
                    String end = "\\d\\s+record\\(s\\)\\sselected";//读取数据库输出，可以根据第一个数字判断程序有没有读全数据使程序更健壮
                    String outentry = "^(\\d+\\s+)"
                                + "(\\d+\\s+)"//币种-2
                                + "(\\d+\\s+){2}"
                                + "\\S+\\s+"
                                + "(\\d+\\s+)"//第一个9501-4
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"//公司名称-5
                                + "(\\d+\\s)"//第二个9501-8
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"//公司名称-9
                                + "(\\d+)";//amount-12
                    
                    String inentry = "^(\\d+\\s+)"//币种-1
                                + "(\\d+\\s+){2}"
                                + "\\S+\\s+"
                                + "(\\d+\\s+)"//第一个9501-3
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"//公司名称-4
                                + "(\\d+\\s+)"//第二个9501-7
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"//公司名称-8
                                + "(\\d+)"; //amount-11
                    
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
                    //System.out.println("Matcher finished");
                    
                    if (outm.find()) {//来帐数据开始
                        Matcher outentrym;
                        while (true) {//循环读来帐条目
                            String outline = br.readLine();
                            outentrym = outentryp.matcher(outline);
                            if (outentrym.find()) {//判断为条目
                                //if((outentrym.group(6).trim().equals("0009501"))||(outentrym.group(6).trim().equals("0009507"))){
                                if(limitString.contains(outentrym.group(8).trim())){
                                    //如果和前面某条记录的name1和name2相等，则累加
                                    boolean IsAdded = false;
                                    for(int t=0; t < num;t++){
                                        if(first[num].name1.equals(outentrym.group(5).trim()) 
                                                && first[num].name2.equals(outentrym.group(9).trim())){
                                            first[num].money += Long.parseLong(outentrym.group(12).trim());
                                            IsAdded = true;
                                            break;
                                        }
                                        FirstClass temp = new FirstClass();
                                        temp.currtype = outentrym.group(2).trim();
                                        temp.pyrBank = outentrym.group(4).trim();
                                        temp.name1 = outentrym.group(5).trim();
                                        temp.name2 = outentrym.group(8).trim();
                                        temp.pyeBank = outentrym.group(9).trim();
                                        temp.money = Long.parseLong(outentrym.group(12).trim());
                                        first[num] = temp;
                                        //System.out.println(num);
                                        System.out.println(first[num].currtype + " " + first[num].pyrBank + " " + first[num].name1 + " " 
                                                + first[num].name2 + " " + first[num].pyeBank + " " + first[num].money);
                                    }
                                    
                                    num++;
                                }
                                else if(limitString.contains(outentrym.group(4).trim())){
                                    
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
                                //if(inentrym.group(6).trim().equals("0009501")||inentrym.group(6).trim().equals("0009507")){
                                if(limitString.contains(inentrym.group(7).trim())){
                                    FirstClass temp = new FirstClass();
                                    temp.currtype = inentrym.group(1).trim();
                                    temp.pyrBank = inentrym.group(3).trim();
                                    temp.name1 = inentrym.group(4).trim();
                                    temp.name2 = inentrym.group(7).trim();
                                    temp.pyeBank = inentrym.group(8).trim();
                                    temp.money = Long.parseLong(inentrym.group(11).trim());
                                    first[num] = temp;
                                    //System.out.println(num);
                                    System.out.println(first[num].currtype + " " + first[num].pyrBank + " " + first[num].name1 + " " 
                                            + first[num].name2 + " " + first[num].pyeBank + " " + first[num].money);
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
//                try {  
//                    //get file.  
//                    Workbook wb = Workbook.getWorkbook(new File("F:/test.xls"));  
//                    //open a copy file(new file), then write content with same content with Test.xls.    
//                    WritableWorkbook book = Workbook.createWorkbook(new File("F:/test.xls"), wb);  
//                    //add a Sheet.  
//                    WritableSheet sheet = book.createSheet("Sheet11", 0); //参数0表示是第一页 
//                    Label label1 = new Label(0,0,"name1");
//                    sheet.addCell(label1);
//                    Label label2 = new Label(1,0,"name2");
//                    sheet.addCell(label2);
//                    Label label3 = new Label(2,0,"money");
//                    sheet.addCell(label3);
//                    for(int i = 0;i < num;i++) {
//                        Label label01 = new Label(0,i + 1,first[i].name1);
//                        System.out.println(first[i].name1);
//                        sheet.addCell(label01);
//                        Label label02 = new Label(1,i + 1,first[i].name2);
//                        sheet.addCell(label02);
//                        Number number = new Number(2,i + 1,first[i].money);
//                        sheet.addCell(number);
//                    }
//                    book.write();  
//                    book.close();  
//                } catch (Exception e) {  
//                    e.printStackTrace();  
//                } 
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void main(String argv[]){
        Test step = new Test();
        String[] ss = new String[]{"0009501","0009507"};
	step.readTxtFile("D:\\Documents and Settings\\桌面\\cx\\小包数据\\原始数据\\cxls_wb_jrnl_20130604.txt",ss);
    }         
}

class FirstClass {
    String currtype;//币种
    String pyrBank;//第一个9501
    String pyeBank;//第二个9501
    String name1;
    String name2;
    long money;
}

