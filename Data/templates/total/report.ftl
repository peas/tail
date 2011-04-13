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
	<tr class="row1">
		<td>${x}</td>
		<td class="date">${report.slicer.slice(0).period}</td>
		<td class="strategy">-</td>
		<td>-</td>
		<#list criterions as criterion>
			<td>-</td>			
		</#list>
	</tr>

	
	<#list report.decisions as decision>
	<#if (x%2)= 0>
	<tr class="row1">
	<#else>
	<tr class="row2">
	</#if>	
	<#assign x=x+1>
		<td>${x}</td>
		<td class="date">${decision.getActualSlice().period}</td>
		<td class="strategy">${decision.strategy.name}</td>
		<td>${report.applyedCriterion.calculate(decision.getActualSlice(), decision.trades)}</td>
		<#list criterions as criterion>
			<td>${criterion.calculate(decision.getActualSlice(), decision.trades)}</td>			
		</#list>
	</tr>
	</#list>
	<tr class="total">
		<td>TOTAL</td>
		<td>${report.slicer.series.period}</td>
		<td>-</td>
		<td>${report.applyedCriterion.summarize(report.slicer.series, report.decisions)}</td>
		<#list criterions as criterion>
			<td>${criterion.summarize(report.slicer.series, report.decisions)}</td>			
		</#list>
	</tr>
	
</table>

<#include "footer.ftl">
