package ca.nicho.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import ca.nicho.client.Game;
import ca.nicho.client.SpriteSheet;
import ca.nicho.client.entity.Entity;
import ca.nicho.client.packet.Packet;
import ca.nicho.client.tile.Tile;
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
			System.out.println("Starting server on port " + PORT + "...");
			ServerSocket socket = new ServerSocket(PORT);
			((ServerWorld)Game.world).startClock();;
			while(true){
				Socket con = socket.accept();
				ServerGameSocket gameCon = new ServerGameSocket(con);
				new Thread(gameCon).start();
				if(con1 == null)
					con1 = gameCon;
				else if (con2 == null)
					con2 = gameCon;
				else {
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
		if(ServerStart.con1 != null && con1.ready)
			ServerStart.con1.sendPacket(packet);
		if(ServerStart.con2 != null && con2.ready)
			ServerStart.con2.sendPacket(packet);
	}
	
}
