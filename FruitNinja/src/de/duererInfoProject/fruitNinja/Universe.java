package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//Draws the game
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
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.stopGame();
			}
		});
		updateCursor();
		
		lblNewLabel.setIcon(new ImageIcon(Universe.class.getResource("/de/duererInfoProject/fruitNinja/img/pause-icon.png")));
		lblNewLabel.setBounds(10, 11, 48, 48);
		add(lblNewLabel);
		
//		JLabel pmgrn = new JLabel("");
//		Icon pmgrnNormal = new ImageIcon(Universe.class.getResource("/de/duererInfoProject/fruitNinja/img/Pomegranate.png"));
//		Icon pmgrnSplit = new ImageIcon(Universe.class.getResource("/de/duererInfoProject/fruitNinja/img/PomegranateSplit.png"));
//		pmgrn.setIcon(pmgrnNormal);
//		
//		pmgrn.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				pmgrn.setIcon(pmgrnSplit);
//			}
//		});
//		
//		pmgrn.setBounds(50, 50, 200, 200);
//		add(pmgrn);
		
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
	
	//Position the lives label at the right position
	public void postInit() {
		lives.setBounds(getWidth() - 300, 10, 290, 50);
		time.setBounds(getWidth() - 300, 70, 290, 50);
		updateCursor();
	}

	//Called by game
	//Shows @count at the countdown label
	public void setCountdown(int count) {
		if (count > 0) {
			countdown.setBounds((getWidth()/2) - 75, (getHeight()/2) - 75, 150, 150);
			countdown.setText(count + "");
		} else {
			countdown.setText("");
		}
	}
	
	public void updateCursor() {
		int cursorNumber = controller.getPreferences().getInt("cursor", 0);
		java.awt.Cursor cursor = java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR);
		if (cursorNumber == 3) cursor = toolkit.createCustomCursor(toolkit.getImage(GUI.class.getResource("img/cursor" + cursorNumber + ".png")), new Point(1, 1), "cursor" + cursorNumber);
		else if (cursorNumber == 2 || cursorNumber == 1) cursor = toolkit.createCustomCursor(toolkit.getImage(GUI.class.getResource("img/cursor" + cursorNumber + ".png")), new Point(16, 16), "cursor" + cursorNumber);
		setCursor(cursor);
	}
	
	public void setScore(int s) {
		score.setText("Score: " + s);
	}
	
	public void setTime(int t) {
		time.setText("Time: " + t);
	}
	
	public void setLives(int l) {
		String string = "";
		for (int i = 0; i < l; i++) string += " X";
		lives.setText("Lives:" + string);
	}
	
	public double getGravity() {
		return GRAVITY;
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
}
