package net.sf.tail.report.xls;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class CellStylist {

	protected HSSFWorkbook workbook;

	public CellStylist(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	protected int loadPicture(HSSFWorkbook wb, File reportPNG) throws IOException {
		int pictureIndex = 0;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(reportPNG);
			bos = new ByteArrayOutputStream();
			int c;
			while ((c = fis.read()) != -1)
				bos.write(c);
			pictureIndex = wb.addPicture(bos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG);
		} finally {
			if (fis != null)
				fis.close();
			if (bos != null)
				bos.close();
		}
		return pictureIndex;
	}

	protected void rearrangeSheet(HSSFSheet sheet, int numColumn) {
		for (int i = 0; i < numColumn; i++) {
			sheet.autoSizeColumn((short) i);
		}
	}

	protected HSSFCellStyle createHeaderCellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.DARK_BLUE.index);
		style.setLeftBorderColor(HSSFColor.DARK_BLUE.index);
		style.setTopBorderColor(HSSFColor.DARK_BLUE.index);
		style.setBottomBorderColor(HSSFColor.DARK_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.DARK_BLUE.index);
		style.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		return style;
	}

	protected HSSFCellStyle createFirstCellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.DARK_BLUE.index);
		style.setLeftBorderColor(HSSFColor.DARK_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.WHITE.index);
		style.setFillForegroundColor(HSSFColor.WHITE.index);

		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		return style;
	}

	protected HSSFCellStyle createInternalCellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.DARK_BLUE.index);
		style.setLeftBorderColor(HSSFColor.DARK_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.WHITE.index);
		style.setFillForegroundColor(HSSFColor.WHITE.index);

		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		return style;
	}
	
	protected HSSFCellStyle createInternal2CellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.DARK_BLUE.index);
		style.setLeftBorderColor(HSSFColor.DARK_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);

		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		return style;
	}

	protected HSSFCellStyle createSummaryCellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.DARK_BLUE.index);
		style.setLeftBorderColor(HSSFColor.DARK_BLUE.index);
		style.setTopBorderColor(HSSFColor.DARK_BLUE.index);
		style.setBottomBorderColor(HSSFColor.DARK_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

		HSSFFont font = workbook.createFont();
		font.setFontName("Courier New");
		font.setFontHeightInPoints((short) 15);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		return style;
	}

	protected void drawImage(File reportPNG, HSSFSheet sheet, int columnStart, int columnEnd) throws IOException {
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 240, 0, (short) columnStart, 5, (short) columnEnd, 23);
		anchor.setAnchorType(3);

		patriarch.createPicture(anchor, loadPicture(workbook, reportPNG));
	}

	public HSSFCellStyle createLastCellStyle(boolean white) {
		HSSFCellStyle style = workbook.createCellStyle();

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.DARK_BLUE.index);
		style.setLeftBorderColor(HSSFColor.DARK_BLUE.index);
		style.setBottomBorderColor(HSSFColor.DARK_BLUE.index);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		if (white){
			style.setFillBackgroundColor(HSSFColor.WHITE.index);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
		}
		else
		{
			style.setFillBackgroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		}

		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		return style;
	}

	public HSSFCellStyle createTitleCellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setUnderline(HSSFFont.U_SINGLE);
		font.setColor(HSSFColor.GREY_80_PERCENT.index);
		style.setFont(font);
		return style;
	}

	public HSSFCellStyle createSubTitleCellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);

		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.DARK_BLUE.index);
		style.setFont(font);
		return style;
	}

	public HSSFCellStyle createInfoCellStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		return style;
	}
	
	public HSSFCellStyle createScriptStyle() {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		return style;
	}

}