package app.controllers;

import app.models.Playlist;
import deezerapi.objects.Track;
import javafx.collections.ObservableList;

public class PlaylistController
{
	private Playlist playlist;
	private ObservableList<String> playlistDisplay;
	
	public PlaylistController(Playlist pl, ObservableList<String> ol)
	{
		this.playlist = pl;
		this.playlistDisplay = ol;
	}
	
	public void addTrack(Track track)
	{
		playlist.addTrack(track);
		playlistDisplay.add(track.getTitle());
	}
}
