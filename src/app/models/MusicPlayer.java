package app.models;

import java.util.ArrayList;

import deezerapi.objects.Track;
import javafx.event.Event;
import javafx.event.EventHandler;
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
	private ArrayList<EventHandler> eventHandler = new ArrayList<>();
	
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
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
		}
		Media m = new Media(t.getPreview().toString());
		mediaPlayer = new MediaPlayer(m);
		mediaPlayer.setVolume(0.5);
		play();
		mediaPlayer.setOnEndOfMedia(() -> {
			notifyHandler();
		});
	}

	private void notifyHandler()
	{
		for (EventHandler eh : eventHandler)
		{
			eh.handle(null);
		}
	}

	public void addEventHandler(EventHandler eh)
	{
		this.eventHandler.add(eh);
	}
	
	public boolean isPlaying()
	{
		return isPlaying;
	}
	
	public boolean mediaPlayerNull()
	{
		return mediaPlayer == null;
	}
}
