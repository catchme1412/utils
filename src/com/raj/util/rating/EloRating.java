package com.raj.util.rating;


/**
 * @author rkv
 * @see http://www.chess-iecc.com/ratings/algor.html
 */
public class EloRating {

	private static final int K = 1;

	/*
	 * returns the expected scores using the formula : Ea = 1/(1+10^(Ra-Rb)/400)
	 */
	public static double getExpectedScore(Rating expectedScorePlayer, Rating competetor) {
		return 1.0 / (1.0 + Math.pow(10, ((expectedScorePlayer.getRating() - competetor.getRating()))) / 400.0);
	}

	/*
	 * updating the ratings.
	 */
	public static void updateRatings(Rating expectedScorePlayer, Rating competetor, double score) {
		double expectedScoreOfWinner = getExpectedScore(expectedScorePlayer, competetor);
		double expectedScoreOfLoser = getExpectedScore(competetor, expectedScorePlayer);
		/*
		 * Formula : new rating = K (ActualRating-ExpectedRating) ActualRating =
		 * 1 if the person wins ExpectedRating = 0 if the person loses Here K =
		 * 10
		 */
		double newRating = expectedScorePlayer.getRating() + (K * (score - expectedScoreOfWinner));
		expectedScorePlayer.setRating(newRating);

		newRating = competetor.getRating() + (K * ((1 - score) - expectedScoreOfLoser));
		competetor.setRating(newRating);

	}

	public static void main(String[] args) {
		Rating winner = new Rating(1760);
		Rating looser = new Rating(1760);
		updateRatings(winner, looser, 0.5);
		System.out.println(winner.getRating());
		System.out.println(looser.getRating());
		updateRatings(looser, winner, 1);
		System.out.println(winner.getRating());
		System.out.println(looser.getRating());
		updateRatings(looser, winner, 1);
		System.out.println(winner.getRating());
		System.out.println(looser.getRating());
		for (int i = 0; i < 100; i++) {
			updateRatings(looser, winner, 0.5);
		}
		System.out.println(">>>>>>>>>>>>>>>>.");
		System.out.println(winner.getRating());
		System.out.println(looser.getRating());
	}
}
