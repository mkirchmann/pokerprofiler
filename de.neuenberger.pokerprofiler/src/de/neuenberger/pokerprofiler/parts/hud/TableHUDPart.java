package de.neuenberger.pokerprofiler.parts.hud;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JWindow;

import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;
import de.neuenberger.pokerprofiler.parts.PopupWindowPart;
import de.neuenberger.pokerprofiler.ui.hud.HUDOptions;
import de.neuenberger.pokerprofiler.ui.hud.TableOverlaySetupFrame;

public class TableHUDPart implements IController {

	GameDescription gameDescription;
	
	PopupWindowPart popupWindowPart[]=new PopupWindowPart[10];
	
	Vector<JWindow> componentVector=new Vector<JWindow>();
	TableOverlaySetupFrame tableOverlaySetupFrame=new TableOverlaySetupFrame();
	HUDOptions hudOptions=new HUDOptions();
	
	boolean componentsVisible=false;
	
	Rectangle currentRect=null;

	private boolean shortHanded;
	private boolean detailled;
	public TableHUDPart() {
		for (int i=0; i<popupWindowPart.length; i++) {
			popupWindowPart[i]=new PopupWindowPart();
			componentVector.add(popupWindowPart[i].getComponentCasted());
		}
		
		tableOverlaySetupFrame.getJbClose().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				tableOverlaySetupFrame.setVisible(false);
				setComponentsVisible(false);
			}
			
		});
		
		tableOverlaySetupFrame.getJbDone().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				
				synchronize();
				tableOverlaySetupFrame.setVisible(false);
				setComponentsVisible(true);
			}
			
		});
		
		tableOverlaySetupFrame.addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent arg0) {
				// 
				
			}

			public void componentMoved(ComponentEvent arg0) {
				synchronize();
				
			}

			public void componentResized(ComponentEvent arg0) {
				synchronize();
				
			}

			public void componentShown(ComponentEvent arg0) {
				// 
				
			}
			
		});
		
		hudOptions.getJlClose().addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent mc) {
				setComponentsVisible(false);
				tableOverlaySetupFrame.setVisible(false);
			}
			
		});
		
		hudOptions.getJlDetailled().addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent mc) {
				setDetailled(!isDetailled());
			}
			
		});
		
		hudOptions.getJlSetup().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mc) {
				setDetailled(false);
				setup();
			}
		});
		
		tableOverlaySetupFrame.getJcbSH().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				boolean b=tableOverlaySetupFrame.getJcbSH().isSelected();
				setShortHanded(b);
			}
		});
		
		tableOverlaySetupFrame.getJcbDetailed().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				setDetailled(tableOverlaySetupFrame.getJcbDetailed().isSelected());
			}
			
		});
	}
	
	protected void synchronize() {
		Rectangle rect=tableOverlaySetupFrame.getBounds();
		Rectangle rectCopy=new Rectangle(rect);
		Point pt=tableOverlaySetupFrame.getLocationOnScreen();
		rectCopy.x=pt.x;
		rectCopy.y=pt.y;
		setFromRect(rectCopy);
	}
	
	public void setFromRect(Rectangle rect) {
		currentRect=new Rectangle(rect);
		double rot=Math.PI;
		
		int maxPlayers=componentVector.size();
		
		if (this.isShortHanded()) {
			rot/=3.0;
			maxPlayers=6;
			for (int i=maxPlayers; i<componentVector.size(); i++) {
				componentVector.get(i).setVisible(false);
			}
		} else {
			rot/=5.0;
		}
		
		
		double start=Math.PI/20.0-Math.PI*3.0/8.0;
		
		
		hudOptions.setBounds(new Rectangle(rect.x,rect.y,30,12));
		for (int i=0; i<maxPlayers; i++) {
			if (popupWindowPart[i]!=null) {
				popupWindowPart[i].resizeToPreferred();
			}
		}
		
		
		for (int i=0; i<maxPlayers; i++) {
			// schnittpunkte von rotierender linie und rectangle.
			int x=rect.width/2+(int)((double)0.6*(double)rect.width*Math.cos(start+rot*(double)i));
			int y=rect.height/2+(int)((double)1.0*(double)rect.height*Math.sin(start+rot*(double)i));
			//System.out.println("initial:"+x+" "+y);
			int w=componentVector.get(i).getWidth();
			int h=componentVector.get(i).getHeight();
			/*int minWidth=10;
			int minHeight=10;
			if (detailled==true) {
				minWidth=150;
				minHeight=90;
			}
			// if (w<minWidth) {
				w=minWidth;
			// }
			// if (h<minHeight) {
				h=minHeight;
			// }
			*/
			
			if (x+w>rect.width) {
				x=rect.width-w;
			}
			if (x<0) {
				x=0;
			}
			if (y+h>rect.height) {
				y=rect.height-h;
			}
			if (y<0) {
				y=0;
			}
			componentVector.get(i).setBounds(new Rectangle(x+rect.x,y+rect.y,w,h));
		}
	}
	
	public void setup() {
		tableOverlaySetupFrame.setVisible(true);
		setFromRect(tableOverlaySetupFrame.getBounds());
		setComponentsVisible(true);
	}
	
	public void setComponentsVisible(boolean flag) {
		hudOptions.setVisible(flag);
		for (int i=0; i<componentVector.size(); i++) {
			JWindow component = componentVector.get(i);
			component.setVisible(flag);
			component.setAlwaysOnTop(flag);
			component.toFront();
		}
		componentsVisible=flag;
	}
	
	public Component getComponent() {
		return null;
	}

	public void saveComponents() {
		// TODO Auto-generated method stub

	}

	public GameDescription getGameDescription() {
		return gameDescription;
	}

	public void setGameDescription(GameDescription gameDescription) {
		this.gameDescription = gameDescription;
		
		GamePlay[] gamePlayArray = gameDescription.getGamePlayArray();
		
		for (int i=0; i<gamePlayArray.length; i++) {
			if (gamePlayArray[i]!=null) {
				popupWindowPart[i].setPlayerDescription(gamePlayArray[i].getPlayer());
			} else {
				popupWindowPart[i].setPlayerDescription(null);
			}
		}
		
		tableOverlaySetupFrame.setTitle("Table "+gameDescription.getPokerTableDescription().getName()+" Overlay Setup Frame.");
	}

	public TableOverlaySetupFrame getTableOverlaySetupFrame() {
		return tableOverlaySetupFrame;
	}

	public boolean isComponentsVisible() {
		return componentsVisible;
	}

	public void refreshComponents() {
		if (componentsVisible) {
			//this.setComponentsVisible(false);
			this.setComponentsVisible(true);
			
			this.setFromRect(currentRect);
		}
	}

	public boolean isShortHanded() {
		return shortHanded;
	}

	public void setShortHanded(boolean shortHanded) {
		this.shortHanded = shortHanded;
		
		if (shortHanded==false) {
			setComponentsVisible(isComponentsVisible());
		}
		setFromRect(this.currentRect);
	}

	public boolean isDetailled() {
		return detailled;
	}

	public void setDetailled(boolean detailled) {
		this.detailled = detailled;
		
		for (int i=0; i<popupWindowPart.length; i++) {
			popupWindowPart[i].setDetailled(detailled);
		}
		setFromRect(this.currentRect);
	}
	
	

}
