package app.controllers;

import java.io.IOException;

import app.models.MusicPlayer;
import app.models.Playlist;
import app.util.Listener;
import app.util.NewTimeListener;
import app.util.NextSongListener;
import app.util.SongChangedListener;
import deezerapi.objects.Track;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

public class MainController
{
	@FXML ListView<String> listViewResults;
	@FXML ListView<String> listViewPlaylist;
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
	
	ObservableList<String> playlistDisplay;
	Playlist playlist;
	PlaylistController playlistController;
	DeezerRequestController deezerRequestController;
	Listener listener;
	MusicPlayer musicPlayer;
	
	@FXML
	public void initialize()
	{
		playlistDisplay = FXCollections.observableArrayList();
		playlist = new Playlist();
		playlistController = new PlaylistController(playlist, playlistDisplay);
		deezerRequestController = new DeezerRequestController(listViewResults,playlistController);
		listener = new Listener(this,labDuration,labCurrentTime,sliderTime);
		musicPlayer = new MusicPlayer(listener,listener,listener);
		
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
		
		sliderVolume.setValue(50.);
		sliderVolume.valueProperty().addListener(cl -> {
			musicPlayer.changeVolume(sliderVolume.getValue()/100.);
		});
		
		sliderTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasChanging, Boolean isNowChanging) {
		        listener.setDragging(true);
		    	if (!isNowChanging) {
		        	musicPlayer.changeCurrentTime(sliderTime.getValue());
		        	listener.setDragging(false);
		        }
		    }
		});
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
	public void forward()
	{
		if (playlistDisplay.size() > 0)
		{
			playlist.forward();
			listViewPlaylist.getFocusModel().focus(playlist.getPos());
			listViewPlaylist.getSelectionModel().select(playlist.getPos());
			listViewPlaylist.scrollTo(playlist.getPos());
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
		musicPlayer.reset();
	}
	
	@FXML
	private void searchArtist() throws IOException
	{
		System.out.println(listViewResults == null);
		deezerRequestController.searchArtist(textFieldSearch.getText());
	}
}
