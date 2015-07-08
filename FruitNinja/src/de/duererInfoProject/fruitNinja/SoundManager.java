package de.duererInfoProject.fruitNinja;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Used to play sound, manages sound files
public class SoundManager {
	
	private Controller controller;
	private URL[] splashSounds;
	private URL background_music;
	private URL swoosh_sound;
	private URL bomb_swoosh_sound;
	private URL bomb_sound;
	private URL button_sound;
	private URL back_button_sound;
	private URL miss_sound;
	private Random random;
	private URL lives_sound;
	private URL score_sound;
	
	public SoundManager(Controller controller) {
		//Initialize attributes
		this.controller = controller;
		random = new Random();
		splashSounds = new URL[4];
		
		//Load audio file urls
		background_music = GUI.class.getResource("sound/background_music.wav");
		swoosh_sound = GUI.class.getResource("sound/swoosh.wav");
		bomb_swoosh_sound = GUI.class.getResource("sound/bomb_swoosh.wav");
		button_sound = GUI.class.getResource("sound/button1.wav");
		back_button_sound = GUI.class.getResource("sound/button2.wav");
		bomb_sound = GUI.class.getResource("sound/bomb.wav");
		miss_sound = GUI.class.getResource("sound/miss.wav");
		lives_sound = GUI.class.getResource("sound/lives.wav");
		score_sound = GUI.class.getResource("sound/score.wav");
		for (int i = 0; i < 4; i++) splashSounds[i] = GUI.class.getResource("sound/splash" + (i + 1) + ".wav");
	}
	
	//Plays the background music in a loop
	public void playBackground() {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(background_music));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			controller.errorMessage(e, "Error while loading an audio file!");
		}
	}
	
	//Plays the audio file @audio
	public void playSound(URL audio) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(audio));
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			controller.errorMessage(e, "Error while loading an audio file!");
		}
	}
	
	public void playButton(boolean backButton) {
		if (backButton) playSound(back_button_sound);
		else playSound(button_sound);
	}
	
	public void playSwoosh(int itemTypeID) {
		if (itemTypeID == Bomb.ItemTypeID || itemTypeID == ScoreBomb.ItemTypeID) playSound(bomb_swoosh_sound);
		else playSound(swoosh_sound);
	}
	
	public void playLives() {
		playSound(lives_sound);
	}
	
	public void playBomb() {
		playSound(bomb_sound);
	}
	
	public void playScore() {
		playSound(score_sound);
	}
	
	public void playMiss() {
		playSound(miss_sound);
	}
	
	public void playSplash() {
		playSound(splashSounds[random.nextInt(4)]);
	}
}
