
(1..50).collect{|numero| 
	Tail::IndicatorCrossedIndicatorStrategy.new($close, ema(numero))
}

