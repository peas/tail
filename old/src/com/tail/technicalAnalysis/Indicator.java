/**
 * 
 */
package technicalAnalysis;

import java.util.Date;
import java.util.Map;

/**
 * @author Marcio
 *
 */
public interface Indicator {
	public void periodUpdated();
	public void calc();
	public Map<Date, Map<String, Double>> getIndicatorResults();
}
