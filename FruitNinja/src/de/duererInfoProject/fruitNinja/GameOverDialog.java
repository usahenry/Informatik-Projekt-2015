package de.duererInfoProject.fruitNinja;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GameOverDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Controller controller;
	private Highscore highscore;
	private int score, playtime;
	private JTextField input_name;
	private JTable table;
	private JPanel panel_highscore;

	public GameOverDialog(Controller controller, int score, int playtime) {
		this.controller = controller;
		highscore = controller.getHighscore();
		this.score = score;
		this.playtime = playtime;
		controller.setLookAndFeel();
		setResizable(false);
		setBounds(170, 170, 577, 610);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblGameOver = new JLabel("GAME OVER!");
		lblGameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setFont(new Font("Narkisim", Font.BOLD, 60));
		lblGameOver.setBounds(12, 13, 543, 67);
		contentPanel.add(lblGameOver);
		
		JLabel txt_score = new JLabel("Score: " + score);
		txt_score.setFont(new Font("Narkisim", Font.BOLD, 25));
		txt_score.setBounds(22, 85, 533, 26);
		contentPanel.add(txt_score);
		
		panel_highscore = new JPanel();
		panel_highscore.setBounds(12, 122, 543, 37);
		contentPanel.add(panel_highscore);
		
		JLabel lblName = new JLabel("Name:");
		panel_highscore.add(lblName);
		
		input_name = new JTextField();
		input_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) addHighscore();
			}
		});
		panel_highscore.add(input_name);
		input_name.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHighscore();
			}
		});
		panel_highscore.add(btnOk);
		
		table = new JTable();
		setTableModel();
		table.setBounds(12, 172, 543, 314);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 172, 543, 350);
		contentPanel.add(scrollPane);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Main Menu");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						controller.stopGame();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("New Game");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						controller.restartGame();
						dispose();
					}
				});
				cancelButton.setFocusable(false);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		panel_highscore.setVisible(highscore.isHighscore(score));
	}
	
	public void addHighscore() {
		highscore.load();
		
		highscore.newHighscore(new String[] {input_name.getText(), score + "", Math.round(playtime / 100) + ""});
		highscore.save();
		setTableModel();
		panel_highscore.setVisible(false);
	}
	
	public void setTableModel() {
		table.setModel(new DefaultTableModel(
				createTableContent(), 
				new String[] {"TOP", "Name", "Score", "Playtime"}
			) {
				boolean[] columnEditables = new boolean[] {false, false, false, false};
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
	}
	
	public Object[][] createTableContent() {
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
}
