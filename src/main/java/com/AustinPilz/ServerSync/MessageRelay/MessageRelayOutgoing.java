package com.AustinPilz.ServerSync.MessageRelay;

import com.AustinPilz.ServerSync.Components.ServerSyncServer;
import com.AustinPilz.ServerSync.ServerSync;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

public class MessageRelayOutgoing 
{
	/**
	 * Send version mismatch
	 * @param serverName
	 */
	protected void sendVersionMismatch(String serverName)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(stream);
	    
	    try
	    {
		    out.writeUTF(ServerSync.messageRelay.messageSubchannel); //Subchannel
		    out.writeUTF("VersionMismatch"); //Operation
		    out.writeUTF(ServerSync.pluginVersion); //Hub ServerSync Version
		    
		  //Send the request
		    ServerSync.bungeeOutgoing.sendMessage(stream, serverName);
	    }
	    catch (IOException exception)
	    {
			ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "MessageRelay - Error while attempting to compose version mismatch stream");
	    }
	    
	    
	}

	/**
	 * Sends command to all servers
	 */
	protected void sendCommandRelay(String playerUUID, String command, String originalServer)
	{
		Iterator it = ServerSync.servers.entrySet().iterator();
		while (it.hasNext()) 
		{
		    Map.Entry entry = (Map.Entry) it.next();
		    ServerSyncServer server = (ServerSyncServer)entry.getValue();
			
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
			    DataOutputStream out = new DataOutputStream(stream);
			    
			    try
			    {
			    	out.writeUTF(ServerSync.messageRelay.messageSubchannel); //Subchannel
				    out.writeUTF("CommandRelay"); //Operation
				    out.writeUTF(playerUUID); //Player
				    out.writeUTF(command); //Command
			    }
			    catch (IOException exception)
			    {
					ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "MessageRelay - Command Relay - Error while attempting to compose relay message");
			    }
				
				ServerSync.bungeeOutgoing.sendMessage(stream, server.getServerName());
		}
	}

}
