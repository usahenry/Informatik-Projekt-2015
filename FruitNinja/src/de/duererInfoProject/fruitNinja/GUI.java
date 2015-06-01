package de.duererInfoProject.fruitNinja;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.CardLayout;
import javax.swing.JPanel;

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
		
		mainMenu = new JPanel();
		frame.getContentPane().add(mainMenu, "name_259883664488859");
		mainMenu.setLayout(null);
		mainMenu.setVisible(true);
		
		settings = new JPanel();
		frame.getContentPane().add(settings, "name_259887979946234");
		settings.setLayout(null);
		settings.setVisible(false);
		
		frame.getContentPane().add(universe);
		universe.setLayout(null);
		universe.setVisible(false);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		btnBack.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnBack.setBounds(6, 6, 137, 39);
		settings.add(btnBack);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblSettings.setBounds(304, 40, 90, 48);
		settings.add(lblSettings);
		
		JLabel label = new JLabel("");
		label.setBounds(136, 6, 440, 189);
		mainMenu.add(label);
		label.setIcon(new ImageIcon(GUI.class.getResource("/de/duererInfoProject/fruitNinja/img/logo.png")));
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				universe.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		btnPlay.setBounds(261, 236, 137, 39);
		mainMenu.add(btnPlay);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		btnSettings.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnSettings.setBounds(261, 305, 137, 39);
		mainMenu.add(btnSettings);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnExit.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnExit.setBounds(261, 382, 137, 39);
		mainMenu.add(btnExit);
	}
}
