package net.sf.tail.report.xls;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.tail.Indicator;
import net.sf.tail.Operation;
import net.sf.tail.OperationType;
import net.sf.tail.Strategy;
import net.sf.tail.Trade;
import net.sf.tail.analysis.criteria.TotalProfitCriterion;
import net.sf.tail.analysis.evaluator.Decision;
import net.sf.tail.graphics.SeriesChart;
import net.sf.tail.graphics.SeriesDataset;
import net.sf.tail.indicator.simple.ClosePriceIndicator;
import net.sf.tail.indicator.tracker.EMAIndicator;
import net.sf.tail.report.Report;
import net.sf.tail.runner.HistoryRunner;
import net.sf.tail.sample.SampleTimeSeries;
import net.sf.tail.series.FullyMemorizedSlicer;
import net.sf.tail.strategiesSet.JavaStrategiesSet;
import net.sf.tail.strategy.FakeStrategy;
import net.sf.tail.strategy.IndicatorCrossedIndicatorStrategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorkbookGeneratorTest {

	private WorkbookGenerator Workbook;
	
	private final String CHAR_DIST = "./";

	private Report report;

	private Period period;

	private List<File> charts;

	private HSSFWorkbook book;

	@Before
	public void setUp() throws IOException {
		Workbook = new WorkbookGenerator();
		SampleTimeSeries series = new SampleTimeSeries();
		ArrayList<Trade> trades = new ArrayList<Trade>();
		trades.add(new Trade(new Operation(0, OperationType.BUY), new Operation(2, OperationType.SELL)));
		trades.add(new Trade(new Operation(3, OperationType.BUY), new Operation(5, OperationType.SELL)));

		period = new Period().withYears(1);
		FullyMemorizedSlicer slicer = new FullyMemorizedSlicer(series, period);
		ArrayList<Decision> decisions = new ArrayList<Decision>();
		Strategy fakeStrategy = new FakeStrategy(new Operation[0], new Operation[0]);
		Decision decision = new Decision(fakeStrategy, slicer, 0,
				new TotalProfitCriterion(), trades, new HistoryRunner(slicer, fakeStrategy));
		decisions.add(decision);
		

		Set<Strategy> strategies = new HashSet<Strategy>();
		Indicator<Double> close = new ClosePriceIndicator(series);
		List<Indicator<? extends Number>> indicators = new ArrayList<Indicator<? extends Number>>();
		for (int i = 4; i < 5; i++) {
			Indicator<Double> tracker = new EMAIndicator(close, i);
			Strategy strategy = new IndicatorCrossedIndicatorStrategy(close, tracker);
			indicators.add(tracker);
			strategies.add(strategy);
		}
		
		SeriesDataset seriesDataset = new SeriesDataset(series, indicators, series.getBegin(), series.getEnd());
		SeriesChart seriesChart = new SeriesChart(seriesDataset);
		JFreeChart jfreechart = seriesChart.createChart("", false, true);
		String imagePath = CHAR_DIST + File.separatorChar + decision.getFileName() + ".png";
		File file = new File(imagePath);
		ChartUtilities.saveChartAsPNG(file, jfreechart, 800, 300);

		charts = new ArrayList<File>();
		for (int i = 0; i < decisions.size(); i++) {
			charts.add(file);
		}
		charts.add(file);
		report = new Report(new JavaStrategiesSet( strategies), new TotalProfitCriterion(), slicer, decisions);

		book = Workbook.generate(report, period, charts);
	}

	@Test
	public void testGenerateWorkbook() throws IOException {
		assertEquals(3, book.getNumberOfSheets());
	}
	
	@After
	public void tearDown() {
		for (File f : charts) {
			f.delete();
		}
	}
}
