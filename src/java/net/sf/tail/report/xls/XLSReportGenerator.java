package net.sf.tail.report.xls;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.tail.AnalysisCriterion;
import net.sf.tail.report.Report;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.joda.time.Period;

public interface XLSReportGenerator {

	List<HSSFSheet> generate(Report report, List<AnalysisCriterion> criteria, Period period, List<File> chart) throws IOException;

	List<HSSFSheet> generate(Report report, Period period, List<File> charts) throws IOException;

}