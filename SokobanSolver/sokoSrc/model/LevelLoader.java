package model;

import java.io.InputStream;

public interface LevelLoader {
	public Level loadLevelFromStream(InputStream in) throws Exception;
}
