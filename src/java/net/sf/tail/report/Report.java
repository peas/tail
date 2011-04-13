package net.sf.tail.report;

import java.util.ArrayList;
import java.util.List;

import net.sf.tail.AnalysisCriterion;
import net.sf.tail.Operation;
import net.sf.tail.OperationType;
import net.sf.tail.StrategiesSet;
import net.sf.tail.TimeSeriesSlicer;
import net.sf.tail.Trade;
import net.sf.tail.analysis.evaluator.Decision;

public class Report {

	private Technic technic;

	private transient List<Decision> decisions;

	private AnalysisCriterion applyedCriterion;

	private Number criterionValue;

	private List<Number> additionalCriteriaValue;

	private List<AnalysisCriterion> additionalCriteria;

	private String name;

	private transient List<Trade> trades;

	public Report(StrategiesSet strategiesSet, AnalysisCriterion criterion, TimeSeriesSlicer slicer,
			List<Decision> decisions) {
		this.applyedCriterion = criterion;
		this.decisions = decisions;
		additionalCriteria = new ArrayList<AnalysisCriterion>();
		additionalCriteriaValue = new ArrayList<Number>();
		criterionValue = criterion.summarize(slicer.getSeries(), decisions);
		this.technic = new Technic(strategiesSet, slicer);
	}

	public Number addSummarizedCriteria(AnalysisCriterion criterion) {
		Number result = criterion.summarize(technic.getSlicer().getSeries(), decisions);
		additionalCriteria.add(criterion);
		additionalCriteriaValue.add(result);
		return result;
	}

	public List<Decision> getDecisions() {
		return decisions;
	}

	public TimeSeriesSlicer getSlicer() {
		return technic.getSlicer();
	}

	public AnalysisCriterion getApplyedCriterion() {
		return applyedCriterion;
	}

	public Number getCriterionValue() {
		return criterionValue;
	}

	public List<Trade> getAllTrades() {
		if (trades == null) {
			List<Trade> trades = new ArrayList<Trade>();
			for (Decision decision : decisions) {
				trades.addAll(decision.getTrades());
			}
			this.trades = trades;
		}
		return trades;
	}

	public List<Trade> getTradesUntilIndex(int index) {
		List<Trade> trades = getAllTrades();
		List<Trade> partialTrades = new ArrayList<Trade>();
		for (Trade trade : trades) {
			if (trade.getExit().getIndex() <= index)
				partialTrades.add(new Trade(trade.getEntry(), trade.getExit()));
			else if (trade.getEntry().getIndex() < index) {
				Trade artificialTrade;
				if (trade.getEntry().getType() == OperationType.BUY) {
					artificialTrade = new Trade(trade.getEntry(), new Operation(index, OperationType.SELL));
				} else {
					artificialTrade = new Trade(trade.getEntry(), new Operation(index, OperationType.BUY));
				}
				partialTrades.add(artificialTrade);
				break;
			}
		}
		return partialTrades;
	}

	public String getSlicePeriodName() {
		return technic.getSlicer().getSeries().getName() + ": " + technic.getSlicer().getSeries().getPeriodName();
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return this.getClass().getSimpleName()
				+ technic.getSlicer().getSeries().getTick(technic.getSlicer().getSeries().getBegin()).getDate()
						.toString("hhmmddMMyyyy");
	}

	public List<AnalysisCriterion> getAdditionalCriteria() {
		return additionalCriteria;
	}

	public Number getValue(AnalysisCriterion criteria) {
		if (additionalCriteria.contains(criteria)) {
			int index = additionalCriteria.indexOf(criteria);
			return additionalCriteriaValue.get(index);
		}
		if (applyedCriterion.equals(criteria)) {
			return criterionValue;
		}
		return criteria.summarize(technic.getSlicer().getSeries(), decisions);
	}

	public void setName(String name) {
		this.name = name;
	}

	public Technic getTechnic() {
		return technic;
	}

	public String getScript() {
		return technic.getStrategiesSet().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalCriteria == null) ? 0 : additionalCriteria.hashCode());
		result = prime * result + ((additionalCriteriaValue == null) ? 0 : additionalCriteriaValue.hashCode());
		result = prime * result + ((applyedCriterion == null) ? 0 : applyedCriterion.hashCode());
		result = prime * result + ((criterionValue == null) ? 0 : criterionValue.hashCode());
		result = prime * result + ((decisions == null) ? 0 : decisions.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((technic == null) ? 0 : technic.hashCode());
		result = prime * result + ((trades == null) ? 0 : trades.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Report other = (Report) obj;
		if (additionalCriteria == null) {
			if (other.additionalCriteria != null)
				return false;
		} else if (!additionalCriteria.equals(other.additionalCriteria))
			return false;
		if (additionalCriteriaValue == null) {
			if (other.additionalCriteriaValue != null)
				return false;
		} else if (!additionalCriteriaValue.equals(other.additionalCriteriaValue))
			return false;
		if (applyedCriterion == null) {
			if (other.applyedCriterion != null)
				return false;
		} else if (!applyedCriterion.equals(other.applyedCriterion))
			return false;
		if (criterionValue == null) {
			if (other.criterionValue != null)
				return false;
		} else if (!criterionValue.equals(other.criterionValue))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (technic == null) {
			if (other.technic != null)
				return false;
		} else if (!technic.equals(other.technic))
			return false;
		if (trades == null) {
			if (other.trades != null)
				return false;
		} else if (!trades.equals(other.trades))
			return false;
		return true;
	}
	

}
