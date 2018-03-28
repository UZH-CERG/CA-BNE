package ch.uzh.ifi.ce.cabne.BR;

import java.util.List;

import ch.uzh.ifi.ce.cabne.Helpers;
import ch.uzh.ifi.ce.cabne.algorithm.BNESolverContext;
import ch.uzh.ifi.ce.cabne.pointwiseBR.Optimizer;
import ch.uzh.ifi.ce.cabne.strategy.Strategy;

public class HeuristicUnivariateVerifier implements Verifier<Double, Double> {
	
	BNESolverContext<Double, Double> context;

	public HeuristicUnivariateVerifier(BNESolverContext<Double, Double> context) {
		super();
		this.context = context;
	}

	public Strategy<Double, Double> convertStrategy(int gridsize, Strategy<Double, Double> s) {
		return s;
	}

	public double computeEpsilon(int gridsize, int i, Strategy<Double, Double> si, List<Strategy<Double, Double>> s) {
		double highestEpsilon = 0.0;
		
		double maxValue = si.getMaxValue();
		for (int j = 0; j<=gridsize; j++) {
			double v = maxValue * ((double) j) / (gridsize);
			Double oldbid = si.getBid(v);
			Optimizer.Result<Double> result = context.optimizer.findBR(i, v, oldbid, s);

			// epsilon at control point itself
			double epsilon = Helpers.absoluteUtilityLoss(result.oldutility, result.utility);
			highestEpsilon = Math.max(highestEpsilon, epsilon);
		}
		
		return highestEpsilon;
	}

}
