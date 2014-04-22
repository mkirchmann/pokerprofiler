package de.neuenberger.pokerprofiler.logic.analyzer;

import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;

public class PokerTableDescriptionLogic extends BaseLogic {
	/**
	 * 
	 * @param pokerTableDescription
	 * @return Returns the average number of players, that see the flop, if the
	 *         flop is seen.
	 */
	public static float getPlayerSeeFlop(
			final PokerTableDescription pokerTableDescription) {
		final GameDescription gdArr[] = pokerTableDescription
				.getGameDescriptionArray();
		int nominator = 0;
		int denominator = 0;
		for (int i = 0; i < gdArr.length; i++) {
			final GamePlay gpArr[] = gdArr[i].getGamePlayArray();
			boolean isFlopped = false;
			for (int x = 0; x < gpArr.length; x++) {
				if (gpArr[x] != null) {
					if (gpArr[x].getFlopBets().length > 0) {
						nominator++;
						isFlopped = true;
					}
				}

			}
			if (isFlopped) {
				denominator++;
			}
		}

		return getPercentage(nominator, denominator) * 0.01f;
	}

	/**
	 * 
	 * @param pokerTableDescription
	 * @return Returns the percentage of games where the flop is seen.
	 */
	public static float getPercentageFlopSeen(
			final PokerTableDescription pokerTableDescription) {
		final GameDescription gdArr[] = pokerTableDescription
				.getGameDescriptionArray();
		int nominator = 0;
		int denominator = 0;
		for (int i = 0; i < gdArr.length; i++) {
			final GamePlay gpArr[] = gdArr[i].getGamePlayArray();
			boolean isFlopped = false;
			for (int x = 0; x < gpArr.length; x++) {
				if (gpArr[x] != null) {
					if (gpArr[x].getFlopBets().length > 0) {
						isFlopped = true;
					}
				}
			}
			if (isFlopped) {
				nominator++;
			}
			denominator++;
		}

		return getPercentage(nominator, denominator) * 0.01f;
	}

	public static float getFlopCost(
			final PokerTableDescription pokerTableDescription) {
		final GameDescription gdArr[] = pokerTableDescription
				.getGameDescriptionArray();
		float sum = 0.0f;
		for (int i = 0; i < gdArr.length; i++) {
			final GamePlay gpArr[] = gdArr[i].getGamePlayArray();
			float maxFlopCost = 0.0f;
			for (int x = 0; x < gpArr.length; x++) {
				if (gpArr[x] != null) {
					if (gpArr[x].getPreFlopBets().length > 0) {
						final float flopCost = BetLogic.sumAllBets(gpArr[x]
								.getPreFlopBets())
								+ gpArr[x].getBlind()
								+ gpArr[x].getAnte();
						if (flopCost > maxFlopCost) {
							maxFlopCost = flopCost;
						}
					}
				}
			}
			sum += maxFlopCost;
		}
		if (gdArr.length == 0) {
			return -1;
		}
		return sum / gdArr.length;
	}

	public static boolean isPlayerPartOfTable(final PokerTableDescription ptd,
			final PlayerDescription pd) {
		final GameDescription[] gameDescriptionArray = ptd
				.getGameDescriptionArray();
		for (int i = 0; i < gameDescriptionArray.length; i++) {
			if (GameDescriptionLogic.containsPlayerDescription(
					gameDescriptionArray[i], pd)) {
				return true;
			}
		}
		return false;
	}
}
