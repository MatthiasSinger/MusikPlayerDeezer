package app.util;

import javafx.util.Duration;

public interface NewTimeListener extends MyListener
{
	void updateCurrentTime(Duration d);
}
