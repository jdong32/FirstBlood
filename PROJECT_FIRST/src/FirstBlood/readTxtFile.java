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
     public static void read(String filePath, String in[], String out[]){    
    	int num = 0;
        try {
        	File file=new File(filePath);
        	
            if(file.isFile() && file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("GBK")));
                String line;
				Set<String> limitStringIn = new HashSet<String>();
                                Set<String> limitStringOut = new HashSet<String>();
                                for(int s= 0;s<in.length;s++){
                                    limitStringIn.add(in[s]);
                                 }
                                for(int s= 0;s<out.length;s++){
                                    limitStringOut.add(out[s]);
                                 }

                while((line = br.readLine()) != null){
                    
                    String outstart = "慈溪同城外币往账流水";
                    String instart = "慈溪同城外币来账流水";
                    String end = "\\d\\s+record\\(s\\)\\sselected";
                    String outentry = "^(\\d+\\s+){4}"
                            + "\\S+\\s+"
                            + "(\\d+\\s+){2}"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+\\s)"
                            + "(\\d+\\s+)"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+)";
                    String inentry = "^(\\d+\\s+){3}"
                            + "\\S+\\s+"
                            + "(\\d+\\s+){2}"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+\\s+)"
                            + "(\\d+\\s+)"
                            + "((\\S+(\\s+\\S+)*)\\s+)"
                            + "(\\d+)";                    
                   
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
                                if(limitStringOut.contains(outentrym.group(6).trim())){
                                    FirstClass temp = new FirstClass();
                                    temp.name1 = outentrym.group(4).trim();
                                    temp.name2 = outentrym.group(8).trim();
                                    temp.money = Long.parseLong(outentrym.group(11).trim());
                                    first[num] = temp;
                                    System.out.println(first[num].name1 + first[num].name2 + first[num].money);
                                    num++;
                                }
                            } else {
                                endm = endp.matcher(outline);
                                if (endm.find()) {
                                        break;
                                }
                            }
                        }
                        
                    } else if (inm.find()) {
                        
                        Matcher inentrym;
                        while(true){
                            String inline = br.readLine();
                            inentrym = inentryp.matcher(inline);
                            if(inentrym.find()){
                                //if(inentrym.group(6).trim().equals("0009501")||inentrym.group(6).trim().equals("0009507")){
                                if(limitStringIn.contains(inentrym.group(6).trim())){
                                    FirstClass temp = new FirstClass();
                                    temp.name1 = inentrym.group(4).trim();
                                    temp.name2 = inentrym.group(8).trim();
                                    temp.money = Long.parseLong(inentrym.group(11).trim());
                                    first[num] = temp;
                                    System.out.println(first[num].name1 + first[num].name2 + first[num].money);
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
                for(int ii=num;ii<40;ii++) {
            		FirstClass ff = new FirstClass();
            		first[ii] = ff;
            	}
            }
        }catch(Exception e){
        	System.out.println("111");
            System.err.println(e.getMessage());
        }
    }
      
    public static void Read(String txtPath, String dirPath, String in[], String out[]) {

    	readTxtFile.read(txtPath, in, out);    
        Step1Phase2.xiaoyu(first, dirPath);
    }

}
