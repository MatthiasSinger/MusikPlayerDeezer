package app.models;

import java.util.ArrayList;
import deezerapi.objects.Track;

/**
 * @author Matze
 *
 * Playlist Klasse dient der Speicherung der Playlist, wird in ListView angezeigt
 */
public class Playlist
{
	private ArrayList<Track> trackList = new ArrayList<>();
	private int pos = 0;
	/**
	 * @param t Track der hinzugefügt werden soll
	 */
	public void addTrack(Track t)
	{
		trackList.add(t);
	}
	/**
	 * @param t Track der entfernt werden soll
	 */
	public void removeTrack(Track t)
	{
		int indexOf = trackList.indexOf(t);
		trackList.remove(indexOf);
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
		pos = (pos-1)%trackList.size();
	}
	/**
	 * @return aktueller Track
	 */
	public Track getCurrentTrack()
	{
		return trackList.get(pos);
	}
	/**
	 * Playlist wird auf leeren Zustand gesetzt
	 */
	public void reset()
	{
		trackList = new ArrayList<>();
		pos = 0;
	}
}
