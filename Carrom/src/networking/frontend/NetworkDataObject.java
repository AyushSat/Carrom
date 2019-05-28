package networking.frontend;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * 
 * A piece of data sent over the network between clients and servers.
 * 
 * @author john_shelby
 *
 *
 *
 *
 */
public class NetworkDataObject implements Serializable {

	private static final long serialVersionUID = 5172572529299632629L;

	/**
	 * Used by clients attempting to connect to the server. The receipt of a HANDSHAKE indicates that new client has connected.
	 * 
	 * Message array should be length 1, containing only the programID (type String).
	 */
	public static final String HANDSHAKE = "HANDSHAKE";
	
	/**
	 * Sent by the server whenever clients connect or disconnect.
	 * 
	 * Message array should be length == number of connected clients, containing the IP of each connected client (type InetAddress).
	 */
	public static final String CLIENT_LIST = "CLIENT_LIST";
	
	/**
	 * Sent by the server whenever a client disconnects (or the server itself disconnects).
	 * 
	 * Message array should be length 0.
	 */
	public static final String DISCONNECT = "DISCONNECT";
	
	/**
	 * Used by client programs to send program data. Ignored by the server.
	 * 
	 * Message array can be whatever is desired by client programs.
	 */
	public static final String MESSAGE = "MESSAGE";
	
	/**
	 * The IP address of the connected server.
	 */
	public InetAddress serverHost;
	
	/**
	 * The IP address of the client or server that originated this data object (as viewed by the server).
	 */
	public InetAddress dataSource;
	
	/**
	 * The type of message being sent in this data object. Possible values include HANDSHAKE, CLIENT_LIST, DISCONNECT, or MESSAGE. 
	 */
	public String messageType;
	
	/**
	 * Additional data. The length of this array, and the types of data it contains, depends on the messageType and the messages sent by connected clients.
	 */
	public Object[] message;
	
	
	/**
	 * 
	 * @return The IP address of the dataSource, as a String.
	 */
	public String getSourceIP() {
		return dataSource.getHostAddress();
	}
	
	/**
	 * 
	 * @return The name of the dataSource.
	 */
	public String getSourceName() {
		return dataSource.getHostName();
	}
	
}
