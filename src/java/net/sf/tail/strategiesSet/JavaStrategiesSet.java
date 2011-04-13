package net.sf.tail.strategiesSet;

import java.util.Set;

import net.sf.tail.StrategiesSet;
import net.sf.tail.Strategy;

public class JavaStrategiesSet implements StrategiesSet {
	
	private Set<Strategy> strategies;
	
	public JavaStrategiesSet(Set<Strategy> strategies) {
		this.strategies = strategies;
	}
	
	public Set<Strategy> getStrategies() {
		return strategies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((strategies == null) ? 0 : strategies.hashCode());
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
		final JavaStrategiesSet other = (JavaStrategiesSet) obj;
		if (strategies == null) {
			if (other.strategies != null)
				return false;
		} else if (!strategies.equals(other.strategies))
			return false;
		return true;
	}
}
