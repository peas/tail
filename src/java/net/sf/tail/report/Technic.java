package net.sf.tail.report;

import net.sf.tail.StrategiesSet;
import net.sf.tail.TimeSeriesSlicer;

public class Technic {
	
	private StrategiesSet strategiesSet;
	
	private TimeSeriesSlicer slicer;
	
	public Technic(StrategiesSet strategiesSet, TimeSeriesSlicer slicer) {
		this.strategiesSet = strategiesSet;
		this.slicer = slicer;
	}


	public TimeSeriesSlicer getSlicer() {
		return slicer;
	}


	public StrategiesSet getStrategiesSet() {
		return strategiesSet;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((slicer == null) ? 0 : slicer.hashCode());
		result = prime * result + ((strategiesSet == null) ? 0 : strategiesSet.hashCode());
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
		final Technic other = (Technic) obj;
		if (slicer == null) {
			if (other.slicer != null)
				return false;
		} else if (!slicer.equals(other.slicer))
			return false;
		if (strategiesSet == null) {
			if (other.strategiesSet != null)
				return false;
		} else if (!strategiesSet.equals(other.strategiesSet))
			return false;
		return true;
	}


}
