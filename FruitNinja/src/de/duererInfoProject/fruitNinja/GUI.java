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
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;

public class GUI {

	private JFullscreenFrame frame;
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
		frame = new JFullscreenFrame(game.getPreferences().getBoolean("fullscreen", false));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		mainMenu = new JPanelBG(GUI.class.getResource("img/background.jpg").getPath());
		frame.getContentPane().add(mainMenu, "name_259883664488859");
		mainMenu.setVisible(true);
		
		settings = new JPanelBG(GUI.class.getResource("img/background.jpg").getPath());
		frame.getContentPane().add(settings, "name_259887979946234");
		settings.setVisible(false);
		
		frame.getContentPane().add(universe);
		universe.setLayout(null);
		universe.setVisible(false);
		settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.add(verticalBox);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_1);
		
		JButton btnFullscreen = new JButton("Window Mode");
		btnFullscreen.setMaximumSize(new Dimension(180, 45));
		if (game.getPreferences().getBoolean("fullscreen", false)) btnFullscreen.setText("Fullscreen Mode");
		btnFullscreen.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(btnFullscreen);
		btnFullscreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.getPreferences().getBoolean("fullscreen", false)) {
					game.getPreferences().putBoolean("fullscreen", false);
					btnFullscreen.setText("Window Mode");
					frame.stopFullscreen();
				} else {
					game.getPreferences().putBoolean("fullscreen", true);
					btnFullscreen.setText("Fulscreen Mode");
					frame.startFullscreen();
				}
			}
		});
		btnFullscreen.setHorizontalAlignment(SwingConstants.LEFT);
		btnFullscreen.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnFullscreen.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/fullscreen-icon.png")));
		btnFullscreen.setIconTextGap(10);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 40));
		verticalBox.add(verticalStrut);
		
		JButton btnHand = new JButton("Right Hand");
		btnHand.setMaximumSize(new Dimension(180, 45));
		verticalBox.add(btnHand);
		btnHand.setHorizontalAlignment(SwingConstants.LEFT);
		btnHand.setAlignmentX(Component.CENTER_ALIGNMENT);
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
		btnHand.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/right-hand-icon.png")));
		btnHand.setIconTextGap(10);
		btnHand.setFont(new Font("SansSerif", Font.BOLD, 15));
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_2);
		if (!game.getPreferences().getBoolean("hand", true)) {
			btnHand.setText("Left Hand");
			btnHand.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/left-hand-icon.png")));
		}
		
		Box horizontalBox = Box.createHorizontalBox();
		settings.add(horizontalBox);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setMaximumSize(new Dimension(20, 0));
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		horizontalBox.add(horizontalStrut);
		
		JButton btnBack = new JButton("Back");
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBack.setPreferredSize(new Dimension(137, 45));
		btnBack.setMinimumSize(new Dimension(137, 45));
		btnBack.setMaximumSize(new Dimension(137, 45));
		horizontalBox.add(btnBack);
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
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		verticalStrut_4.setPreferredSize(new Dimension(0, 10));
		verticalStrut_4.setMinimumSize(new Dimension(0, 10));
		settings.add(verticalStrut_4);
		mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		mainMenu.add(verticalStrut_3);
		
		JLabel label = new JLabel("");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenu.add(label);
		label.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/logo.png")));
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setMaximumSize(new Dimension(137, 45));
		btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
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
		
		Component verticalGlue_3 = Box.createVerticalGlue();
		mainMenu.add(verticalGlue_3);
		mainMenu.add(btnPlay);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setMaximumSize(new Dimension(137, 45));
		btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSettings.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/settings-icon.png")));
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		btnSettings.setIconTextGap(10);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 30));
		mainMenu.add(verticalStrut_1);
		btnSettings.setFont(new Font("SansSerif", Font.BOLD, 15));
		mainMenu.add(btnSettings);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setMaximumSize(new Dimension(137, 45));
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setIconTextGap(10);
		btnExit.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/stop-icon.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(0, 30));
		mainMenu.add(verticalStrut_2);
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 15));
		mainMenu.add(btnExit);
		
		Component verticalGlue = Box.createVerticalGlue();
		mainMenu.add(verticalGlue);
	}
}
