package app.models;

import java.util.ArrayList;

import deezerapi.objects.Track;

public class Playlist
{
	private ArrayList<Track> trackList = new ArrayList<>();
	private int pos = 0;
	
	public void addTrack(Track t)
	{
		trackList.add(t);
	}
	
	public void removeTrack(Track t)
	{
		int indexOf = trackList.indexOf(t);
		trackList.remove(indexOf);
	}
	
	public void forward()
	{
		pos = (pos+1)%trackList.size();
	}
	
	public void backward()
	{
		pos = (pos-1)%trackList.size();
	}
	
	public Track getCurrentTrack()
	{
		return trackList.get(pos);
	}
	
	public void reset()
	{
		trackList = new ArrayList<>();
		pos = 0;
	}
}
