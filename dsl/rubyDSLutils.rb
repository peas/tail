require 'java'

module Tail
	include_package 'java.util' 
	include_package 'net.sf.tail' 
	include_package 'net.sf.tail.indicator' 
	include_package 'net.sf.tail.indicator.tracker' 
	include_package 'net.sf.tail.indicator.simple' 
	include_package 'net.sf.tail.indicator.oscilator'
	include_package 'net.sf.tail.strategy' 
	include_package 'net.sf.tail.series' 
end

#Indicator
def averageGain(timeFrame)
	Tail::AverageGainIndicator.new($close, timeFrame)
end

#Indicator
def averageGain
	Tail::AverageGainIndicator.new($close, $series.getSize())
end

#Indicator
def averageLoss(timeFrame)
	Tail::AverageLossIndicator.new($close, timeFrame)
end

#Indicator
def averageLoss
	Tail::AverageLossIndicator.new($close, $series.getSize())
end

#Indicator
def max(indicator)
	Tail::HighestValueIndicator.new(indicator, $series.getSize())
end

#Indicator
def min(indicator)
	Tail::LowestValueIndicator.new(indicator, $series.getSize())
end

#Indicator
def averageDirectionalMovement(timeFrame)
	Tail::AverageDirectionalMovementIndicator.new($series, timeFrame)
end

#Indicator
def directionalMovement(timeFrame)
	Tail::DirectionalMovementIndicator.new($series, timeFrame)
end

#Indicator
def onBalanceVolume
	Tail::OnBalanceVolumeIndicator.new($series)
end

#Indicator
def standartDeviation(indicator, timeFrame)
	Tail::StandartDeviationIndicator.new(indicator, timeFrame)
end

#Indicator
def ema(timeFrame)
	Tail::EMAIndicator.new($close, timeFrame)
end

#Indicator
def multiplierIndicator(value)
	Tail::SimpleMultiplierIndicator.new($close, value)
end

#Indicator
def parabolicSAR(timeFrame)
	Tail::ParabolicSarIndicator.new($series, timeFrame)
end

#Indicator
def rsi(timeFrame)
	Tail::RSIIndicator.new(close, timeFrame)
end

#Indicator
def close
	Tail::ClosePriceIndicator.new($series)
end

#Indicator
def amount
	Tail::AmountIndicator.new($series)
end

#Indicator
def maxPrice
	Tail::MaxPriceIndicator.new($series)
end

#Indicator
def minPrice
	Tail::MinPriceIndicator.new($series)
end

#Indicator
def openPrice
	Tail::OpenPriceIndicator.new($series)
end

#Indicator
def volume
	Tail::VolumeIndicator.new($series)
end

#Indicator
def sma (timeFrame)
	Tail::SMAIndicator.new($close, timeFrame)
end

#Indicator
def wma (timeFrame)
	Tail::WMAIndicator.new($close, timeFrame)
end

#Indicator
def williamsR(timeFrame)
	Tail::WilliamsRIndicator.new($close, timeFrame)
end

#Strategy
def cross (indicator1, indicator2)
	Tail::IndicatorCrossedIndicatorStrategy.new(indicator1, indicator2)
end

#Strategy
def pipeEnter(indicatorUpper, indicatorLower, indicatorValue) 
	Tail::PipeEnterStrategy.new(indicatorUpper, indicatorLower, indicatorValue)
end

#Indicator
def stochastic(timeFrame)
	Tail::StochasticOscilatorK.new($series,timeFrame)
end

#Indicator
def constantIndicator(value)
	Tail::ConstantIndicator.new(value)
end

#Strategy
def notSoFast(strategy, ticks)
	Tail::NotSoFastStrategy.new(strategy, ticks)
end

#Strategy
def strategies
	$strategies
end
