package com.AustinPilz.ServerSync.IO;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import jline.internal.Log;

import com.AustinPilz.ServerSync.ServerSync;
import com.AustinPilz.ServerSync.Components.BungeeMessage;
import com.AustinPilz.ServerSync.Components.Queue;
import com.google.common.io.ByteArrayDataOutput;

import net.md_5.bungee.api.config.ServerInfo;

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
			Log.info(ServerSync.consolePrefix + "Added message to server ["+serverName+"] queue");
		}
		else
		{
			Log.info(ServerSync.consolePrefix + "Error adding message to server ["+serverName+"] queue, server does not exist");
		}
	}
}
