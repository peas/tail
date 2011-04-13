package net.sf.tail.dsl.ruby;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;
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

public class RubyDSL implements TailDSL {

	private static final String NEWLINE = "\n";

	private String header;

	private TimeSeries series;

	private ScriptEngineManager factory;

	public RubyDSL(String header, TimeSeries series) {
		this.header = header+ NEWLINE;
		this.series = series;
		factory = new ScriptEngineManager();
	}

	public Indicator<?> evalIndicator(String code) {
		ScriptEngine engine = factory.getEngineByName("jruby");

		engine.put("series", series);

		try {
			engine.eval(header + code);
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
		Invocable invocable = (Invocable)engine;
		Indicator<?> indicator = invocable.getInterface(Indicator.class);
		return indicator;
	}

	public Set<Strategy> evalStrategies(String code) {
		ScriptEngine engine = factory.getEngineByName("jruby");

		Set<Strategy> strategies = new LinkedHashSet<Strategy>();
		engine.put("strategies", strategies);
		engine.put("series", series);
		engine.put("close", new ClosePriceIndicator(series));
		try {
			engine.eval(header + code );
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}

		return strategies;
	}

	public static String read(String name) {
		String text = "";
		Scanner scanner;
		try {
			scanner = new Scanner(new FileInputStream(name));
			while (scanner.hasNextLine()) {
				text += scanner.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return text;
	}

}
