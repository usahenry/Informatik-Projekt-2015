package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Universe extends JPanelBG {
	
	private Controller controller;
	private JLabel countdown, score, lives;
	private final double GRAVITY = -0.15;
	private Game game;
	
	public Universe (Controller g, String img) {
		super(img);
		controller = g;

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.getGui().pauseUniverse();
			}
		});
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
		countdown.setHorizontalTextPosition(SwingConstants.CENTER);
		countdown.setFont(new Font("Narkisim", Font.BOLD, 200));
		countdown.setForeground(Color.WHITE);
		add(countdown);
		
		score = new JLabel("0");
		score.setHorizontalAlignment(SwingConstants.LEFT);
		score.setHorizontalTextPosition(SwingConstants.LEFT);
		score.setFont(new Font("Narkisim", Font.BOLD, 50));
		score.setForeground(Color.WHITE);
		score.setBounds(50, 10, 100, 50);
		add(score);
		
		lives = new JLabel("3");
		score.setHorizontalAlignment(SwingConstants.RIGHT);
		lives.setHorizontalTextPosition(SwingConstants.RIGHT);
		lives.setFont(new Font("Narkisim", Font.BOLD, 50));
		lives.setForeground(Color.WHITE);
		add(lives);
	}
	
	public void postInit() {
		lives.setBounds(getWidth() - 50, 10, 100, 50);
	}

	public void setCountdown(int count) {
		if (count > 0) {
			countdown.setBounds((getWidth()/2) - 75, (getHeight()/2) - 75, 150, 150);
			countdown.setText(count + "");
		} else {
			countdown.setText("");
		}
	}
	
	public void setScore(int s) {
		score.setText("" + s);
	}
	
	public void setLives(int l) {
		lives.setText("" + l);
	}
	
	public double getGravity() {
		return GRAVITY;
	}
	
	//Override paint Method to also paint the game
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//Draw items and cursor
		game = controller.getGame();
		Cursor cursor = game.getCursor();
		cursor.updatePoints();
		cursor.paint(g2d);
		for (Item item : game.getItemList()) {
			item.move();
			item.paint(g2d);
			item.checkHit(cursor.getPoints());
		}
		for (ItemPart itemPart : game.getItemPartsList()) {
			itemPart.move();
			itemPart.paint(g2d);
		}
	}
}
