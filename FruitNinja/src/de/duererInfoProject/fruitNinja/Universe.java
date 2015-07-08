package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//Displays the game
public class Universe extends JPanelBG {
	
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLabel countdown, score, lives, time;
	private final double GRAVITY = -0.15;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Game game;
	
	public Universe (Controller g, String img) {
		super(img);
		controller = g;
		
		//Initializing the GUI
		JButton stopButton = new JButton(new ImageIcon(Universe.class.getResource("img/stop-icon.png")));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.stopGame();
			}
		});
		stopButton.setBorderPainted(false); 
		stopButton.setContentAreaFilled(false); 
		stopButton.setFocusPainted(false); 
		stopButton.setOpaque(false);
		stopButton.setBounds(20, 20, 48, 48);
		add(stopButton);
		updateCursor();
		
		countdown = new JLabel("");
		countdown.setHorizontalAlignment(SwingConstants.CENTER);
		countdown.setFont(new Font("Narkisim", Font.BOLD, 200));
		countdown.setForeground(Color.WHITE);
		add(countdown);
		
		score = new JLabel("Score: 0");
		score.setBounds(150, 10, 500, 50);
		score.setFont(new Font("Narkisim", Font.BOLD, 50));
		score.setForeground(Color.WHITE);
		add(score);
		
		lives = new JLabel("Lives: X X X");
		lives.setFont(new Font("Narkisim", Font.BOLD, 50));
		lives.setForeground(Color.WHITE);
		add(lives);
		
		time = new JLabel("Time: 0");
		time.setFont(new Font("Narkisim", Font.BOLD, 50));
		time.setForeground(Color.WHITE);
		add(time);
	}
	
	//Called every time a new game gets started
	//Position the lives label at the right position
	public void postInit() {
		lives.setBounds(getWidth() - 300, 10, 290, 50);
		time.setBounds(getWidth() - 300, 70, 290, 50);
		updateCursor();
	}

	//Called by game to set the countdown
	//Shows @count at the countdown label if its positive
	public void setCountdown(int count) {
		if (count > 0) {
			countdown.setBounds((getWidth()/2) - 75, (getHeight()/2) - 75, 150, 150);
			countdown.setText(count + "");
		} else {
			countdown.setText("");
		}
	}
	
	//Updates the cursor to the currently selected one
	public void updateCursor() {
		int cursorNumber = controller.getPreferences().getInt("cursor", 0);
		java.awt.Cursor cursor = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR);
		if (cursorNumber == 3) cursor = toolkit.createCustomCursor(toolkit.getImage(GUI.class.getResource("img/cursor" + cursorNumber + ".png")), new Point(1, 1), "cursor" + cursorNumber);
		else if (cursorNumber == 2 || cursorNumber == 1) cursor = toolkit.createCustomCursor(toolkit.getImage(GUI.class.getResource("img/cursor" + cursorNumber + ".png")), new Point(16, 16), "cursor" + cursorNumber);
		setCursor(cursor);
	}
	
	//Override paint method to also paint the game
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//Moving and drawing Cursor
		game = controller.getGame();
		Cursor cursor = game.getCursor();
		cursor.updatePoints();
		cursor.paint(g2d);
		
		//Moving and drawing Items
		for (Item item : game.getItemList()) {
			item.move();
			item.paint(g2d);
			item.checkHit(cursor.getPoints());
		}

		//Moving and drawing ItemParts
		for (ItemPart itemPart : game.getItemPartsList()) {
			itemPart.move();
			itemPart.paint(g2d);
		}
	}
	
	public void setScore(int s) {
		score.setText("Score: " + s);
	}
	
	public void setTime(int t) {
		time.setText("Time: " + t);
	}
	
	public void setLives(int l) {
		String string = "";
		if (l > 3) string = " " + l + " X";
		else for (int i = 0; i < l; i++) string += " X";
		lives.setText("Lives:" + string);
	}
	
	public double getGravity() {
		return GRAVITY;
	}
}
