
(1..50).collect{|numero| 
	Tail::IndicatorCrossedIndicatorStrategy.new($close, Tail::EMAIndicator.new($close, numero))
}


