package net.sf.tail.report.xls;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.sf.tail.Indicator;
import net.sf.tail.Operation;
import net.sf.tail.OperationType;
import net.sf.tail.Strategy;
import net.sf.tail.Trade;
import net.sf.tail.analysis.criteria.TotalProfitCriterion;
import net.sf.tail.analysis.evaluator.Decision;
import net.sf.tail.indicator.simple.ClosePriceIndicator;
import net.sf.tail.indicator.tracker.EMAIndicator;
import net.sf.tail.report.Report;
import net.sf.tail.runner.HistoryRunner;
import net.sf.tail.sample.SampleTimeSeries;
import net.sf.tail.series.FullyMemorizedSlicer;
import net.sf.tail.strategiesSet.JavaStrategiesSet;
import net.sf.tail.strategy.FakeStrategy;
import net.sf.tail.strategy.IndicatorCrossedIndicatorStrategy;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

public class TImeSeriesXlsGeneratorTest {

	private TimeSeriesXlsGenerator XLStimeSeries;

	@Before
	public void setUp()
	{
		XLStimeSeries = new TimeSeriesXlsGenerator(new HSSFWorkbook());
	}
	
	@Test
	public void testGenerateTimeSeriesXls() throws IOException
	{
		SampleTimeSeries series = new SampleTimeSeries();
		ArrayList<Trade> trades = new ArrayList<Trade>();
		trades.add(new Trade(new Operation(0, OperationType.BUY), new Operation(2, OperationType.SELL)));
		trades.add(new Trade(new Operation(3, OperationType.BUY), new Operation(5, OperationType.SELL)));

		FullyMemorizedSlicer slicer = new FullyMemorizedSlicer(series, new Period().withYears(1));
		ArrayList<Decision> decisions = new ArrayList<Decision>();
		Strategy fakeStrategy = new FakeStrategy(new Operation[0], new Operation[0]);
		Decision decision = new Decision(fakeStrategy, slicer, 0,
				new TotalProfitCriterion(), trades, new HistoryRunner(slicer, fakeStrategy));
		decisions.add(decision);

		Set<Strategy> strategies = new HashSet<Strategy>();
		Indicator<Double> close = new ClosePriceIndicator(series);
		for (int i = 4; i < 5; i++) {
			Indicator<Double> tracker = new EMAIndicator(close, i);
			Strategy strategy = new IndicatorCrossedIndicatorStrategy(close, tracker);
			strategies.add(strategy);
		}
		
		Report report = new Report(new JavaStrategiesSet( strategies), new TotalProfitCriterion(), slicer, decisions);
		
		HSSFSheet sheet = XLStimeSeries.generate(report);
		assertEquals(13, sheet.getPhysicalNumberOfRows());
	}
}
