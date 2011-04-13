package net.sf.tail.dsl.ruby;

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
import net.sf.tail.indicator.tracker.EMAIndicator;
import net.sf.tail.sample.SampleTimeSeries;
import net.sf.tail.strategy.IndicatorCrossedIndicatorStrategy;

import org.junit.Before;
import org.junit.Test;

public class RubyDSLTest {

	private TimeSeries series;
	private ClosePriceIndicator close;

	@Before
	public void setUp(){
		series = new SampleTimeSeries();
		close = new ClosePriceIndicator(series);
	}
	
	@Test
	public void testCreationOfSMAStrategiesUsingFQN() throws ScriptException {
		String text = read("EMAStrategies3.rb");
		
		List<String> defaultStrategies = new ArrayList<String>();
		for (int i = 1; i <= 50; i++) {
			defaultStrategies.add(new IndicatorCrossedIndicatorStrategy(close,new EMAIndicator(close,i)).getName());
		}
		
		RubyDSL dsl = new RubyDSL(read("/rubyDSLutils.rb"), series);
		Set<Strategy> dslStrategies = dsl.evalStrategies(text);

		for (Strategy strategy : dslStrategies) {
			assertTrue(defaultStrategies.contains(strategy.getName()));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEvalIndicatorIndexedSMA() throws ScriptException{
		RubyDSL dsl = new RubyDSL(read("/rubyDSLutils.rb"), series);
		
		Indicator<Double> scriptSMA = (Indicator<Double>) dsl.evalIndicator(read("IndexedSMAIndicator.rb"));
		
		assertEquals(Double.POSITIVE_INFINITY,scriptSMA.getValue(0));
		assertEquals(4d,scriptSMA.getValue(1));
		assertEquals(4.5,scriptSMA.getValue(2));
		assertEquals(5.3,scriptSMA.getValue(3),0.1);
	}

	private static String read(String name) {
		String text = "";
		Scanner scanner = new Scanner(RubyDSLTest.class.getResourceAsStream(name));
		while (scanner.hasNextLine()) {
			text += scanner.nextLine() + "\n";
		}
		return text;
	}
}
