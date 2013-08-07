package FirstBlood;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import jxl.*;
import jxl.write.*;

public class Step1Phase2 {
	private int row_cnt = 0;
	private Map<String, String> branchDict = new HashMap<String, String>();

	private void writeLabel(WritableSheet wsheet, int row, int col, String str) throws Exception  {
		jxl.write.Label label = new jxl.write.Label(col, row, str);
		WritableCell wc = (WritableCell) label;
		wsheet.addCell(wc);
	}
	
	private void writeNumber(WritableSheet wsheet, int row, int col, long num) throws Exception  {
		jxl.write.Number number = new jxl.write.Number(col, row, num);
		WritableCell wc = (WritableCell) number;
		wsheet.addCell(wc);
	}
	
	private void appendEntry(WritableSheet wsheet, FirstClass fc, String branch) throws Exception {
		System.out.println(fc.name1 + fc.name2 + fc.money);
		writeLabel(wsheet, row_cnt, 0, branch);
		writeLabel(wsheet, row_cnt, 1, fc.name1);
		writeLabel(wsheet, row_cnt, 2, fc.name2);
		writeNumber(wsheet, row_cnt, 3, fc.money);
		row_cnt += 1;
	}

	private void genDictionary(String srcPath) throws Exception {
		Workbook wb = Workbook.getWorkbook(new File(srcPath));
		Sheet sheet = wb.getSheet(0);
		int numRows = sheet.getRows();
		//BufferedWriter bw = new BufferedWriter(new FileWriter("data/branches.txt"));
		//OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("data/branches.txt"), Charset.forName("GBK"));
		ArrayList<String> br = new ArrayList<String>();

		for (int i = 0; i < numRows; i += 1) {
			Cell[] c = sheet.getRow(i);
			if (c[0].getContents().equals("")) {
				if (!c[1].getContents().equals("")) {
					br.add(c[1].getContents());
				} else {
					continue;
				}
			}
			else {
				branchDict.put(c[1].getContents(), c[0].getContents());
			}
		}
		
		/*for (String str : br) {
			osw.write(str + "\r\n");
		}
		osw.close();*/
	}

	private String lookupDictionary(String name) {
		String retval = "";
		if (branchDict.containsKey(name)) retval = branchDict.get(name);
		return retval;
	}
	
	private void process(FirstClass[] inputs, FirstClass[] inputs1, String path) {
		File destFile = new File(path + "初步文件.xls");
		try {
				if (!destFile.exists()) {
				destFile.createNewFile();
			}
			
			WritableWorkbook wwb = Workbook.createWorkbook(destFile);
			WritableSheet wsheet = wwb.createSheet("汇入表", 0);

			genDictionary("data/dictsrc.xls");
			for (FirstClass fc : inputs) {
				if (fc.name1 == null || fc.money ==0) {
					continue;
				} else {
					//System.out.println(fc.name1 + " : " + lookupDictionary(fc.name2));
					appendEntry(wsheet, fc, lookupDictionary(fc.name2));
					
				}
			}
			
			row_cnt = 0;
			WritableSheet wsheet1 = wwb.createSheet("汇出表", 1);
			for (FirstClass fc : inputs1) {
				if (fc.name1 == null || fc.money ==0) {
					continue;
				} else {
					//System.out.println(fc.name1 + " : " + lookupDictionary(fc.name2));
					appendEntry(wsheet1, fc, lookupDictionary(fc.name2));
					
				}
			}
			wwb.write();
			wwb.close();
			
		} catch (Exception e) {
			System.out.println("222");
			System.err.println(e.getMessage());
		}
	}
	
	public static void xiaoyu(FirstClass[] inputs, String path) {
		Step1Phase2 step = new Step1Phase2();
		step.process(inputs, inputs, path);
	}

}