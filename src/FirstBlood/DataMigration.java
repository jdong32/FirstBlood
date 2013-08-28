package FirstBlood;

import java.io.*;

import javax.swing.JOptionPane;

import jxl.*;
import jxl.write.*;

public class DataMigration extends XlsProcesser {
	private void dataMigrate(String company, String oldbranch, String newbranch) throws Exception {
		File oldf = new File("data/" + oldbranch + ".xls");
		File newoldf = new File("data/woldxls.xls");
		Workbook oldxls = Workbook.getWorkbook(oldf);
		WritableWorkbook woldxls = Workbook.createWorkbook(newoldf, oldxls);
		
		WritableSheet woldsheet = woldxls.getSheet(0);
		int oldnumRows = woldsheet.getRows();
		int endCol = woldsheet.getColumns();
		for (int i = 0; i < 2; i += 1)
			woldsheet.removeRow(--oldnumRows);
		
		File newf = new File("data/" + newbranch + ".xls");
		File newnewf = new File("data/wnewxls.xls");
		Workbook newxls = Workbook.getWorkbook(newf);
		WritableWorkbook wnewxls = Workbook.createWorkbook(newnewf, newxls);
		
		WritableSheet wnewsheet = wnewxls.getSheet(0);
		int newnumRows = wnewsheet.getRows();
		for (int i = 0; i < 2; i += 1)
			wnewsheet.removeRow(--newnumRows);
		
		for (int i = 1; i < oldnumRows - 2; i += 1) {
			Cell[] row = woldsheet.getRow(i);
			if (row[0].getContents().equals(company)) {
				appendRow(wnewsheet, newnumRows, row);
				newnumRows += 1;
				woldsheet.removeRow(i);
				oldnumRows -=1;
			}
		}
		
		Step2 step = new Step2();
		step.calculateDailySum(woldsheet, oldnumRows, 1, endCol - 1);
		step.calculateDailySum(wnewsheet, newnumRows, 1, endCol - 1);
		
		woldxls.write();
		woldxls.close();
		wnewxls.write();
		wnewxls.close();
		
		oldf.delete();
		newf.delete();
		newoldf.renameTo(oldf);
		newnewf.renameTo(newf);
	}
	
	public static void run(String company, String oldbranch, String newbranch) throws Exception {
			DataMigration dm = new DataMigration();
			Step2 step = new Step2("", myWindow.txtDirPath + "/最终文件.xls", "");
			step.backupAllBranch();
			dm.dataMigrate(company, oldbranch, newbranch);
			step.mergeBranchXls();
			JOptionPane.showMessageDialog(null, "操作成功", "提示", JOptionPane.INFORMATION_MESSAGE);

	}
}
