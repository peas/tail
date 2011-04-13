package net.sf.tail.report.xls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.sf.tail.AnalysisCriterion;
import net.sf.tail.analysis.criteria.NumberOfTicksCriterion;
import net.sf.tail.analysis.criteria.TotalProfitCriterion;
import net.sf.tail.report.Report;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.Period;

public class WorkbookGenerator {

	private static final Logger LOG = Logger.getLogger(WorkbookGenerator.class);
	public WorkbookGenerator() {
		LOG.setLevel(Level.WARN);
	}

	public HSSFWorkbook generate(Report report, Period period, List<File> charts) throws IOException {
		List<AnalysisCriterion> criteria = new LinkedList<AnalysisCriterion>();
		criteria.add(new NumberOfTicksCriterion());
		criteria.add(new TotalProfitCriterion());
		return generate(report, criteria, period, charts);
	}

	public HSSFWorkbook generate(Report report, List<AnalysisCriterion> criteria, Period period, List<File> charts)
	throws IOException {
		long time = System.currentTimeMillis();
		LOG.info("Initializing the generation of the XLS");

		List<File> chartFile = new ArrayList<File>();
		if(charts.size() > 0)
			chartFile.add(charts.remove(0));
		HSSFWorkbook workbook = new HSSFWorkbook();
		ReportXlsGenerator reportXls = new ReportXlsGenerator(workbook);
		XLSReportGenerator slicerXls = new SliceXlsGenerator(workbook);
		TimeSeriesXlsGenerator seriesXls = new TimeSeriesXlsGenerator(workbook);

		List<AnalysisCriterion> criteria2 = new ArrayList<AnalysisCriterion>();
		for (AnalysisCriterion analysisCriterion : criteria) {
			criteria2.add(analysisCriterion);
		}
		criteria2.add(0, report.getApplyedCriterion());

		seriesXls.generate(report);
		if(chartFile.size() > 0)
			reportXls.generate(report, criteria, period, chartFile);
		else
			reportXls.generate(report, criteria, period);
		slicerXls.generate(report, criteria2, period, charts);

		for (File file : charts) {
			file.delete();
		}
		for (File file : chartFile) {
			file.delete();
		}

		LOG.info("XLS generated (" + (System.currentTimeMillis() - time) + " miliseconds)");
		return workbook;
	}

}
