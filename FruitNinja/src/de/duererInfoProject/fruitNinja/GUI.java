package de.duererInfoProject.fruitNinja;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private Game game;
	private JPanel settings;
	private JPanel mainMenu;
	private Universe universe;
	private boolean fullscreen;
	private Rectangle DIMENSIONS = new Rectangle(100, 100, 750, 550);

	public GUI(Game g) {
		//Call JFrame Constructor and setup Attributes
		super();
		game = g;
		universe = game.getUniverse();
		
		//Setup GUI and start fullscreen Mode if necessary 
		setBounds(DIMENSIONS);
		boolean startFullscreen = game.getPreferences().getBoolean("fullscreen", false);
		if (startFullscreen) startFullscreen();
		fullscreen = startFullscreen;
		
		game.setLookAndFeel();
		initialize();
		setVisible(true);
	}
	
	public void startFullscreen() {
		if (fullscreen) return;
		fullscreen = true;
		dispose();
		setUndecorated(true);
		setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
		setResizable(false);
		setVisible(true);
	}
	
	public void stopFullscreen() {
		if (!fullscreen) return;
		fullscreen = false;
		dispose();
		setUndecorated(false);
		setBounds(DIMENSIONS);
		setResizable(true);
		setVisible(true);
	}

	//Initialize the contents of the frame.
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		mainMenu = new JPanelBG(GUI.class.getResource("img/background.jpg").getPath());
		getContentPane().add(mainMenu, "name_259883664488859");
		mainMenu.setVisible(true);
		
		settings = new JPanelBG(GUI.class.getResource("img/background.jpg").getPath());
		getContentPane().add(settings, "name_259887979946234");
		settings.setVisible(false);
		settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
		
		JPanel highscore = new JPanelBG(GUI.class.getResource("img/background.jpg").getPath());
		getContentPane().add(highscore, "name_328767235184261");
		highscore.setLayout(new BoxLayout(highscore, BoxLayout.Y_AXIS));
		
		Box verticalBox_1 = Box.createVerticalBox();
		highscore.add(verticalBox_1);
		
		Component verticalGlue_4 = Box.createVerticalGlue();
		verticalBox_1.add(verticalGlue_4);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		highscore.add(horizontalBox_2);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
		horizontalStrut_1.setMaximumSize(new Dimension(20, 0));
		horizontalBox_2.add(horizontalStrut_1);
		
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				highscore.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		button.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/back-icon.png")));
		button.setPreferredSize(new Dimension(150, 45));
		button.setMinimumSize(new Dimension(137, 45));
		button.setMaximumSize(new Dimension(150, 45));
		button.setIconTextGap(10);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setFont(new Font("SansSerif", Font.BOLD, 15));
		button.setAlignmentX(0.5f);
		horizontalBox_2.add(button);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox_2.add(horizontalGlue_1);
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		verticalStrut_6.setPreferredSize(new Dimension(0, 10));
		verticalStrut_6.setMinimumSize(new Dimension(0, 10));
		highscore.add(verticalStrut_6);
		settings.setVisible(false);
		
		getContentPane().add(universe);
		universe.setLayout(null);
		universe.setVisible(false);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.add(verticalBox);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_1);
		
		JButton btnFullscreen = new JButton("Window Mode");
		btnFullscreen.setMaximumSize(new Dimension(200, 50));
		btnFullscreen.setPreferredSize(new Dimension(200, 50));
		if (game.getPreferences().getBoolean("fullscreen", false)) btnFullscreen.setText("Fullscreen Mode");
		btnFullscreen.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(btnFullscreen);
		btnFullscreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game.getPreferences().getBoolean("fullscreen", false)) {
					game.getPreferences().putBoolean("fullscreen", false);
					btnFullscreen.setText("Window Mode");
					stopFullscreen();
				} else {
					game.getPreferences().putBoolean("fullscreen", true);
					btnFullscreen.setText("Fullscreen Mode");
					startFullscreen();
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
		btnHand.setPreferredSize(new Dimension(200, 50));
		btnHand.setMaximumSize(new Dimension(200, 50));
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
		btnBack.setPreferredSize(new Dimension(150, 45));
		btnBack.setMinimumSize(new Dimension(137, 45));
		btnBack.setMaximumSize(new Dimension(150, 45));
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
		btnPlay.setMaximumSize(new Dimension(150, 45));
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
		btnSettings.setMaximumSize(new Dimension(150, 45));
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
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		mainMenu.add(verticalStrut_5);
		
		JButton btnHighscore = new JButton("Highscore");
		btnHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				highscore.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		btnHighscore.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/highscore-icon.png")));
		btnHighscore.setMaximumSize(new Dimension(150, 45));
		btnHighscore.setIconTextGap(10);
		btnHighscore.setHorizontalAlignment(SwingConstants.LEFT);
		btnHighscore.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnHighscore.setAlignmentX(0.5f);
		mainMenu.add(btnHighscore);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		mainMenu.add(verticalStrut_1);
		btnSettings.setFont(new Font("SansSerif", Font.BOLD, 15));
		mainMenu.add(btnSettings);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setMaximumSize(new Dimension(150, 45));
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setIconTextGap(10);
		btnExit.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/stop-icon.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		mainMenu.add(verticalStrut_2);
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 15));
		mainMenu.add(btnExit);
		
		Component verticalGlue = Box.createVerticalGlue();
		mainMenu.add(verticalGlue);
	}
}
