package model.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	public Boolean handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception;
}
