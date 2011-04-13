package net.sf.tail.dsl;

import java.util.Set;

import javax.script.ScriptException;

import net.sf.tail.Indicator;
import net.sf.tail.Strategy;

public interface TailDSL {
	Indicator<?> evalIndicator(String code) throws ScriptException;
	Set<Strategy> evalStrategies(String code) throws ScriptException;
}
