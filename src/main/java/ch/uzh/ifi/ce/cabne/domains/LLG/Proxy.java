package ch.uzh.ifi.ce.cabne.domains.LLG;


import ch.uzh.ifi.ce.cabne.domains.Mechanism;


public class Proxy implements Mechanism<Double, Double> {

	@Override
	public double computeUtility(int i, Double v, Double[] bids) {
		// In this mechanism, we assume that all players bid only on their bundle of interest.
		// bids is therefore an array of length 3.
		
		if (i==2) {
			// utility of global player
			if (bids[2] > bids[0] + bids[1]) {
				return v - bids[1] - bids[0];
			}
			return 0.0;
		} else if (bids[2] > bids[0] + bids[1]) {
        	// global player wins
            return 0.0;
        } else {
        	double payment;
        	
            if (bids[2] > bids[0] + bids[1]) {
                payment = 0;
            } else if (bids[2] <= 2 * Math.min(bids[0], bids[1])) {
                payment = bids[2] / 2;
            } else if (bids[0] == bids[1]) {
            	payment = bids[2] / 2;
            } else if (bids[i] < bids[(i+1) % 2]) {
            	payment = bids[i];
            } else {
            	payment = bids[2] - bids[(i+1) % 2];
            }
            return v - payment;
        }
    }
}
