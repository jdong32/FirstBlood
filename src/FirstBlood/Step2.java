package FirstBlood;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.channels.*;
import java.util.*;

import jxl.*;
import jxl.write.*;
//import jxl.format.*;
import jxl.format.Colour;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import java.math.BigInteger;

public class Step2 extends XlsProcesser {
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
	private ArrayList<Entry> entries1 = new ArrayList<Entry>();
	private ArrayList<String> branches = new ArrayList<String>();
	
	Step2() {
		iniBranches();
	}
	
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
	
	private void appendEntry(WritableSheet wsheet, int row, int endCol, Entry e) throws Exception {
		writeLabel(wsheet, row, 0, e.getName());
		writeNumber(wsheet, row, endCol, e.getAmount());
		//writeNumber(wsheet, row, 1, calculateSum(wsheet, row, endCol));
		writeLabel(wsheet, row, 1, calculateSum(wsheet, row, endCol));
	}
	
	private void modifyEntry(WritableSheet wsheet, int row, int endCol, Entry e) throws Exception {
		writeLabel(wsheet, row, 0, e.getName());
		writeNumber(wsheet, row, endCol, e.getAmount());
		//updateNumber(wsheet, row, 1, calculateSum(wsheet, row, endCol));
		updateLabel(wsheet, row, 1, calculateSum(wsheet, row, endCol));
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
	
	public void backupAllBranch() throws Exception {
		for (String str : branches) {
			copyFile(new File("data/" + str + ".xls"), new File("backup/" + str + ".xls"));
		}
	}
	
	public String calculateSum(WritableSheet wsheet, int row, int endCol) {
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
	
	private String calculateSumPro(WritableSheet wsheet, int row, int startCol, int endCol) {
		BigInteger sum = new BigInteger("0");
		for (int i = startCol; i <= endCol; i += 1) {
			Cell cell = wsheet.getCell(i, row);
			String num = cell.getContents();
			if (!num.equals("")) {
				sum = sum.add(new BigInteger(num));
			}
		}
		return sum.toString();
	}
	
	public String calculateDailySum(WritableSheet wsheet, int endRow, int startCol, int endCol) throws Exception {
		BigInteger zero = new BigInteger("0");
		BigInteger sum = zero;
		int numTrans = 0;

		for (int i = startCol; i < endCol + 1; i += 1) {
			sum = zero;
			numTrans = 0;
			for (int j = 1; j < endRow; j += 1) {
				Cell cell = wsheet.getCell(i, j);
				String num = cell.getContents();
				if (!(num.equals("")||num.equals("0"))) {
					numTrans += 1;
					sum = sum.add(new BigInteger(num));
				}
			}
			writeLabelWithFormat(wsheet, endRow, i, sum.toString(), Colour.LIME);
			writeNumber(wsheet, endRow + 1, i, numTrans);
		}
		//System.out.println(calculateSum(wsheet, startCol, endCol));
		writeLabel(wsheet, endRow + 1, startCol, calculateSumPro(wsheet, endRow + 1, startCol + 1, endCol));
		writeLabelWithFormat(wsheet, endRow, 0, "合计", Colour.CORAL);
		writeLabelWithFormat(wsheet, endRow + 1, 0, "笔数", Colour.CORAL);
		
		//padding
		for (int i = 1; i < startCol; i += 1) {
			writeLabelWithFormat(wsheet, endRow, i, " ", Colour.CORAL);
			writeLabelWithFormat(wsheet, endRow + 1, i, " ", Colour.CORAL);
		}
		return sum.toString();
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
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
	
	private int readSrcXls(int option) {
		int numRows = 0;
		try {
			Workbook wb = Workbook.getWorkbook(new File(srcPath));
			Sheet sheet = wb.getSheet(option);
			numRows = sheet.getRows();
			
			for (int i = 0; i < numRows; i += 1) {
				Cell[] c = sheet.getRow(i);
				if (c[0].getContents().equals("")) {
					continue;
				} else {
					if (option == 0)
						entries.add(new Entry(c[0].getContents(), c[2].getContents(), Long.parseLong(c[3].getContents().trim())));
					else if (option == 1) 
						entries1.add(new Entry(c[0].getContents(), c[2].getContents(), Long.parseLong(c[3].getContents().trim())));
				}
			}	
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return numRows;
	}
	
	private int processDestXls(ArrayList<Entry> temp, ArrayList<Entry> temp1, String dpath) {
	
		
		try {
			File destFile = new File(dpath);
			if (destFile.exists()) {
				File tempFile = new File("data/out.xls");
				Workbook wb = Workbook.getWorkbook(destFile);
				WritableWorkbook copy = Workbook.createWorkbook(tempFile, wb);
				
				ArrayList<Entry> tempentries = new ArrayList<Entry>();
				System.out.println(temp.size() + ":" + temp1.size());
				for (int j = 0; j < 2; j += 1) {	
					int startRow = 0;
					int endRow = 0;
					//int startCol = 2;
					int endCol = 2;
					
					tempentries = (j == 0)?temp:temp1;
					Sheet sheet = wb.getSheet(j);
					WritableSheet wsheet = copy.getSheet(j);

					endCol = sheet.getColumns();
					//exclude the last tow rows(daily sums & num of transactions)
					endRow = sheet.getRows() - 2; 

					Hashtable<String, Integer> companies = new Hashtable<String, Integer>();
					for (int i = 1; i < endRow; i += 1) {
						Cell[] c = sheet.getRow(i);
						companies.put(c[0].getContents() , new Integer(i));
					}

					writeLabel(wsheet, 0, endCol, date);
					for (Entry e : tempentries) {
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
				}
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
		return 0;
	}
	
	private void genBranchXls() throws Exception {
		ArrayList<Entry> temp = new ArrayList<Entry>();
		ArrayList<Entry> temp1 = new ArrayList<Entry>();
		for (String str : branches) {
			for (Entry e : entries) {
				if (e.getBranch().equals(str)) {
					temp.add(e);
				}
			}
			for (Entry e : entries1) {
				if (e.getBranch().equals(str)) {
					temp1.add(e);
				}
			}
			copyFile(new File("data/" + str + ".xls"), new File("backup/" + str + ".xls"));
			processDestXls(temp, temp1, "data/" + str + ".xls");
			temp.clear();
			temp1.clear();
		}
	}
	
	public void mergeBranchXls() throws Exception {
		WritableWorkbook wwb = Workbook.createWorkbook(new File(destPath));
//		WritableSheet wsheet = wwb.createSheet("Final", 0);
		
		WritableSheet insumsheet = wwb.createSheet("汇入汇总表", 0);
		WritableSheet outsumsheet = wwb.createSheet("汇出汇总表", 1);
		WritableSheet indetailsheet = wwb.createSheet("汇入明细表", 2);
		WritableSheet outdetailsheet = wwb.createSheet("汇出明细表", 3);
		
		for (int ii = 0; ii < 2; ii += 1) {
			Workbook temp;
			WritableSheet wsheet = indetailsheet;
			WritableSheet bssheet  = insumsheet;
			int endRow = 0;
			int endCol = 0;
			int br_cnt = 0;
			boolean firstenter = true;
			if (ii == 1) {
				wsheet = outdetailsheet;
				bssheet = outsumsheet;
			}

			Colour color = Colour.IVORY;
			Colour titlecolor = Colour.CORAL;
			for (String br : branches) {
				temp = Workbook.getWorkbook(new File("data/" + br + ".xls"));
				Sheet sheet = temp.getSheet(ii);
				int br_endRow = sheet.getRows() - 2;

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
					writeLabelWithFormat(bssheet, 0, 0, "支行名称", Colour.WHITE);
					writeLabelWithFormat(bssheet, 0, 1, "金额汇总", Colour.WHITE);

					firstenter = false;
					endRow++;
				}
				//write branch name
				titlecolor = titlecolor.equals(Colour.CORAL)?Colour.PERIWINKLE:Colour.CORAL;
				writeLabelWithFormat(wsheet, endRow, 0, " ", titlecolor);
				writeLabelWithFormat(wsheet, endRow, 1, br, titlecolor);
				writeLabelWithFormat(bssheet, ++br_cnt, 0, br, Colour.WHITE);

				//write branch sum
				Cell c = sheet.getCell(1, br_endRow);
				writeLabelWithFormat(wsheet, endRow, 2, c.getContents(), titlecolor);
				writeLabelWithFormat(bssheet, br_cnt, 1, c.getContents(), Colour.WHITE);
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
			calculateDailySum(bssheet, br_cnt + 1, 1, 1);
			calculateDailySum(wsheet, endRow, 3, endCol + 1);
			writeLabelWithFormat(bssheet, br_cnt + 2, 1, wsheet.getCell(3, endRow + 1).getContents(), Colour.TAN);
		}
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
					
					for (int j = 0; j < 2; j += 1) {
						WritableSheet wsheet = wwb.createSheet("Output", j);

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
					}
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
			step.readSrcXls(0);
			step.readSrcXls(1);
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
