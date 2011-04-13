package net.sf.tail.dsl.javascript;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.script.ScriptException;

import net.sf.tail.Indicator;
import net.sf.tail.Strategy;
import net.sf.tail.TimeSeries;
import net.sf.tail.indicator.simple.ClosePriceIndicator;
import net.sf.tail.indicator.tracker.SMAIndicator;
import net.sf.tail.sample.SampleTimeSeries;
import net.sf.tail.strategy.IndicatorCrossedIndicatorStrategy;

import org.junit.Before;
import org.junit.Test;

public class JavaScriptDSLTest {

	private TimeSeries series;
	private ClosePriceIndicator close;

	@Before
	public void setUp() throws Exception {
		series = new SampleTimeSeries();
		close = new ClosePriceIndicator(series);
	}

	
	@Test
	public void testEvalIndicator() throws ScriptException {
		JavaScriptDSL dsl = new JavaScriptDSL("",series);
		String text = read("IndexedSMAIndicator.js");
		@SuppressWarnings("unchecked")
		Indicator<Double> scriptSMA = (Indicator<Double>) dsl.evalIndicator(text);
	
		assertEquals(Double.NaN,scriptSMA.getValue(0));
		assertEquals(2d,scriptSMA.getValue(1));
		assertEquals(3d,scriptSMA.getValue(2));
		assertEquals(4d,scriptSMA.getValue(3),0.1);
	}

	@Test
	public void testEvalStrategies() throws ScriptException {
		String text2 = read("Strategy.js");
		
		List<String> defaultStrategies = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			defaultStrategies.add(new IndicatorCrossedIndicatorStrategy(new SMAIndicator(close,i), new SMAIndicator(close,2*i)).getName());
		}		
		JavaScriptDSL dsl = new JavaScriptDSL("", series);
		Set<Strategy> dslStrategies = dsl.evalStrategies(text2);

		for (Strategy strategy : dslStrategies) {
			assertTrue(defaultStrategies.contains(strategy.getName()));
		}
	}
	
	private static String read(String name) {
		String text = "";
		Scanner scanner = new Scanner(JavaScriptDSLTest.class.getResourceAsStream(name));
		while (scanner.hasNextLine()) {
			text += scanner.nextLine();
		}
		return text;
	}

}
