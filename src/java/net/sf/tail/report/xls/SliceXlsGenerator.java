package net.sf.tail.report.xls;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.sf.tail.AnalysisCriterion;
import net.sf.tail.OperationType;
import net.sf.tail.Trade;
import net.sf.tail.analysis.criteria.NumberOfTicksCriterion;
import net.sf.tail.analysis.criteria.TotalProfitCriterion;
import net.sf.tail.analysis.evaluator.Decision;
import net.sf.tail.report.Report;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.Period;

public class SliceXlsGenerator implements XLSReportGenerator {

	protected static final int INDEX_FIRST_COLUMN = 1;

	protected static final int INDEX_FIRST_ROW = 1;

	protected static final String FM_REPORT_DIR = "reports" + File.separatorChar;

	protected static final String FM_CHART_DIR = "PNGCharts" + File.separatorChar;

	private HSSFWorkbook workbook;

	private CellStylist stylist;

	private static final Logger LOG = Logger.getLogger(SliceXlsGenerator.class);

	public SliceXlsGenerator(HSSFWorkbook workbook) {
		this.workbook = workbook;
		this.stylist = new CellStylist(workbook);
		LOG.setLevel(Level.WARN);
	}

	public List<HSSFSheet> generate(Report report, Period period, List<File> charts) throws IOException {
		List<AnalysisCriterion> criteria = new LinkedList<AnalysisCriterion>();

		criteria.add(new NumberOfTicksCriterion());
		criteria.add(new TotalProfitCriterion());

		return generate(report, criteria, period, charts);
	}

	public HSSFSheet generate(Decision decision, List<AnalysisCriterion> criteria, Period period, File chart)
	throws IOException {
		
		HSSFSheet sheet = generate(decision, criteria, period);

		stylist.drawImage(chart, sheet, 1, 7);

		stylist.rearrangeSheet(sheet, criteria.size() + 6);


		return sheet;
	}

	public HSSFSheet generate(Decision decision, List<AnalysisCriterion> criteria, Period period)
	throws IOException {
		LOG.info("Initializing XLS Decision for " + decision.getFileName());
		long time = System.currentTimeMillis();
		int row = INDEX_FIRST_ROW;

		HSSFSheet sheet = workbook.createSheet(decision.getFileName());

		HSSFCellStyle style = stylist.createTitleCellStyle();
		String title = "Slice Report";
		row = generateTitle(row, title, style, sheet);

		style = stylist.createSubTitleCellStyle();
		String[] subtitle = new String[4];
		subtitle[0] = "Stock:";
		subtitle[1] = decision.getActualSlice().getName();
		subtitle[2] = "for:";
		subtitle[3] = decision.getActualSlice().getPeriodName();
		row = generateSubTitle(row, subtitle, style, sheet);

		style = stylist.createInfoCellStyle();
		String[] info = new String[4];
		info[0] = "Strategy:";
		info[1] = decision.getStrategy().getClass().getSimpleName();
		info[2] = "Criteria:";
		info[3] = decision.getCriterion().getName();
		row = generateInfo(row, info, style, sheet);

		row = 24;

		style = stylist.createHeaderCellStyle();
		row = generateHeader(row, workbook, sheet, criteria, style);

		style = stylist.createInternalCellStyle();
		HSSFCellStyle style2 = stylist.createInternal2CellStyle();
		row = generateInternalRow(row, workbook, sheet, decision, criteria, style, style2);

		style = stylist.createSummaryCellStyle();
		generateSummaryRow(row, workbook, sheet, decision, criteria, style);

		LOG.info("Decision "+ decision.getFileName() +" generated (" + (System.currentTimeMillis() - time) + " miliseconds)");
		return sheet;
	}



	public List<HSSFSheet> generate(Report report, List<AnalysisCriterion> criteria, Period period, List<File> charts) throws IOException {
		List<HSSFSheet> sheets = new LinkedList<HSSFSheet>();
		for (int i = 0; i < report.getDecisions().size(); i++) {
			if(charts.size() > 0)
				sheets.add(this.generate(report.getDecisions().get(i), criteria, period, charts.get(i)));
			else
				sheets.add(this.generate(report.getDecisions().get(i), criteria, period));
		}
		return sheets;
	}

	private int generateTitle(int firstRow, String title,  HSSFCellStyle style, HSSFSheet sheet) {
		HSSFRow rowHeader = sheet.createRow((short) firstRow++);
		int columnIndex = INDEX_FIRST_COLUMN;

		createCell(rowHeader, title, (short) columnIndex++, style);

		LOG.info("Title created");
		return firstRow;
	}

	private int generateSubTitle(int firstRow, String[] title,  HSSFCellStyle style, HSSFSheet sheet) {
		HSSFRow rowHeader = sheet.createRow((short) firstRow++);
		int columnIndex = INDEX_FIRST_COLUMN;

		createCell(rowHeader, title[0], (short) columnIndex++, style);
		createCell(rowHeader, title[1], (short) columnIndex++, style);
		createCell(rowHeader, title[2], (short) columnIndex++, style);
		createCell(rowHeader, title[3], (short) columnIndex++, style);

		LOG.info("Subtitle created");
		return firstRow;
	}

