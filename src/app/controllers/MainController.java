package app.controllers;

import java.io.IOException;
import java.util.List;

import app.models.DeezerRequest;
import app.models.MusicPlayer;
import app.models.Playlist;
import deezerapi.objects.Album;
import deezerapi.objects.Artist;
import deezerapi.objects.ArtistAlbums;
import deezerapi.objects.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class MainController implements EventHandler
{

	@FXML ListView listViewResults;
	@FXML ListView listViewPlaylist;
	@FXML Label labAlben;
	@FXML Label labTitel;
	@FXML Label labSearch;
	@FXML Label labPlaylist;
	@FXML Label labCurrentTime;
	@FXML Label labDuration;
	@FXML TextField textFieldSearch;
	@FXML Button butSearch;
	@FXML Button butReset;
	@FXML Button butBack;
	@FXML Button butPlay;
	@FXML Button butForward;
	@FXML Slider sliderTime;
	@FXML Slider sliderVolume;
	ObservableList<String> playlistDisplay = FXCollections.observableArrayList();
	
	MusicPlayer musicPlayer = new MusicPlayer();
	Playlist playlist = new Playlist();
	DeezerRequest deezerRequest = new DeezerRequest();
	
	@FXML
	public void initialize()
	{
		listViewPlaylist.setItems(playlistDisplay);
		listViewPlaylist.setOnMouseClicked(e -> {
			MouseButton button = e.getButton();
			if (button.equals(MouseButton.PRIMARY))
			{
				String title = (String)listViewPlaylist.getSelectionModel().getSelectedItem();
				Track t = playlist.getTrack(title);
				musicPlayer.startSong(t);
				butPlay.setText("Pause");
				int n = listViewPlaylist.getSelectionModel().getSelectedIndex();
				playlist.setPos(n);
				listViewPlaylist.getFocusModel().focus(n);
			}
			else if (button.equals(MouseButton.SECONDARY))
			{
				String title = (String)listViewPlaylist.getSelectionModel().getSelectedItem();
				playlistDisplay.remove(listViewPlaylist.getSelectionModel().getSelectedItem());
				playlist.removeTrack(title);
			}
		});
		musicPlayer.addEventHandler(this);
	}
	
	@FXML
	private void playButton()
	{
		if (!musicPlayer.isPlaying() && !musicPlayer.mediaPlayerNull())
		{
			musicPlayer.play();
			butPlay.setText("Pause");
		}
		else
		{
			musicPlayer.pause();
			butPlay.setText("Play");
		}
	}
	
	@FXML
	private void forward()
	{
		if (playlistDisplay.size() > 0)
		{
			playlist.forward();
			listViewPlaylist.getFocusModel().focus(playlist.getPos());
			listViewPlaylist.getSelectionModel().select(playlist.getPos());
			musicPlayer.startSong(playlist.getCurrentTrack());
		}
	}
	
	@FXML
	private void backward()
	{
		if (playlistDisplay.size() > 0)
		{
			playlist.backward();
			listViewPlaylist.getFocusModel().focus(playlist.getPos());
			listViewPlaylist.getSelectionModel().select(playlist.getPos());
			musicPlayer.startSong(playlist.getCurrentTrack());
		}
	}
	
	@FXML
	private void reset()
	{
		playlist.reset();
		playlistDisplay.clear();
	}
	
	@FXML
	private void searchArtist() throws IOException
	{
		ObservableList<String> list = FXCollections.observableArrayList();
		List<Artist> artists = deezerRequest.loadArtists(textFieldSearch.getText());
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
			this.addTrack(track);
		});
	}

	private void addTrack(Track track)
	{
		playlist.addTrack(track);
		playlistDisplay.add(track.getTitle());
	}

	public void handle(Event e)
	{
		forward();
	}
	
}
