package de.neuenberger.pokerprofiler.ui.hud;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class TableOverlaySetupFrame extends JFrame {
	JToolBar jToolbar=new JToolBar();
	
	JButton jbDone=new JButton("Done");
	JButton jbClose=new JButton("Close");
	JCheckBox jcbSH=new JCheckBox("ShortHanded");
	JCheckBox jcbDetailed=new JCheckBox("Detailed");
	
	public TableOverlaySetupFrame() {
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(jToolbar,BorderLayout.NORTH);
		jToolbar.add(jcbSH);
		jToolbar.add(jcbDetailed);
		jToolbar.add(jbDone);
		jToolbar.add(jbClose);
		
		this.getContentPane().add(new JPanel(),BorderLayout.CENTER);
	}
	
	public Point getTopLeftScreenPoint() {
		return jToolbar.getLocationOnScreen();
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public JButton getJbDone() {
		return jbDone;
	}

	public JCheckBox getJcbSH() {
		return jcbSH;
	}

	public JCheckBox getJcbDetailed() {
		return jcbDetailed;
	}
}
