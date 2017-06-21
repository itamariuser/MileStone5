package model.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handle sokoban clients using thread pool (ExecutorService).
 */
public class ServerModel extends Observable implements Server {
	
	private int port;
	private volatile boolean stop;
	private BlockingQueue<Socket> awaitingClients;
	private BlockingQueue<Socket> handledClients; 
	private ClientHandler ch;
	private int nThreads;
	
	public ServerModel(int port, ClientHandler ch, int nThreads) {
		this.port = port;
		this.ch = ch;
		this.nThreads = nThreads;
	}
	
	public class ServerTask implements Callable<Boolean> {

		@Override
		public Boolean call() throws Exception {
			Socket aClient = awaitingClients.take();
			handledClients.put(aClient);
			boolean isSolvable = ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
			aClient.getInputStream().close();
			aClient.getOutputStream().close();
			return isSolvable;
		}
	}
	
	@Override
	public void runServer() throws Exception
	{
		ExecutorService executor = Executors.newFixedThreadPool(this.nThreads);
		ServerSocket server = new ServerSocket(this.port);
		server.setSoTimeout(10000);
		while(!stop)
		{ 
			awaitingClients.put(server.accept());
			executor.submit(new ServerTask());
		}
		server.close();	
	}
		
}

