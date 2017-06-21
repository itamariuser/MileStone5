package model;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class XMLLevelLoader implements LevelLoader {

	@Override
	public Level loadLevelFromStream(InputStream in) {
		XMLDecoder e = new XMLDecoder(
		    new BufferedInputStream(in));
		Level newLevel=(Level)e.readObject();
		e.close();
		return newLevel;
	}

}
