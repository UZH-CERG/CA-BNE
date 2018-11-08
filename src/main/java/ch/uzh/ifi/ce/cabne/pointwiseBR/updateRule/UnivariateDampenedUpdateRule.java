package ch.uzh.ifi.ce.cabne.pointwiseBR.updateRule;

import ch.uzh.ifi.ce.cabne.Helpers;

public class UnivariateDampenedUpdateRule implements UpdateRule<Double, Double> {
	double wMin, wMax;
	double c;
	boolean useAbsolute;
	
	public UnivariateDampenedUpdateRule(double wMin, double wMax, double c, boolean useAbsolute) {
		// default values should be something like 
		//		UnivariateDampenedUpdateRule(0.2, 0.7, 0.5 / 1e-5, true) where 1e-5 is the target epsilon, or
		//		UnivariateDampenedUpdateRule(0.2, 0.7, 0.5, false)
		this.wMin = wMin;
		this.wMax = wMax;
		this.c = c;
		this.useAbsolute = useAbsolute;
	}

	@Override
	public Double update(Double v, Double oldbid, Double newbid, double oldutility, double newutility) {
        // Note that the update factor depends on epsilon, so for larger epsilon, it will switch into 
		// "near to convergence" mode faster.
		
		double utilityLoss = Helpers.utilityLoss(oldutility, newutility, useAbsolute);
        double w = 2 / Math.PI * Math.atan(c * utilityLoss) * (wMax - wMin) + wMin;

		return oldbid * (1-w) + newbid * w;
	}
}
