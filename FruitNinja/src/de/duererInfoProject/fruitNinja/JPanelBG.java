package de.duererInfoProject.fruitNinja;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Extends JPanel to have an Image as background
public class JPanelBG extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Image bg;
	
	public JPanelBG(String img) {
		super();
		
		//Try to get the img-File
		try {
			bg = ImageIO.read(new File(img));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Paint the background and all other components
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
	}
}
