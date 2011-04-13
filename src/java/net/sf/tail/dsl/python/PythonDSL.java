package net.sf.tail.dsl.python;

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

public class PythonDSL implements TailDSL {

	private static final String NEWLINE = "\n";

	private String header;

	private TimeSeries series;

	private ScriptEngineManager factory;

	public PythonDSL(String header, TimeSeries series) {
		this.header = header + NEWLINE;
		this.series = series;
		factory = new ScriptEngineManager();
	}

	public Indicator<?> evalIndicator(String code) throws ScriptException {		
		
		ScriptEngine engine = factory.getEngineByName("jython");
		
		engine.put("series", series);
		
		engine.eval(header + code);
		Invocable invocable = (Invocable)engine;
		Indicator<?> indicator = invocable.getInterface(Indicator.class);
		return indicator;
	}

	public Set<Strategy> evalStrategies(String code) throws ScriptException {
		/*
		for (ScriptEngineFactory engine : factory.getEngineFactories()) {
			System.out.print(engine.getEngineName());
		} */
		ScriptEngine engine = factory.getEngineByName("jython");

		Set<Strategy> strategies = new HashSet<Strategy>();
		
		engine.put("series", series);
		engine.put("close", new ClosePriceIndicator(series));
		engine.put("strategies", strategies);

		engine.eval(header + code);

		return strategies;
	}

}
