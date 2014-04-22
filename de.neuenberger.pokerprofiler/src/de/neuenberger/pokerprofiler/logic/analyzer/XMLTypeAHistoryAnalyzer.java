package de.neuenberger.pokerprofiler.logic.analyzer;

import java.util.List;

import org.w3c.dom.Element;

import sun.misc.Compare;
import sun.misc.Sort;
import de.neuenberger.poker.common.model.Card;
import de.neuenberger.poker.common.model.Deck;
import de.neuenberger.pokerprofiler.logic.parser.BaseXMLHistoryAnalyzer;
import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GamePlay;

abstract public class XMLTypeAHistoryAnalyzer extends BaseXMLHistoryAnalyzer {
	private static final boolean DEBUG = false;

	@Override
	public String getServer() {
		return "CDPoker";
	}

	@Override
	protected void analyzeDocument(final Element element) {
		final Element generalElement = getElementByName(element, "general");
		final String tableName = this.getValueOfElement(generalElement,
				"tablename");
		// this.pokerTableDescription =
		// this.getPokerTableDescriptionFromString(tableName);
		this.pokerTableDescription.setName(tableName);
		final List<Element> gameList = this.getListOfElementsNamed(element,
				"game");
		for (int i = 0; i < gameList.size(); i++) {
			final Element gameElement = gameList.get(i);
			final String gameKey = this.getValueOfAttribute(gameElement,
					"gamecode");
			newGameStarted(gameKey);
			if (DEBUG == true) {
				System.out.println("New game: " + gameKey);
			}
			if (!gameDescription.isAnalyzed()) {
				analyzeGameGeneral(this
						.getElementByName(gameElement, "general"));
				analyzeRounds(this.getListOfElementsNamed(gameElement, "round"));
			}
		}
	}

