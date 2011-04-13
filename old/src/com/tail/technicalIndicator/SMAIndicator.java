package technicalIndicator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import technicalAnalysis.Indicator;
import technicalAnalysis.Period;
import technicalAnalysis.TimeSeries;

public class SMAIndicator implements Indicator {
	private int periodsNumber;
	private TimeSeries timeSeries;
	private Map<Date,Map<String, Double>> indicatorResults ;
	
	public SMAIndicator(TimeSeries s,int periodsNumber) {
		indicatorResults = new HashMap<Date,Map<String, Double>>();
		this.periodsNumber = periodsNumber;
		timeSeries = s;
		calc();
	}
	
	public void calc() {
		List<Period> periodList = timeSeries.getPeriodsList();
		
		for(int j = 0; j < periodsNumber; j++)
		{
			Map<String, Double> result = new HashMap<String, Double>();
			result.put("SMA", null);
			indicatorResults.put(periodList.get(j).getDate(), result);
		}
		
		
		for (int j = periodsNumber - 1; j < periodList.size(); j++)
		{
			double average = 0;
			for (int i = j; i > j - periodsNumber; i--) {
				average += periodList.get(i).getClose();
			}
			average = average / periodsNumber;
			
			HashMap<String, Double> result = new HashMap<String, Double>();
			result.put("SMA", average);
			indicatorResults.put(periodList.get(j).getDate(), result);
		}
	}

	public void periodUpdated() {
		double average = 0;
		List<Period> periodList = timeSeries.getPeriodsList();
		
		int size = timeSeries.getPeriodsList().size() - 1;
		for (int i = size ; i > size - periodsNumber; i--) {
			average += periodList.get(i).getClose();
		}
		average = average / periodsNumber;
		
		HashMap<String, Double> result = new HashMap<String, Double>();
		result.put("SMA", average);
		indicatorResults.put(periodList.get(size).getDate(), result);
	}

	public int getTimeTradeNumber() {
		return periodsNumber;
	}

	public TimeSeries getTimeSeries() {
		return timeSeries;
	}

	public Map<Date, Map<String, Double>> getIndicatorResults() {
		return indicatorResults;
	}
	
	public static double CalcOneSMA(TimeSeries s, int periodsNumber)
	{
		
//		TODO: Fazer o método
		return 0.0;
		
	}

	

}
