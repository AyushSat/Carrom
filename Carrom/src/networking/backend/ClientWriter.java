/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networking.backend;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import networking.frontend.NetworkDataObject;

/**
 *
 * @author john_shelby
 */
public class ClientWriter implements Runnable{

	private static final int RETRY_TIMEOUT = 10;
	private static final int WAIT_TIME = 16;
	
    private Socket s;
    private InetAddress host;
    private ObjectOutputStream out;
    private Queue<NetworkDataObject> messageQueue;
    private boolean looping;

    public ClientWriter(Socket s) {
        this.s = s;
        host = s.getInetAddress();
        messageQueue = new ConcurrentLinkedQueue<NetworkDataObject>();
    }
    
    
    public InetAddress getHost() {
    	return host;
    }
    
    public void sendMessage(NetworkDataObject ndo) {
    	messageQueue.add(ndo);
    }
    
    public void stop() {
    	looping = false;
    }
    
    public void start() {
    	try {
    		if (!looping) {
        		out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
                out.flush();
        		looping = true;
        		new Thread(this).start();
        	}
        } catch(IOException e) {
            System.err.println("Error connecting output stream.");
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
    	return looping;
    }

    public void run() {

    	looping = true;
        try {
        	int tries = 0;
            while(looping) {
            	long startTime = System.currentTimeMillis();
            	
            	try {
            		while (!messageQueue.isEmpty())
            			out.writeObject(messageQueue.poll());
                    out.flush();
                    out.reset();
                    tries = 0;
                } catch (IOException e) {
                	tries++;
                	if (tries >= RETRY_TIMEOUT) {
                		looping = false;
                	}
                    e.printStackTrace();
                }
            	
                long waitTime = WAIT_TIME - (System.currentTimeMillis() - startTime);
                
                if (waitTime > 0) {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {}
                } else Thread.yield();
                
            }
        } finally {
            try {
                if (out != null)
                    out.close();
                if (!s.isClosed())
                    s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }



}
