package FirstBlood;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.channels.*;
import java.util.*;

import jxl.*;
import jxl.write.*;
import jxl.format.*;
import jxl.format.Colour;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import java.math.BigInteger;

public class Step2 {
	private class Entry {	
		//inner class practice, prevent outside access!
		private String branchName;
		private String companyName;
		private long amountOfMoney;
		
		private Entry (String branch, String name, long amount) {
			branchName = branch;
			companyName = name;
			amountOfMoney = amount;
		}
		private String getName() {
			return companyName;
		}
		private long getAmount() {
			return amountOfMoney;
		}
		private String getBranch() {
			return branchName;
		}
		public String toString() {
			return "[" + branchName + " : " + companyName + " : " + amountOfMoney + "]";
		}
	}
	private String srcPath;
	private static String destPath;
	private String date;
	private ArrayList<Entry> entries = new ArrayList<Entry>();
	private Set<String> branches = new HashSet<String>();
	
	
	Step2(String src_path, String dest_path, String input_date) {
		srcPath = src_path;
		destPath = dest_path;
		date = input_date;
		iniBranches();
	}
	
	private void iniBranches() {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/branches.txt"), Charset.forName("GBK")));
			String line;
			while((line = br.readLine()) != null) {
				branches.add(line.trim());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void printBranches() {
		for (String str : branches) {
			System.out.println(str);
		}
		System.out.println(branches.size());
	}
	
	private void writeLabel(WritableSheet wsheet, int row, int col, String str) throws Exception  {
		jxl.write.Label label = new jxl.write.Label(col, row, str);
		WritableCell wc = (WritableCell) label;
		wsheet.addCell(wc);
	}
	
	private void writeLabelWithFormat(WritableSheet wsheet, int row, int col, String str, Colour color) throws Exception  {
		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setBackground(color);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		jxl.write.Label label = new jxl.write.Label(col, row, str);
		label.setCellFormat(wcf);
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
		//writeNumber(wsheet, row, 1, calculateSum(wsheet, row, endCol));
		writeLabel(wsheet, row, 1, calculateSum(wsheet, row, endCol));
	}
	
	private void updateNumber(WritableSheet wsheet, int row, int col, long num) throws Exception {
		WritableCell wc = wsheet.getWritableCell(col, row);
		jxl.write.Number number = (jxl.write.Number) wc;
		number.setValue(num);
	}
	
	private void updateLabel(WritableSheet wsheet, int row, int col, String str) throws Exception {
		WritableCell wc = wsheet.getWritableCell(col, row);
		jxl.write.Label label = (jxl.write.Label) wc;
		label.setString(str);
	}
	
	private void modifyEntry(WritableSheet wsheet, int row, int endCol, Entry e) throws Exception {
		writeLabel(wsheet, row, 0, e.getName());
		writeNumber(wsheet, row, endCol, e.getAmount());
		//updateNumber(wsheet, row, 1, calculateSum(wsheet, row, endCol));
		updateLabel(wsheet, row, 1, calculateSum(wsheet, row, endCol));
	}
	
	private void setColumnFormat(WritableSheet wsheet, int col, int width, Colour bgcolor) throws Exception {
		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setBackground(bgcolor);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);

		CellView cv = new CellView();
		cv.setSize(width);
		cv.setFormat(wcf);
		wsheet.setColumnView(col, cv);
	}
	
	private void updateDictionary(String srcPath) throws Exception {
		HashMap<String, String> dict= new HashMap<String, String>();
		HashSet<String> brset = new HashSet<String>();
		
		File dictFile = new File(srcPath);
		File tempFile = new File("data/temp.xls");
		Workbook wb = Workbook.getWorkbook(dictFile);
		Sheet sheet = wb.getSheet(0);
		int numRows = sheet.getRows();

		for (int i = 0; i < numRows; i += 1) {
			Cell[] c = sheet.getRow(i);
			if (!c[0].getContents().equals("")) {
				dict.put(c[1].getContents(), c[0].getContents());
			} else if (!c[1].getContents().equals("")) {
				brset.add(c[1].getContents());
			}
		}
		
		WritableWorkbook copy = Workbook.createWorkbook(tempFile, wb);
		WritableSheet wsheet = copy.getSheet(0);
		
		for (Entry e : entries) {
			if (!dict.containsKey(e.getName())) {
				writeLabel(wsheet, numRows, 0, e.getBranch());
				writeLabel(wsheet, numRows, 1, e.getName());
				numRows += 1;
				
				if (!brset.contains(e.getBranch())) {
					brset.add(e.getBranch());
					writeLabel(wsheet, numRows, 1, e.getBranch());
					numRows += 1;
				}
			}
		}
		
		copy.write();
		copy.close();
		dictFile.delete();
		tempFile.renameTo(new File(srcPath));
	}

	
	private String calculateSum(WritableSheet wsheet, int row, int endCol) {
		BigInteger sum = new BigInteger("0");
		for (int i = 2; i <= endCol; i += 1) {
			Cell cell = wsheet.getCell(i, row);
			String num = cell.getContents();
			if (!num.equals("")) {
				sum = sum.add(new BigInteger(num));
			}
		}
		return sum.toString();
	}
	
	private String calculateDailySum(WritableSheet wsheet, int endRow, int startCol, int endCol) throws Exception {
		BigInteger zero = new BigInteger("0");
		BigInteger sum = zero;
		for (int i = startCol; i < endCol + 1; i += 1) {
			sum = zero;
			for (int j = 1; j < endRow; j += 1) {
				Cell cell = wsheet.getCell(i, j);
				String num = cell.getContents();
				if (!num.equals("")) {
					sum = sum.add(new BigInteger(num));
				}
			}
			writeLabelWithFormat(wsheet, endRow, i, sum.toString(), Colour.LIME);
		}
		writeLabelWithFormat(wsheet, endRow, 0, "合计", Colour.CORAL);
		
		//padding
		for (int i = 1; i < startCol; i += 1)
			writeLabelWithFormat(wsheet, endRow, i, " ", Colour.CORAL);
		return sum.toString();
	}
	
	private static void copyFile(File sourceFile, File destFile) throws IOException {
		//code from stackoverflow.com
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;
	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();

	        // previous code: destination.transferFrom(source, 0, source.size());
	        // to avoid infinite loops, should be:
	        long count = 0;
	        long size = source.size();              
	        while((count += destination.transferFrom(source, count, size-count))<size);
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	
	private int readSrcXls() {
		int numRows = 0;
		try {
			Workbook wb = Workbook.getWorkbook(new File(srcPath));
			Sheet sheet = wb.getSheet(0);
			numRows = sheet.getRows();
			
			for (int i = 0; i < numRows; i += 1) {
				Cell[] c = sheet.getRow(i);
				if (c[0].getContents().equals("")) {
					continue;
				} else {
					entries.add(new Entry(c[0].getContents(), c[2].getContents(), Long.parseLong(c[3].getContents().trim())));
				}
			}	
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return numRows;
	}
	
	private int processDestXls(ArrayList<Entry> temp, String dpath) {
		int startRow = 0;
		int endRow = 0;
		//int startCol = 2;
		int endCol = 2;
		
		try {
			File destFile = new File(dpath);
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
				for (Entry e : temp) {
					if (companies.containsKey(e.getName())) {
						int row = companies.get(e.getName());
						modifyEntry(wsheet, row, endCol, e);
					} else {
						wsheet.removeRow(endRow);
						appendEntry(wsheet, endRow, endCol, e);
						endRow += 1;
					}
				}
				calculateDailySum(wsheet, endRow, 1, endCol);
				copy.write();
				copy.close();
				
				destFile.delete();
				tempFile.renameTo(new File(dpath));
				
			} else {
				;
			}
		} catch (Exception e) {
			System.out.println("333");
			System.out.println(e.getMessage());
		}
		return endRow - startRow + 1;
	}
	
	private void genBranchXls() throws Exception {
		ArrayList<Entry> temp = new ArrayList<Entry>();
		for (String str : branches) {
			for (Entry e : entries) {
				if (e.getBranch().equals(str)) {
					temp.add(e);
				}
			}
			copyFile(new File("data/" + str + ".xls"), new File("backup/" + str + ".xls"));
			processDestXls(temp, "data/" + str + ".xls");
			temp.clear();
		}
	}
	
	private void mergeBranchXls() throws Exception {
		WritableWorkbook wwb = Workbook.createWorkbook(new File(destPath));
		WritableSheet wsheet = wwb.createSheet("Final", 0);
		Workbook temp;

		int endRow = 0;
		int endCol = 0;
		boolean firstenter = true;
		
		Colour color = Colour.IVORY;
		Colour titlecolor = Colour.CORAL;
		for (String br : branches) {
			temp = Workbook.getWorkbook(new File("data/" + br + ".xls"));
			Sheet sheet = temp.getSheet(0);
			int br_endRow = sheet.getRows() - 1;
			
			if (firstenter == true) {
				//write titles
				Cell[] cells = sheet.getRow(0);
				endCol = sheet.getColumns();

				writeLabelWithFormat(wsheet, 0, 0, "支行名称", Colour.ROSE);
				writeLabelWithFormat(wsheet, 0, 1, "机构名称", Colour.ROSE);
				writeLabelWithFormat(wsheet, 0, 2, "支行总计", Colour.ROSE);
				for (int i = 1; i < endCol; i += 1) {
					writeLabel(wsheet, 0, i + 2, cells[i].getContents());
				}
					
				firstenter = false;
				endRow++;
			}
			//write branch name
			titlecolor = titlecolor.equals(Colour.CORAL)?Colour.PERIWINKLE:Colour.CORAL;
			writeLabelWithFormat(wsheet, endRow, 0, " ", titlecolor);
			writeLabelWithFormat(wsheet, endRow, 1, br, titlecolor);
			//write branch sum
			Cell c = sheet.getCell(1, br_endRow);
			writeLabelWithFormat(wsheet, endRow, 2, c.getContents(), titlecolor);
			endRow++;
			
			color = color.equals(Colour.IVORY)?Colour.ICE_BLUE: Colour.IVORY;
			//copy entries
			for (int i = 1; i < br_endRow; i += 1) {
				writeLabelWithFormat(wsheet, endRow, 0, br, color);
				writeLabelWithFormat(wsheet, endRow, 1, sheet.getCell(0, i).getContents(), color);
				writeLabelWithFormat(wsheet, endRow, 2, " ", color);
				
				for (int j = 1; j < endCol; j += 1) {
					String content = sheet.getCell(j, i).getContents();
					if (content.equals("")) continue;
					else {
						if (j == 1) writeLabel(wsheet, endRow, j + 2, content);
						else writeNumber(wsheet, endRow, j + 2, Long.parseLong(content));
					}
				}
				endRow += 1;
			}
			
		}
		
		setColumnFormat(wsheet, 0, 3000, Colour.WHITE);
		setColumnFormat(wsheet, 1, 9000, Colour.WHITE);
		setColumnFormat(wsheet, 2, 3000, Colour.WHITE);
		setColumnFormat(wsheet, 3, 3000, Colour.TAN);
		for (int i = 4; i < endCol + 2; i += 1) {
			color = Colour.LIGHT_TURQUOISE;
			if (i % 2 == 0) color = Colour.VERY_LIGHT_YELLOW;
			setColumnFormat(wsheet, i, 3000, color);
		}
		
		calculateDailySum(wsheet, endRow, 3, endCol + 1);
		wwb.write();
		wwb.close();
	}
	
	public void genTemplates() {
		class DictEntry {
			private String branch;
			private String company;
			
			DictEntry(String b, String c) {
				branch = b;
				company = c;
			}
		}
		
		try {
			Workbook wb = Workbook.getWorkbook(new File("data/dictsrc.xls"));
			Sheet sheet = wb.getSheet(0);
			int numRows = sheet.getRows();
			
			ArrayList<DictEntry> dictentry = new ArrayList<DictEntry>();
			
			for (int i = 0; i < numRows; i += 1) {
				Cell[] cells = sheet.getRow(i);
				if (cells[0].getContents().equals("")) continue;
				else {
					dictentry.add(new DictEntry(cells[0].getContents(), cells[1].getContents()));
				}
			}
			
			for (String str : branches) {
				File f = new File("data/" + str + ".xls");
				if (!f.exists()) {
					int endRow = 0;
					int endCol = 1;
					
					f.createNewFile();
					WritableWorkbook wwb = Workbook.createWorkbook(f);
					WritableSheet wsheet = wwb.createSheet("Output", 0);
					
					String[] titles = {"机构名称", "小计"};
					int i = 0;
					for (String title : titles) {
						writeLabel(wsheet, 0, i++, title);
					}
					//writeLabel(wsheet, 0, endCol, date);
					
					endRow = 1;
					BigInteger zero = new BigInteger("0");
					for (DictEntry de : dictentry) {
						if (de.branch.equals(str)) {
							writeLabel(wsheet, endRow, 0, de.company);
							writeLabel(wsheet, endRow, 1, zero.toString());
							endRow += 1;
						}
					}
					calculateDailySum(wsheet, endRow, 1, endCol);
					wwb.write();
					wwb.close();
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void rollback() {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/branches.txt"), Charset.forName("GBK")));
			String line;
			while((line = br.readLine()) != null) {
				File f = new File("backup/" + line + ".xls");
				if (f.exists())
					copyFile(f, new File("data/" + line + ".xls"));
			}
			Step2 step = new Step2("", destPath, "");
			step.mergeBranchXls();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	public static void cleardata() {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/branches.txt"), Charset.forName("GBK")));
			String line;
			while((line = br.readLine()) != null) {
				File f = new File("data/" + line + ".xls");
				if (f.exists())
					f.delete();
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	public static void zuzhang(String srcPath, String destPath, String date) {
		//Example usage
		try {
			Step2 step = new Step2(srcPath, destPath, date);
			step.readSrcXls();
			step.updateDictionary("data/dictsrc.xls");
			step.genTemplates();
			step.genBranchXls();
			step.mergeBranchXls();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		//ArrayList<Entry> temp = new ArrayList<Entry>();
		//step.printBranches();
	}
	
//	public static void main(String[] argv) {
//		zuzhang("data/初步文件.xls", "data/newoutput.xls", "20130607");
//	}
}
