package ca.nicho.client;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class AudioHandler {

	public static ClipWrapper PANDEMIC;
	public static ClipWrapper DENIED;
	public static ClipWrapper PURCHASE;
	public static ClipWrapper SHOT;
	
	public AudioHandler(){
		System.out.println("AudioHandler: Loading and encoding audio files");
		try{
			PANDEMIC = loadClip("pandemic.mp3");
			DENIED = loadClip("denied.wav");
			PURCHASE = loadClip("purchase.mp3");
			SHOT = loadClip("shot.mp3", true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public ClipWrapper loadClip(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		return this.loadClip(file, false);
	}
	
	public ClipWrapper loadClip(String file, boolean overwrite) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("res/" + file));
		AudioFormat baseFormat = audioStream.getFormat();
		AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
		        baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
		AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
		
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream2);
		return new ClipWrapper(clip, overwrite);
	}
	
	public class ClipWrapper {
		public Clip clip;
		public boolean overwrite = false;
		
		public ClipWrapper(Clip c){
			this.clip = c;
		}
		
		public ClipWrapper(Clip c, boolean overwrite){
			this.clip = c;
			this.overwrite = overwrite;
		}
		
		public void setVolume(float vol){
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(vol);
		}
		
		public void restart(){
			clip.setFramePosition(0);
		}
		
		public void play(){
			if(!clip.isRunning() || overwrite)
				restart();
			clip.start();
		}
	}
}
