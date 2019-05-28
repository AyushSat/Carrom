package networking.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultCaret;

import networkedgame.NetworkedGameBoard;
import networking.backend.PeerDiscovery;
import networking.backend.SchoolClient;
import networking.backend.SchoolServer;

public class NetworkManagementPanel extends JPanel
{

	private static final int TCP_PORT = 4444;
	private static final int BROADCAST_PORT = 4444;
	
	private static final int DISCOVERY_TIMEOUT = 15;

	private JTextArea statusText;
	private JList<InetAddress> hostList, connectedList;

	private JProgressBar discoveryProcess; 

	private JButton connectButton;
	private JButton serverButton;
	private JButton discoverButton;
	private JButton disconnectButton;
	private JButton connectCustomButton;

	private InetAddress myIP;
	private PeerDiscovery discover;
	private SchoolServer ss;
	private SchoolClient sc;
	
	private String programID;
	private NetworkListener clientProgram;

	private Timer refreshTimer;
	private int timeOut;
	
	private int maxPerServer;

	private String serverIP;
	
	/**
	 * Constructs and makes visible a window containing network management tools.
	 * 
	 * @param programID A String containing a unique ID for the program. Servers will reject clients with different programIDs.
	 * @param maxPerServer The maximum number of connections a server should accept.
	 * @param nl The listener to be notified of network events.
	 */
	public NetworkManagementPanel (String programID, int maxPerServer, NetworkListener nl) {
		ActionHandler actionEventHandler = new ActionHandler();
		refreshTimer = new Timer(1000, actionEventHandler);
		setLayout(new BorderLayout());
		
		this.programID = programID;
		this.clientProgram = nl;
		this.maxPerServer = maxPerServer;

		JPanel cPanel = new JPanel();
		statusText = new JTextArea();
		statusText.setEditable(false);
		DefaultCaret caret = (DefaultCaret) statusText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane pane = new JScrollPane(statusText);
		pane.setPreferredSize(new Dimension(100,100));
		pane.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		cPanel.setLayout(new BorderLayout());
		cPanel.add(pane, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);

		JPanel cssPanel = new JPanel();
		cssPanel.setLayout(new GridLayout(1,2));
		cPanel.add(cssPanel,BorderLayout.CENTER);

		JPanel cnPanel = new JPanel();
		cnPanel.setLayout(new BorderLayout());
		hostList = new JList<InetAddress>();
		cnPanel.add(hostList,BorderLayout.CENTER);
		JLabel ah = new JLabel("Available Hosts");
		ah.setHorizontalAlignment(JLabel.CENTER);
		cnPanel.add(ah,BorderLayout.NORTH);
		cnPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		cssPanel.add(cnPanel);

		JPanel csPanel = new JPanel();
		csPanel.setLayout(new BorderLayout());
		connectedList = new JList<InetAddress>();
		csPanel.add(connectedList,BorderLayout.CENTER);
		JLabel ch = new JLabel("Connected Hosts");
		ch.setHorizontalAlignment(JLabel.CENTER);
		csPanel.add(ch,BorderLayout.NORTH);
		csPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		cssPanel.add(csPanel);
		

		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(2,1));

		JPanel ePanel = new JPanel();
		ePanel.setLayout(new GridLayout(1,5,15,15));
		discoverButton = new JButton("<html><center>Discover<br>Servers</center></html>");
		discoverButton.addActionListener(actionEventHandler);
		connectButton = new JButton("<html><center>Connect<br>to Selected</center></html>");
		connectButton.addActionListener(actionEventHandler);
		disconnectButton = new JButton("<html><center>Disconnect<br>All</center></html>");
		disconnectButton.addActionListener(actionEventHandler);
		connectCustomButton = new JButton("<html><center>Connect<br>to Custom IP</center></html>");
		connectCustomButton.addActionListener(actionEventHandler);
		serverButton = new JButton("<html><center>Start<br>Server</center></html>");
		serverButton.addActionListener(actionEventHandler);
		
		ePanel.add(discoverButton);
		ePanel.add(connectButton);
		ePanel.add(connectCustomButton);
		ePanel.add(disconnectButton);
		ePanel.add(serverButton);

		discoveryProcess = new JProgressBar();
		bPanel.add(ePanel);
		bPanel.add(discoveryProcess);

