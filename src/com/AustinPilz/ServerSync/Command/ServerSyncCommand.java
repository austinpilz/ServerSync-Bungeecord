package com.AustinPilz.ServerSync.Command;

import com.AustinPilz.ServerSync.ServerSync;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ServerSyncCommand extends Command
{
	public ServerSyncCommand()
	{
		super("ServerSyncBungee");
	}
	
	@Override
	public void execute(CommandSender commandSender, String[] args) 
	{	
		if (args.length > 0)
		{
			if (args[0].equalsIgnoreCase("settings"))
			{
				if (args.length == 2)
				{
					if (args[1].equalsIgnoreCase("Verbose"))
					{
						if (ServerSync.verbose)
						{
							//Verbose now disabled
							ServerSync.verbose = false;
							
							TextComponent message = new TextComponent(ServerSync.chatPrefix);
							message.setColor(ChatColor.GREEN);
							message.addExtra("Verbose logging now disabled");
							commandSender.sendMessage(message);
						}
						else
						{
							//Verbose now enabled
							ServerSync.verbose = true;
							
							TextComponent message = new TextComponent(ServerSync.chatPrefix);
							message.setColor(ChatColor.GREEN);
							message.addExtra("Verbose logging now enabled");
							commandSender.sendMessage(message);
						}
					}
					else
					{
						//Unknown Commands
						sendUnknownCommand(commandSender);
					}
				}
				else
				{
					//Unknown Commands
					sendUnknownCommand(commandSender);
				}
			}
			else
			{
				//Unknown Commands
				sendUnknownCommand(commandSender);
			}
		}
		else
		{
			//ServerSync Information
			TextComponent message = new TextComponent(ServerSync.chatPrefix + ServerSync.pluginName + " v" + ServerSync.pluginVersion);
			message.setColor(ChatColor.GREEN);
			commandSender.sendMessage(message);
			
			TextComponent message2 = new TextComponent( "ServerSync Bungeecord Website" );
			message2.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/serversync-bungeecord.14904/" ) );
			message2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Goto ServerSync Bungeecord Website!").create() ) );
			commandSender.sendMessage( message2 );
		}
	}
	
	private void sendUnknownCommand(CommandSender commandSender)
	{
		//Unknown Command
		TextComponent message = new TextComponent(ServerSync.chatPrefix);
		message.setColor(ChatColor.GREEN);
		message.addExtra("Unknown Command!");
		commandSender.sendMessage(message);
	}

}
