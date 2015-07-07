package de.duererInfoProject.fruitNinja;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

//Manages the GUI
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private Highscore highscore;
	private JPanel settingsPanel, mainMenuPanel, highscorePanel;
	private Universe universe;
	private SoundManager soundManager;
	private boolean fullscreen;
	private Rectangle DIMENSIONS = new Rectangle(100, 100, 1280, 800);
	private JTable table;

	public GUI(Controller g) {
		// Call JFrames Constructor and initialize Attributes
		super();
		setMinimumSize(new Dimension(750, 550));
		controller = g;
		universe = controller.getUniverse();
		highscore = controller.getHighscore();
		soundManager = controller.getSoundManager();

		// Setup GUI and start fullscreen mode if necessary
		setBounds(DIMENSIONS);
		boolean startFullscreen = controller.getPreferences().getBoolean(
				"fullscreen", false);
		if (startFullscreen)
			startFullscreen();
		fullscreen = startFullscreen;

		controller.setLookAndFeel();
		initialize();
		setVisible(true);
	}

	// Starts fullscreen mode
	public void startFullscreen() {
		if (fullscreen)
			return;
		fullscreen = true;
		dispose();
		setUndecorated(true);
		setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit()
				.getScreenSize().height);
		setResizable(false);
		setVisible(true);
	}

	// Stops fullscreen mode
	public void stopFullscreen() {
		if (!fullscreen)
			return;
		fullscreen = false;
		dispose();
		setUndecorated(false);
		setBounds(DIMENSIONS);
		setResizable(true);
		setVisible(true);
	}
	
	//Display an error dialog with @message and an option to show more details
	public void errorMessage(Exception e, String message) {
		Object[] options = {"More Details", "OK"};
		int result = JOptionPane.showOptionDialog(this, message, "An Error has occured!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[1]);
		if (result == JOptionPane.YES_OPTION) {
			PrintWriter printWriter = new PrintWriter(new StringWriter());
			e.printStackTrace(printWriter);
			JOptionPane.showMessageDialog(this, printWriter.toString(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	// Shows the main menu
	public void backToMenu() {
		settingsPanel.setVisible(false);
		universe.setVisible(false);
		highscorePanel.setVisible(false);
		mainMenuPanel.setVisible(true);
	}

	//Called when a new game starts
	//Shows the universe
	public void showUniverse() {
		universe.setVisible(true);
		mainMenuPanel.setVisible(false);
	}

	//Loads the highscore, sorts it and converts it into an array, used to display it in the highscore table
	public Object[][] createHighscoreTableContent() {
		Object[][] return_object = new Object[highscore.TOP_NUMBER][4];
		highscore.load();
		highscore.sort(1, true);
		LinkedList<String[]> highscoreList = highscore.getHighscoreList();
		int i = 1;
		for (String[] stringArray : highscoreList) {
			return_object[highscoreList.indexOf(stringArray)][0] = i + "";
			return_object[highscoreList.indexOf(stringArray)][1] = stringArray[0];
			return_object[highscoreList.indexOf(stringArray)][2] = stringArray[1];
			return_object[highscoreList.indexOf(stringArray)][3] = stringArray[2];
			i++;
		}
		return return_object;
	}

	// Initialize the contents of the frame.
	@SuppressWarnings("serial")
	private void initialize() {
		setTitle("Fruit Ninja!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout(0, 0));

		mainMenuPanel = new JPanelBG("img/background.jpg");
		getContentPane().add(mainMenuPanel, "name_259883664488859");
		mainMenuPanel.setVisible(true);

		settingsPanel = new JPanelBG("img/background.jpg");
		getContentPane().add(settingsPanel, "name_259887979946234");
		settingsPanel.setVisible(false);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

		highscorePanel = new JPanelBG("img/background.jpg");
		getContentPane().add(highscorePanel, "name_328767235184261");
		highscorePanel.setLayout(new BoxLayout(highscorePanel, BoxLayout.Y_AXIS));

		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		highscorePanel.add(verticalBox_1);

		Component verticalGlue_4 = Box.createVerticalGlue();
		verticalBox_1.add(verticalGlue_4);

		JLabel lblNewLabel = new JLabel("Highscores");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Narkisim", Font.BOLD, 40));
		verticalBox_1.add(lblNewLabel);

		Component verticalStrut_7 = Box.createVerticalStrut(20);
		verticalBox_1.add(verticalStrut_7);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_1);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setPreferredSize(new Dimension(300, 1));
		horizontalBox_1.add(horizontalStrut_2);

		table = new JTable();
		horizontalBox_1.add(new JScrollPane(table));
		table.setModel(new DefaultTableModel(createHighscoreTableContent(),
				new String[] { "TOP", "Name", "Score", "Playtime" }) {
			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalStrut_3.setPreferredSize(new Dimension(300, 1));
		horizontalBox_1.add(horizontalStrut_3);

		Component verticalGlue_5 = Box.createVerticalGlue();
		highscorePanel.add(verticalGlue_5);

		Box horizontalBox_2 = Box.createHorizontalBox();
		highscorePanel.add(horizontalBox_2);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
		horizontalStrut_1.setMaximumSize(new Dimension(20, 0));
		horizontalBox_2.add(horizontalStrut_1);

		JButton button = new JButton("Back");
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				soundManager.playButton(true);
				backToMenu();
			}
		});
		button.setIcon(new ImageIcon(
				GUI.class
						.getResource("/de/duererInfoProject/fruitNinja/img/back-icon.png")));
		button.setPreferredSize(new Dimension(150, 45));
		button.setMinimumSize(new Dimension(137, 45));
		button.setMaximumSize(new Dimension(150, 45));
		button.setIconTextGap(10);
		button.setFont(new Font("SansSerif", Font.BOLD, 15));
		button.setAlignmentX(0.5f);
		horizontalBox_2.add(button);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox_2.add(horizontalGlue_1);

		Component verticalStrut_6 = Box.createVerticalStrut(20);
		verticalStrut_6.setPreferredSize(new Dimension(0, 10));
		verticalStrut_6.setMinimumSize(new Dimension(0, 10));
		highscorePanel.add(verticalStrut_6);
		settingsPanel.setVisible(false);

		getContentPane().add(universe);
		universe.setLayout(null);
		universe.setVisible(false);

		Box verticalBox = Box.createVerticalBox();
		verticalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(verticalBox);

		Component verticalGlue_1 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_1);

		JButton btnFullscreen = new JButton("Window Mode");
		btnFullscreen.setMaximumSize(new Dimension(200, 50));
		btnFullscreen.setPreferredSize(new Dimension(200, 50));
		if (controller.getPreferences().getBoolean("fullscreen", false))
			btnFullscreen.setText("Fullscreen Mode");
		btnFullscreen.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(btnFullscreen);
		btnFullscreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundManager.playButton(false);
				if (controller.getPreferences().getBoolean("fullscreen", false)) {
					controller.getPreferences().putBoolean("fullscreen", false);
					btnFullscreen.setText("Window Mode");
					stopFullscreen();
				} else {
					controller.getPreferences().putBoolean("fullscreen", true);
					btnFullscreen.setText("Fullscreen Mode");
					startFullscreen();
				}
			}
		});
		btnFullscreen.setHorizontalAlignment(SwingConstants.LEFT);
		btnFullscreen.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnFullscreen
				.setIcon(new ImageIcon(
						GUI.class
								.getResource("/de/duererInfoProject/fruitNinja/img/fullscreen-icon.png")));
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
				soundManager.playButton(false);
				int cursor = controller.getPreferences().getInt("cursor", 0);
				if (cursor == 3)
					cursor = 0;
				else
					cursor++;
				controller.getPreferences().putInt("cursor", cursor);
				btnHand.setIcon(new ImageIcon(
						GUI.class
								.getResource("/de/duererInfoProject/fruitNinja/img/cursor"
										+ cursor + ".png")));

				if (cursor == 0)
					btnHand.setText("Windows Cursor");
				else if (cursor == 1)
					btnHand.setText("Ninja Cursor");
				else if (cursor == 2)
					btnHand.setText("Red Cursor");
				else if (cursor == 3)
					btnHand.setText("No Cursor");
			}
		});
		btnHand.setIconTextGap(10);
		btnHand.setFont(new Font("SansSerif", Font.BOLD, 15));

		int cursor = controller.getPreferences().getInt("cursor", 0);
		btnHand.setIcon(new ImageIcon(GUI.class
				.getResource("/de/duererInfoProject/fruitNinja/img/cursor"
						+ cursor + ".png")));
		if (cursor == 0)
			btnHand.setText("Windows Cursor");
		else if (cursor == 1)
			btnHand.setText("Ninja Cursor");
		else if (cursor == 2)
			btnHand.setText("Red Cursor");
		else if (cursor == 3)
			btnHand.setText("No Cursor");

		Component verticalGlue_2 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_2);

		Box horizontalBox = Box.createHorizontalBox();
		settingsPanel.add(horizontalBox);

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
		btnBack.setIcon(new ImageIcon(
				GUI.class
						.getResource("/de/duererInfoProject/fruitNinja/img/back-icon.png")));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundManager.playButton(true);
				backToMenu();
			}
		});
		btnBack.setFont(new Font("SansSerif", Font.BOLD, 15));

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		verticalStrut_4.setPreferredSize(new Dimension(0, 10));
		verticalStrut_4.setMinimumSize(new Dimension(0, 10));
		settingsPanel.add(verticalStrut_4);
		mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		mainMenuPanel.add(verticalStrut_3);

		JLabel label = new JLabel("");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenuPanel.add(label);
		label.setIcon(new ImageIcon(GUI.class
				.getResource("/de/duererInfoProject/fruitNinja/img/logo.png")));

		JButton btnPlay = new JButton("Play");
		btnPlay.setMaximumSize(new Dimension(150, 45));
		btnPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPlay.setHorizontalAlignment(SwingConstants.LEFT);
		btnPlay.setIconTextGap(10);
		btnPlay.setIcon(new ImageIcon(
				GUI.class
						.getResource("/de/duererInfoProject/fruitNinja/img/play-icon.png")));
		btnPlay.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundManager.playButton(false);
				controller.newGame();
			}
		});

		Component verticalGlue_3 = Box.createVerticalGlue();
		mainMenuPanel.add(verticalGlue_3);
		mainMenuPanel.add(btnPlay);

		JButton btnSettings = new JButton("Settings");
		btnSettings.setMaximumSize(new Dimension(150, 45));
		btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSettings
				.setIcon(new ImageIcon(
						GUI.class
								.getResource("/de/duererInfoProject/fruitNinja/img/settings-icon.png")));
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		btnSettings.setIconTextGap(10);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundManager.playButton(false);
				settingsPanel.setVisible(true);
				mainMenuPanel.setVisible(false);
			}
		});

		Component verticalStrut_5 = Box.createVerticalStrut(20);
		mainMenuPanel.add(verticalStrut_5);

		JButton btnHighscore = new JButton("Highscore");
		btnHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundManager.playButton(false);
				highscorePanel.setVisible(true);
				mainMenuPanel.setVisible(false);
			}
		});
		btnHighscore
				.setIcon(new ImageIcon(
						GUI.class
								.getResource("/de/duererInfoProject/fruitNinja/img/highscore-icon.png")));
		btnHighscore.setMaximumSize(new Dimension(150, 45));
		btnHighscore.setIconTextGap(10);
		btnHighscore.setHorizontalAlignment(SwingConstants.LEFT);
		btnHighscore.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnHighscore.setAlignmentX(0.5f);
		mainMenuPanel.add(btnHighscore);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		mainMenuPanel.add(verticalStrut_1);
		btnSettings.setFont(new Font("SansSerif", Font.BOLD, 15));
		mainMenuPanel.add(btnSettings);

		JButton btnExit = new JButton("Exit");
		btnExit.setMaximumSize(new Dimension(150, 45));
		btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setIconTextGap(10);
		btnExit.setIcon(new ImageIcon(
				GUI.class
						.getResource("/de/duererInfoProject/fruitNinja/img/exit-icon.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				soundManager.playButton(false);
				dispose();
			}
		});

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		mainMenuPanel.add(verticalStrut_2);
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 15));
		mainMenuPanel.add(btnExit);

		Component verticalGlue = Box.createVerticalGlue();
		mainMenuPanel.add(verticalGlue);

		controller.getSoundManager().playBackground();
	}
}
