package audio;
import javax.sound.sampled.*;
public class MusicPlayer {
	private Clip clip;
	public MusicPlayer(String s) {
		try {
			// adds in the input stream, gets the input stream from the game class
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(s));
			//creates clip
			clip = AudioSystem.getClip();
			// opens clip from Audio Input Stream
			clip.open(ais);
		}
		//catches any useless errors so the audio can run
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void play() {
		//if the clip is empty
		if(clip == null) {
			return;
		}
		stop();
		//start clip from beginning
		clip.setFramePosition(0);
		//start clip
		clip.start();
	}
	private void stop() {
		//if clip is running stop
		if(clip.isRunning()) {
			clip.stop();
		}
	}
	public void close() {
		//stop the clip
		stop();
		//close the clip
		clip.close();
	}
	//method to set volume of the music
	public float setVol(float vol) {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(vol);
			return vol;
	}

}
