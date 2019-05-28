/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networking.backend;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.SwingUtilities;

import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;

/**
 *
 * @author john_shelby
 */
public class ClientReader implements Runnable{

	private static final int RETRY_TIMEOUT = 10;
	
    private Socket s;
    private ObjectInputStream in;
    private InetAddress host;
    private boolean looping;
    private boolean setTheSource;
    
    private Queue<NetworkDataObject> messageQueue;
    
    private List<NetworkListener> listeners;
    
    
    public ClientReader(Socket s) {
    	this(s,null);
    }

    public ClientReader(Socket s, Queue<NetworkDataObject> messageQueue) {
        this.s = s;
        
        setTheSource = false;
        host = s.getInetAddress();
        this.messageQueue = messageQueue;
        
    }
    
    public InetAddress getHost() {
    	return host;
    }
    
    public Queue<NetworkDataObject> getMessageQueue() {
    	return messageQueue;
    }
    
    public void start() {
    	try {
    		if (!looping) {
        		in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
        		looping = true;
        		new Thread(this).start();
        	}
        } catch(IOException e) {
            System.err.println("Error connecting input stream.");
            e.printStackTrace();
        }
    }
    
    public void stop() {
    	looping = false;
    }

    public boolean isConnected() {
    	return looping;
    }
    
    
    public void setListeners(List<NetworkListener> listeners) {
    	this.listeners = listeners;
    }
    
    
    public void setDataSource(boolean setTheSource) {
    	this.setTheSource = setTheSource;
    }

    public void run() {
    	
        try {
        	int tries = 0;
            while(looping) {

                try {
                	Serializable data = (Serializable) in.readObject();
                	if (data instanceof NetworkDataObject) {
                		
                		NetworkDataObject ndo = (NetworkDataObject)data;
						if (setTheSource)
							ndo.dataSource = host;
						
						if (messageQueue != null)
							messageQueue.add(ndo);
                		
                		if (listeners != null) {
                			synchronized(listeners) {
                				for (NetworkListener nl : listeners) {
                					SwingUtilities.invokeLater(new Runnable() {
                						public void run() {
                							nl.networkMessageReceived(ndo);
                						}
                					});
                				}
                			}
                		}

                	}

                    tries = 0;
                } catch (IOException e) {
                	tries++;
                	if (tries >= RETRY_TIMEOUT) {
                		looping = false;
                	}
                    e.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    System.exit(0);
                }

            }
            
            NetworkDataObject ndo = new NetworkDataObject();
			ndo.dataSource = host;
			ndo.serverHost = host;
			ndo.messageType = NetworkDataObject.DISCONNECT;
			ndo.message = new Object[]{};
            
			if (messageQueue != null)
				messageQueue.add(ndo);
            
            if (listeners != null) {
            	synchronized(listeners) {
            		for (NetworkListener nl : listeners) {
            			SwingUtilities.invokeLater(new Runnable() {
            				public void run() {
            					nl.networkMessageReceived(ndo);
            				}
            			});
            		}
            	}
            }
            

        } finally {
            try {
                if (in != null)
                    in.close();
                if (!s.isClosed())
                    s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
