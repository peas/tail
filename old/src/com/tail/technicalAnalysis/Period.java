/**
 * 
 */
package technicalAnalysis;

import java.util.Date;

/**
 * @author Marcio
 * 
 *Um Time Trade é o intervalo de medição determinado por sua Série.
 *  
 */
public class Period {
	public Period(Date date, double open, double close, double high, double low, double change,double previous,
			double volumeAmount, double volumeFinancier, double quantity) {
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.change = change;
		this.previous = previous;
		this.volumeAmount = volumeAmount;
		this.volumeFinancier = volumeFinancier;
		this.quantity = quantity;
	}
	
	private Date date;
	private double open;
	private double close;
	private double high;
	private double low;
	private double change;
	private double previous;
	private double volumeAmount;
	private double volumeFinancier;
	private double quantity;
	
	public double getClose() {
		return close;
	}
	public Date getDate() {
		return date;
	}
	public double getHigh() {
		return high;
	}
	public double getLow() {
		return low;
	}
	public double getOpen() {
		return open;
	}
	public double getPrevious() {
		return previous;
	}
	public double getQuantity() {
		return quantity;
	}
	public double getVolumeAmount() {
		return volumeAmount;
	}
	public double getVolumeFinancier() {
		return volumeFinancier;
	}
	public double getChange() {
		return change;
	}
	
	
}
