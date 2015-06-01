package de.duererInfoProject.fruitNinja;

import javax.swing.JFrame;

public class JFullscreenFrame extends JFrame {
	
	private boolean fullscreen;
	
	public JFullscreenFrame(Boolean f) {
		setBounds(100, 100, 700, 490);
		if (f) startFullscreen();
		fullscreen = f;
	}
	
	public void startFullscreen() {
		if (fullscreen) return;
		dispose();
		setUndecorated(true);
		setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
		setResizable(false);
		setVisible(true);
	}
	
	public void stopFullscreen() {
		if (!fullscreen) return;
		dispose();
		setUndecorated(false);
		setBounds(100, 100, 700, 490);
		setResizable(true);
		setVisible(true);
	}
}
