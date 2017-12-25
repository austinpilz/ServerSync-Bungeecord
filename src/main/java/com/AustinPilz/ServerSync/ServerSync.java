package com.AustinPilz.ServerSync;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.AustinPilz.ServerSync.Command.ServerSyncCommand;
import com.AustinPilz.ServerSync.Components.ServerSyncServer;
import com.AustinPilz.ServerSync.IO.BungeeIncoming;
import com.AustinPilz.ServerSync.IO.BungeeOutgoing;
import com.AustinPilz.ServerSync.MessageRelay.MessageRelay;
import com.AustinPilz.ServerSync.PlayerPoints.PlayerPoints;
import com.AustinPilz.ServerSync.Vault.Vault;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class ServerSync extends Plugin
{
	//Bungeecord Channels
	public static final String bungeeChannel = "ServerSync";
	public static final String vaultSubchannel = "Vault";
	public static final String playerPointsSubchannel = "PlayerPoints";
	public static final String checkinSubChannel = "Checkin";
	public static HashMap<String, ServerSyncServer> servers; //Connected servers
	
	//IO
	public static BungeeOutgoing bungeeOutgoing;
	
	//Strings
	public static final String consolePrefix = "[ServerSync Bungee] ";
	public static final String chatPrefix = "[ServerSync Bungee]";
	public static final String pluginName = "ServerSync";
	public static final String pluginVersion = "1.6.3";
	
	
	//Plugins
	public static Vault vault;
	public static PlayerPoints playerPoints;
	public static MessageRelay messageRelay;
	
	//Instance
	public static ServerSync instance;
	public static Logger logger;

	//Etc
	public static boolean verbose;
	
	
	//have list of servers
	//send message to server telling them to go
		//that server sends info back to this server which relays it to all other servers
	
	
	@Override
	public void onEnable() 
	{
		//Instance
		instance = this;
		logger = getLogger();
		verbose = false;
		
		//Bungee Outgoing
		servers = new HashMap<String, ServerSyncServer>();
		bungeeOutgoing = new BungeeOutgoing();
		
		//Plugins
        vault = new Vault();
        playerPoints = new PlayerPoints();
        messageRelay = new MessageRelay();
		
		//Register Bungeecord Channels
        this.getProxy().registerChannel("ServerSync");
        this.getProxy().getPluginManager().registerListener(this, new BungeeIncoming()); //Incoming
        
        //Register Schedule
        //getProxy().getScheduler().schedule(this, new VaultRunnable(), 1, 10, TimeUnit.SECONDS);
        //getProxy().getScheduler().schedule(this, new PlayerPointsRunnable(), 1, 10, TimeUnit.SECONDS);
        
        //Register Commands
        getProxy().getPluginManager().registerCommand(this, new ServerSyncCommand());
        
        
    }    
}
