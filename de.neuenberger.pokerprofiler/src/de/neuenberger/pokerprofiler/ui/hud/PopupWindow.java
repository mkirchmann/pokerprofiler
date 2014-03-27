package de.neuenberger.pokerprofiler.ui.hud;

import java.awt.BorderLayout;

import javax.swing.JWindow;

import de.neuenberger.pokerprofiler.ui.PlayerDescriptionSummaryPanel;

public class PopupWindow extends JWindow {
	
	PlayerDescriptionSummaryPanel playerDescriptionSummaryPanel;
	
	public PopupWindow() {
		this.getContentPane().setLayout(new BorderLayout());
	}

	public PlayerDescriptionSummaryPanel getPlayerDescriptionSummaryPanel() {
		return playerDescriptionSummaryPanel;
	}

	public void setPlayerDescriptionSummaryPanel(
			PlayerDescriptionSummaryPanel playerDescriptionSummaryPanel) {
		this.playerDescriptionSummaryPanel = playerDescriptionSummaryPanel;
		this.getContentPane().add(this.playerDescriptionSummaryPanel,BorderLayout.CENTER);
		
	}
	
	
}
