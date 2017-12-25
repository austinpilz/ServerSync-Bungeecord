package com.AustinPilz.ServerSync.Vault;

public class Vault
{
	
	private boolean syncStarted;
	private boolean syncFinished;
	protected VaultOutgoing outgoing;
	public static VaultIncoming incoming;
	
	public final String vaultSubchannel = "Vault";
	public final String economySubchannel = "Economy";
	public final String permissionSubchannel = "Permission";
	
	public Vault()
	{
		syncStarted = false;
		syncFinished = true;
		outgoing = new VaultOutgoing();
		incoming = new VaultIncoming();
	}
	
	
	public boolean syncStarted()
	{
		return syncStarted;
	}
	
	public boolean syncFinished()
	{
		return syncFinished;
	}

	/**
	 * Initiates a balance request for all online players for the provided server name
	 * @param - serverName Name of BUNGEECORD server
	 */
	private void requestBalances(String serverName)
	{
		outgoing.sendVaultBalanceRequest(serverName);
	}
	
	/**
	 * Initiates a group request for all online players for the provided server name
	 * @param - serverName Name of BUNGEECORD server
	 */
	private void requestGroups(String serverName)
	{
		outgoing.sendPlayerGroupRequest(serverName);
	}
	
	/**
	 * Relays balance update to all servers
	 * @param playerName
	 * @param playerBalance
	 * @param serverName
	 */
	public void relayBalance(String playerName, String playerBalance, String serverName)
	{
		outgoing.sendVaultBalance(playerName, playerBalance, serverName);
	}
	
	/**
	 * Relays group update to all servers
	 * @param UUID
	 * @param world
	 * @param groups
	 */
	public void relayGroups(String UUID, String world, String groups, String serverName)
	{
		outgoing.sendPermissionGroups(UUID, world, groups, serverName);
	}



}
