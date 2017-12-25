package com.AustinPilz.ServerSync.Components;

import java.io.ByteArrayOutputStream;

import com.google.common.io.ByteArrayDataOutput;

public class BungeeMessage 
{
	private ByteArrayOutputStream out;
	private String channel;
	
	public BungeeMessage (ByteArrayOutputStream o, String c)
	{
		out = o;
		channel = c;
	}
	
	/**
	 * Get the data out stream
	 * @return
	 */
	public ByteArrayOutputStream getData()
	{
		return out;
	}
	
	/**
	 * Get channel
	 * @return
	 */
	public String getChannel()
	{
		return channel;
	}

}
