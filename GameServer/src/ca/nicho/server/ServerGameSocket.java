package ca.nicho.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import ca.nicho.client.entity.Entity;
import ca.nicho.client.entity.EntityPlayer;
import ca.nicho.client.packet.ConnectPacket;
import ca.nicho.client.packet.EntityPacket;
import ca.nicho.client.packet.Packet;
import ca.nicho.client.packet.TilePacket;
import ca.nicho.client.tile.Tile;

public class ServerGameSocket implements Runnable{

	public boolean ready = false;
	public Socket socket;
	public DataInputStream in;
	public DataOutputStream out;
	
	public ServerGameSocket(Socket socket){
		this.socket = socket;
		try {
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			//Remove from list of connections
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		int id = Game.world.entId++;
		System.out.println("Player joined - assigning entity ID " + id);
		EntityPlayer player = new EntityPlayer((int)(Math.random() * 800), 200, id);
		Game.world.spawnEntity(player); //Spawn the user into the world
		this.sendIntitalWorldState(); //Send the world
		this.sendPacket(new EntityPacket(player)); //Send the player entity
		this.sendInitialEntities(); //Send the remaining entities
		this.sendPacket(new ConnectPacket(id)); //Send final connection packet
		while(true){
			try{
				readPacket();
			}catch(IOException e){
				System.out.println("ServerGameSocket: Client disconnected");
				if(ServerStart.con1 == this)
					ServerStart.con1 = null;
				else if(ServerStart.con2 == this)
					ServerStart.con2 = null;
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
				this.ready = true;
				break;
		}
	}	
	
	public void sendIntitalWorldState(){
		for(int pos = 0; pos < Game.world.map.length; pos++){
			Tile t = Game.world.map[pos];
			if(t != null){
				sendPacket(new TilePacket(pos, t));
			}
		}
	}
	
	public void sendInitialEntities(){
		for(Map.Entry<Integer, Entity> ent : Game.world.entities.entrySet()){
			sendPacket(new EntityPacket(ent.getValue()));
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
