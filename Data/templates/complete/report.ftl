<#include "header.ftl">
<#include "chart.ftl">

 <table align="center">
	<tr>
		<th>Period</th>
		<th>Date</th>
		<th>Strategy</th>
		<th>${report.applyedCriterion.class.simpleName.split("Criterion")[0]}</th>
		<#list criterions as criterion>
			<th>${criterion.class.simpleName.split("Criterion")[0]}</th>			
		</#list>
	</tr>
	<#assign x=1>
	<tr class="row2">
		<td>${x}</td>
		<td class="date">${report.slicer.slice(0).periodName}</td>
		<td class="strategy">-</td>
		<td>-</td>
		<#list criterions as criterion>
			<td>-</td>			
		</#list>
	</tr>

	
	<#list report.decisions as decision>
	<#if (x%2)= 0>
	<tr class="row2">
	<#else>
	<tr class="row1">
	</#if>		
	<#assign x=x+1>
		<td>${x}</td>
		<td class="date">${decision.getActualSlice().periodName}</td>
		<td class="strategy"><a href=${urls.get(x - 2)}>${decision.strategy.name}</a></td>
		<td>${report.applyedCriterion.calculate(decision.getActualSlice(), decision.trades)}</td>
		<#list criterions as criterion>
			<td>${criterion.calculate(decision.getActualSlice(), decision.trades)}</td>			
		</#list>
	</tr>
	
	</#list>
	<tr class="total">
		<td>TOTAL</td>
		<td>${report.slicer.series.periodName}</td>
		<td>-</td>
		<td>${report.applyedCriterion.summarize(report.slicer.series, report.decisions)}</td>
		<#list criterions as criterion>
			<td>${criterion.summarize(report.slicer.series, report.decisions)}</td>			
		</#list>
	</tr>
	
</table>

<#include "footer.ftl">
