package technicalAnalysis;

import java.util.Date;
import java.util.List;

public class TimeSeries {
	private List<Period> periodsList;
	private Interval interval;
	private List<Indicator> indicatorList;	
	
	public TimeSeries(List<Period> periodsList,Interval interval) {
		this.periodsList = periodsList;
		this.interval = interval;
	}
	
	public enum Interval{
		InterDay,
		IntraDay15Minutes;
	}
	
	/**
	 * Adiciona um novo TimeTrade na Serie e avisa todos os objetos que implementam Indicator que a Serie foi atualizada.
	 * 
	 * @param period
	 * @throws Exception 
	 */
	public void addPeriod(Period period) throws NotSincronizedException{
		
		if (period.getDate().compareTo(getFinalDate()) >= 0){
			periodsList.add(period);
			notifyIndicators();
		}
		else if (period.getDate().compareTo(getInitialDate()) <= 0){
			periodsList.add(0,period);
			notifyIndicators();
		}else{
			throw new NotSincronizedException();
		}	
	}

	private void notifyIndicators() {
		if(indicatorList != null) {
			for (Indicator indicator : indicatorList) {
				indicator.periodUpdated();
			}
		}
	}
	
	public Date getFinalDate() {
		return periodsList.get(periodsList.size() - 1).getDate();
	}
	
	public List<Indicator> getIndicatorList() {
		return indicatorList;
	}
	
	public Date getInitialDate() {
		return periodsList.get(0).getDate();
	}
	
	public Interval getInterval() {
		return interval;
	}
	
	public List<Period> getPeriodsList() {
		return periodsList;
	}
}
