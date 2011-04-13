importPackage(Packages.net.sf.tail.indicator.simple);
importPackage(Packages.net.sf.tail.indicator.tracker);
importPackage(Packages.net.sf.tail);
importPackage(Packages.net.sf.tail.strategy);

	for (var i = 0; i < 50; i++)
			strategies.add(new IndicatorCrossedIndicatorStrategy(new SMAIndicator(new ClosePriceIndicator(series),i), new SMAIndicator(new ClosePriceIndicator(series),2*i)));
	