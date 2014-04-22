package de.neuenberger.pokerprofiler.parts;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.neuenberger.pokercalc.parts.IController;
import de.neuenberger.pokerprofiler.logic.analyzer.BaseLogic;
import de.neuenberger.pokerprofiler.logic.analyzer.PokerTableDescriptionLogic;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;
import de.neuenberger.pokerprofiler.model.contentprovider.DetailedGameTableModel;
import de.neuenberger.pokerprofiler.parts.util.TableHUDManager;
import de.neuenberger.pokerprofiler.ui.GamePlayTablePanel;
import de.neuenberger.pokerprofiler.ui.tablerenderer.CardArrayRenderer;

public class GamePlayTablePart implements IController {

	GamePlayTablePanel gamePlayTablePanel = new GamePlayTablePanel();

	DetailedGameTableModel detailedGameTableModel = new DetailedGameTableModel();

	GameDescription gameDescription;

	public GamePlayTablePart() {
		gamePlayTablePanel.getJTable().setModel(detailedGameTableModel);

		gamePlayTablePanel.getJlPlayersSeeFlop();
		gamePlayTablePanel.getJbLastGame().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent arg0) {
						final PokerTableDescription pokerTableDescription = gameDescription
								.getPokerTableDescription();
						setGameDescription(pokerTableDescription
								.getLastGameDescription());
					}

				});

		gamePlayTablePanel.getJbOpen().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				TableHUDManager.getInstance().open(
						gameDescription.getPokerTableDescription());

			}

		});
	}

	@Override
	public Component getComponent() {
		return gamePlayTablePanel;
	}

	@Override
	public void saveComponents() {
		// TODO Auto-generated method stub

	}

	public GameDescription getGameDescription() {
		return gameDescription;
	}

	public void setGameDescription(final GameDescription gameDescription) {
		this.gameDescription = gameDescription;
		detailedGameTableModel.setGameDescription(gameDescription);
		final float ppf = PokerTableDescriptionLogic
				.getPlayerSeeFlop(gameDescription.getPokerTableDescription());
		gamePlayTablePanel.getJlPlayersSeeFlop().setText(
				BaseLogic.getFloatAsCurrencyString(ppf));
		gamePlayTablePanel.getJpBoardCards().removeAll();
		CardArrayRenderer.addCardsToJPanel(
				gamePlayTablePanel.getJpBoardCards(),
				gameDescription.getBoardCards());
	}

	public GamePlayTablePanel getGamePlayTablePanel() {
		return gamePlayTablePanel;
	}

}