		cPanel.add(bPanel,BorderLayout.SOUTH);


		try {
			myIP = InetAddress.getLocalHost();
			statusText.append("Your Hostname/IP address is " + myIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			statusText.append("Error getting your IP address!");
		}

		try {
			discover = new PeerDiscovery(InetAddress.getByName("255.255.255.255"),BROADCAST_PORT);
			statusText.append("\nBroadcast discovery server running on " + BROADCAST_PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
			statusText.append("\nError starting broadcast discovery server on port " + BROADCAST_PORT + "\nCannot discover or be discovered.");
			discoverButton.setEnabled(false);
		}

		JFrame window = new JFrame("Network Management");
		window.setBounds(300, 300, 700, 480);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setVisible(true);
	}
	
	
	/**
	 * Sets the maximum number of connections allowed per server. Only affects servers started after calling this method.
	 * 
	 * @param max The maximum number of clients a server will accept.
	 */
	public void setMaxPerServer(int max) {
		this.maxPerServer = max;
	}
	

	
	private void setButtons(boolean x) {
		connectButton.setEnabled(x);
		connectCustomButton.setEnabled(x);
		serverButton.setEnabled(x);
	}
	
	private void disconnect() {
		if (sc != null) {
			sc.disconnect();
			sc = null;
		}
		setButtons(true);
	}
	
	private void connect(String host) {
		try {
			connect(InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void connect(InetAddress host) {
		if (host != null) {
			disconnect();
			sc = new SchoolClient(programID, myIP);
			boolean success = sc.connect(host,TCP_PORT);
			if (!success) {
				statusText.append("\nCould not connect to "+host+" on " + TCP_PORT);
				sc.disconnect();
				sc = null;
			} else {
				statusText.append("\nConnected to "+host+" on " + TCP_PORT);
				sc.addNetworkListener(clientProgram);
				sc.addNetworkListener(new NetworkMessageHandler());
				clientProgram.connectedToServer(sc);
				setButtons(false);
			}
		}
	}


	private class NetworkMessageHandler implements NetworkListener {
		@Override
		public void networkMessageReceived(NetworkDataObject ndo) {

			if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				statusText.append("\nClient list updated.");
				connectedList.setListData(Arrays.copyOf(ndo.message, ndo.message.length, InetAddress[].class));
			} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
				statusText.append("\nDisconnected from " + ndo.dataSource);
				connectedList.setListData(new InetAddress[]{});
			}

		}

		@Override
		public void connectedToServer(NetworkMessenger nm) {
			// TODO Auto-generated method stub
		}
	}
	

	private class ActionHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == discoverButton) {
				try {
					statusText.append("\nSending broadcast packet...");
					discover.sendDiscoveryPacket();
					timeOut = DISCOVERY_TIMEOUT;
					refreshTimer.restart();
				} catch (IOException e1) {
					statusText.append("\nError sending discovery packet.");
					e1.printStackTrace();
				} 
			} else if (source == connectButton) {
				InetAddress host = hostList.getSelectedValue();
				connect(host);
			} else if (source == connectCustomButton) {
				String host = JOptionPane.showInputDialog("What IP?");
				connect(host);
			} else if (source == disconnectButton) {
				disconnect();
				if (ss != null) {
					ss.disconnectFromAllClients();
					ss.shutdownServer();
					ss = null;

				}
			} else if (source == serverButton) {
				ss = new SchoolServer(programID, myIP);
				serverIP = myIP.getHostAddress();
				if(clientProgram instanceof NetworkedGameBoard) {
					NetworkedGameBoard cl = (NetworkedGameBoard)clientProgram;
					cl.setServerIP(serverIP);
				}
				ss.setMaxConnections(maxPerServer);
				ss.waitForConnections(TCP_PORT);
				statusText.append("\nTCP server running on " + TCP_PORT);
				if (discover != null)
					discover.setDiscoverable(true);
				connect(myIP);
			} else if (source == refreshTimer) {
				timeOut--;
				discoveryProcess.setValue((int)((double)(DISCOVERY_TIMEOUT-timeOut)/DISCOVERY_TIMEOUT*100));
				if (timeOut <= 0) {
					refreshTimer.stop();
					statusText.append("\nFinished discovery.");
				}
				hostList.setListData(discover.getPeers());
			}

		}
	}

}