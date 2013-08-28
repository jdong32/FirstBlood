package FirstBlood;

import java.io.*;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ModifyData extends XlsProcesser {
	private void modifyData(String branch, String company, String date, long newVal) throws Exception {
		File f = new File("data/" + branch + ".xls");
		File newf = new File("data/temp.xls");
		Workbook wb = Workbook.getWorkbook(f);
		WritableWorkbook wwb = Workbook.createWorkbook(newf, wb);
		
		WritableSheet wsheet = wwb.getSheet(0);
		int numRows = wsheet.getRows();
		int endCol = wsheet.getColumns();
		for (int i = 0; i < 2; i += 1)
			wsheet.removeRow(--numRows);
		
		Cell[] titles = wsheet.getRow(0);
		Step2 step = new Step2();
		for (int i = 1; i < numRows; i += 1) {
			Cell[] row = wsheet.getRow(i);
			if (row[0].getContents().equals(company)) {
				if (date.equals("delete")) {
					wsheet.removeRow(i);
					numRows -= 1;
					break;
				} else {
					for (int j = 2; j < endCol; j += 1) {
						if (titles[j].getContents().equals(date)) {
							if (newVal != 0)
								writeNumber(wsheet, i, j, newVal);
							else
								writeLabel(wsheet, i, j, "");
							writeLabel(wsheet, i, 1, step.calculateSum(wsheet, i, endCol));
							break;
						}
					}
				}
			}
		}
		step.calculateDailySum(wsheet, numRows, 1, endCol - 1);
		wwb.write();
		wwb.close();
		
		f.delete();
		newf.renameTo(f);
	}
	
	public static void run(String branch, String company, String date, Long value) {
		try {
			ModifyData md = new ModifyData();
			Step2 step = new Step2("", myWindow.txtDirPath + "/最终文件.xls", "");
			step.backupAllBranch();
			md.modifyData(branch, company, date, value);
			step.mergeBranchXls();	
			JOptionPane.showMessageDialog(null, "操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
