package util;

import java.math.BigDecimal;

public class MidasUtil {

	public static BigDecimal roundDouble(Double d) {
		BigDecimal SMAValue;
		if(d != null   ) {
			
			SMAValue = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		else
		{
			SMAValue = null;
		}
		return SMAValue;
	}
}
