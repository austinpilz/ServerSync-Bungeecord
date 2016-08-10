package com.AustinPilz.ServerSync.Vault;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import jline.internal.Log;

import com.AustinPilz.ServerSync.ServerSync;
import com.AustinPilz.ServerSync.Components.ServerSyncServer;

public class VaultOutgoing 
{
	/**
	 * Sends version mismatch message to supplied server
	 * @param serverName
	 */
	protected void sendVersionMismatch(String serverName)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(stream);
	    
	    try
	    {
		    out.writeUTF(ServerSync.vaultSubchannel); //Subchannel
		    out.writeUTF("VersionMismatch"); //Operation
		    out.writeUTF(ServerSync.pluginVersion); //Hub ServerSync Version
	    }
	    catch (IOException exception)
	    {
	    	Log.error(ServerSync.consolePrefix + "Error while attempting to compose version mismatch stream");
	    }
	    
	    //Send the request
	    ServerSync.bungeeOutgoing.sendMessage(stream, serverName);
	}
	
	/**
	 * Sends request to server to send vault balances in need of updating
	 * @param serverName
	 */
	protected void sendVaultBalanceRequest(String serverName)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(stream);
	    
	    try
	    {
		    out.writeUTF(ServerSync.vault.vaultSubchannel); //Subchannel
		    out.writeUTF(ServerSync.vault.economySubchannel); //Economy subchannel
		    out.writeUTF("BalanceRequest");
	    }
	    catch (IOException exception)
	    {
	    	Log.error(ServerSync.consolePrefix + "Error while attempting to compose balance request stream");
	    }
	    
	    //Send the request
	    ServerSync.bungeeOutgoing.sendMessage(stream, serverName);
	}
	
	/**
	 * Sends request to specified server to send their online player's groups
	 * @param serverName
	 */
	protected void sendPlayerGroupRequest(String serverName)
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(stream);
	    
	    try
	    {
		    out.writeUTF(ServerSync.vault.vaultSubchannel); //Subchannel
		    out.writeUTF(ServerSync.vault.permissionSubchannel); //Permission subchannel
		    out.writeUTF("GroupsRequest");
	    }
	    catch (IOException exception)
	    {
	    	Log.error(ServerSync.consolePrefix + "Error while attempting to compose balance request stream");
	    }
	    
	    //Send the request
	    ServerSync.bungeeOutgoing.sendMessage(stream, serverName);
	}
	
	/**
	 * Sends player balance to all registered servers
	 */
	protected void sendVaultBalance(String playerName, String balance, String originalServer)
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
			    	out.writeUTF(ServerSync.vault.vaultSubchannel); //Subchannel
				    out.writeUTF(ServerSync.vault.economySubchannel); //Economy subchannel
				    out.writeUTF("Balance"); //Operation
				    out.writeUTF(playerName); //Player
				    out.writeUTF(balance); //Balance
			    }
			    catch (IOException exception)
			    {
			    	Log.error(ServerSync.consolePrefix + "Error while attempting to compose balance update stream");
			    }
				
				ServerSync.bungeeOutgoing.sendMessage(stream, server.getServerName());
			}
		}
	}
	
	/**
	 * Sends player group update to all registered servers
	 * @param UUID
	 * @param world
	 * @param groups
	 * @param originalServer
	 */
	protected void sendPermissionGroups(String UUID, String world, String groups, String originalServer)
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
			    	out.writeUTF(ServerSync.vault.vaultSubchannel); //Subchannel
				    out.writeUTF(ServerSync.vault.permissionSubchannel); //Permission subchannel
				    out.writeUTF("PlayerGroups"); //Operation
				    out.writeUTF(UUID); //UUID
				    out.writeUTF(world); //World
				    out.writeUTF(groups); //Groups
			    }
			    catch (IOException exception)
			    {
			    	Log.error(ServerSync.consolePrefix + "Error while attempting to compose group update stream");
			    }
				
				ServerSync.bungeeOutgoing.sendMessage(stream, server.getServerName());
			}
		}
	}

	
	

}
