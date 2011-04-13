package net.sf.tail.strategiesSet;

import java.util.ResourceBundle;
import java.util.Set;

import net.sf.tail.StrategiesSet;
import net.sf.tail.Strategy;
import net.sf.tail.TimeSeriesSlicer;
import net.sf.tail.dsl.ruby.RubyDSL;

public class RubyStrategiesSet implements StrategiesSet {
	
	private String dslHeader;
	private Set<Strategy> strategies;
	private String script;
	private static final ResourceBundle fileBundle = ResourceBundle.getBundle("net.sf.tail.i18n.ruby");

	
	
	public RubyStrategiesSet(String script, TimeSeriesSlicer slicer) {
		this.dslHeader = fileBundle.getString("RUBY_UTILS_FILE");
		RubyDSL dsl = new RubyDSL(RubyDSL.read(dslHeader), slicer.getSeries());
		this.script = script;
		this.strategies = dsl.evalStrategies(script);
	}
	
	public String getScript() {
		return script;
	}

	@Override
	public String toString() {
		return script;
	}
	
	public Set<Strategy> getStrategies() {
		return strategies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dslHeader == null) ? 0 : dslHeader.hashCode());
		result = prime * result + ((script == null) ? 0 : script.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RubyStrategiesSet other = (RubyStrategiesSet) obj;
		if (script == null) {
			if (other.script != null)
				return false;
		} else if (!script.equals(other.script))
			return false;
		return true;
	}

}
