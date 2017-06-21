package model.server;

import java.io.InputStream;
import java.io.OutputStream;

public class Client {
	
	private String ip;
	private String name;
	private boolean connected;
	private InputStream input;
	private OutputStream output;
	
	public Client(String ip) {
		
	}
	
	public InputStream getInput() {
		return input;
	}
	public void setInput(InputStream input) {
		this.input = input;
	}
	public OutputStream getOutput() {
		return output;
	}
	public void setOutput(OutputStream output) {
		this.output = output;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	
}
