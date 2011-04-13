	
	@series = $series;
	
	def getValue(x)
		sum = 0;
		0.upto(x){
			sum += @series.getTick(x).getClosePrice();
		};
		return sum/x;
	end

