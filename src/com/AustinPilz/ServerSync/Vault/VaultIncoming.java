package com.AustinPilz.ServerSync.Vault;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import jline.internal.Log;

import com.AustinPilz.ServerSync.ServerSync;

public class VaultIncoming 
{
	public void incoming(DataInputStream in)
	{
	        
		//Vault Subchannel
    	//Format: Channel->Subchannel->Vault SubSubChannel->Operation->ServerName->Client Version->Player->Amount

		String vaultSubchannel = "";
    	String clientVersion = "";
    	String operation = "";
    	String serverName = "";
    	
    	try
    	{
    		vaultSubchannel = in.readUTF();
        	operation = in.readUTF();
        	serverName = in.readUTF();
        	clientVersion = in.readUTF();
    	}
    	catch (IOException exception)
    	{
    		Log.error("Vault - error while attempting to read in message fields");
    	}
    	
    	if (ServerSync.pluginVersion.equalsIgnoreCase(clientVersion))
    	{
    		if (vaultSubchannel.equals(ServerSync.vault.economySubchannel))
    		{
    			//Economy
    			if (ServerSync.verbose)
    			{
    				Log.info(ServerSync.consolePrefix + "Vault - received economy message from " + serverName);
    			}
    			
	        	if (operation.equals("Balance")) //Server is sending us balance information
	        	{
	        		String playerName = "";
	        		String playerBalance = "";
	        		
	        		try
	        		{
		        		playerName = in.readUTF();
		        		playerBalance = in.readUTF();
	        		}
	        		catch (IOException exception)
	            	{
	            		Log.error("Vault - error while attempting to read in balance message fields");
	            	}
		        		
	        		if (!playerName.isEmpty() && !playerBalance.isEmpty())
	        		{
	        			//Relay information to all servers
	        			ServerSync.vault.relayBalance(playerName, playerBalance, serverName);
	        			
	        			if (ServerSync.verbose)
	        			{
	        				Log.info(ServerSync.consolePrefix + "Vault - relaying balance for player ("+playerName+") at $"+playerBalance+" from server " + serverName);
	        			}
	        		}
	        		else
	        		{
	        			Log.error(ServerSync.consolePrefix + "Error: Corrupt Data - Received balance information for player " + playerName + " with balance of $" + playerBalance);
	        		}
	        	}
	        	else
	        	{
	        		Log.info(ServerSync.consolePrefix + "Unknown operation requested ("+operation+")");
	        	}
    		}
    		else if (vaultSubchannel.equals(ServerSync.vault.permissionSubchannel))
    		{
    			//Permission 
    			if (operation.equals("PlayerGroups")) //Server is sending us balance information
	        	{
    				//UUID -> World -> Groups[]
    				String UUID = "";
    				String world = "";
    				String groups = "";
    				
    				try
	        		{
		        		UUID = in.readUTF();
		        		world = in.readUTF();
		        		groups = in.readUTF();
	        		}
	        		catch (IOException exception)
	            	{
	            		Log.error("Vault - error while attempting to read in permission group message fields");
	            	}
    				
    				if (!UUID.isEmpty())
    				{
    					//Relay
    					ServerSync.vault.relayGroups(UUID, world, groups, serverName);
    					
    					if (ServerSync.verbose)
	        			{
	        				Log.info(ServerSync.consolePrefix + "Vault - relaying groups for ("+UUID+") from server " + serverName);
	        			}
    				}
    				else
    				{
    					//Transmission corrupt
    					Log.error(ServerSync.consolePrefix + "Error: Corrupt Data - Received blank UUID when attempting to relay permission information");
	        		}
	        	}
    			else
    			{
    				//Unknown operation
    				Log.error(ServerSync.consolePrefix + "Vault - Unknown permission operation ("+operation+") received from " + serverName);
        		}
    		}
    		else
    		{
    			//Unknown vault subchannel
    			Log.error(ServerSync.consolePrefix + "Vault - Unknown vault subchannel ("+vaultSubchannel+") received from " + serverName);
    		}
    	}
    	else
    	{
    		//Message received is from a client whos version is different than the hub's
    		ServerSync.vault.outgoing.sendVersionMismatch(serverName);
    		Log.error(ServerSync.consolePrefix + "Vault - messaged received from client ("+serverName+"). Hub Version " + ServerSync.pluginVersion + " | Client Version " + clientVersion);
    	}
	}

}
