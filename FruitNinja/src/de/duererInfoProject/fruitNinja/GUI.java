package de.duererInfoProject.fruitNinja;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame frame;
	private Game game;
	private JPanel settings;
	private JPanel mainMenu;
	private Universe universe;

	public GUI(Game g) {
		game = g;
		universe = game.getUniverse();
		
		game.setLookAndFeel();
		initialize();
		frame.setVisible(true);
	}

	//Initialize the contents of the frame.
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		
		mainMenu = new JPanelBG(GUI.class.getResource("img/background.jpg").getPath());
		frame.getContentPane().add(mainMenu, "name_259883664488859");
		mainMenu.setLayout(null);
		mainMenu.setVisible(true);
		
		settings = new JPanelBG(GUI.class.getResource("img/background.jpg").getPath());
		frame.getContentPane().add(settings, "name_259887979946234");
		settings.setLayout(null);
		settings.setVisible(false);
		
		frame.getContentPane().add(universe);
		universe.setLayout(null);
		universe.setVisible(false);
		
		JButton btnBack = new JButton("Back");
		btnBack.setIconTextGap(10);
		btnBack.setHorizontalAlignment(SwingConstants.LEFT);
		btnBack.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/back-icon.png")));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		btnBack.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnBack.setBounds(6, 401, 137, 45);
		settings.add(btnBack);
		
		JButton btnFullscreen = new JButton("Window Mode");
		btnFullscreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.getPreferences().getBoolean("fullscreen", false)) {
					game.getPreferences().putBoolean("fullscreen", false);
					btnFullscreen.setText("Window Mode");
				} else {
					game.getPreferences().putBoolean("fullscreen", true);
					btnFullscreen.setText("Fulscreen Mode");
				}
			}
		});
		btnFullscreen.setHorizontalAlignment(SwingConstants.LEFT);
		btnFullscreen.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnFullscreen.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/fullscreen-icon.png")));
		if (game.getPreferences().getBoolean("fullscreen", false)) {
			btnFullscreen.setText("Fullscreen Mode");
		}
		btnFullscreen.setIconTextGap(10);
		btnFullscreen.setBounds(254, 117, 185, 45);
		settings.add(btnFullscreen);
		
		JButton btnHand = new JButton("Right Hand");
		btnHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean rightHand = game.getPreferences().getBoolean("hand", true);
				if (rightHand) {
					game.getPreferences().putBoolean("hand", false);
					btnHand.setText("Left Hand");
					btnHand.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/left-hand-icon.png")));
				} else {
					game.getPreferences().putBoolean("hand", true);
					btnHand.setText("Right Hand");
					btnHand.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/right-hand-icon.png")));
				}
			}
		});
		btnHand.setHorizontalAlignment(SwingConstants.LEFT);
		btnHand.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnHand.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/right-hand-icon.png")));
		if (!game.getPreferences().getBoolean("hand", true)) {
			btnHand.setText("Left Hand");
			btnHand.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/left-hand-icon.png")));
		}
		btnHand.setIconTextGap(10);
		btnHand.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnHand.setBounds(254, 222, 185, 45);
		settings.add(btnHand);
		
		JLabel label = new JLabel("");
		label.setBounds(136, 6, 440, 189);
		mainMenu.add(label);
		label.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/logo.png")));
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setHorizontalAlignment(SwingConstants.LEFT);
		btnPlay.setIconTextGap(10);
		btnPlay.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/play-icon.png")));
		btnPlay.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				universe.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		btnPlay.setBounds(261, 236, 137, 45);
		mainMenu.add(btnPlay);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/settings-icon.png")));
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		btnSettings.setIconTextGap(10);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		btnSettings.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSettings.setBounds(261, 305, 137, 45);
		mainMenu.add(btnSettings);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setIconTextGap(10);
		btnExit.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/stop-icon.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnExit.setBounds(261, 382, 137, 45);
		mainMenu.add(btnExit);
	}
}
