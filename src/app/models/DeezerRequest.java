package app.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import deezerapi.objects.Track;
import deezerapi.objects.Tracks;
import deezerapi.objects.Album;
import deezerapi.objects.Artist;
import deezerapi.objects.ArtistAlbums;
import services.DeezerService;

public class DeezerRequest
{
	public List<Artist> loadArtists(String query) throws IOException
	{
		List<Artist> artists = DeezerService.searchForArtists(query);
		System.out.println(artists.size());
		return artists;
	}
	
	public List<Album> loadAlbums(Artist a) throws IOException
	{
		ArtistAlbums albums_artist = DeezerService.loadArtistsAlbums(a.getId());
		List<Album> albums = albums_artist.getAlbums();
		return albums;
	}

	public List<Track> loadTracks(Album a)
	{
		Tracks tracks = a.getTracks();
		List<Track> tracklist = tracks.getTracks();
		return tracklist;
	}
}
