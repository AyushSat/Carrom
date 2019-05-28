package networking.frontend;

public interface NetworkListener {

	/**
	 * Called when a successful connection with a server is made.
	 * 
	 * @param nm The NetworkMessager that this client program should use to communicate messages to other connected clients. Store this in a field.
	 */
	public void connectedToServer(NetworkMessenger nm);
	
	/**
	 * Called when data is received from other connected clients, or from the server itself. It might be unwise to process
	 * the message inside your implementation of this method, as you could have issues with threading (this method will be called
	 * using the Swing Event Dispatch Thread). Instead, consider calling NetworkMessenger's getQueuedMessages() method somewhere 
	 * inside your standard game/program loop to get all messages that have been received.
	 * 
	 * Also note - even though this method is called when you received messages, the messages are still stored in the message queue 
	 * until you remove them.
	 * 
	 * @param ndo The data that was received.
	 */
	public void networkMessageReceived(NetworkDataObject ndo);
	
}
