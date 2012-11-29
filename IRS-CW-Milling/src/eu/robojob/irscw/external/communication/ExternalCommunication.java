package eu.robojob.irscw.external.communication;

import java.io.IOException;

import eu.robojob.irscw.threading.ThreadManager;

public abstract class ExternalCommunication {

	public static final int READ_RETRY_INTERVAL = 50;
	public static final int DEFAULT_WAIT_TIMEOUT = 10000;
	
	private int defaultWaitTimeout;
	
	protected ExternalCommunicationThread extCommThread;
		
	public ExternalCommunication(SocketConnection socketConnection) {
		this.extCommThread = new ExternalCommunicationThread(socketConnection, this);
		ThreadManager.getInstance().submit(extCommThread);
		defaultWaitTimeout = DEFAULT_WAIT_TIMEOUT;
	}
	
	public boolean isConnected() {
		return extCommThread.isConnected();
	}
	
	public synchronized void disconnect() {
		extCommThread.justDisconnect();
	}
	
	public synchronized boolean hasMessage() {
		return extCommThread.hasNextMessage();
	}
	
	public synchronized String getNextMessage() {
		return extCommThread.getNextMessage();
	}
	
	public synchronized void writeMessage(String message) throws DisconnectedException {
		extCommThread.writeMessage(message);
	}
	
	public void setDefaultWaitTimeout(int defaultWaitTimeout) {
		this.defaultWaitTimeout = defaultWaitTimeout;
	}
	
	public int getDefaultWaitTimeout() {
		return defaultWaitTimeout;
	}
	
	public abstract void connected();
	public abstract void disconnected();
	public abstract void iOExceptionOccured(IOException e);
	
}
