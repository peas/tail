package net.sf.tail.report;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.tail.AnalysisCriterion;
import net.sf.tail.Operation;
import net.sf.tail.OperationType;
import net.sf.tail.Strategy;
import net.sf.tail.TimeSeriesSlicer;
import net.sf.tail.Trade;
import net.sf.tail.analysis.criteria.NumberOfTicksCriterion;
import net.sf.tail.analysis.criteria.TotalProfitCriterion;
import net.sf.tail.analysis.evaluator.Decision;
import net.sf.tail.report.html.ReportHTMLGenerator;
import net.sf.tail.runner.HistoryRunner;
import net.sf.tail.sample.SampleTimeSeries;
import net.sf.tail.series.RegularSlicer;
import net.sf.tail.strategy.FakeStrategy;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

public class SliceReportGeneratorTest {

	private Decision decision;
		
	private List<AnalysisCriterion> criteria;
	private List<Trade> trades;

	@Before
	public void setUp() throws Exception {
		SampleTimeSeries series = new SampleTimeSeries(new DateTime().withDayOfMonth(1), new DateTime().withDayOfMonth(2), new DateTime().withDayOfMonth(3)
				, new DateTime().withDayOfMonth(4), new DateTime().withDayOfMonth(5), new DateTime().withDayOfMonth(6));
		trades = new ArrayList<Trade>();
		trades.add(new Trade(new Operation(0, OperationType.BUY), new Operation(2, OperationType.SELL)));
		trades.add(new Trade(new Operation(3, OperationType.BUY), new Operation(5, OperationType.SELL)));

		TimeSeriesSlicer slicer = new RegularSlicer(series, new Period().withYears(2000));
		Strategy strategy = new FakeStrategy(new Operation[0], new Operation[0]);
		decision = new Decision(strategy, slicer, 0,
				new TotalProfitCriterion(), trades, new HistoryRunner(slicer, strategy));
		
		criteria = new ArrayList<AnalysisCriterion>();
		criteria.add(new NumberOfTicksCriterion());
		criteria.add(new TotalProfitCriterion());
	}

	@Test
	public void testSMAGenerate() throws IOException {
		StringBuffer html = new ReportHTMLGenerator().generate(decision, criteria, "");
		
		assertTrue(html.toString().contains("NumberOfTicks"));
		assertTrue(html.toString().contains("4"));
	}
	
	@Test
	public void testSMAGenerateWithoutCriteria() throws IOException {
		StringBuffer html = new ReportHTMLGenerator().generate(decision, "");

		assertTrue(html.toString().contains("TotalProfitCriterion"));
		assertTrue(html.toString().contains("6"));
	}
}
