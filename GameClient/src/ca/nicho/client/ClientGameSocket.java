package ca.nicho.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ca.nicho.client.packet.ConnectPacket;
import ca.nicho.client.packet.EntityPacket;
import ca.nicho.client.packet.KillEntityPacket;
import ca.nicho.client.packet.Packet;
import ca.nicho.client.packet.PointPacket;
import ca.nicho.client.packet.TilePacket;
import ca.nicho.client.tile.Tile;

public class ClientGameSocket implements Runnable {

	public DataInputStream in;
	public DataOutputStream out;
	
	private ClientStart game;
	
	public ClientGameSocket(ClientStart game){
		this.game = game;
		try {
			Socket socket = new Socket(ClientStart.HOST, ClientStart.PORT);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			new Thread(this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(true){
			try {
				readPacket();
			} catch (IOException e) {
				System.out.println("ClientGameSocket: Disconnected from server");
				break;
			}
		}
	}
	
	/**
	 * Reads the packet data, and parses it out, instantiating the appropriate packet object
	 */
	public void readPacket() throws IOException{
		int type = in.readInt();
		int length = in.readInt();
		byte[] data = new byte[length];
		in.read(data);
		if(length == 0){
			System.out.println("Corrupt packet received");
			return;
		}
		switch(type){
			case Packet.PACKET_ENTITY:
				Game.world.entityUpdatePacketRecieved(new EntityPacket(data));
				break;	
			case Packet.PACKET_CONNECT:
				ConnectPacket packetCon = new ConnectPacket(data);
				Game.ships = new int[4];
				Game.ships[0] = packetCon.ship1ID;
				Game.ships[1] = packetCon.ship2ID;
				Game.ships[2] = packetCon.ship3ID;
				Game.ships[3] = packetCon.ship4ID;
				System.out.println("Ship IDs: " + packetCon.ship1ID + " " + packetCon.ship2ID + " " + packetCon.ship3ID + " " + packetCon.ship4ID);
				this.sendPacket(new ConnectPacket(0, 0, 0, 0)); //Returning the received ID is not necessary (yet)
				break;
			case Packet.PACKET_TILE:
				TilePacket packetTile = new TilePacket(data);
				Game.world.setTileByPos(packetTile.pos, Tile.getTileByID(packetTile.type));
				break;
			case Packet.PACKET_KILL_ENTITY:
				KillEntityPacket packetKill = new KillEntityPacket(data);
				Game.world.killEntity(packetKill.id);
				break;
			case Packet.PACKET_POINTS:
				PointPacket packetPoints = new PointPacket(data);
				System.out.println(packetPoints.points);
				break;
		}
	}	
	
	public synchronized void sendPacket(Packet packet){
		try{
			out.writeInt(packet.packetType);
			byte[] data = packet.getPacketData();
			out.writeInt(data.length);
			out.write(data);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
