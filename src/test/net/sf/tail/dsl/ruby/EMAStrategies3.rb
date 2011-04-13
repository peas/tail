(1..50).collect{|numero| 
	$strategies.add(cross( close, ema(numero)))
}
