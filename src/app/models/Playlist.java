package app.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import deezerapi.objects.Track;

/**
 * @author Matze
 *
 * Playlist Klasse dient der Speicherung der Playlist, wird in ListView angezeigt
 */
public class Playlist
{
	private Map<String,Track> trackList = new LinkedHashMap<>();
	private int pos = 0;
	
	/**
	 * @param t Track der hinzugefügt werden soll
	 */
	public void addTrack(Track t)
	{
		trackList.put(t.getTitle(), t);
	}
	/**
	 * @param t Track der entfernt werden soll
	 */
	public void removeTrack(String track)
	{
		trackList.remove(track);
	}
	/**
	 * Nächster Song 
	 */
	public void forward()
	{
		pos = (pos+1)%trackList.size();
	}
	/**
	 *  Vorheriger Song
	 */
	public void backward()
	{
		pos = Math.floorMod(pos-1, trackList.size());
	}
	/**
	 * @return aktueller Track
	 */
	public Track getCurrentTrack()
	{
		 return new ArrayList<Track>(trackList.values()).get(pos);
	}
	/**
	 * Playlist wird auf leeren Zustand gesetzt
	 */
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
