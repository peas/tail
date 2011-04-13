<html>
<head>
   <title>Walk Forward Report</title>
   <link rel="stylesheet" type="text/css" href="../style/style.css">
</head>

<body>

<h1>Slice Report</h1>
<h2>Stock: <span class="titleHeader">${decision.getActualSlice().name} for: ${decision.getActualSlice().periodName}</span></h2>

<h3>
<span class="settingsFixed">Strategy: </span> <span class="settings">${decision.strategy.class.simpleName}</span> 
<span class="settingsFixed">Criteria: </span> <span class="settings">${decision.criterion.class.simpleName}</span></h3>