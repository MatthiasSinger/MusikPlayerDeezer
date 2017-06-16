package app.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import deezerapi.objects.Track;

public class Playlist
{
	private Map<String,Track> trackList = new LinkedHashMap<>();
	private int pos = 0;
	
	public void addTrack(Track t)
	{
		trackList.put(t.getTitle(), t);
	}
	
	public void removeTrack(String track)
	{
		trackList.remove(track);
	}
	
	public void forward()
	{
		pos = (pos+1)%trackList.size();
	}
	
	public void backward()
	{
		pos = Math.floorMod(pos-1, trackList.size());
	}
	
	public Track getCurrentTrack()
	{
		 return new ArrayList<Track>(trackList.values()).get(pos);
	}
	
	public void reset()
	{
		trackList = new LinkedHashMap<String,Track>();
		pos = 0;
	}
	public Track getTrack(String title)
	{
		return trackList.get(title);
	}
	public void setPos(int n)
	{
		this.pos = n;
	}
	public int getPos()
	{
		return this.pos;
	}
	
	
}
