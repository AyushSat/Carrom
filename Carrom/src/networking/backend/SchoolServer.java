/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networking.backend;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;


/**
 *
 * @author john_shelby
 */
public class SchoolServer implements NetworkMessenger {

    private boolean listening;
    
    private List<ClientWriter> writers;
    private List<ClientReader> readers;
    private List<NetworkListener> listeners;
    
    private String programID;
    
    private InetAddress myIP;
    private ServerSocket serverSocket;
    
    private int maxConnections;
    

    public SchoolServer(String programID, InetAddress myIP) {
    	
    	this.programID = programID;
    	this.myIP = myIP;
    	
        listening = false;
        this.writers = new ArrayList<ClientWriter>();
        this.readers = new ArrayList<ClientReader>();
        listeners = new ArrayList<NetworkListener>();
        addNetworkListener(new NetworkListener() {
        	@Override
        	public void networkMessageReceived(NetworkDataObject ndo) {
        		
        		new Thread(new Runnable() {

        			@Override
        			public void run() {

        				InetAddress address = ndo.dataSource;

        				repeatMessage(ndo);

        				if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
        					synchronized(SchoolServer.this) {

        						for (int i = writers.size()-1; i >= 0; i--) {	
        							if (writers.get(i).getHost().equals(address))
        								writers.remove(i).stop();
        						}
        						for (int i = readers.size()-1; i >= 0; i--) {
        							if (readers.get(i).getHost().equals(address))
        								readers.remove(i).stop();
        						}

        						sendClientList();
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
    
    public void setMaxConnections(int max) {
    	this.maxConnections = max;
    }
    
    public synchronized void sendMessage(String messageType, Object... message) {
    	NetworkDataObject ndo = new NetworkDataObject();
    	ndo.serverHost = myIP;
    	ndo.dataSource = myIP;
    	ndo.messageType = messageType;
    	ndo.message = message;
		for (ClientWriter cw: writers) {
			cw.sendMessage(ndo);
		}
    }
    
    
    public synchronized void repeatMessage(NetworkDataObject ndo) {
		for (ClientWriter cw: writers) {
			if (!cw.getHost().equals(ndo.dataSource))
				cw.sendMessage(ndo);
		}
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
    
    public synchronized InetAddress[] getConnectedHosts() {
    	ArrayList<InetAddress> ips = new ArrayList<InetAddress>();
    	for (ClientReader cr : readers) {
    		if (cr.isConnected())
    			ips.add(cr.getHost());
    	}
    	return ips.toArray(new InetAddress[ips.size()]);
    }
    
    		
    public synchronized void disconnectFromClient(InetAddress host) {

    	for (int i = writers.size()-1; i >= 0; i--) {	
			if (writers.get(i).getHost().equals(host))
				writers.remove(i).stop();
		}
		for (int i = readers.size()-1; i >= 0; i--) {
			if (readers.get(i).getHost().equals(host))
				readers.remove(i).stop();
		}
    	
    }
    		
    public synchronized void disconnectFromClient(String host) {
    	
		try {
			InetAddress hostAdd = InetAddress.getByName(host);
			disconnectFromClient(hostAdd);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public synchronized void disconnectFromAllClients() {
    	for (ClientWriter cw : writers)
    		cw.stop();
    	for (ClientReader cr : readers)
    		cr.stop();
    	writers.clear();
    	readers.clear();
    }
    
    
    
    public synchronized void shutdownServer() {
    	listening = false;
    	if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			serverSocket = null;
    	}
    }
    
    

    public void waitForConnections(int port) {
        
    	if (serverSocket != null)
    		return;
    	
    	new Thread(new Runnable() {
    		@Override
    		public void run() {
    			
    	        listening = true;
    	        
    	        try {
    	            serverSocket = new ServerSocket(port);

    	            while (listening) {
    	                Socket s = serverSocket.accept();
    	                
    	                System.out.println("Server connected to " + s.getInetAddress().getHostAddress());
    	                
    	                if (maxConnections == readers.size()) {
    	                	System.out.println("At maximum capacity: " + maxConnections + ". Dropping connection.");
    	                	s.close();
    	                	continue;
    	                }
    	                	
    	                
    	                ClientWriter cw = new ClientWriter(s);
    	            	ClientReader cr = new ClientReader(s);
    	            	
    	            	cr.setDataSource(true);
    	                
    	                ArrayList<NetworkListener> handshakeListener = new ArrayList<NetworkListener>();
    	             
    	                handshakeListener.add(new NetworkListener() {

    	                	
    	                	private java.util.Timer timeout = new java.util.Timer();

    	                	{
    	                		timeout.schedule(new java.util.TimerTask() {

    	                			@Override
    	                			public void run() {
    	                				cw.stop();
    									cr.stop();
    	                			}

    	                		}, 10000L);
    	                	}

							@Override
							public void networkMessageReceived(NetworkDataObject ndo) {
								timeout.cancel();
								if (ndo.messageType.equals(NetworkDataObject.HANDSHAKE) && ndo.message[0].equals(programID)) {
									synchronized(SchoolServer.this) {
										cr.setListeners(listeners);
										writers.add(cw);
										readers.add(cr);
									}
									repeatMessage(ndo);
									sendClientList();
								} else {
									NetworkDataObject ndo2 = new NetworkDataObject();
									ndo2.dataSource = myIP;
									ndo2.serverHost = myIP;
									ndo2.messageType = NetworkDataObject.DISCONNECT;
									ndo2.message = new Object[]{};
									cw.sendMessage(ndo2);
									
									cw.stop();
									cr.stop();
								}
								
							}

							@Override
							public void connectedToServer(NetworkMessenger nm) {
								// TODO Auto-generated method stub
								
							}
    	                	
    	                });
    	                
    	                cr.setListeners(handshakeListener);
    	                
    	                cw.start();
    	                cr.start();
    	            	
    	            }
    	                
    	            

    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        } finally {
    	        	if (serverSocket != null) {
						try {
							serverSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						serverSocket = null;
    	        	}
    	        }
    			
    		}
    	}).start();
    }


    private void sendClientList() {
    	InetAddress[] connections = getConnectedHosts();
    	Object[] message = Arrays.copyOf(connections, connections.length, Object[].class);
    	sendMessage(NetworkDataObject.CLIENT_LIST, message);
    }

	@Override
	public Queue<NetworkDataObject> getQueuedMessages() {
		return null;
	}



}
