package FirstBlood;
import java.io.*;
import jxl.*;
import jxl.write.*;

public class Step1Phase2 {
	private int row_cnt = 0;	
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
	
	private void appendEntry(WritableSheet wsheet, FirstClass fc) throws Exception {
		System.out.println(fc.name1 + fc.name2 + fc.money);
		writeLabel(wsheet, row_cnt, 0, fc.name1);
		writeLabel(wsheet, row_cnt, 1, fc.name2);
		writeNumber(wsheet, row_cnt, 2, fc.money);
		row_cnt += 1;
	}
	
	private void process(FirstClass[] inputs, String path) {
		File destFile = new File(path + "初步文件.xls");
		try {
				if (!destFile.exists()) {
				destFile.createNewFile();
			}
			
			WritableWorkbook wwb = Workbook.createWorkbook(destFile);
			WritableSheet wsheet = wwb.createSheet("output", 0);
			
			
			
			for (FirstClass fc : inputs) {
				if (fc.name1 == null) {
					continue;
				} else {
					appendEntry(wsheet, fc);
					
				}
			}
			
			wwb.write();
			wwb.close();
			
		} catch (Exception e) {
			System.out.println("222");
			System.err.println(e.getMessage());
		}
	}
	
	public static void xiaoyu(FirstClass[] inputs, String path ) {
		Step1Phase2 step = new Step1Phase2();
		step.process(inputs, path);
	}
}
