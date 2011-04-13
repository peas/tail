package technicalIndicator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.tail.indicator.cache.CachedIndicator;

import technicalAnalysis.Indicator;
import technicalAnalysis.Period;
import technicalAnalysis.TimeSeries;

public class EMAIndicator extends CachedIndicator {
	private int periodsNumber;
	private TimeSeries timeSeries;
	private Map<Date,Map<String, Double>> indicatorResults;
	
	public EMAIndicator(TimeSeries s, int periodsNumber){
		timeSeries = s;
		this.periodsNumber = periodsNumber;
		indicatorResults = new HashMap<Date, Map<String, Double>>();
		calc();
	}
	
	public void periodUpdated() {
		calc();

	}

	public void calc() {
		List<Period> periodList = timeSeries.getPeriodsList();
		
		double previous = SMAIndicator.CalcOneSMA(timeSeries, periodsNumber);
		
		double multiplier = ((double)2 / (double)(periodsNumber + 1));
		double result;
		for (int j = periodsNumber; j < periodList.size(); j++){
			result = ((periodList.get(j).getClose() - previous) * multiplier) + previous;
			
	
			HashMap<String, Double> resultHash = new HashMap<String, Double>();
			resultHash.put("EMA", result);
			indicatorResults.put(periodList.get(j).getDate(), resultHash);
			previous = result;
		}

	}

	public Map<Date, Map<String, Double>> getIndicatorResults() {
		
		return indicatorResults;
	}

}