	private int generateInfo(int firstRow, String[] title,  HSSFCellStyle style, HSSFSheet sheet) {
		HSSFRow rowHeader = sheet.createRow((short) firstRow);
		int columnIndex = INDEX_FIRST_COLUMN;

		createCell(rowHeader, title[0], (short) columnIndex++, style);
		createCell(rowHeader, title[1], (short) columnIndex++, style);
		createCell(rowHeader, title[2], (short) columnIndex++, style);
		createCell(rowHeader, title[3], (short) columnIndex++, style);

		LOG.info("Info created");
		return firstRow+2;
	}

	private int generateHeader(int firtRow, HSSFWorkbook workbook, HSSFSheet sheet, List<AnalysisCriterion> criteria,
			HSSFCellStyle style) {
		HSSFRow rowHeader = sheet.createRow((short) firtRow++);
		int columnIndex = INDEX_FIRST_COLUMN;

		createCell(rowHeader, "Trade", (short) columnIndex++, style);
		createCell(rowHeader, "Buy Date", (short) columnIndex++, style);
		createCell(rowHeader, "Buy Price", (short) columnIndex++, style);
		createCell(rowHeader, "Sell Date", (short) columnIndex++, style);
		createCell(rowHeader, "Sell Price", (short) columnIndex++, style);
		for (AnalysisCriterion criterium : criteria) {
			createCell(rowHeader, criterium.getClass().getSimpleName(), (short) columnIndex++, style);
		}
		LOG.info("Header created");
		return firtRow;
	}

	private int generateInternalRow(int firtRow, HSSFWorkbook wb, HSSFSheet sheet, Decision decision,
			List<AnalysisCriterion> criteria, HSSFCellStyle style2, HSSFCellStyle style3) {
		boolean even = true;
		HSSFCellStyle style = null;
		LOG.info("Initializing Internal rows of the Decision " + decision.getFileName());
		long time = System.currentTimeMillis();
		double calculate;
		int index = firtRow - 24;
		for (Trade trade : decision.getTrades()) {
			int i = 0;
			if (even){
				style = style2;
			}
			else {
				style = style3;
			}
			int indexcolumn = 1;
			HSSFRow rowDecision = sheet.createRow((short) firtRow++);
			createCell(rowDecision, index++, (short) indexcolumn++, style);
			if (trade.getEntry().getType() == OperationType.BUY) {
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getEntry().getIndex()).getDateName(),
						(short) indexcolumn++, style);
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getEntry().getIndex()).getClosePrice(),
						(short) indexcolumn++, style);
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getExit().getIndex()).getDateName(),
						(short) indexcolumn++, style);
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getExit().getIndex()).getClosePrice(),
						(short) indexcolumn++, style);
			} else {
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getExit().getIndex()).getDateName(),
						(short) indexcolumn++, style);
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getExit().getIndex()).getClosePrice(),
						(short) indexcolumn++, style);
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getEntry().getIndex()).getDateName(),
						(short) indexcolumn++, style);
				createCell(rowDecision, decision.getActualSlice().getTick(trade.getEntry().getIndex()).getClosePrice(),
						(short) indexcolumn++, style);
			}
			for (AnalysisCriterion criterium : criteria) {
				calculate = criterium.calculate(decision.getActualSlice(), trade);
				createCell(rowDecision, calculate, (short) indexcolumn++, style);
			}
			i++;
			even = !even;
		}

		LOG.info("Ending Internal rows of the Decision " + decision.getFileName() + " (" + (System.currentTimeMillis() - time) + " miliseconds)");
		return firtRow;
	}

	private void generateSummaryRow(int firtRow, HSSFWorkbook wb, HSSFSheet sheet, Decision decision,
			List<AnalysisCriterion> criteria, HSSFCellStyle style) {
		int indexcolumn;
		double calculate;
		indexcolumn = 1;

		HSSFRow rowLast = sheet.createRow((short) firtRow++);
		createCell(rowLast, "TOTAL", (short) indexcolumn++, style);
		createCell(rowLast, decision.getActualSlice().getTick(decision.getActualSlice().getBegin()).getDateName(),
				(short) indexcolumn++, style);
		createCell(rowLast, " - ", (short) indexcolumn++, style);
		createCell(rowLast, decision.getActualSlice().getTick(decision.getActualSlice().getEnd()).getDateName(),
				(short) indexcolumn++, style);
		createCell(rowLast, " - ", (short) indexcolumn++, style);
		for (AnalysisCriterion criterium : criteria) {
			calculate = criterium.calculate(decision.getActualSlice(), decision.getTrades());
			createCell(rowLast, calculate, (short) indexcolumn++, style);
		}
		LOG.info("Summary created");
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
