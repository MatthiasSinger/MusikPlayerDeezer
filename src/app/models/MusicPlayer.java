package app.models;

import deezerapi.objects.Track;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * 	@author Matze
 *
 *	Kümmert sich um das Abspielen und Stoppen der Musik, außerdem Lautstärke
 */
public class MusicPlayer
{
	private  MediaPlayer mediaPlayer;
	private boolean isPlaying = false;
	
	public void play()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.play();
			isPlaying = true ;
		}
	}
	
	public void pause()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.pause();
			isPlaying = false;
		}
	}
	
	public void startSong(Track t)
	{
		Media m = new Media(t.getPreview().toString());
		mediaPlayer = new MediaPlayer(m);
		mediaPlayer.setVolume(0.5);
		play();
	}

	public boolean isPlaying()
	{
		return isPlaying;
	}
}
