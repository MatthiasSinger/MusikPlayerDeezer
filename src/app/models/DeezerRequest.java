package app.models;

import java.io.IOException;
import java.util.List;

import deezerapi.objects.Album;
import deezerapi.objects.Artist;
import deezerapi.objects.ArtistAlbums;
import deezerapi.objects.Track;
import services.DeezerService;

public class DeezerRequest
{
	public List<Artist> loadArtists(String query) throws IOException
	{
		List<Artist> artists = DeezerService.searchForArtists(query);
		return artists;
	}
	
	public List<Album> loadAlbums(Artist a) throws IOException
	{
		ArtistAlbums albums_artist = DeezerService.loadArtistsAlbums(a.getId());
		List<Album> albums = albums_artist.getAlbums();
		return albums;
	}

	public List<Track> loadTracks(Album a) throws IOException
	{
		List<Track> tracklist = DeezerService.loadAlbumTracks(a);
		return tracklist;
	}
}
