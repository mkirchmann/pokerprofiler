package de.neuenberger.pokerprofiler.parts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.ui.hud.PopupWindow;

public class PopupWindowPart implements IController {
	PlayerDescriptionSummaryPart playerDescriptionSummaryPart=new PlayerDescriptionSummaryPart(false);
	PlayerDescriptionSummaryPart playerDescriptionCompactPart=new PlayerDescriptionSummaryPart(true);	
	PopupWindow popupWindow=new PopupWindow();
	
	PlayerDescription playerDescription;
	JWindow jwActivatorHolder=new JWindow();
	JPanel jpActivator=new JPanel();
	
	int pWidth=400;
	int pHeight=300;
	boolean detailled=false;
	
	public PopupWindowPart() {
		
		jwActivatorHolder.setLayout(new BorderLayout());
		jwActivatorHolder.setAlwaysOnTop(true);
		jwActivatorHolder.add(jpActivator,BorderLayout.CENTER);
		
		popupWindow.setPlayerDescriptionSummaryPanel(playerDescriptionSummaryPart.getPlayerDescriptionSummaryPanel());
		
		
		jpActivator.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent arg0) {
				activate();
			}

			public void mouseExited(MouseEvent arg0) {
				deactivate();
			}
			
		});
		
		popupWindow.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent arg0) {
				
			}
		});
		
		jpActivator.setBackground(Color.BLACK);
		jpActivator.setOpaque(true);
		
		jpActivator.setLayout(new BorderLayout());
		jpActivator.add(Box.createVerticalStrut(5),BorderLayout.NORTH);
		jpActivator.add(Box.createHorizontalStrut(5),BorderLayout.WEST);
		jpActivator.add(Box.createHorizontalStrut(5),BorderLayout.EAST);
		//jpActivator.add(Box.createVerticalStrut(3),BorderLayout.SOUTH);
		jpActivator.add(playerDescriptionCompactPart.getPlayerDescriptionSummaryPanel(),BorderLayout.CENTER);
		
		this.setDetailled(false);
	}

	public void setVisible(boolean flag) {
		jwActivatorHolder.setAlwaysOnTop(flag);
	}
	
	
	
	protected void activate() {
		if (playerDescription==null) {
			System.out.println("PlayerDescription == null");
			return;
		}
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		
		
		popupWindow.setSize(pWidth,pHeight);
		
		Point pt=jwActivatorHolder.getLocationOnScreen();
		int calcX=pt.x;
		int calcY=pt.y;
		
		if (calcX>calcY) {
			calcX+=jwActivatorHolder.getWidth();
		} else {
			calcY+=jwActivatorHolder.getHeight();
		}
		
		
		if (calcX+pWidth>dim.width) {
			calcX=pt.x-pWidth;
		}
		if (calcY+pHeight>dim.height) {
			calcY=pt.y-pHeight;
		}
		popupWindow.setLocation(calcX, calcY);
		
		playerDescriptionSummaryPart.setPlayerDescription(playerDescription);
		
		popupWindow.setVisible(true);
		popupWindow.setAlwaysOnTop(true);
		popupWindow.toFront();
	}

	protected void deactivate() {
		popupWindow.setVisible(false);
	}



	public Component getComponent() {
		return jwActivatorHolder;
	}
	
	public JWindow getComponentCasted() {
		return jwActivatorHolder;
	}



	public void saveComponents() {
		// TODO Auto-generated method stub
		
	}

	public PlayerDescription getPlayerDescription() {
		return playerDescription;
	}

	public void setPlayerDescription(PlayerDescription playerDescription) {
		this.playerDescription = playerDescription;
		if (playerDescription!=null) {
			jpActivator.setBorder(new LineBorder(Color.YELLOW));
			
		} else {
			jpActivator.setBorder(new LineBorder(null));
		}
		playerDescriptionCompactPart.setPlayerDescription(playerDescription);
	}

	public void setDetailled(boolean b) {
		detailled=b;
		
		playerDescriptionCompactPart.getPlayerDescriptionSummaryPanel().setVisible(b);
	}

	public void resizeToPreferred() {
		if (detailled==true && playerDescription!=null) {
			jwActivatorHolder.setSize(150, 90);
		} else {
			jwActivatorHolder.setSize(10, 10);
		}
		
	}
}
