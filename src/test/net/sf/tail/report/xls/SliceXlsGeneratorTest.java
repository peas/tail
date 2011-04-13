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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SliceXlsGeneratorTest {

	private SliceXlsGenerator XLSSlicerSeries;

	private Report report;

	private Period period;

	private List<File> charts;

	private List<HSSFSheet> sheet;

	private final String CHAR_DIST = "./";

	@Before
	public void setUp() throws IOException {
		XLSSlicerSeries = new SliceXlsGenerator(new HSSFWorkbook());
		SampleTimeSeries series = new SampleTimeSeries();
		ArrayList<Trade> trades = new ArrayList<Trade>();
		trades.add(new Trade(new Operation(0, OperationType.BUY), new Operation(2, OperationType.SELL)));
		trades.add(new Trade(new Operation(3, OperationType.BUY), new Operation(5, OperationType.SELL)));

		ArrayList<Decision> decisions = new ArrayList<Decision>();
		period = new Period().withYears(1);
		FullyMemorizedSlicer slicer = new FullyMemorizedSlicer(series, period);
		Strategy fakeStrategy = new FakeStrategy(new Operation[0], new Operation[0]);
		Decision decision = new Decision(fakeStrategy, slicer, 0,
				new TotalProfitCriterion(), trades, new HistoryRunner(slicer, fakeStrategy));
		decisions.add(decision);

		

		Set<Strategy> strategies = new HashSet<Strategy>();
		List<Indicator<? extends Number>> indicators = new ArrayList<Indicator<? extends Number>>();
		Indicator<Double> close = new ClosePriceIndicator(series);
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

		report = new Report(new JavaStrategiesSet( strategies), new TotalProfitCriterion(), slicer, decisions);

		sheet = XLSSlicerSeries.generate(report, period, charts);
	}

	@Test
	public void testGenerateSlicer() throws IOException {
		assertEquals(27, sheet.get(0).getLastRowNum());
	}
	
	@After
	public void tearDown()
	{
		for (File f : charts) {
			f.delete();
		}
	}
}
