//Author: Gokulkrishnan Harikrishnan
//Period: 1
//Purpose: Object that plays music with methods to pause and play music. Is played using a thread.
//Date: 05-11-2018
//Revision: 2

import java.io.File;
import javax.sound.sampled.*;

public class Music implements Runnable {

	// Music field
	private static File music;

	// Clip field
	private Clip clip;

	// Music object, called in the thread's initialization
	public Music(File m) {
		music = m;
	}

	// Called by thread, starts the music
	@Override
	public void run() {

		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(music));

			clip.start();
			clip.loop(20); // 20 times is more than enough for the program to run

		} catch (Exception e) { // Could possibly throw an error, AudioSystem requires a try and catch statement

		}
	}

	// Pause music
	public void pause() {
		clip.stop();
	}

	// Play music
	public void play() {
		clip.start();
	}
}
