package FirstBlood;

import java.util.*;

import jxl.*;
import jxl.write.*;
//import jxl.format.*;
import jxl.format.Colour;
import jxl.format.Border;
import jxl.format.BorderLineStyle;

public class XlsProcesser {
	protected static void writeLabel(WritableSheet wsheet, int row, int col, String str) throws Exception  {
		jxl.write.Label label = new jxl.write.Label(col, row, str);
		WritableCell wc = (WritableCell) label;
		wsheet.addCell(wc);
	}
	
	protected static void writeLabelWithFormat(WritableSheet wsheet, int row, int col, String str, Colour color) throws Exception  {
		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setBackground(color);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		jxl.write.Label label = new jxl.write.Label(col, row, str);
		label.setCellFormat(wcf);
		WritableCell wc = (WritableCell) label;
		wsheet.addCell(wc);
	}
	
	protected static void writeNumber(WritableSheet wsheet, int row, int col, long num) throws Exception  {
		jxl.write.Number number = new jxl.write.Number(col, row, num);
		WritableCell wc = (WritableCell) number;
		wsheet.addCell(wc);
	}
	
	
	protected static void updateNumber(WritableSheet wsheet, int row, int col, long num) throws Exception {
		WritableCell wc = wsheet.getWritableCell(col, row);
		jxl.write.Number number = (jxl.write.Number) wc;
		number.setValue(num);
	}
	
	protected static void updateLabel(WritableSheet wsheet, int row, int col, String str) throws Exception {
		WritableCell wc = wsheet.getWritableCell(col, row);
		jxl.write.Label label = (jxl.write.Label) wc;
		label.setString(str);
	}
	
	protected static void setColumnFormat(WritableSheet wsheet, int col, int width, Colour bgcolor) throws Exception {
		WritableCellFormat wcf = new WritableCellFormat();
		wcf.setBackground(bgcolor);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);

		CellView cv = new CellView();
		cv.setSize(width);
		cv.setFormat(wcf);
		wsheet.setColumnView(col, cv);
	}
	
	protected static void appendRow(WritableSheet wsheet, int row, Cell[] cells) throws Exception {
		int col = 0;
		for (Cell c : cells) {
			if (c.getType() == CellType.LABEL) {
				writeLabel(wsheet, row, col++, c.getContents());
			} else if (c.getType() == CellType.NUMBER) {
				writeNumber(wsheet, row, col++, Long.parseLong(c.getContents()));
			} else if (c.getType() == CellType.EMPTY) {
				col++;
			}
		}
	}
}
