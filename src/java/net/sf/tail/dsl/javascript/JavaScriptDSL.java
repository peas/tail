package net.sf.tail.dsl.javascript;

import java.util.HashSet;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.tail.Indicator;
import net.sf.tail.Strategy;
import net.sf.tail.TimeSeries;
import net.sf.tail.dsl.TailDSL;
import net.sf.tail.indicator.simple.ClosePriceIndicator;

public class JavaScriptDSL implements TailDSL {

	private static final String NEWLINE = "\n";

	private String header;

	private TimeSeries series;

	private ScriptEngineManager factory;

	public JavaScriptDSL(String header, TimeSeries series) {
		this.header = header + NEWLINE;
		this.series = series;
		factory = new ScriptEngineManager();
	}

	public Indicator<?> evalIndicator(String code) throws ScriptException {
		ScriptEngine engine = factory.getEngineByName("JavaScript");

		engine.put("series", series);
		
		engine.eval(header + code);
		Invocable invocable = (Invocable)engine;
		Indicator<?> indicator = invocable.getInterface(Indicator.class);
		return indicator;
	}

	public Set<Strategy> evalStrategies(String code) throws ScriptException {
		ScriptEngine engine = factory.getEngineByName("JavaScript");

		Set<Strategy> strategies = new HashSet<Strategy>();
		engine.put("strategies", strategies);
		engine.put("series", series);
		engine.put("close", new ClosePriceIndicator(series));

		engine.eval(header + code);

		return strategies;
	}

}
