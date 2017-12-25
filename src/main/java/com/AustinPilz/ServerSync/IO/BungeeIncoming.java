package com.AustinPilz.ServerSync.IO;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import com.AustinPilz.ServerSync.ServerSync;
import com.AustinPilz.ServerSync.Components.ServerSyncServer;

import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeIncoming implements Listener
{
	@EventHandler
    public void onPluginMessage(PluginMessageEvent ev) throws IOException 
    {
		//See if incoming message is on our channel
        if (!ev.getTag().equals(ServerSync.bungeeChannel)) 
        {
            return;
        }
   
        //Make sure incoming message is being sent by a server
        if (!(ev.getSender() instanceof Server)) {
            return;
        }
 
        //Get the data
        ByteArrayInputStream stream = new ByteArrayInputStream(ev.getData());
        DataInputStream in = new DataInputStream(stream);
        
        String subChannel = in.readUTF();
        
        //Locate which sub channel we're operating on
        if (subChannel.equals(ServerSync.vaultSubchannel))
        {
        	//Reroute to Vault
        	ServerSync.vault.incoming.incoming(in);
        }
        else if (subChannel.equals(ServerSync.playerPointsSubchannel))
        {
        	//Reroute to Vault
        	ServerSync.playerPoints.incoming.incoming(in);
        }
        else if (subChannel.equals(ServerSync.messageRelay.messageSubchannel))
        {
        	//Reroute to MessageRelay
        	ServerSync.messageRelay.incoming.incoming(in);
        }
        else if (subChannel.equals(ServerSync.checkinSubChannel))
        {
        	//Format: Channel->Subchannel->Operation->ServerName->Client Version->Player->Amount
        	String clientVersion = "";
        	String operation = "";
        	String serverName = "";
        	
        	try
        	{
	        	operation = in.readUTF();
	        	serverName = in.readUTF();
	        	clientVersion = in.readUTF();
        	}
        	catch (IOException exception)
        	{
				ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix+ "Checkin - error while attempting to read in message fields");
        	}
        	
        	if (ServerSync.pluginVersion.equalsIgnoreCase(clientVersion))
        	{
	        	if (operation.equals("Checkin")) //Server is registering itself with the hub
	        	{
	        		if (!serverName.isEmpty())
	        		{
	        			if (!ServerSync.servers.containsKey(serverName))
	        			{
	        				//Add server
	        				ServerSync.servers.put(serverName, new ServerSyncServer(serverName));
	        				ServerSync.servers.get(serverName).checkin();
	        				
	        				if (ServerSync.verbose)
	        				{
	        					ServerSync.logger.info(ServerSync.consolePrefix + "Added new server ["+serverName+"]");
	        				}
	        			}
	        			else
	        			{
	        				//We already have 
	        				ServerSync.servers.get(serverName).checkin();
	        			}
	        		}
	        		else
	        		{
	        			ServerSync.logger.log(Level.WARNING, ServerSync.consolePrefix + "Checkin requested, but no server name was supplied");
	        		}
	        	}
	        	else
	        	{
	        		//Unknown Operation
					ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Unknown operation ("+operation+") supplied for check-in operation");
	        	}
        	}
        	else
        	{
        		//Version Mismatch
				ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Checkin - messaged received from client ("+serverName+"). Hub Version " + ServerSync.pluginVersion + " | Client Version " + clientVersion);
				ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Checkin - messaged received from client ("+serverName+"). Hub Version " + ServerSync.pluginVersion + " | Client Version " + clientVersion);
				ServerSync.logger.log(Level.WARNING,ServerSync.consolePrefix + "Checkin - messaged received from client ("+serverName+"). Hub Version " + ServerSync.pluginVersion + " | Client Version " + clientVersion);
        	}
        }
        else
        {
        	//Unknown subchannel
        	ServerSync.logger.info(ServerSync.consolePrefix + "Received a message on an unknown subchannel ("+subChannel+")");
        }
    }
}
