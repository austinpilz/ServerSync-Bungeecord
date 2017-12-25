package com.AustinPilz.ServerSync.PlayerPoints;

import java.util.Iterator;

import com.AustinPilz.ServerSync.ServerSync;

public class PlayerPoints 
{
	private boolean syncStarted;
	protected PlayerPointsOutgoing outgoing;
	public static PlayerPointsIncoming incoming;
	
	/** 
	 * Constructor
	 */
	public PlayerPoints()
	{
		syncStarted = false;
		outgoing = new PlayerPointsOutgoing();
		incoming = new PlayerPointsIncoming();
	}
	
	/**
	 * Returns boolean if sync is in progress
	 * @return
	 */
	public boolean syncStarted()
	{
		return syncStarted;
	}

	
	/**
	 * Sends update request to server
	 * @param serverName Bungee server name
	 */
	private synchronized void requestBalances(String serverName)
	{
		outgoing.sendBalanceRequest(serverName); //Send request
	}
	
	/**
	 * Relays balance update to all servers
	 * @param playerName
	 * @param playerBalance
	 * @param serverName
	 */
	public void relayBalance(String playerName, String playerBalance, String serverName)
	{
		outgoing.sendBalanceUpdate(playerName, playerBalance, serverName);
	}
}
