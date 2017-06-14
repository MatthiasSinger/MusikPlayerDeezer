package app.util;

import javafx.util.Duration;

public interface SongChangedListener extends MyListener
{
	void updateTime(Duration d);
}
