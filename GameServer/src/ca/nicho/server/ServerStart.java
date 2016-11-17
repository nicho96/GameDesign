package ca.nicho.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;


import ca.nicho.foundation.Game;
import ca.nicho.foundation.SpriteSheet;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.packet.Packet;
import ca.nicho.foundation.tile.Tile;
import ca.nicho.server.world.ServerWorld;


public class ServerStart {

	public static int PORT = 1024;
	
	public static ServerGameSocket con1;
	public static ServerGameSocket con2;	
	
	public static void main(String[] s){
		SpriteSheet.initSprites();
		Tile.initTiles();
		Scanner sc = new Scanner(System.in);
		System.out.println("Server - Enter port: ");
		PORT = Integer.parseInt(sc.nextLine());
		sc.close();
		ServerGame.initWorld();
		new ServerStart();
	}
	
	public ServerStart(){
		try {
		//	LogHandler log = getLogInstance();
			System.out.println("Starting server on port " + PORT + "...");
			ServerSocket socket = new ServerSocket(PORT);
			((ServerWorld)Game.world).startClock();
			while(true){
				Socket con = socket.accept();
				if(con1 == null){ //New connection
					ServerGameSocket gameCon = new ServerGameSocket(con, (byte)1);
					new Thread(gameCon).start();
					con1 = gameCon;
					System.out.println("Player 1 Connected");
				} else if(con1.socket == null){ //Reconnect p1
					con1.setSocket(con);
					new Thread(con1).start();
					System.out.println("Player 1 Reconnected");
				} else if (con2 == null){
					ServerGameSocket gameCon = new ServerGameSocket(con, (byte)2);
					new Thread(gameCon).start();
					con2 = gameCon;
					System.out.println("Player 2 Connected");
				}else if (con2.socket == null) { //Reconnect p2
					con2.setSocket(con);
					new Thread(con2).start();
					System.out.println("Player 2 Reconnected");
				}else {
					//Close the connection as there are too many people on the server
					con.close();
					continue; //Prevents execution of any post-connection code for this connection
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send a packet to all clients
	 * Synchronized to be thread safe, not being synchronized will cause packet data to be mixed
	 * and results in packet corruption.
	 * @param packet The Packet to be sent
	 */
	public static synchronized void sendGlobalPacket(Packet packet){
		if(ServerStart.con1 != null && con1.ready && con1.socket != null)
			ServerStart.con1.sendPacket(packet);
		if(ServerStart.con2 != null && con2.ready && con2.socket != null)
			ServerStart.con2.sendPacket(packet);
	}
	
}
