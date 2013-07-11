package FirstBlood;

import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.*;

public class readTxtFile {
     public static FirstClass first[] = new FirstClass[40]; 	
     
     public static void read(String filePath, String dirPath, String in[], String out[]){    
    	int num = 0;
        try {
        	
            File file=new File(filePath);
            if(file.isFile() && file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("GBK")));
                String line;
                FirstClass first[] = new FirstClass[40];
                Set<String> limitString = new HashSet<String>();
                for(int s= 0;s<in.length;s++){
                    limitString.add(in[s]);
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
                    
                    if (outm.find()) {//往帐数据开始
                        Matcher outentrym;
                        while (true) {//循环读来帐条目
                            String outline = br.readLine();
                            outentrym = outentryp.matcher(outline);
                            
                            if (outentrym.find()) {//判断为条目
                                if(limitString.contains(outentrym.group(8).trim())){
                                    System.out.println(outentrym.group(2).trim() + " " + outentrym.group(4).trim() + " " + outentrym.group(5).trim() + " " 
                                                + outentrym.group(8).trim() + " " + outentrym.group(9).trim() + " " + outentrym.group(12).trim());
                                    //如果和前面某条记录的name1和name2相等，则累加
                                    boolean IsAdded = false;
                                    if(num != 0){
                                        //判断是否有可累加的数据
                                        for(int t=0; t < num;t++){
                                        if(((first[t].name1).equals(outentrym.group(5).trim())) 
                                            && ((first[t].name2).equals(outentrym.group(9).trim()))){
                                            first[t].money += Long.parseLong(outentrym.group(12).trim());
                                            IsAdded = true;
                                            break;
                                        }                      
                                        }
                                    }
                                    
                                    if(IsAdded == false){
                                        FirstClass temp = new FirstClass();
                                        temp.currtype = outentrym.group(2).trim();
                                        temp.pyrBank = outentrym.group(4).trim();
                                        temp.name1 = outentrym.group(5).trim();
                                        temp.pyeBank = outentrym.group(8).trim();
                                        temp.name2 = outentrym.group(9).trim();                                        
                                        temp.money = Long.parseLong(outentrym.group(12).trim());
                                        first[num] = temp;
                                        num++;                                        
                                    }
                              }
                                else if(limitString.contains(outentrym.group(4).trim())){
//                                    System.out.println(outentrym.group(2).trim() + " " + outentrym.group(4).trim() + " " + outentrym.group(5).trim() + " " 
//                                                + outentrym.group(8).trim() + " " + outentrym.group(9).trim() + " " + outentrym.group(12).trim());
                                    //判断是否有可抵消的数据
                                    for(int t=0; t < num;t++){
                                        if(first[t].name1.equals(outentrym.group(9).trim()) 
                                                && first[t].name2.equals(outentrym.group(5).trim())){
                                            first[t].money -= (Long.parseLong(outentrym.group(12).trim()));
                                            if(first[t].money < 0)
                                                first[t].money = 0;
                                        }                                       
                                    }                                    
                                }
                            } else {
                                endm = endp.matcher(outline);
                                if (endm.find()) {
                                        break;
                                }
                            }
                        }
                        System.out.println();
                    } else if (inm.find()) {//来帐数据开始
                        Matcher inentrym;
                        while(true){//循环读取来帐条目
                            String inline = br.readLine();
                            inentrym = inentryp.matcher(inline);
                            if(inentrym.find()){
                                if(limitString.contains(inentrym.group(7).trim())){
                                    System.out.println(inentrym.group(1).trim() + " " +inentrym.group(3).trim() + " " + inentrym.group(4).trim() + " " 
                                                + inentrym.group(7).trim() + " " + inentrym.group(8).trim() + " " + inentrym.group(11).trim());
                                    boolean IsAdded = false;
                                    if(num != 0){
                                        //判断是否有可累加的数据
                                        for(int t=0; t < num;t++){
                                        if(first[t].name1.equals(inentrym.group(4).trim()) 
                                                && first[t].name2.equals(inentrym.group(8).trim())){
                                            first[t].money += (Long.parseLong(inentrym.group(11).trim()));
                                            IsAdded = true;
                                            break;
                                        }                      
                                    }
                                    }
                                    if(IsAdded == false){
                                        FirstClass temp = new FirstClass();
                                        temp.currtype = inentrym.group(1).trim();
                                        temp.pyrBank = inentrym.group(3).trim();
                                        temp.name1 = inentrym.group(4).trim();
                                        temp.name2 = inentrym.group(8).trim();
                                        temp.pyeBank = inentrym.group(7).trim();
                                        temp.money = Long.parseLong(inentrym.group(11).trim());
                                        first[num] = temp;
                                        num++;
                                    }
                                } else if(limitString.contains(inentrym.group(3).trim())){
                                    //判断是否有可抵消的数据
                                    for(int t=0; t < num;t++){
                                        if(first[t].name1.equals(inentrym.group(8).trim()) 
                                                && first[t].name2.equals(inentrym.group(4).trim())){
                                            first[t].money -= (Long.parseLong(inentrym.group(11).trim()));
                                            if(first[t].money < 0)
                                                first[t].money = 0;
                                        }                                       
                                    }                                    
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
                System.out.println();System.out.println();System.out.println();
//                for(int y = 0;y < num;y++)
//                    System.out.println(first[y].currtype + " " + first[y].pyrBank + " " + first[y].name1 + " " 
//                                        + first[y].name2 + " " + first[y].pyeBank + " " + first[y].money);
                //将FirstClass数组写入文件
            }
        }catch(Exception e){            
            System.out.println("111");
            System.err.println(e.getMessage());
        }
    }
      
    public static void Read(String txtPath, String dirPath, String in[], String out[]) {
    	
	readTxtFile.read(txtPath,dirPath, in, out);    
        Step1Phase2.xiaoyu(first, dirPath);
    }

}
