package ca.nicho.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import ca.nicho.client.entity.EntityPlayer;
import ca.nicho.client.packet.ConnectPacket;
import ca.nicho.client.packet.EntityPacket;
import ca.nicho.client.packet.Packet;
import ca.nicho.client.packet.TilePacket;
import ca.nicho.client.tile.Tile;

public class ClientGameSocket implements Runnable {

	public DataInputStream in;
	public DataOutputStream out;
	
	private ClientStart game;
	
	public ClientGameSocket(ClientStart game){
		this.game = game;
		try {
			Socket socket = new Socket("localhost", 1024);
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
			
		switch(type){
			case Packet.PACKET_ENTITY:
				Game.world.entityUpdatePacketRecieved(new EntityPacket(data));
				break;	
			case Packet.PACKET_CONNECT:
				ConnectPacket packetCon = new ConnectPacket(data);
				this.sendPacket(new ConnectPacket(packetCon.id)); //Returning the received ID is not necessary (yet)
				//System.out.println("Received Player Entity ID: " + Game.playerID);
				break;
			case Packet.PACKET_TILE:
				TilePacket packetTile = new TilePacket(data);
				Game.world.setTileByPos(packetTile.pos, Tile.getTileByID(packetTile.type));
				break;
				
		}
	}	
	
	public void sendPacket(Packet packet){
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
