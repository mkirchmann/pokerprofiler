package de.neuenberger.pokerprofiler.logic.analyzer;

import de.neuenberger.poker.common.logic.HandRank;
import de.neuenberger.poker.common.logic.HandRankFactory;
import de.neuenberger.poker.common.model.Card;
import de.neuenberger.poker.common.model.TexasHoldem;
import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PlayerShowDownSummary;
import de.neuenberger.pokerprofiler.model.contentprovider.IContentFilter;
import de.neuenberger.pokerprofiler.model.contentprovider.filter.DefaultFilter;

public class PlayerDescriptionLogic extends BaseLogic {

	public static final int BET_STYLE_FOLD = 0;
	public static final int BET_STYLE_CHECK = 1;
	public static final int BET_STYLE_CALL = 2;
	public static final int BET_STYLE_BET = 3;
	public static final int BET_STYLE_RAISE = 4;
	public static final int BET_STYLE_CHECK_FOLD = 5;
	public static final int BET_STYLE_CHECK_CALL = 6;
	public static final int BET_STYLE_CHECK_RAISE = 7;
	public static final int BET_STYLE_BET_FOLD = 8;
	public static final int BET_STYLE_RAISE_FOLD = 9;

	public static IContentFilter contentFilter = new DefaultFilter();

	public static float getBettingStyleProbability(final PlayerDescription pd,
			final int bet_style) {
		int nominator = 0;
		int denominator = 0;
		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (!contentFilter.accepts(gp)) {
				continue;
			}
			final Bet betArr[][] = new Bet[4][];
			betArr[0] = gp.getPreFlopBets();
			betArr[1] = gp.getFlopBets();
			betArr[2] = gp.getTurnBets();
			betArr[3] = gp.getRiverBets();
			for (int x = 0; x < betArr.length; x++) {
				boolean validity = false;
				if (betArr[x].length == 0) {

				} else if (bet_style >= BET_STYLE_CHECK_FOLD) {
					if (bet_style == BET_STYLE_RAISE_FOLD) {
						if (BetLogic.isRaise(betArr[x])) {
							denominator++;
							if (BetLogic.isRaiseFold(betArr[x])) {
								nominator++;
							}
						}
					} else if (bet_style == BET_STYLE_BET_FOLD) {
						if (BetLogic.isBet(betArr[x])) {
							denominator++;
							if (BetLogic.isBetFold(betArr[x])) {
								nominator++;
							}
						}
					} else {
						if (BetLogic.isCheck(betArr[x])) {
							validity = true;
						}
					}
				} else {
					validity = true;
				}

				if (validity == true) {
					boolean fulfilled = false;
					denominator++;
					switch (bet_style) {
					case BET_STYLE_FOLD:
						fulfilled = BetLogic.isFold(betArr[x]);
						break;
					case BET_STYLE_BET:
						fulfilled = BetLogic.isBet(betArr[x]);
						break;
					case BET_STYLE_RAISE:
						fulfilled = BetLogic.isRaise(betArr[x]);
						break;
					case BET_STYLE_CHECK:
						fulfilled = BetLogic.isCheck(betArr[x]);
						break;
					case BET_STYLE_CALL:
						fulfilled = BetLogic.isCall(betArr[x]);
						break;
					case BET_STYLE_CHECK_FOLD:
						fulfilled = BetLogic.isCheckFold(betArr[x]);
						break;
					case BET_STYLE_CHECK_CALL:
						fulfilled = BetLogic.isCheckCall(betArr[x]);
						break;
					case BET_STYLE_CHECK_RAISE:
						fulfilled = BetLogic.isCheckRaise(betArr[x]);
						break;
					}

					if (fulfilled == true) {
						nominator++;
					}

				}
			}
		}

