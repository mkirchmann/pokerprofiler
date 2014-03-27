package de.neuenberger.pokerprofiler;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import de.neuenberger.pokerprofiler.logic.parser.ParserPool;
import de.neuenberger.pokerprofiler.logic.parser.cdpoker.CDPokerCashgameHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.cdpoker.CDPokerTournamentHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.partypoker.PartyPokerHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.partypoker.PartyPokerTourneyHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.pokerstars.PokerstarsHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.pokerstars.PokerstarsPlaymoneyHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.pokerstars.PokerstarsTourneyHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.titanpoker.TitanPokerCashgameHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.titanpoker.TitanPokerTournamentHistoryAnalyzer;
import de.neuenberger.pokerprofiler.parts.ProfilerMainPart;
import de.neuenberger.pokerprofiler.parts.StatusPart;

public class PokerProfiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ParserPool.getInstance().addHistoryAnalyzer(PartyPokerHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(PartyPokerTourneyHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(PokerstarsHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(PokerstarsTourneyHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(PokerstarsPlaymoneyHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(CDPokerCashgameHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(CDPokerTournamentHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(TitanPokerCashgameHistoryAnalyzer.getInstance());
		ParserPool.getInstance().addHistoryAnalyzer(TitanPokerTournamentHistoryAnalyzer.getInstance());
		
		
		ParserPool.getInstance().update();
		
		JFrame jFrame=new JFrame() {

			@Override
			protected void processWindowEvent(WindowEvent ev) {
				if (ev.getID()==WindowEvent.WINDOW_CLOSING) {
					System.exit(0);
				}
				super.processWindowEvent(ev);
			}
			
		};
		
		
		ProfilerMainPart pmp=new ProfilerMainPart();
		jFrame.getContentPane().setLayout(new BorderLayout());
		jFrame.getContentPane().add(pmp.getComponent(),BorderLayout.CENTER);
		jFrame.setSize(1200,800);
		jFrame.setVisible(true);
		jFrame.getContentPane().add(StatusPart.getInstance().getComponent(),BorderLayout.SOUTH);
		jFrame.getContentPane().doLayout();
	}

}
