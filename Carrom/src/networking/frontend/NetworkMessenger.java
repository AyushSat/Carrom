package networking.frontend;

import java.util.ArrayList;
import java.util.Queue;

public interface NetworkMessenger {

	/**
	 * Sends data to other connected clients.
	 * 
	 * @param messageType The type of message being sent. For client programs, this should always be NetworkDataObject.MESSAGE.
	 * @param message Any number of objects containing data to be sent.
	 */
	public void sendMessage(String messageType, Object... message);
	
	/**
	 * Gets a queue containing all messages that have been received. Your program should remove items from this queue as you
	 * process the messages so that they don't build up (messages will get garbage collected once they're removed from here).
	 * 
	 * @return A queue containing all messages received by this client in the order that they were received.
	 */
	public Queue<NetworkDataObject> getQueuedMessages();
	
}
