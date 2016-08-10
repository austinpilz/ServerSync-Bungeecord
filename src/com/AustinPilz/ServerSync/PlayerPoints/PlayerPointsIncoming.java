package com.AustinPilz.ServerSync.PlayerPoints;

import java.io.DataInputStream;
import java.io.IOException;

import jline.internal.Log;

import com.AustinPilz.ServerSync.ServerSync;

public class PlayerPointsIncoming 
{
	public void incoming(DataInputStream in)
	{
	        
		//Player Points Subchannel
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
    		Log.error("Player Points - error while attempting to read in message fields");
    	}
    	
    	if (ServerSync.pluginVersion.equalsIgnoreCase(clientVersion))
    	{
        	if (operation.equals("Balance")) //Server is sending us balance information
        	{
        		String playerName = ""; //UUID
        		String playerBalance = "";
        		
        		try
        		{
	        		playerName = in.readUTF();
	        		playerBalance = in.readUTF();
        		}
        		catch (IOException exception)
            	{
            		Log.error("Player Points - error while attempting to read in balance message fields");
            	}
	        		
        		if (!playerName.isEmpty() && !playerBalance.isEmpty())
        		{
        			//Relay information to all servers
        			ServerSync.playerPoints.relayBalance(playerName, playerBalance, serverName);
        			
        			if (ServerSync.verbose)
        			{
        				Log.info(ServerSync.consolePrefix + "Player Points - relaying balance for player ("+playerName+") at $"+playerBalance+" from server " + serverName);
        			}
        		}
        		else
        		{
        			Log.error(ServerSync.consolePrefix + "Player Points - Error: Corrupt Data - Received balance information for player " + playerName + " with balance of $" + playerBalance);
        		}
        	}
        	else
        	{
        		Log.error(ServerSync.consolePrefix + "Player Points - Unknown operation requested ("+operation+")");
        	}
    	}
    	else
    	{
    		//Message received is from a client whos version is different than the hub's
    		ServerSync.playerPoints.outgoing.sendVersionMismatch(serverName);
    		Log.error(ServerSync.consolePrefix + "Player Points - messaged received from client ("+serverName+"). Hub Version " + ServerSync.pluginVersion + " | Client Version " + clientVersion);
    	}
	}

}
