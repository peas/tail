package dataRead;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import technicalAnalysis.Period;
import technicalAnalysis.TimeSeries;
import technicalAnalysis.TimeSeries.Interval;

/**
 * 
 * @author Marcio
 *
 */
public class TimeSeriesFactory {
	
	public TimeSeries readSeries(String fileName) throws IOException
	{
		List<Period> periodsList = new ArrayList<Period>();

		List<List<String>> llObj = CSVFileReader.read(fileName);
		for (List<String> lObj : llObj) {
			periodsList.add(0,readTrade(lObj));
		}	
		return new TimeSeries(periodsList,Interval.IntraDay15Minutes);
	}
	
	private Period readTrade(List<String> obj) {
		Period trade = null;
		
			SimpleDateFormat simpleDate = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
			Date date = new Date();
			try {
				date = simpleDate.parse(obj.get(0));
			} catch (ParseException e) {

			}
			double open = java.lang.Double.parseDouble(obj.get(1));
			double close = java.lang.Double.parseDouble(obj.get(2));
			double high = java.lang.Double.parseDouble(obj.get(3));
			double low = java.lang.Double.parseDouble(obj.get(4));
			double change = java.lang.Double.parseDouble(obj.get(5));
			double previous = java.lang.Double.parseDouble(obj.get(6));
			double volumeAmount = java.lang.Double.parseDouble(obj.get(7));
			double volumeFinancier = java.lang.Double.parseDouble(obj.get(8));
			double quantity = java.lang.Double.parseDouble(obj.get(9));

			trade = new Period(date,open,close, high, low, change, previous, volumeAmount, 
					volumeFinancier, quantity);
			return trade;
	}

	
}

