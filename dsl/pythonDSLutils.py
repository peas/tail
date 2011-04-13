import java.lang as lang
import java.util
import net.sf.tail as tail 
import net.sf.tail.indicator
import net.sf.tail.indicator.tracker as tracker
import net.sf.tail.indicator.simple as simple
import net.sf.tail.strategy as strategy
import net.sf.tail.series as series

def exit( event ):
    lang.System.exit(0)

def sma(frame):
    return tracker.SMAIndicator(close,frame)

def ema (frame):
    return tracker.EMAIndicator(close,frame)

def crossed(upper,lower):
    return strategy.IndicatorCrossedIndicatorStrategy(upper,lower)