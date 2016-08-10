package com.AustinPilz.ServerSync.MessageRelay;

import java.io.DataInputStream;
import java.io.IOException;

import com.AustinPilz.ServerSync.ServerSync;

import jline.internal.Log;

public class MessageRelay 
{
	public static MessageRelayIncoming incoming;
	protected static MessageRelayOutgoing outgoing;
	public static final String messageSubchannel = "MessageRelay";
	
	public MessageRelay()
	{
		incoming = new MessageRelayIncoming();
		outgoing = new MessageRelayOutgoing();
	}
}
