package com.AustinPilz.ServerSync.Components;

import com.AustinPilz.ServerSync.ServerSync;

import java.util.logging.Level;

public class ServerSyncServer 
{
	private String name; //server name
	private Queue<BungeeMessage> queue; //Outgoing message queue
	
	public ServerSyncServer(String n)
	{
		name = n;
		queue = new Queue<BungeeMessage>();
	}
	
	/**
	 * Add outgoing message to server queue
	 * @param message
	 */
	public void enqueueMessage(BungeeMessage message)
	{
		queue.enqueue(message);
	}
	
	/**
	 * Server check in services
	 */
	public void checkin()
	{
		if (ServerSync.verbose)
		{
			ServerSync.logger.log(Level.INFO,ServerSync.consolePrefix + "Server [" + getServerName() + "] checkin");
		}
		
		processQueue();
	}
	
	/**
	 * Processes outgoing message queue
	 */
	private void processQueue()
	{
		int count = 0;
		while (!queue.isEmpty())
		{
			BungeeMessage tmp = queue.dequeue();
			ServerSync.instance.getProxy().getServerInfo(getServerName()).sendData(tmp.getChannel(), tmp.getData().toByteArray());
			count++;
		}
		
		if (ServerSync.verbose && count > 0)
		{
			ServerSync.logger.log(Level.INFO,ServerSync.consolePrefix + "Server [" + getServerName() + "] - sent " + count + " outgoing message(s)");
		}
	}
	
	/**
	 * Returns the bungee server name
	 * @return
	 */
	public String getServerName()
	{
		return name;
	}

}