		return getPercentage(nominator, denominator);
	}

	public static float getVpiP(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;
		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {

			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp)) {
				denominator++;
				if (GamePlayLogic.getVoluntarilyInvestedMoney(gp) > 0) {
					nominator++;
				}
			}

		}

		return getPercentage(nominator, denominator);
	}

	public static float getDealerVp(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;
		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {

			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp)) {
				if (gp.getPosition() == GamePlay.POSITION_DEALER) {
					denominator++;
					if (GamePlayLogic.getVoluntarilyInvestedMoney(gp) > 0) {
						nominator++;
					}
				}
			}

		}

		return getPercentage(nominator, denominator);
	}

	public static float getPreFlopRaise(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp) && gp.getPreFlopBets().length != 0) {
				denominator++;

				if (BetLogic.isRaise(gp.getPreFlopBets())) {
					nominator++;
				}
			}
		}

		return getPercentage(nominator, denominator);
	}

	public static float getPreFlopAggression(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp) && gp.getPreFlopBets().length > 0
					&& BetLogic.isRaise(gp.getPreFlopBets())) {
				denominator++;
				final float bb = GameDescriptionLogic.getBigBlind(gp
						.getGameDescription());
				final Bet[] preFlopBets = gp.getPreFlopBets();
				for (int x = 0; x < preFlopBets.length; x++) {
					if (preFlopBets[x].getType() == Bet.TYPE_RAISE) {
						if (preFlopBets[x].getAmount() > 4.0f * bb) {
							nominator++;
							break;
						}
					}
				}
			}
		}

		return getPercentage(nominator, denominator);
	}

	public static float getPlaysMonsterSlowProbability(
			final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)) {
				if (GamePlayLogic.isMonsterPlay(gp)
						&& gp.getPreFlopBets().length > 0) {
					denominator++;

					if (GamePlayLogic.isPreflopSlowPlay(gp)) {
						nominator++;
					}
				}
			}
		}

		return getPercentage(nominator, denominator);
	}

	public static float getPlaysTrashProbability(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)) {
				denominator++;

				if (GamePlayLogic.isTrashPlay(gp)) {
					nominator++;
				}
			}
		}

		return getPercentage(nominator, denominator);

	}

	public static float sumInvestment(final PlayerDescription pd) {
		float ret = 0.0f;
		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp)) {
				ret += GamePlayLogic.getInvestedMoney(gp);
			}
		}
		return ret;
	}

	public static float sumCollectedPots(final PlayerDescription pd) {
		float ret = 0.0f;
		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp)) {
				ret += gp.getPot() + gp.getUnansweared();
			}
		}
		return ret;
	}

	public static float getContiBetProbability(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;
		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			if (contentFilter.accepts(gp)
					&& GamePlayLogic.isPreFlopAggressor(gp)) {
				final Bet betArr[] = gp.getFlopBets();
				if (betArr.length > 0) {
					denominator++;
					if (GamePlayLogic.hasFlopContinuationBetSize(gp)) {
						nominator++;
					}
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getContiBetFoldProbability(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;
		for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
			final GamePlay gp = pd.getGamePlay(i);
			final Bet betArr[] = gp.getFlopBets();
			final GamePlay gpAggressor = GamePlayLogic.getPreFlopAggressor(gp);
			if (contentFilter.accepts(gp) && gpAggressor != null) {
				if (GamePlayLogic.hasFlopContinuationBetSize(gpAggressor)) {
					denominator++;
					if (BetLogic.containsFold(betArr)) {
						nominator++;
					}
				}
			}
		}
		return getPercentage(nominator, denominator);
	}

	public static float wentToShowDown(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;
		float result = -1;
		if (pd != null) {
			for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
				final GamePlay gp = pd.getGamePlay(i);
				if (contentFilter.accepts(gp)
						&& GamePlayLogic.isReachingFlop(gp)) {
					denominator++;
					if (GamePlayLogic.isReachingShowDown(gp)) {
						nominator++;
					}
				}
			}
			result = getPercentage(nominator, denominator);
		}
		return result;
	}

	public static float wonWhenSawFlop(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;
		float result = -1;
		if (pd != null) {
			for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
				final GamePlay gp = pd.getGamePlay(i);
				if (contentFilter.accepts(gp)
						&& GamePlayLogic.isReachingFlop(gp)) {
					denominator++;
					if (gp.getPot() == GameDescriptionLogic.getMaximumWonPot(gp
							.getGameDescription())) {
						nominator++;
					}
				}
			}
			result = getPercentage(nominator, denominator);
		}
		return result;
	}

	public static float wonAtShowdown(final PlayerDescription pd) {
		int nominator = 0;
		int denominator = 0;
		float result = -1;
		if (pd != null) {
			for (int i = 0; i < pd.getSizeOfGamePlay(); i++) {
				final GamePlay gp = pd.getGamePlay(i);
				if (contentFilter.accepts(gp)
						&& GamePlayLogic.isReachingShowDown(gp)) {
					denominator++;
					if (gp.getPot() == GameDescriptionLogic.getMaximumWonPot(gp
							.getGameDescription())) {
						nominator++;
					}
				}
			}
			result = getPercentage(nominator, denominator);
		}
		return result;
	}

	public static PlayerShowDownSummary getHandRankProbabilityAtShowDown(
			final PlayerDescription playerDescription) {
		final PlayerShowDownSummary playerShowDownSummary = new PlayerShowDownSummary();

		final TexasHoldem texasHoldem = new TexasHoldem();
		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)
					&& gp.getGameDescription().getBoardCards()[0] != null) {
				texasHoldem.getPlayer()[0] = gp.getPlayerCards()[0];
				texasHoldem.getPlayer()[1] = gp.getPlayerCards()[1];
				texasHoldem.getFlop()[0] = gp.getGameDescription()
						.getBoardCards()[0];
				texasHoldem.getFlop()[1] = gp.getGameDescription()
						.getBoardCards()[1];
				texasHoldem.getFlop()[2] = gp.getGameDescription()
						.getBoardCards()[2];
				texasHoldem.setTurn(gp.getGameDescription().getBoardCards()[3]);
				texasHoldem
						.setRiver(gp.getGameDescription().getBoardCards()[4]);
				final Card[] cardsOfPlayer = texasHoldem.getCardsOfPlayer();
				try {
					final HandRank handRank = HandRankFactory.getInstance()
							.getHandRank(cardsOfPlayer);

					PlayerShowDownSummaryLogic.addHandRank(
							playerShowDownSummary, handRank);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		return playerShowDownSummary;
	}

	public static float getPocketAxProbability(
			final PlayerDescription playerDescription) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)
					&& GamePlayLogic.isTrashPlay(gp)) {
				denominator++;
				final Card[] cards = gp.getPlayerCards();
				if (CardLogic.isAx(cards)) {
					nominator++;
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getPocketKxProbability(
			final PlayerDescription playerDescription) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)
					&& GamePlayLogic.isTrashPlay(gp)) {
				denominator++;
				final Card[] cards = gp.getPlayerCards();
				if (CardLogic.isKx(cards)) {
					nominator++;
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getPocketSuitedProbability(
			final PlayerDescription playerDescription) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)
					&& GamePlayLogic.isTrashPlay(gp)) {
				denominator++;
				final Card[] cards = gp.getPlayerCards();
				if (CardLogic.isSuited(cards)) {
					nominator++;
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getPocketConnectedProbability(
			final PlayerDescription playerDescription) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)
					&& GamePlayLogic.isTrashPlay(gp)) {
				denominator++;
				final Card[] cards = gp.getPlayerCards();
				if (CardLogic.isConnected(cards)) {
					nominator++;
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getPocketPairProbability(
			final PlayerDescription playerDescription) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)
					&& GamePlayLogic.isTrashPlay(gp)) {
				denominator++;
				final Card[] cards = gp.getPlayerCards();
				if (CardLogic.isPair(cards)) {
					nominator++;
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getPocketUnclassifiedProbability(
			final PlayerDescription playerDescription) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			if (contentFilter.accepts(gp) && GamePlayLogic.isHandKnown(gp)
					&& GamePlayLogic.isTrashPlay(gp)) {
				denominator++;
				final Card[] cards = gp.getPlayerCards();
				if (CardLogic.isUnclassified(cards)) {
					nominator++;
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static IContentFilter getContentFilter() {
		return contentFilter;
	}

	public static void setContentFilter(final IContentFilter contentFilter) {
		PlayerDescriptionLogic.contentFilter = contentFilter;

		if (contentFilter == null) {
			setContentFilter(new DefaultFilter());
		}
	}

	public static float getBluffBetProbability(
			final PlayerDescription playerDescription, final int boardSize) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			Bet betArr[] = null;

			if (boardSize == 3) {
				betArr = gp.getFlopBets();
			} else if (boardSize == 4) {
				betArr = gp.getTurnBets();
			} else if (boardSize == 5) {
				betArr = gp.getRiverBets();
			}

			if (contentFilter.accepts(gp)
					&& GamePlayLogic.isHandKnown(gp)
					&& gp.getGameDescription().getBoardCards()[boardSize - 1] != null) {
				if (betArr.length > 0) {
					if (betArr[0].getType() == Bet.TYPE_BET
							|| betArr[0].getType() == Bet.TYPE_RAISE) {
						denominator++;
						final int boardRank = GameDescriptionLogic
								.getBoardRank(gp.getGameDescription(),
										boardSize);
						final int pHandRank = GamePlayLogic.getHandRank(gp,
								boardSize);
						if (boardRank == pHandRank) {
							nominator++;
						}
					}
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getBluffBetFlopProbability(
			final PlayerDescription playerDescription) {
		return getBluffBetProbability(playerDescription, 3);
	}

	public static float getBluffBetTurnProbability(
			final PlayerDescription playerDescription) {
		return getBluffBetProbability(playerDescription, 4);
	}

	public static float getBluffBetRiverProbability(
			final PlayerDescription playerDescription) {
		return getBluffBetProbability(playerDescription, 5);
	}

	public static float getCallWithNothingProbability(
			final PlayerDescription playerDescription, final int boardSize) {
		int nominator = 0;
		int denominator = 0;

		for (int i = 0; i < playerDescription.getSizeOfGamePlay(); i++) {
			final GamePlay gp = playerDescription.getGamePlay(i);
			Bet betArr[] = null;

			if (boardSize == 3) {
				betArr = gp.getFlopBets();
			} else if (boardSize == 4) {
				betArr = gp.getTurnBets();
			} else if (boardSize == 5) {
				betArr = gp.getRiverBets();
			}

			if (contentFilter.accepts(gp)
					&& GamePlayLogic.isHandKnown(gp)
					&& gp.getGameDescription().getBoardCards()[boardSize - 1] != null) {
				if (betArr.length > 0) {
					if (betArr[0].getType() == Bet.TYPE_CALL
							|| (betArr.length > 1
									&& betArr[0].getType() == Bet.TYPE_CHECK && betArr[1]
									.getType() == Bet.TYPE_CALL)) {
						denominator++;
						final int boardRank = GameDescriptionLogic
								.getBoardRank(gp.getGameDescription(),
										boardSize);
						final int pHandRank = GamePlayLogic.getHandRank(gp,
								boardSize);
						if (boardRank == pHandRank) {
							nominator++;
						}
					}
				}
			}

		}
		return getPercentage(nominator, denominator);
	}

	public static float getCallWithNothingFlopProbability(
			final PlayerDescription playerDescription) {
		return getCallWithNothingProbability(playerDescription, 3);
	}

	public static float getCallWithNothingTurnProbability(
			final PlayerDescription playerDescription) {
		return getCallWithNothingProbability(playerDescription, 4);
	}

	public static float getCallWithNothingRiverProbability(
			final PlayerDescription playerDescription) {
		return getCallWithNothingProbability(playerDescription, 5);
	}
}