	protected void analyzeGameGeneral(final Element element) {
		final Element playersEl = getElementByName(element, "players");
		final List<Element> nodeList = this.getListOfElementsNamed(playersEl,
				"player");

		if (nodeList.size() < 2) {
			if (DEBUG == true) {
				System.out.println("ERROR: -------------------- nodeList<2");
			}
		}

		for (int i = 0; i < nodeList.size(); i++) {
			try {
				final Element pEl = nodeList.get(i);
				final String name = pEl.getAttribute("name");
				final String dealer = pEl.getAttribute("dealer");
				final float win = getMoneyFromString(pEl.getAttribute("win"));
				final int seat = Integer.parseInt(pEl.getAttribute("seat")) - 1;

				final GamePlay gamePlay = this.registerPlayer(name, seat);
				if (DEBUG == true) {
					System.out.println("--Registered Player: " + name);
				}
				gamePlay.setPot(win);
				if ("1".equals(dealer)) {
					gamePlay.setPosition(GamePlay.POSITION_DEALER);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected float getMoneyFromString(final String str) {
		if (str.equals("DP")) {
			return 0.0f;
		} else if (str.startsWith("$") || (str.startsWith("€"))
				|| str.startsWith("£")) {
			return Float.parseFloat(str.replace(",", "").substring(1));
		} else {
			return Float.parseFloat(str.replace(",", ""));
		}
	}

	protected void analyzeRounds(final List<Element> listOfRounds) {
		for (int i = 0; i < listOfRounds.size(); i++) {
			final Element el = listOfRounds.get(i);
			final Element[] arrayOfRounds = sortNodeListOfRounds(this
					.getListOfElementsNamed(el, "action"));
			final int roundNo = Integer.parseInt(el.getAttribute("no"));
			for (int x = 0; x < arrayOfRounds.length; x++) {
				final String player = arrayOfRounds[x].getAttribute("player");
				final int type = Integer.parseInt(arrayOfRounds[x]
						.getAttribute("type"));
				final float sum = getMoneyFromString(arrayOfRounds[x]
						.getAttribute("sum"));
				final GamePlay gp = this.getPlayerFromNick(player);
				if (gp == null) {
					if (DEBUG == true) {
						System.err.println("unknown player: " + player);
					}
					if (DEBUG == true) {
						System.out
								.println("ERROR: -------------- unknown player: "
										+ player);
					}
				} else if (roundNo == 0) {
					if (type == 1) {
						gp.setBlind(sum);
						gp.setPosition(GamePlay.POSITION_SMALLBLIND);
					} else if (type == 2) {
						gp.setBlind(sum);
						gp.setPosition(GamePlay.POSITION_BIGBLIND);
					}
				} else {
					final Bet bet = getBetFromAction(arrayOfRounds[x]);
					if (bet != null) {
						if (roundNo == 1) {
							gp.addPreFlopBet(bet);
						} else if (roundNo == 2) {
							gp.addFlopBet(bet);
						} else if (roundNo == 3) {
							gp.addTurnBet(bet);
						} else if (roundNo == 4) {
							gp.addRiverBet(bet);
						}
					}
				}
			}

			final List<Element> cardList = this.getListOfElementsNamed(el,
					"cards");
			for (int x = 0; x < cardList.size(); x++) {
				final Element cardEl = cardList.get(x);
				final String type = cardEl.getAttribute("type");
				final String cardString = cardEl.getTextContent();
				if (!"X X".equals(cardString)) {
					final Card[] cardArray = getCardsFromCDPokerString(cardString);
					if ("Pocket".equals(type)) {
						final String player = cardEl.getAttribute("player");
						final GamePlay gp = this.getPlayerFromNick(player);
						if (gp != null) {
							gp.getPlayerCards()[0] = cardArray[0];
							gp.getPlayerCards()[1] = cardArray[1];
						} else {
							if (DEBUG == true) {
								System.err.println("unknown player: " + player);
							}
							if (DEBUG == true) {
								System.out
										.println("ERROR: - ------------ unknown player: "
												+ player);
							}
						}
					} else if ("Flop".equals(type)) {
						gameDescription.getBoardCards()[0] = cardArray[0];
						gameDescription.getBoardCards()[1] = cardArray[1];
						gameDescription.getBoardCards()[2] = cardArray[2];
					} else if ("Turn".equals(type)) {
						gameDescription.getBoardCards()[3] = cardArray[0];
					} else if ("River".equals(type)) {
						gameDescription.getBoardCards()[4] = cardArray[0];
					}
				}
			}
		}
	}

	protected Card[] getCardsFromCDPokerString(final String str) {
		final Deck deck = Deck.getInstance();
		final String strArr[] = str.split(" ");
		final Card cardArray[] = new Card[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			final int color = Card.getColorFromCharacter(strArr[i].charAt(0));
			char c = strArr[i].charAt(1);
			if (c == '1') {
				c = 'T';
			}
			final int rank = Card.getRankFromCharacter(c);
			cardArray[i] = deck.gimmeCard(rank, color);
		}
		return cardArray;
	}

	protected Bet getBetFromAction(final Element el) {
		final int type = Integer.parseInt(el.getAttribute("type"));
		final float sum = getMoneyFromString(el.getAttribute("sum"));
		switch (type) {
		case 0:
			return new Bet(Bet.TYPE_FOLD);
		case 3:
			return new Bet(Bet.TYPE_CALL, sum);
		case 4:
			return new Bet(Bet.TYPE_CHECK);
		case 5:
			return new Bet(Bet.TYPE_BET, sum);
		case 6:
			return new Bet(Bet.TYPE_RAISE, sum);
		}
		return new Bet(Bet.TYPE_N_A, sum);
	}

	protected Element[] sortNodeListOfRounds(final List<Element> nl) {
		final Element elArr[] = new Element[nl.size()];
		for (int i = 0; i < nl.size(); i++) {
			elArr[i] = nl.get(i);
		}

		Sort.quicksort(elArr, new Compare() {
			@Override
			public int doCompare(final Object arg0, final Object arg1) {
				final Element ela = (Element) arg0;
				final Element elb = (Element) arg1;
				try {
					final Integer ia = Integer.valueOf(ela.getAttribute("no"));
					final Integer ib = Integer.valueOf(elb.getAttribute("no"));
					return ia.compareTo(ib);
				} catch (final Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});

		return elArr;
	}

}
