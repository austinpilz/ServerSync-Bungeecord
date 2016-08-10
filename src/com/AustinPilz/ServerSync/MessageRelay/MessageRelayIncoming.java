package com.AustinPilz.ServerSync.MessageRelay;

import java.io.DataInputStream;
import java.io.IOException;

import com.AustinPilz.ServerSync.ServerSync;

import jline.internal.Log;

public class MessageRelayIncoming 
{
	public void incoming(DataInputStream in)
	{
	        
		//Vault Subchannel
    	//Format: Channel->SubChannel->Operation->ServerName->Client Version->Player UUID->Command

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
    		Log.error(ServerSync.consolePrefix + "Message Relay - error while attempting to read in message fields");
    	}
    	
    	if (ServerSync.pluginVersion.equalsIgnoreCase(clientVersion))
    	{
    		if (operation.equalsIgnoreCase("CommandRelay"))
    		{
    			String playerUUID = "";
        		String command = "";
        		
        		try
        		{
	        		playerUUID = in.readUTF();
	        		command = in.readUTF();
        		}
        		catch (IOException exception)
            	{
            		Log.error("MessageRelay - CommandRelay - error while attempting to read in UUID and command");
            	}
        		
        		if (!command.isEmpty())
        		{
        			ServerSync.messageRelay.outgoing.sendCommandRelay(playerUUID, command, serverName);
        		}
        		else
        		{
        			Log.info(ServerSync.consolePrefix + "MessageRelay - Command Relay - Empty command received, ignoring");
        		}
    		}
    		else
    		{
    			//Unknown subchannel
    			Log.error(ServerSync.consolePrefix + "Message Relay - Unknown operation ("+operation+") received from " + serverName);
    		}
    	}
    	else
    	{
    		//Message received is from a client whos version is different than the hub's
    		ServerSync.messageRelay.outgoing.sendVersionMismatch(serverName);
    		Log.error(ServerSync.consolePrefix + "Vault - messaged received from client ("+serverName+"). Hub Version " + ServerSync.pluginVersion + " | Client Version " + clientVersion);
    	}
	}

}
