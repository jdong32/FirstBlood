package FirstBlood;

import java.io.*;
import java.util.*;
import jxl.*;
import jxl.write.*;

public class Step2 {
	private class Entry {	
		//inner class practice, prevent outside access!
		private String companyName;
		private long amountOfMoney;
		
		private Entry (String name, long amount) {
			companyName = name;
			amountOfMoney = amount;
		}
		private String getName() {
			return companyName;
		}
		private long getAmount() {
			return amountOfMoney;
		}
	}
	private String srcPath, destPath;
	private String date;
	private ArrayList<Entry> entries = new ArrayList<Entry>();
	
	Step2(String src_path, String dest_path, String input_date) {
		srcPath = src_path;
		destPath = dest_path;
		date = input_date;
	}
	
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
	
	private void appendEntry(WritableSheet wsheet, int row, int endCol, Entry e) throws Exception {
		writeLabel(wsheet, row, 0, e.getName());
		writeNumber(wsheet, row, endCol, e.getAmount());
		writeNumber(wsheet, row, 1, calculateSum(wsheet, row, endCol));
	}
	
	private void updateNumber(WritableSheet wsheet, int row, int col, long num) throws Exception {
		WritableCell wc = wsheet.getWritableCell(col, row);
		jxl.write.Number number = (jxl.write.Number) wc;
		number.setValue(num);
	}
	
	private void modifyEntry(WritableSheet wsheet, int row, int endCol, Entry e) throws Exception {
		writeLabel(wsheet, row, 0, e.getName());
		writeNumber(wsheet, row, endCol, e.getAmount());
		updateNumber(wsheet, row, 1, calculateSum(wsheet, row, endCol));
	}
	
	private long calculateSum(WritableSheet wsheet, int row, int endCol) {
		long sum = 0;
		for (int i = 2; i <= endCol; i += 1) {
			Cell cell = wsheet.getCell(i, row);
			String num = cell.getContents();
			if (num != "") {
				sum += Long.parseLong(num);
			}
		}
		return sum;
	}
	
	private long calculateDailySum(WritableSheet wsheet, int endRow, int endCol) throws Exception {
		long sum = 0;
		for (int i = 1; i < endCol + 1; i += 1) {
			sum = 0;
			for (int j = 1; j < endRow; j += 1) {
				Cell cell = wsheet.getCell(i, j);
				String num = cell.getContents();
				if (num != "") {
					sum += Long.parseLong(num);
				}
			}
			writeNumber(wsheet, endRow, i, sum);
		}
		writeLabel(wsheet, endRow++, 0, "合计");
		return sum;
	}
	
	private int readSrcXls() {
		int numRows = 0;
		try {
			Workbook wb = Workbook.getWorkbook(new File(srcPath));
			Sheet sheet = wb.getSheet(0);
			numRows = sheet.getRows();
			
			for (int i = 0; i < numRows; i += 1) {
				Cell[] c = sheet.getRow(i);
				entries.add(new Entry(c[0].getContents(), Long.parseLong(c[1].getContents().trim())));
			}	
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return numRows;
	}
	
	private int processDestXls() {
		int startRow = 0;
		int endRow = 0;
		//int startCol = 2;
		int endCol = 2;
		
		try {
			File destFile = new File(destPath);
			if (destFile.exists()) {
				File tempFile = new File("data/out.xls");
				Workbook wb = Workbook.getWorkbook(destFile);
				Sheet sheet = wb.getSheet(0);
				WritableWorkbook copy = Workbook.createWorkbook(tempFile, wb);
				WritableSheet wsheet = copy.getSheet(0);
				
				endCol = sheet.getColumns();
				//exclude the last row(daily sums)
				endRow = sheet.getRows() - 1; 
				
				Hashtable<String, Integer> companies = new Hashtable<String, Integer>();
				for (int i = 1; i < endRow; i += 1) {
					Cell[] c = sheet.getRow(i);
					companies.put(c[0].getContents() , new Integer(i));
				}
				
				writeLabel(wsheet, 0, endCol, date);
				for (Entry e : entries) {
					if (companies.containsKey(e.getName())) {
						int row = companies.get(e.getName());
						modifyEntry(wsheet, row, endCol, e);
					} else {
						appendEntry(wsheet, endRow, endCol, e);
						endRow += 1;
					}
				}
				calculateDailySum(wsheet, endRow, endCol);
				copy.write();
				copy.close();
				
				destFile.delete();
				tempFile.renameTo(new File(destPath));
				
			} else {
				//create file
				destFile.createNewFile();
				WritableWorkbook wwb = Workbook.createWorkbook(new File(destPath));
				WritableSheet wsheet = wwb.createSheet("Output", 0);
				
				String[] titles = {"机构名称", "小计"};
				int i = 0;
				for (String title : titles) {
					writeLabel(wsheet, 0, i++, title);
				}
				writeLabel(wsheet, 0, endCol, date);	
				endRow = 1;
				for (Entry e : entries) {
					appendEntry(wsheet, endRow, endCol, e);
					endRow += 1;
				}
				
				calculateDailySum(wsheet, endRow, endCol);
				wwb.write();
				wwb.close();
			}
		} catch (Exception e) {
			System.out.println("333");
			System.out.println(e.getMessage());
		}
		return endRow - startRow + 1;
	}
	
	public static void zuzhang(String srcPath, String destPath, String date) {
		//Example usage
		Step2 step = new Step2(srcPath, destPath, date);
		step.readSrcXls();
		step.processDestXls();
	}
}
