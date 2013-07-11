package FirstBlood;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class readConfig {
	public static void readread(String txtDirPath){
		myWindow.inforin = new String[5];
		myWindow.inforout = new String[5];
	try {        
        String filePath = txtDirPath + "配置文件.txt";
        File file=new File(filePath);
        if(file.isFile() && file.exists()){   //判断配置文件是否存在
        	BufferedReader br = new BufferedReader(new InputStreamReader
            		(new FileInputStream(filePath), Charset.forName("GBK")));            
            String line = null;
            String out[] = new String[5];
            String in[] = new String[5];

            while((line = br.readLine()) != null){

            	String outstart = "慈溪同城外币往账流水：";
                String instart = "慈溪同城外币来账流水：";
                
                String outs = "\\d+(\\s+\\d+)*";
                String ins = "\\d+(\\s+\\d+)*";
                
                Pattern outp = Pattern.compile(outstart, Pattern.CASE_INSENSITIVE);
                Pattern inp = Pattern.compile(instart, Pattern.CASE_INSENSITIVE);
                Pattern outsp = Pattern.compile(outs, Pattern.CASE_INSENSITIVE);
                Pattern insp = Pattern.compile(ins, Pattern.CASE_INSENSITIVE);
                                
                Matcher outm = outp.matcher(line);
                Matcher inm = inp.matcher(line);
                
                if(outm.find()) {
                	Matcher outspm;
                	
                		String outline = br.readLine();
                		outspm = outsp.matcher(outline);
                		if(outspm.find()) {
                			System.out.println(outspm.group());
                			out = outspm.group().split("\\s+");               			
                		}
                		myWindow.inforout = out;
                                	
                }
                else if (inm.find()) {
                	Matcher inspm;
                	
                		String inline = br.readLine();
                		inspm = insp.matcher(inline);
                		if(inspm.find()) {
                			System.out.println(inspm.group());
                			in = inspm.group().split("\\s+");
                		}
                					                		
                		myWindow.inforin = in;
                	}          
            }

            br.close();          
        }else{
            System.out.println("找不到配置文件！");
//            JOptionPane.showMessageDialog(null, "使用默认参数设置",
//          			"提示",JOptionPane.INFORMATION_MESSAGE); 
            myWindow.inforin[0] = "0009501";
            myWindow.inforin[1] = "0009507";
            myWindow.inforout[0] = "0009501";
            myWindow.inforout[1] = "0009507";
        }
        } catch (Exception ee) {
            System.out.println("读取配置文件错误！");
//            JOptionPane.showMessageDialog(null, "使用默认参数设置",
//          			"提示",JOptionPane.INFORMATION_MESSAGE);  
            myWindow.inforin[0] = "0009501";
            myWindow.inforin[1] = "0009507";
            myWindow.inforout[0] = "0009501";
            myWindow.inforout[1] = "0009507";
            ee.printStackTrace();
        }	
	}
}
   
