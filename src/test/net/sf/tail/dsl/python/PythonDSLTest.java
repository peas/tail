package net.sf.tail.dsl.python;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.script.ScriptException;

import net.sf.tail.Strategy;
import net.sf.tail.TimeSeries;
import net.sf.tail.indicator.simple.ClosePriceIndicator;
import net.sf.tail.indicator.tracker.SMAIndicator;
import net.sf.tail.sample.SampleTimeSeries;
import net.sf.tail.strategy.IndicatorCrossedIndicatorStrategy;

import org.junit.Before;
import org.junit.Test;

public class PythonDSLTest {
	
	private TimeSeries series;
	private ClosePriceIndicator close;

	@Before
	public void setUp() throws Exception {
		series = new SampleTimeSeries();
		close = new ClosePriceIndicator(series);
	}
	
	@Test
	public void testEvalStrategies() throws ScriptException {
		String firstStrategy = read("CrossedIndicator.py");
		
		List<String> defaultStrategies = new ArrayList<String>();
		for (int i = 1; i < 50; i++) {
			IndicatorCrossedIndicatorStrategy strategy = new
				IndicatorCrossedIndicatorStrategy(
						new SMAIndicator(close,i),new SMAIndicator(close,2*i));
			defaultStrategies.add(strategy.getName());
			
		}		
		PythonDSL dsl = new PythonDSL(read("/pythonDSLutils.py"), series);
		Set<Strategy> dslStrategies = dsl.evalStrategies(firstStrategy);

		for (Strategy strategy : dslStrategies){
			assertTrue(defaultStrategies.contains(strategy.getName()));
		}		
	}
	
	private static String read(String name) {
		String text = "";
		Scanner scanner = new Scanner(PythonDSLTest.class.getResourceAsStream(name));
		while (scanner.hasNextLine()) {
			text += scanner.nextLine() + "\n";
		}
		return text;
	}

}
