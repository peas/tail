<html>
<head>
   <title>Walk Forward Report</title>
   <link rel="stylesheet" type="text/css" href="style/style.css">
</head>

<body>


<h1>Walk Forward Report</h1>
<h2>Stock: <span class="titleHeader">${report.slicer.series.name} for: ${report.slicer.series.periodName}</span></h2>



<h3><span class="settingsFixed">Slicer:</span><span class="settings">${report.slicer.name}</span>
<span class="settingsFixed">Strategy: </span> <span class="settings">${report.decisions[0].strategy.class.simpleName}</span> 
<span class="settingsFixed">Criteria: </span> <span class="settings">${report.applyedCriterion.class.simpleName}</span></h3>