package dataRead;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import technicalAnalysis.Indicator;
import technicalAnalysis.Period;
import technicalAnalysis.TimeSeries;
import technicalIndicator.SMAIndicator;
import util.MidasUtil;

public class TimeSeriesFactoryTest extends TestCase{
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test public void testReadCSVFileTest(){
		try {
			TimeSeriesFactory tfs = new TimeSeriesFactory();
			TimeSeries ts = tfs.readSeries("BaseBovespa/15min/petr3-Dia-05-07.csv");
			Indicator sma = new SMAIndicator(ts,20);
			for (Period p : ts.getPeriodsList()) {
				Double smaResult;
				smaResult = sma.getIndicatorResults().get(p.getDate()).get("SMA");
				BigDecimal SMAValue = MidasUtil.roundDouble(smaResult);
				System.out.println("Date: "+ p.getDate()+ " Open: "+p.getOpen()+ " Close "+p.getClose()+ " SMA " + SMAValue);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	@After
	public void tearDown() throws Exception {
	}
	
}
