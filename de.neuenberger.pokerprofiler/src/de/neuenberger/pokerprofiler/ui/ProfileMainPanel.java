package de.neuenberger.pokerprofiler.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ProfileMainPanel extends JPanel {
	
	JDesktopPane jDesktopPane=new JDesktopPane();
	JToolBar jToolBar=new JToolBar();
	
	JComboBox jcbServer=new JComboBox();
	
	JCheckBox jcbAutoUpdate=new JCheckBox("Auto Update");
	
	public ProfileMainPanel() {
		this.setLayout(new BorderLayout());
		this.add(jDesktopPane,BorderLayout.CENTER);
		this.add(jToolBar,BorderLayout.NORTH);
		
		jToolBar.add(jcbAutoUpdate);
		jToolBar.add(jcbServer);
		jcbAutoUpdate.setSelected(true);
	}
	
	
	public JInternalFrame addComponentAsInternalFrame(Component component) {
		
		
		
		JInternalFrame jif=new JInternalFrame("Frame");
		jif.setLocation(10,10);
		jif.setSize(400,300);
		jif.setMaximizable(true);
		jif.setIconifiable(true);
		jif.setResizable(true);
		jif.setClosable(false);
		jif.getContentPane().add(component);
		jif.setVisible(true);
		
		jDesktopPane.add(jif);
		return jif;
	}


	public JCheckBox getJcbAutoUpdate() {
		return jcbAutoUpdate;
	}


	public JComboBox getJcbServer() {
		return jcbServer;
	}
}
