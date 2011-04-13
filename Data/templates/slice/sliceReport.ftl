<#include "header.ftl">
<#include "chart.ftl">
 <table align="center">
 	<tr>
 		<th>Trade</th>
 		<th>Buy Date</th>
 		<th>Buy Price</th>
 		<th>Sell Date</th>
 		<th>Sell Price</th>
 		<#list criteria as criterion>
 			<th> ${criterion.class.simpleName} </th>
 		</#list>	
 		
 	</tr>
	 		
 	<#assign x=1>
 		<#list decision.trades as trade>
			<#if (x%2)= 0>
				<tr class="row1">
			<#else>
				<tr class="row2">
			</#if>	
				<td>${x}</td>
				<td class="date">${decision.getActualSlice().getTick(trade.entry.index).getDateName()}</td>
				<td class="date">${decision.getActualSlice().getTick(trade.entry.index).closePrice}</td>
				<td class="date">${decision.getActualSlice().getTick(trade.exit.index).getDateName()}</td>
				<td class="date">${decision.getActualSlice().getTick(trade.exit.index).closePrice}</td>
				<#list criteria as criterion>
 					<td> ${criterion.calculate(decision.getActualSlice(), decision.trades.get(x - 1))}</td>
 				</#list>
				<#assign x=x+1>
		</#list>
	</tr>

	
	<tr class="total">
		<td>TOTAL</td>
		<td>${decision.getActualSlice().getTick(decision.getActualSlice().begin).getDateName()}</td>
		<td>-</td>
		<td>${decision.getActualSlice().getTick(decision.getActualSlice().end).getDateName()}</td>
		<td>-</td>
		<#list criteria as criterion>
 			<td>${criterion.calculate(decision.getActualSlice(), decision.trades)} </td>
 		</#list>
	</tr>
	
</table>

<#include "footer.ftl">
