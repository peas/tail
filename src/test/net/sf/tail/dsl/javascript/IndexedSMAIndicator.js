function getValue(x) { 
	var sum = 0; 
	for(var i = 0; i < x;i++)
		sum += series.getTick(x).getClosePrice(); 
	return sum / x; 
};