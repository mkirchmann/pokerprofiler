package de.neuenberger.pokerprofiler.logic.analyzer;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sun.misc.Compare;
import sun.misc.Sort;
import de.neuenberger.pokercalc.model.Card;
import de.neuenberger.pokercalc.model.Deck;
import de.neuenberger.pokerprofiler.logic.parser.BaseXMLHistoryAnalyzer;
import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GamePlay;

abstract public class XMLTypeAHistoryAnalyzer extends BaseXMLHistoryAnalyzer {
	private static final boolean DEBUG = false;

	public String getServer() {
		return "CDPoker";
	}

	@Override
	protected void analyzeDocument(Element element) {
		Element generalElement = getElementByName(element,"general");
		String tableName=this.getValueOfElement(generalElement, "tablename");
		// this.pokerTableDescription = this.getPokerTableDescriptionFromString(tableName);
		this.pokerTableDescription.setName(tableName);
		List<Element> gameList = this.getListOfElementsNamed(element,"game");
		for (int i=0; i<gameList.size(); i++) {
			Element gameElement=(Element)gameList.get(i);
			String gameKey=this.getValueOfAttribute(gameElement,"gamecode");
			newGameStarted(gameKey);
			if (DEBUG==true) System.out.println("New game: "+gameKey);
			if (!gameDescription.isAnalyzed()) {
				analyzeGameGeneral(this.getElementByName(gameElement, "general"));
				analyzeRounds(this.getListOfElementsNamed(gameElement,"round"));
			}
		}
	}
	
	protected void analyzeGameGeneral(Element element) {
		Element playersEl = getElementByName(element, "players");
		List<Element> nodeList=this.getListOfElementsNamed(playersEl,"player");
		
		if (nodeList.size()<2) {
			if (DEBUG==true) System.out.println("ERROR: -------------------- nodeList<2");
		}
		
		for (int i=0; i<nodeList.size(); i++) {
			try {
				Element pEl = (Element)nodeList.get(i);
				String name=pEl.getAttribute("name");
				String dealer=pEl.getAttribute("dealer");
				float win=getMoneyFromString(pEl.getAttribute("win"));
				int seat=Integer.parseInt(pEl.getAttribute("seat"))-1;
				
				GamePlay gamePlay = this.registerPlayer(name, seat);
				if (DEBUG==true) System.out.println("--Registered Player: "+name);
				gamePlay.setPot(win);
				if ("1".equals(dealer)) {
					gamePlay.setPosition(GamePlay.POSITION_DEALER);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected float getMoneyFromString(String str) {
		if (str.equals("DP")) {
			return 0.0f;
		} else if (str.startsWith("$")) {
			return Float.parseFloat(str.replace(",", "").substring(1));
		} else {
			return Float.parseFloat(str.replace(",", ""));
		}
	}
	
	protected void analyzeRounds(List<Element> listOfRounds) {
		for (int i=0; i<listOfRounds.size(); i++) {
			Element el=(Element)listOfRounds.get(i);
			Element[] arrayOfRounds = sortNodeListOfRounds(this.getListOfElementsNamed(el, "action"));
			int roundNo=Integer.parseInt(el.getAttribute("no"));
			for (int x=0; x<arrayOfRounds.length; x++) {
				String player=arrayOfRounds[x].getAttribute("player");
				int type=Integer.parseInt(arrayOfRounds[x].getAttribute("type"));
				float sum=getMoneyFromString(arrayOfRounds[x].getAttribute("sum"));
				GamePlay gp=this.getPlayerFromNick(player);
				if (gp==null) {
					if (DEBUG==true) System.err.println("unknown player: "+player);
					if (DEBUG==true) System.out.println("ERROR: -------------- unknown player: "+player);
				} else if (roundNo==0) {
					if (type==1) {
						gp.setBlind(sum);
						gp.setPosition(GamePlay.POSITION_SMALLBLIND);
					} else if (type==2) {
						gp.setBlind(sum);
						gp.setPosition(GamePlay.POSITION_BIGBLIND);
					}
				} else {
					Bet bet=getBetFromAction(arrayOfRounds[x]);
					if (bet!=null) {
						if (roundNo==1) {
							gp.addPreFlopBet(bet);
						} else if (roundNo==2) {
							gp.addFlopBet(bet);
						} else if (roundNo==3) {
							gp.addTurnBet(bet);
						} else if (roundNo==4) {
							gp.addRiverBet(bet);
						}
					}
				}
			}
			
			List<Element> cardList=this.getListOfElementsNamed(el,"cards");
			for (int x=0; x<cardList.size(); x++) {
				Element cardEl=(Element)cardList.get(x);
				String type=cardEl.getAttribute("type");
				String cardString=cardEl.getTextContent();
				if (!"X X".equals(cardString)) {
					Card[] cardArray=getCardsFromCDPokerString(cardString);
					if ("Pocket".equals(type)) {
						String player=cardEl.getAttribute("player");
						GamePlay gp=this.getPlayerFromNick(player);
						if (gp!=null) {
							gp.getPlayerCards()[0]=cardArray[0];
							gp.getPlayerCards()[1]=cardArray[1];
						} else {
							if (DEBUG==true) System.err.println("unknown player: "+player);
							if (DEBUG==true) System.out.println("ERROR: - ------------ unknown player: "+player);
						}
					} else if ("Flop".equals(type)) {
						gameDescription.getBoardCards()[0]=cardArray[0];
						gameDescription.getBoardCards()[1]=cardArray[1];
						gameDescription.getBoardCards()[2]=cardArray[2];
					} else if ("Turn".equals(type)) {
						gameDescription.getBoardCards()[3]=cardArray[0];
					} else if ("River".equals(type)) {
						gameDescription.getBoardCards()[4]=cardArray[0];
					}
				}
			}
		}
	}
	
	protected Card[] getCardsFromCDPokerString(String str) {
		Deck deck=Deck.getInstance();
		String strArr[]=str.split(" ");
		Card cardArray[]=new Card[strArr.length];
		for (int i=0; i<strArr.length; i++) {
			int color=Card.getColorFromCharacter(strArr[i].charAt(0));
			char c=strArr[i].charAt(1);
			if (c=='1') {
				c='T';
			}
			int rank=Card.getRankFromCharacter(c);
			cardArray[i]=deck.gimmeCard(rank, color);
		}
		return cardArray;
	}
	
	protected Bet getBetFromAction(Element el) {
		int type=Integer.parseInt(el.getAttribute("type"));
		float sum=getMoneyFromString(el.getAttribute("sum"));
		switch (type) {
		case 0:
			return new Bet(Bet.TYPE_FOLD);
		case 3:
			return new Bet(Bet.TYPE_CALL,sum);
		case 4:
			return new Bet(Bet.TYPE_CHECK);
		case 5:
			return new Bet(Bet.TYPE_BET,sum);
		case 6:
			return new Bet(Bet.TYPE_RAISE,sum);
		}
		return new Bet(Bet.TYPE_N_A,sum);
	}
	
	protected Element[] sortNodeListOfRounds(List<Element> nl) {
		Element elArr[]=new Element[nl.size()];
		for (int i=0; i<nl.size(); i++) {
			elArr[i]=(Element)nl.get(i);
		}
		
		Sort.quicksort(elArr, new Compare(){
			public int doCompare(Object arg0, Object arg1) {
				Element ela=(Element)arg0;
				Element elb=(Element)arg1;
				try {
					Integer ia=Integer.valueOf(ela.getAttribute("no"));
					Integer ib=Integer.valueOf(elb.getAttribute("no"));
					return ia.compareTo(ib);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
		
		return elArr;
	}

	
	
}
