package app.controllers;

import java.io.IOException;
import java.util.List;

import app.models.DeezerRequest;
import deezerapi.objects.Album;
import deezerapi.objects.Artist;
import deezerapi.objects.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class DeezerRequestController
{
	DeezerRequest deezerRequest = new DeezerRequest();
	ListView<String> listViewResults;
	PlaylistController playlistController;
	
	public DeezerRequestController(ListView<String> listviewResults, PlaylistController playlistController)
	{
		this.listViewResults = listviewResults;
		this.playlistController = playlistController;
	}
	
	public void searchArtist(String searchText) throws IOException
	{
		System.out.println(listViewResults == null);
		ObservableList<String> list = FXCollections.observableArrayList();
		List<Artist> artists = deezerRequest.loadArtists(searchText);
		for (int i = 0; i < 20 && i < artists.size(); i++)
		{
			list.add(artists.get(i).getName());
		}
		listViewResults.setItems(list);
		listViewResults.setOnMouseClicked(e -> {
			String selected = (String)listViewResults.getSelectionModel().getSelectedItem();
			Artist a = null;
			for (int i = 0; i < artists.size(); i++)
			{
				if (artists.get(i).getName().equals(selected))
				{
					a = artists.get(i);
					break;
				}
			}
			try
			{
				this.getAlbums(a);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		});
	}
	
	private void getAlbums(Artist a) throws IOException
	{
		ObservableList<String> list = FXCollections.observableArrayList();
		List<Album> albums = deezerRequest.loadAlbums(a);
		albums.forEach(alb -> list.add(alb.getTitle()));
		listViewResults.setItems(list);
		listViewResults.setOnMouseClicked(e -> {
			String selected = (String)listViewResults.getSelectionModel().getSelectedItem();
			Album alb = null;
			for (int i = 0; i < albums.size(); i++)
			{
				if (albums.get(i).getTitle().equals(selected))
				{
					alb = albums.get(i);
					break;
				}
			}
			try
			{
				this.getTracks(alb);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		});
	}
	
	private void getTracks(Album a) throws IOException
	{
		ObservableList<String> list = FXCollections.observableArrayList();
		List<Track> tracks = deezerRequest.loadTracks(a);
		tracks.forEach(tra -> list.add(tra.getTitle()));
		listViewResults.setItems(list);
		listViewResults.setOnMouseClicked(e -> {
			String selected = (String)listViewResults.getSelectionModel().getSelectedItem();
			Track track = null;
			for (int i = 0; i < tracks.size(); i++)
			{
				if (tracks.get(i).getTitle().equals(selected))
				{
					track = tracks.get(i);
					break;
				}
			}
			playlistController.addTrack(track);
		});
	}
	
}
