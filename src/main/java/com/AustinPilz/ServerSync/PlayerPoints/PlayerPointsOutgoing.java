package com.AustinPilz.ServerSync.PlayerPoints;

import com.AustinPilz.ServerSync.Components.ServerSyncServer;
import com.AustinPilz.ServerSync.ServerSync;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

public class PlayerPointsOutgoing 
{
	/**
	 * Sends balance request message to supplied server
	 * @param serverName Bungee server name
	 */
	protected void sendBalanceRequest(String serverName)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(stream);
	    
	    try
	    {
		    out.writeUTF(ServerSync.playerPointsSubchannel); //Subchannel
		    out.writeUTF("BalanceRequest"); //Operation
	    }
	    catch (IOException exception)
	    {
			ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Player Points - Error while attempting to compose balance request stream");
	    }
	    
	    //Send the request
	    ServerSync.bungeeOutgoing.sendMessage(stream, serverName);
	}
	
	/**
	 * Sends player points balance update to all servers except originating server
	 * @param UUID UUID of player 
	 * @param balance Balance to be sent as update
	 * @param originalServer Original server Bungee name
	 */
	protected void sendBalanceUpdate(String UUID, String balance, String originalServer)
	{
		Iterator it = ServerSync.servers.entrySet().iterator();
		while (it.hasNext()) //For each registered server
		{
			 Map.Entry entry = (Map.Entry) it.next();
			 ServerSyncServer server = (ServerSyncServer)entry.getValue();
			
			if (!server.getServerName().equals(originalServer)) //We don't want to send the update back to the original server
			{
				
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
			    DataOutputStream out = new DataOutputStream(stream);
			    
			    try
			    {
				    out.writeUTF(ServerSync.playerPointsSubchannel); //Subchannel
				    out.writeUTF("Balance"); //Operation
				    out.writeUTF(UUID); //Player UUID
				    out.writeUTF(balance); //Balance
			    }
			    catch (IOException exception)
			    {
					ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Player Points - Error while attempting to compose balance update stream");
			    }
				
				ServerSync.bungeeOutgoing.sendMessage(stream, server.getServerName());
			}
		}
	}

	/**
	 * Sends version mismatch message for the supplied server on the Player Points subchannel
	 * @param serverName
	 */
	protected void sendVersionMismatch(String serverName)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(stream);
	    
	    try
	    {
		    out.writeUTF(ServerSync.playerPointsSubchannel); //Subchannel
		    out.writeUTF("VersionMismatch"); //Operation
		    out.writeUTF(ServerSync.pluginVersion); //Hub ServerSync Version
	    }
	    catch (IOException exception)
	    {
	    	ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Player Points - Error while attempting to compose version mismatch stream");
	    }
	    
	    //Send the request
	    ServerSync.bungeeOutgoing.sendMessage(stream, serverName);
	}
}
