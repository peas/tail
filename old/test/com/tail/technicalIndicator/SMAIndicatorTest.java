package technicalIndicator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import technicalAnalysis.Period;
import technicalAnalysis.TimeSeries;
import technicalAnalysis.TimeSeries.Interval;
import dataRead.CSVFileReader;

public class SMAIndicatorTest extends TestCase {
	
	List<Period> periodsList;
	public void setUp() throws IOException
	{
		periodsList = new ArrayList<Period>();
		
		List<List<String>> llobj = CSVFileReader.read("BaseBovespa/tests/petr4-Teste-SMA-parametro-20.csv");
		for (List<String> lObj : llobj) {
			periodsList.add(0,readTestSMATrade(lObj));
			
		}
	}
	
	public void testSMA1()
	{
		Map<Date,Map<String,Double>> sma1Result = createSMA(1).getIndicatorResults();
		
		for (int i = 0; i < sma1Result.size(); i++) {
			assertEquals(periodsList.get(i).getChange(), sma1Result.get(periodsList.get(i).getDate()).get("SMA"));
		}
		//assertFalse(true);
	}
	
	private SMAIndicator createSMA (int time)
	{
		return new SMAIndicator(new TimeSeries(periodsList, Interval.IntraDay15Minutes), time);
	}
	private Period readTestSMATrade(List<String> obj)
	{
		Period trade = null;
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
		Date date = new Date();
		try {
			date = simpleDate.parse(obj.get(0));
		} catch (ParseException e) {

		}
		
		double last = java.lang.Double.parseDouble(obj.get(1));
		double max = java.lang.Double.parseDouble(obj.get(2));
		double min = java.lang.Double.parseDouble(obj.get(3));
		double open = java.lang.Double.parseDouble(obj.get(4));
		double sma1 = java.lang.Double.parseDouble(obj.get(5));
		double sma20 = java.lang.Double.parseDouble(obj.get(6));
		double sma30 = java.lang.Double.parseDouble(obj.get(7));
		
		trade = new Period(date,last,max, min, open, sma1, sma20, sma30, 0, 0);
		
		/*public Period(Date date, double open, double close, double high, double low, double change,double previous,
				double volumeAmount, double volumeFinancier, double quantity)*/ 
		System.out.println(" Date " + date + " Ultima " + last + " Sma1 " + sma1 + " SMA20 " + sma20 + " Sma30 " + sma30);
		return trade;	
		}
}
