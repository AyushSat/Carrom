package main;
import java.io.File;
import javax.sound.sampled.*;

/** The object that takes in a File path for a music file and can play and pause the music file
 * 
 * @author Akshat Jain
 *
 */
public class Music implements Runnable {

	private File music;
	private Clip clip;

	/**
	 * 
	 * @param file File object with the path that leads to the music file
	 */
	public Music(File file) {
		music = file;
	}

	/** Starts playing the music file
	 * 
	 */
	public void run() {

		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(music));

			clip.start();
			clip.loop(100); // 20 times is more than enough for the program to run

		} catch (Exception e) { // Could possibly throw an error, AudioSystem requires a try and catch statement

		}
	}

	/** Pauses the music file
	 * 
	 */
	public void pause() {
		clip.stop();
	}

	/** Continues the music file after being stopped
	 * 
	 */
	public void play() {
		clip.start();
	}
}
