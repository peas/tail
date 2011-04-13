package net.sf.tail.report.xls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.tail.ConstrainedTimeSeries;
import net.sf.tail.Tick;
import net.sf.tail.TimeSeries;
import net.sf.tail.report.Report;
import net.sf.tail.tick.DefaultTick;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class TimeSeriesXlsGenerator {

	protected static final int INDEX_FIRST_COLUMN = 1;

	protected static final int INDEX_FIRST_ROW = 1;

	private HSSFSheet sheet;

	private CellStylist stylist;

	private static final Logger LOG = Logger.getLogger(TimeSeriesXlsGenerator.class);
	
	public TimeSeriesXlsGenerator(HSSFWorkbook workbook) {
		this.sheet = workbook.createSheet("Time Series");
		stylist = new CellStylist(workbook);
		LOG.setLevel(Level.WARN);
	}

	public HSSFSheet generate(Report report) throws IOException {
		LOG.info("Initializing Time Series Generator");
		long time = System.currentTimeMillis();
		int row = INDEX_FIRST_ROW;	
		
		TimeSeries series = new ConstrainedTimeSeries(report.getSlicer().getSeries(), 
				report.getDecisions().get(0).getActualSlice().getBegin(), report.getSlicer().getSeries().getEnd() - 1);
		
		HSSFCellStyle style = stylist.createTitleCellStyle();
		String title = "Time Series";
		row = generateTitle(row, title, style);
		
		List<HSSFCellStyle> styles = new ArrayList<HSSFCellStyle>();
		styles.add(stylist.createHeaderCellStyle());
		styles.add(stylist.createScriptStyle());
		styles.add(stylist.createHeaderCellStyle());
		styles.add(stylist.createScriptStyle());
		
		List<String> line = new ArrayList<String>();
		line.add("Stock:");
		line.add(report.getSlicer().getSeries().getName());
		line.add("for:");
		line.add(report.getSlicer().getSeries().getPeriodName());
		row = generateLine(row, line, styles);

		int flag = row++;
		
		row++;
		
		style = stylist.createHeaderCellStyle();
		row = generateHeader(row, style);

		style = stylist.createInternalCellStyle();
		HSSFCellStyle style2 = stylist.createInternal2CellStyle();
		generateInternalRow(row, series, style, style2);

		stylist.rearrangeSheet(sheet, 11);

		
		
		this.sheet.addMergedRegion(new Region(4,(short)2,3,(short)8));
		
		style = stylist.createScriptStyle();
		
		line = new ArrayList<String>();
		line.add("Script:");
		line.add(report.getScript());
		
		row = generateLine(flag, line, styles);
		
		LOG.info("Time Series generated (" + (System.currentTimeMillis() - time )+ " miliseconds)");
		
		return this.sheet;
	}

	private int generateTitle(int firstRow, String title,  HSSFCellStyle style) {
		HSSFRow rowHeader = sheet.createRow((short) firstRow++);
		int columnIndex = INDEX_FIRST_COLUMN;

		createCell(rowHeader, title, (short) columnIndex++, style);

		LOG.info("Title created");
		return firstRow;
	}
	
	private int generateLine(int firstRow, List<String> title,List<HSSFCellStyle> styles) {
		HSSFRow rowHeader = sheet.createRow((short) firstRow);
		int columnIndex = INDEX_FIRST_COLUMN;

		for (int i = 0; i < title.size(); i++) {
			createCell(rowHeader, title.get(i), (short) columnIndex++, styles.get(i));
		}

		LOG.info("Line created");
		return firstRow+2;
	}
	
	private int generateHeader(int firstRow, HSSFCellStyle style) {
		HSSFRow rowHeader = sheet.createRow((short) firstRow++);
		int columnIndex = INDEX_FIRST_COLUMN;

		createCell(rowHeader, "Data e Hora", (short) columnIndex++, style);
		createCell(rowHeader, "Abertura", (short) columnIndex++, style);
		createCell(rowHeader, "Fechamento", (short) columnIndex++, style);
		createCell(rowHeader, "Máximo", (short) columnIndex++, style);
		createCell(rowHeader, "Variação", (short) columnIndex++, style);
		createCell(rowHeader, "Anterior", (short) columnIndex++, style);
		createCell(rowHeader, "Montante", (short) columnIndex++, style);
		createCell(rowHeader, "Vol($)", (short) columnIndex++, style);
		createCell(rowHeader, "Quantidade", (short) columnIndex++, style);

		LOG.info("Header created");
		return firstRow;
	}

	private void generateInternalRow(int firtRow, TimeSeries series, HSSFCellStyle style, HSSFCellStyle style2) {
		boolean even = true;
		int i;
		LOG.info("Initializing Internal rows of the Time Series");
		long time = System.currentTimeMillis();
		for (i = series.getBegin(); i < series.getEnd(); i++) {
			Tick tick = series.getTick(i);
			int indexcolumn = INDEX_FIRST_COLUMN;
			HSSFRow row = sheet.createRow((short) firtRow++);
			
			if (even){
				createCell(row, tick.getDate().toString("hh:mm dd/MM/yyyy"), (short) indexcolumn++, style);
				createCell(row, tick.getOpenPrice(), (short) indexcolumn++, style);
				createCell(row, tick.getClosePrice(), (short) indexcolumn++, style);
				createCell(row, tick.getMaxPrice(), (short) indexcolumn++, style);
				createCell(row, tick.getVariation(), (short) indexcolumn++, style);
				createCell(row, tick.getPreviousPrice(), (short) indexcolumn++, style);
				createCell(row, tick.getAmount(), (short) indexcolumn++, style);
				createCell(row, tick.getVolume(), (short) indexcolumn++, style);
				createCell(row, tick.getTrades(), (short) indexcolumn++, style);
			}
			else {
				createCell(row, tick.getDate().toString("hh:mm dd/MM/yyyy"), (short) indexcolumn++, style2);
				createCell(row, tick.getOpenPrice(), (short) indexcolumn++, style2);
				createCell(row, tick.getClosePrice(), (short) indexcolumn++, style2);
				createCell(row, tick.getMaxPrice(), (short) indexcolumn++, style2);
				createCell(row, tick.getVariation(), (short) indexcolumn++, style2);
				createCell(row, tick.getPreviousPrice(), (short) indexcolumn++, style2);
				createCell(row, tick.getAmount(), (short) indexcolumn++, style2);
				createCell(row, tick.getVolume(), (short) indexcolumn++, style2);
				createCell(row, tick.getTrades(), (short) indexcolumn++, style2);
			}
			even = !even;
		}
		HSSFCellStyle style3 = stylist.createLastCellStyle(even);
		Tick tick = series.getTick(i);
		int indexcolumn = INDEX_FIRST_COLUMN;
		HSSFRow row = sheet.createRow((short) firtRow++);
		if (tick instanceof DefaultTick)
		{
			DefaultTick defaultTick = (DefaultTick)tick;
			createCell(row, defaultTick.getDate().toString("hh:mm dd/MM/yyyy"), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getOpenPrice(), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getClosePrice(), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getMaxPrice(), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getVariation(), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getPreviousPrice(), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getAmount(), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getVolume(), (short) indexcolumn++, style3);
			createCell(row, defaultTick.getTrades(), (short) indexcolumn++, style3);
		}
		
		LOG.info("Ending Internal of the Time Series (" + (System.currentTimeMillis() - time) + " miliseconds)");

	}

	private static void createCell(HSSFRow row, String value, short column, HSSFCellStyle cellStyle) {
		HSSFCell cell = row.createCell(column);
		HSSFRichTextString hssfString = new HSSFRichTextString(value);
		cellStyle.setDataFormat((short) 0);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(hssfString);
		cell.setCellStyle(cellStyle);
	}

	private static void createCell(HSSFRow row, double value, short column, HSSFCellStyle cellStyle) {
		HSSFCell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
	}

}