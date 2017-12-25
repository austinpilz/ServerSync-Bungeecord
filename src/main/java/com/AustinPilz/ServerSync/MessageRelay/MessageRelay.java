package com.AustinPilz.ServerSync.MessageRelay;

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
