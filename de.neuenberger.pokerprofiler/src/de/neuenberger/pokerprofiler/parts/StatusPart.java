package de.neuenberger.pokerprofiler.parts;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.pokerprofiler.PokerProfiler;

public class StatusPart implements IController, ActionListener {

	ImageIcon IMG_STATUS_OK   = new ImageIcon(PokerProfiler.class.getResource("images/status_ok.png"));
	ImageIcon IMG_STATUS_RUN  = new ImageIcon(PokerProfiler.class.getResource("images/status_run.png"));
	ImageIcon IMG_STATUS_RUN2 = new ImageIcon(PokerProfiler.class.getResource("images/status_run2.png"));
	
	JLabel jLabel=new JLabel();
	Timer busyTimer=new Timer(600,this);
	
	private static StatusPart instance = new StatusPart();
	
	private boolean enlighted=true;
	
	private StatusPart() {
		setBusy(false,null);
		jLabel.setOpaque(true);
		jLabel.setForeground(Color.LIGHT_GRAY);
		jLabel.setBackground(Color.BLACK);
	}
	
	public Component getComponent() {
		return jLabel;
	}

	public void saveComponents() {
		// TODO Auto-generated method stub

	}
	
	
	
	public void setBusy(boolean status, String message) {
		enlighted=true;
		if (status==true) {
			busyTimer.start();
			jLabel.setText(message);
			actionPerformed(null);
		} else {
			busyTimer.stop();
			jLabel.setIcon(IMG_STATUS_OK);
			jLabel.setText("Ready");
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		if (enlighted) {
			jLabel.setIcon(IMG_STATUS_RUN);
		} else {
			jLabel.setIcon(IMG_STATUS_RUN2);
		}
		enlighted=!enlighted;
	}

	public static StatusPart getInstance() {
		return instance;
	}

}
