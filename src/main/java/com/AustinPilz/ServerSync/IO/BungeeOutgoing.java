package com.AustinPilz.ServerSync.IO;

import com.AustinPilz.ServerSync.Components.BungeeMessage;
import com.AustinPilz.ServerSync.Components.Queue;
import com.AustinPilz.ServerSync.ServerSync;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;

public class BungeeOutgoing 
{
	
	private Queue<BungeeMessage> queue;
	
	public BungeeOutgoing()
	{
		queue = new Queue<BungeeMessage>();
	}
	
	
	/**
	 * Sends message across channel to specified server
	 * @param steam
	 * @param serverName BUNGEECORD name of server to send message to
	 */
	public void sendMessage(ByteArrayOutputStream stream, String serverName)
	{
	    //ServerSync.instance.getProxy().getServerInfo(serverName).sendData(ServerSync.bungeeChannel, stream.toByteArray());
		addToQueue(serverName, stream);
	}
	
	/**
	 * Add message to queue
	 * @param out
	 * @param serverName
	 */
	private void addToQueue(String serverName, ByteArrayOutputStream stream)
	{
		if (ServerSync.servers.containsKey(serverName))
		{
			ServerSync.servers.get(serverName).enqueueMessage(new BungeeMessage(stream, ServerSync.bungeeChannel));
			ServerSync.logger.log(Level.INFO,ServerSync.consolePrefix + "Added message to server ["+serverName+"] queue");
		}
		else
		{
			ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Error adding message to server ["+serverName+"] queue, server does not exist");
		}
	}
}
