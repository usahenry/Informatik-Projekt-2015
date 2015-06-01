package de.duererInfoProject.fruitNinja;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.LinkedList;

public class Highscore {
	
	private File highscoreFile;
	private LinkedList<String[]> highscoreList;
	
	public Highscore() {
		//Initialize Attributes
		highscoreFile = new File("C://Users//" + System.getProperty("user.name") + "//Documents//FruitNinjaHighscore.txt");
		if (!highscoreFile.isFile()) {
			try {
				highscoreFile.getParentFile().mkdirs();
				highscoreFile.createNewFile();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		highscoreList = new LinkedList<String[]>();
		
	}
	
	//Save to HighscoreFile
	public void save() {
		if (highscoreList == null) return;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(highscoreFile));
			for (String[] string : highscoreList) {
				for (int i = 0; i < string.length; i++) {
					writer.write(string[i].toString());
					writer.newLine();
				}
			}
			writer.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Load from HighscoreFile
	public void load() {
		if (highscoreList == null) return;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(highscoreFile));
			String line = reader.readLine();
			while (line != null) {
				highscoreList.add(new String[] {line, reader.readLine(), reader.readLine()});
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Sorts HighscoreList by String[valueToSort]
	public void sort(int valueToSort) {
		highscoreList.sort( new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				int x1 = Integer.parseInt(o1[valueToSort]);
				int x2 = Integer.parseInt(o2[valueToSort]);
				if (x1 > x2) {
					return 1;
				} else if (x1 == x2) {
					return 0;
				} else {
					return -1;
				}
			}
		});
	}
}
