package networking.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;

public class SchoolClient implements NetworkMessenger {

	private InetAddress server;
	private InetAddress myIP;

	private ClientWriter writer;
	private ClientReader reader;

	private List<NetworkListener> listeners;
	
	private Queue<NetworkDataObject> messageQueue;

	private String programID;
	
	

	public SchoolClient(String programID, InetAddress myIP) {
		this.myIP = myIP;
		this.programID = programID;
		messageQueue = new ConcurrentLinkedQueue<NetworkDataObject>();
		listeners = new ArrayList<NetworkListener>();
		addNetworkListener(new NetworkListener() {
			@Override
			public void networkMessageReceived(NetworkDataObject ndo) {

				new Thread(new Runnable() {

					@Override
					public void run() {

						synchronized(SchoolClient.this) {

							if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {

								if (ndo.dataSource.equals(server)) {
									disconnect();
								}

							}
						}
					}

				}).start();

			}

			@Override
			public void connectedToServer(NetworkMessenger nm) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	public void addNetworkListener(NetworkListener nl) {
		synchronized(listeners) {
			listeners.add(nl);
		}
	}
	
	public void removeNetworkListener(NetworkListener nl) {
    	synchronized(listeners) {
    		listeners.remove(nl);
    	}
	}

	public synchronized void sendMessage(String messageType, Object... message) {

		if (writer != null) {
			NetworkDataObject ndo = new NetworkDataObject();
			ndo.serverHost = server;
			ndo.dataSource = myIP;
			ndo.messageType = messageType;
			ndo.message = message;

			writer.sendMessage(ndo);
		}
	}


	public synchronized boolean connect(InetAddress host,int port) {
		try {

			disconnect();

			this.server = host;

			Socket s = new Socket(host, port);
			s.setKeepAlive(true);

			System.out.println("Client connected to " + s.getInetAddress().getHostAddress());

			reader = new ClientReader(s, messageQueue);
			writer = new ClientWriter(s);
			
			reader.setListeners(listeners);
			
			reader.start();
			writer.start();

			sendMessage(NetworkDataObject.HANDSHAKE, new Object[]{programID});

		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			if (reader != null) {
				reader.stop();
				reader = null;
			}
			if (writer != null) {
				writer.stop();
				writer = null;
			}
			return false;
		}

		return true;
	}


	public boolean connect(String host,int port) {
		try {
			return connect(InetAddress.getByName(host),port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized void disconnect() {
		if (reader != null) {
			reader.stop();
			reader = null;
		}
		if (writer != null) {
			writer.stop();
			writer = null;
		}

	}


	@Override
	public Queue<NetworkDataObject> getQueuedMessages() {
		return messageQueue;
	}



}
