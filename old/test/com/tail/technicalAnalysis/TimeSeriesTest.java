package technicalAnalysis;

import java.io.IOException;

import dataRead.TimeSeriesFactory;
import junit.framework.TestCase;

public class TimeSeriesTest extends TestCase {
	public TimeSeriesFactory serieFactory;
	public TimeSeries serie;
	
	public void setUp() throws IOException{
		serieFactory = new TimeSeriesFactory();
		serie = serieFactory.readSeries("BaseBovespa/15min/petr3-Dia-05-07.csv");
	}
	
	public void tearDown(){
		
	}
	
	public void testAddPeriodAfter() throws NotSincronizedException{
		int size = serie.getPeriodsList().size();
		Period period = serie.getPeriodsList().remove(size - 1);
		serie.addPeriod(period);
		
		assertEquals(size, serie.getPeriodsList().size());
		assertTrue(period.getDate().compareTo(serie.getPeriodsList().get(size - 2).getDate()) >= 0 );
	}
	
	public void testAddPeriodBefore() throws NotSincronizedException{
		int size = serie.getPeriodsList().size();
		Period period = serie.getPeriodsList().remove(0);
		serie.addPeriod(period);
		
		assertEquals(size, serie.getPeriodsList().size());
		assertTrue(period.getDate().compareTo(serie.getPeriodsList().get(1).getDate()) <= 0 );
	}
	
}
