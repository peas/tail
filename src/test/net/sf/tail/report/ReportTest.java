package net.sf.tail.report;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.tail.AnalysisCriterion;
import net.sf.tail.Operation;
import net.sf.tail.OperationType;
import net.sf.tail.Strategy;
import net.sf.tail.TimeSeriesSlicer;
import net.sf.tail.Trade;
import net.sf.tail.analysis.criteria.BuyAndHoldCriterion;
import net.sf.tail.analysis.criteria.TotalProfitCriterion;
import net.sf.tail.analysis.evaluator.Decision;
import net.sf.tail.runner.HistoryRunner;
import net.sf.tail.sample.SampleTimeSeries;
import net.sf.tail.series.FullyMemorizedSlicer;
import net.sf.tail.strategy.FakeStrategy;

import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

public class ReportTest {

	private List<Decision> decisions;

	private TimeSeriesSlicer slicer;

	private List<Trade> trades;

	@Before
	public void setUp() throws Exception {
		SampleTimeSeries series = new SampleTimeSeries();
		trades = new ArrayList<Trade>();
		trades.add(new Trade(new Operation(0, OperationType.BUY), new Operation(2, OperationType.SELL)));
		trades.add(new Trade(new Operation(3, OperationType.BUY), new Operation(5, OperationType.SELL)));

		decisions = new ArrayList<Decision>();
		slicer = new FullyMemorizedSlicer(series, new Period().withYears(1));
		Strategy fakeStrategy = new FakeStrategy(new Operation[0], new Operation[0]);
		Decision decision = new Decision(fakeStrategy, slicer, 0,
				new TotalProfitCriterion(), trades, new HistoryRunner(slicer, fakeStrategy));
		decisions.add(decision);
		
	}

	@Test
	public void testGetAllTrades() throws IOException {

		Report report = new Report(null, new TotalProfitCriterion(), slicer, decisions);
		assertEquals(trades, report.getAllTrades());
	}

	@Test
	public void testGetCriterionValue() throws IOException {

		Report report = new Report(null, new TotalProfitCriterion(), slicer, decisions);
		assertEquals(4.5, report.getCriterionValue());
	}
	
	@Test
	public void testGetSlicePeriodName() throws IOException {

		Report report = new Report(null, new TotalProfitCriterion(), slicer, decisions);
		assertEquals("SampleTimeSeries: 09:00 31/12/1969 - 09:00 31/12/1969", report.getSlicePeriodName());
	}
	
	@Test
	public void testGetFileName() throws IOException {

		Report report = new Report(null, new TotalProfitCriterion(), slicer, decisions);
		assertEquals("Report090031121969", report.getFileName());
	}
	
	@Test
	public void testGetAdditionalCriteria() throws IOException {

		Report report = new Report(null, new TotalProfitCriterion(), slicer, decisions);
		assertEquals(new ArrayList<AnalysisCriterion>(), report.getAdditionalCriteria());
	}
	
	@Test
	public void testGetValue() throws IOException {

		Report report = new Report(null, new TotalProfitCriterion(), slicer, decisions);
		assertEquals((new TotalProfitCriterion()).summarize(slicer.getSeries(), decisions), report.getValue(report.getApplyedCriterion()));
		report.addSummarizedCriteria(new BuyAndHoldCriterion());
		assertEquals((new BuyAndHoldCriterion()).summarize(slicer.getSeries(), decisions), report.getValue(report.getAdditionalCriteria().get(0)));
	}
}
