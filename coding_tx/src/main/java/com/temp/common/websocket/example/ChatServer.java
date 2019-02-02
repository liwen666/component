package com.temp.common.websocket.example;/*
 * Copyright (c) 2010-2019 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

import com.alibaba.fastjson.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class ChatServer extends WebSocketServer {
	public static Map<String ,WebSocket> clientSocket = new HashMap<String,WebSocket>();

	public ChatServer( int port ) throws UnknownHostException {
		super( new InetSocketAddress( port ) );
	}

	public ChatServer( InetSocketAddress address ) {
		super( address );
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake ) {
		conn.send("Welcome to the server!"); //This method sends a message to the new client
		String resourceDescriptor = "";
		try {
			 resourceDescriptor=URLDecoder.decode(handshake.getResourceDescriptor(),"UTF-8");
			broadcast( "new connection: " + resourceDescriptor); //This method sends a message to all clients connected

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] split = resourceDescriptor.split("\\?");
		if(split.length>1){
			String jsonParam=split[1];
			JSONObject jsonObject = JSONObject.parseObject(jsonParam);
			clientSocket.put((String) jsonObject.get("name"),conn);

			System.out.println(jsonObject.get("name"));

		}
		for(Map.Entry me:clientSocket.entrySet()){
			System.out.println("连接有：  "+me.getKey());
		}
		System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote ) {
		String resourceDescriptor = "";
		try {
			resourceDescriptor=URLDecoder.decode(conn.getResourceDescriptor(),"UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] split = resourceDescriptor.split("\\?");
		if(split.length>1){
			String jsonParam=split[1];
			JSONObject jsonObject = JSONObject.parseObject(jsonParam);
			clientSocket.remove(jsonObject.get("name"));
			System.out.println(jsonObject.get("name"));

		}
		broadcast( conn + " has left the room!" );
		System.out.println( conn + " has left the room!" );

	}

	@Override
	public void onMessage(WebSocket conn, String message ) {
		for(Map.Entry me:clientSocket.entrySet()){
			System.out.println("连接有：  "+me.getKey());
		}
		broadcast( message+"后台返回" );
		System.out.println( conn + ": " + message );
		broadcast("定向发送", new ArrayList<WebSocket>(){{add(conn);}});
	}
	@Override
	public void onMessage(WebSocket conn, ByteBuffer message ) {
		broadcast( message.array() );
		System.out.println( conn + ": " + message );
	}


	public static void main( String[] args ) throws InterruptedException , IOException {
		int port = 8887; // 843 flash policy port
		try {
			port = Integer.parseInt( args[ 0 ] );
		} catch ( Exception ex ) {
		}
		ChatServer s = new ChatServer( port );
		s.start();
		System.out.println( "ChatServer started on port: " + s.getPort() );

		BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
		while ( true ) {
			String in = sysin.readLine();
			s.broadcast( in );
			if( in.equals( "exit" ) ) {
				s.stop(1000);
				break;
			}
		}
	}
	@Override
	public void onError(WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	@Override
	public void onStart() {
		System.out.println("Server started!");
		setConnectionLostTimeout(0);
		setConnectionLostTimeout(100);
	}

}
