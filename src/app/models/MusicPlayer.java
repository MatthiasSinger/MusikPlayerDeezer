package app.models;

import java.util.ArrayList;

import app.util.MyListener;
import app.util.NewTimeListener;
import app.util.NextSongListener;
import app.util.SongChangedListener;
import deezerapi.objects.Track;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * 	@author Matze
 *
 *	Kümmert sich um das Abspielen und Stoppen der Musik, außerdem Lautstärke
 */
public class MusicPlayer
{
	private  MediaPlayer mediaPlayer;
	private boolean isPlaying = false;
	private NextSongListener nsl;
	private SongChangedListener scl;
	private NewTimeListener ntl;
	
	public MusicPlayer(NextSongListener nsl, SongChangedListener scl, NewTimeListener ntl)
	{
		this.nsl = nsl;
		this.scl = scl;
		this.ntl = ntl;
	}
	
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
		mediaPlayer.currentTimeProperty().addListener(cl -> {
			try
			{
				ntl.updateCurrentTime(mediaPlayer.getCurrentTime());
			} catch (NullPointerException npe)
			{
				
			}
		});
		play();
		mediaPlayer.setOnEndOfMedia(() -> {
			nsl.nextSong();
		});
		scl.updateTime(m.getDuration());
	}

	
	public boolean isPlaying()
	{
		return isPlaying;
	}
	
	public boolean mediaPlayerNull()
	{
		return mediaPlayer == null;
	}
	
	
	public void notifyNextSongListener()
	{
		nsl.nextSong();
	}
	
	public void notifySongChangedListener(Duration d)
	{
		scl.updateTime(d);
	}

	public void changeVolume(double d)
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.setVolume(d);
		}
	}

	public void reset()
	{
		mediaPlayer.stop();
		mediaPlayer = null;
	}

	public void changeCurrentTime(double value)
	{
		mediaPlayer.seek(Duration.seconds((value*30.)/100.));
	}
}

