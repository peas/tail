package net.sf.tail.report.xls;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.sf.tail.AnalysisCriterion;
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

public class ReportXlsGenerator implements XLSReportGenerator {

	private static final int INDEX_FIRST_COLUMN = 1;

	private static final int INDEX_FIRST_ROW = 1;

	private HSSFWorkbook workbook;

	private CellStylist stylist;

	private static final Logger LOG = Logger.getLogger(ReportXlsGenerator.class);

	public ReportXlsGenerator(HSSFWorkbook workbook) {
		this.workbook = workbook;
		stylist = new CellStylist(workbook);
		LOG.setLevel(Level.WARN);
	}

	public List<HSSFSheet> generate(Report report, Period period, List<File> charts) throws IOException {
		List<AnalysisCriterion> criteria = new LinkedList<AnalysisCriterion>();

		criteria.add(new NumberOfTicksCriterion());
		criteria.add(new TotalProfitCriterion());

		return generate(report, criteria, period, charts);
	}

	public List<HSSFSheet> generate(Report report, List<AnalysisCriterion> criteria, Period period, List<File> charts)
	throws IOException {

		List<HSSFSheet> sheets = generate(report, criteria, period);

		for (HSSFSheet sheet : sheets) {

			stylist.drawImage(charts.get(0), sheet, 1, 4);

			stylist.rearrangeSheet(sheet, criteria.size() + 5);
		}
		return sheets;
	}

	public List<HSSFSheet> generate(Report report, List<AnalysisCriterion> criteria, Period period)
	throws IOException {
		LOG.info("Initializing XLS Report");
		long time = System.currentTimeMillis();
		int row = INDEX_FIRST_ROW;

		List<HSSFSheet> sheets = new LinkedList<HSSFSheet>();
		HSSFSheet sheet = workbook.createSheet("Complete Report");

		HSSFCellStyle style = stylist.createTitleCellStyle();
		String title = "WalkForward Report";
		row = generateTitle(row, title, style, sheet);

		style = stylist.createSubTitleCellStyle();
		String[] subtitle = new String[4];
		subtitle[0] = "Stock:";
		subtitle[1] = report.getSlicer().getSeries().getName();
		subtitle[2] = "for:";
		subtitle[3] = report.getSlicer().getSeries().getPeriodName();
		row = generateSubTitle(row, subtitle, style, sheet);

		style = stylist.createInfoCellStyle();
		String[] info = new String[4];
		info[0] = "Slicer:";
		info[1] = report.getSlicer().getName();
		info[2] = "Strategy:";
		info[3] = report.getDecisions().get(0).getStrategy().getClass().getSimpleName() + "				Criteria: " + report.getApplyedCriterion().getName();
		row = generateInfo(row, info, style, sheet);

		row = 24;

		style = stylist.createHeaderCellStyle();
		row = generateHeader(row, sheet, report, criteria, style);

		style = stylist.createFirstCellStyle();
		row = generateFirstRow(row, sheet, report, criteria, style);

		style = stylist.createInternalCellStyle();
		HSSFCellStyle style2 = stylist.createInternal2CellStyle();
		row = generateInternalRow(row, sheet, report, criteria, style, style2);

		style = stylist.createSummaryCellStyle();
		generateSummaryRow(row, sheet, report, criteria, style);
		sheets.add(sheet);
		LOG.info("XLS Report generated (" + (System.currentTimeMillis() - time) + " miliseconds)");
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

	private int generateHeader(int firtRow, HSSFSheet sheet, Report report, List<AnalysisCriterion> criteria,
			HSSFCellStyle style) {
		HSSFRow rowHeader = sheet.createRow((short) firtRow++);
		int columnIndex = INDEX_FIRST_COLUMN;

		createCell(rowHeader, "Period", (short) columnIndex++, style);
		createCell(rowHeader, "Initial Date", (short) columnIndex++, style);
		createCell(rowHeader, "Final Date", (short) columnIndex++, style);
		createCell(rowHeader, "Strategy", (short) columnIndex++, style);
		for (AnalysisCriterion criterium : criteria) {
			createCell(rowHeader, criterium.getClass().getSimpleName(), (short) columnIndex++, style);
		}
		LOG.info("Header created");
		return firtRow;
	}

	private int generateFirstRow(int firtRow, HSSFSheet sheet, Report report, List<AnalysisCriterion> criteria,
			HSSFCellStyle style) {
		int indexcolumn;
		HSSFRow rowFirst = sheet.createRow((short) firtRow++);
		indexcolumn = 1;

		createCell(rowFirst, (firtRow - 25), (short) indexcolumn++, style);
		createCell(rowFirst, report.getSlicer().getSlice(0).getTick(report.getSlicer().getSlice(0).getBegin())
				.getDateName(), (short) indexcolumn++, style);
		createCell(rowFirst, report.getSlicer().getSlice(0).getTick(report.getSlicer().getSlice(0).getEnd())
				.getDateName(), (short) indexcolumn++, style);
		createCell(rowFirst, " - ", (short) indexcolumn++, style);

		for (int i = 0; i < criteria.size(); i++) {
			createCell(rowFirst, " - ", (short) indexcolumn++, style);
		}
		LOG.info("First Row created");
		return firtRow;
	}

	private int generateInternalRow(int firtRow, HSSFSheet sheet, Report report, List<AnalysisCriterion> criteria,
			HSSFCellStyle style2, HSSFCellStyle style3) {
		boolean even = false;
		HSSFCellStyle style = null;
		LOG.info("Initializing Internal rows of the Report");
		long time = System.currentTimeMillis();
		double calculate;
		int index = firtRow - 24;
		for (Decision decision : report.getDecisions()) {
			if (even) {
				style = style2;
			}
			else {
				style = style3;
			}
			int indexcolumn = 1;
			HSSFRow rowDecision = sheet.createRow((short) firtRow++);

			createCell(rowDecision, index++, (short) indexcolumn++, style);
			createCell(rowDecision, decision.getActualSlice().getTick(decision.getActualSlice().getBegin()).getDateName(),
					(short) indexcolumn++, style);
			createCell(rowDecision, decision.getActualSlice().getTick(decision.getActualSlice().getEnd()).getDateName(),
					(short) indexcolumn++, style);
			createCell(rowDecision, decision.getStrategy().getName(), (short) indexcolumn++, style);
			for (AnalysisCriterion criterium : criteria) {
				calculate = criterium.calculate(decision.getActualSlice(), decision.getTrades());
				createCell(rowDecision, calculate, (short) indexcolumn++, style);
			}
			even = !even;
		}
		LOG.info("Ending Internal rows of the Report (" + (System.currentTimeMillis() - time) + " miliseconds)");
		return firtRow;
	}

	private void generateSummaryRow(int firtRow, HSSFSheet sheet, Report report, List<AnalysisCriterion> criteria,
			HSSFCellStyle style) {
		int indexcolumn;
		double calculate;
		indexcolumn = 1;

		HSSFRow rowLast = sheet.createRow((short) firtRow++);
		createCell(rowLast, "TOTAL", (short) indexcolumn++, style);
		createCell(rowLast, report.getSlicer().getSeries().getTick(report.getSlicer().getSeries().getBegin())
				.getDateName(), (short) indexcolumn++, style);
		createCell(rowLast, report.getSlicer().getSeries().getTick(report.getSlicer().getSeries().getEnd())
				.getDateName(), (short) indexcolumn++, style);
		createCell(rowLast, " - ", (short) indexcolumn++, style);
		for (AnalysisCriterion criterium : criteria) {
			calculate = criterium.summarize(report.getSlicer().getSeries(), report.getDecisions());
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