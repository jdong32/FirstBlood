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
     public static FirstClass first[] = new FirstClass[400]; 	
     
     public static void read(String filePath, String dirPath, String in[]){ 
    	 for(int i=0; i<400; i++) {
        	 FirstClass ini = new FirstClass();
         	first[i] = ini;
         	
         }
    	int num = 0;
        try {
        	float amount = 0;
            File file=new File(filePath);
            float ratef = Float.parseFloat(myWindow.rate);
            if(file.isFile() && file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("GBK")));
                String line;
                 
                Set<String> limitString = new HashSet<String>();
                for(int s= 0;s<in.length;s++){
                    limitString.add(in[s]);
                }
                while((line = br.readLine()) != null){
                  
                	String outstart = "=+慈溪同城外币往账流水=+";//往帐数据开始
                    String instart = "=+慈溪同城外币来账流水=+";//来张数据开始
                    String end = "\\d\\s+record\\(s\\)\\sselected";
                    String outentry = "^(\\d+\\s+)"
                                + "(\\d+\\s+)"
                                + "(\\d+\\s+){2}"
                                + "\\S+\\s+"
                                + "(\\d+\\s+)"
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"
                                + "(\\d+\\s)"
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"
                                + "(\\d+)";//amount-12
                    
                    String inentry = "^(\\d+\\s+)"
                                + "(\\d+\\s+){2}"
                                + "\\S+\\s+"
                                + "(\\d+\\s+)"
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"
                                + "(\\d+\\s+)"
                                + "\\d+\\s+"
                                + "((\\S+(\\s+\\S+)*)\\s+)"
                                + "(\\d+)"; //amount-11
                    
                   
                    Pattern outp = Pattern.compile(outstart, Pattern.CASE_INSENSITIVE);
                    Pattern inp = Pattern.compile(instart, Pattern.CASE_INSENSITIVE);
                    Pattern endp = Pattern.compile(end, Pattern.CASE_INSENSITIVE);
                    Pattern outentryp = Pattern.compile(outentry, Pattern.CASE_INSENSITIVE);
                    Pattern inentryp = Pattern.compile(inentry, Pattern.CASE_INSENSITIVE);
                    
                    Matcher outm = outp.matcher(line);
                    Matcher inm = inp.matcher(line);
                    Matcher endm;
                    
                    if (outm.find()) {
                        Matcher outentrym;
                        while (true) { 
                            String outline = br.readLine();
                            outentrym = outentryp.matcher(outline);
                            
                            if (outentrym.find()) {                       	
                                if(limitString.contains(outentrym.group(8).trim())){
                                	if(outentrym.group(2).trim().equals("38")){
                                		amount = Long.parseLong(outentrym.group(12).trim()) * ratef;
                                		System.out.println(amount);
                                	}else{
                                		amount = Long.parseLong(outentrym.group(12).trim());
                                	}
                                 
                                    boolean IsAdded = false;
                                    if(num != 0){
                                        
                                        for(int t=0; t < num;t++){
                                        if(((first[t].name1).equals(outentrym.group(5).trim())) 
                                            && ((first[t].name2).equals(outentrym.group(9).trim()))){
                                            first[t].money += (long)amount;
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
                                        temp.money = (long)amount;
                                        first[num] = temp;
                                        num++;                                        
                                    }
                              }
                                else if(limitString.contains(outentrym.group(4).trim())){
                                	if(outentrym.group(2).trim().equals("38")){
                                		amount = Long.parseLong(outentrym.group(12).trim()) * ratef;
                                		System.out.println(amount);
                                	}else{
                                		amount = Long.parseLong(outentrym.group(12).trim());
                                	}
                                  
                                    for(int t=0; t < num;t++){
                                        if(first[t].name1.equals(outentrym.group(9).trim()) 
                                                && first[t].name2.equals(outentrym.group(5).trim())){
                                            first[t].money -= ((long)amount);
                                         
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
                    } else if (inm.find()) { 
                        Matcher inentrym;
                        while(true){ 
                            String inline = br.readLine();
                            inentrym = inentryp.matcher(inline);
                            if(inentrym.find()){
                                if(limitString.contains(inentrym.group(7).trim())){
                                	if(inentrym.group(1).trim().equals("38")){
                                		amount = Long.parseLong(inentrym.group(11).trim()) * ratef;
                                		System.out.println(amount);
                                	}else{
                                		amount = Long.parseLong(inentrym.group(11).trim());
                                	}

                                    boolean IsAdded = false;
                                    if(num != 0){
                                       
                                        for(int t=0; t < num;t++){
                                        if(first[t].name1.equals(inentrym.group(4).trim()) 
                                                && first[t].name2.equals(inentrym.group(8).trim())){
                                            first[t].money += (long)amount;
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
                                        temp.money = (long)amount;
                                        first[num] = temp;
                                        num++;
                                    }
                                } else if(limitString.contains(inentrym.group(3).trim())){
                                	if(inentrym.group(1).trim().equals("38")){
                                		amount = Long.parseLong(inentrym.group(11).trim()) * ratef;
                                		System.out.println(amount);
                                	}else{
                                		amount = Long.parseLong(inentrym.group(11).trim());
                                	}
                                  
                                    for(int t=0; t < num;t++){
                                        if(first[t].name1.equals(inentrym.group(8).trim()) 
                                                && first[t].name2.equals(inentrym.group(4).trim())){
                                            first[t].money -= (long)amount;

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

                for(int y = 0;y < num;y++){
//                    System.out.println(first[y].currtype + " " + first[y].pyrBank + " " + first[y].name1 + " " 
//                                        + first[y].name2 + " " + first[y].pyeBank + " " + first[y].money);
                    if(first[y].money < 0)
                        first[y].money = 0;
//                    System.out.println(first[y].currtype + " " + first[y].pyrBank + " " + first[y].name1 + " " 
//                                        + first[y].name2 + " " + first[y].pyeBank + " " + first[y].money);
//                    System.out.println();
                }
       
            }
//            float ratef = Float.parseFloat(myWindow.rate);
			float temp;		
			for (int i = 0; i < 400; i++) {
				if ((first[i].currtype != null)
						&& first[i].currtype.equals("38")) {
					temp = ratef * first[i].money;
					System.out.println(temp);
					first[i].money = (long) temp;
					System.out.println(first[i].money);
				}
			}

        }catch(Exception e){            
      
            System.err.println(e.getMessage());
        }
    }
      
    public static void Read(String txtPath, String dirPath, String in[]) {
    	
	readTxtFile.read(txtPath,dirPath, in);    
        Step1Phase2.xiaoyu(first, dirPath);
    }

}
