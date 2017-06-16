package app.util;

import app.controllers.MainController;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.Duration;

public class Listener implements NewTimeListener, NextSongListener, SongChangedListener
{

	MainController mainController;
	Label labDuration,labCurrentTime;
	Slider sliderTime;
	boolean dragging;
	
	public Listener(MainController mainController, Label labDuration, Label labCurrentTime, Slider sliderTime)
	{
		this.mainController = mainController;
		this.labDuration = labDuration;
		this.labCurrentTime = labCurrentTime;
		this.sliderTime = sliderTime;
		this.dragging = false;
	}
	
	@Override
	public void updateTime(Duration d)
	{
		labDuration.setText("00:30");
	}

	@Override
	public void nextSong()
	{
		mainController.forward();
	}

	@Override
	public void updateCurrentTime(Duration d)
	{
		if (!dragging)
		{
			sliderTime.setValue((d.toSeconds()/30.)*100);
			int seconds = (int)d.toSeconds();
			int minutes = seconds/60;
			seconds %= 60;
			String timeString = String.format("%02d:%02d", minutes,seconds);
			labCurrentTime.setText(timeString);
		}
	}

	public void setDragging(boolean b)
	{
		this.dragging = b;
	}
}
