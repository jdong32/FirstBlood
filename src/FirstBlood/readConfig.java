package FirstBlood;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


public class readConfig {
	public static void readread(String txtDirPath){
		myWindow.inforin = new String[5];
	try {        
        String filePath = "收款行编号.txt";
        File file=new File(filePath);
        if(file.isFile() && file.exists()){   //判断配置文件是否存在
        	BufferedReader br = new BufferedReader(new InputStreamReader
            		(new FileInputStream(filePath), Charset.forName("GBK")));            
            String line = null;
            String in[] = new String[5];

            while((line = br.readLine()) != null){
                String instart = "收款行编号：（请空格分开）";

                String ins = "\\d+(\\s+\\d+)*";
                
                Pattern inp = Pattern.compile(instart, Pattern.CASE_INSENSITIVE);

                Pattern insp = Pattern.compile(ins, Pattern.CASE_INSENSITIVE);

                Matcher inm = inp.matcher(line);
                
                if (inm.find()) {
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
            
            JOptionPane.showMessageDialog(null, "找不到 收款行编号.txt，使用默认收款行编号！",
          			"提示",JOptionPane.INFORMATION_MESSAGE); 
            myWindow.inforin[0] = "0009501";
            myWindow.inforin[1] = "0009507";

        }
        } catch (Exception ee) {
           
            JOptionPane.showMessageDialog(null, "读取 收款行编号.txt 错误，使用默认收款行编号！",
          			"提示",JOptionPane.INFORMATION_MESSAGE);  
            myWindow.inforin[0] = "0009501";
            myWindow.inforin[1] = "0009507";

            ee.printStackTrace();
        }	
	}
}
   
