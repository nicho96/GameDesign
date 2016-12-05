package ca.nicho.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import ca.nicho.foundation.Game;
import ca.nicho.foundation.entity.Entity;
import ca.nicho.foundation.entity.EntityBattleship;
import ca.nicho.foundation.entity.EntityCargoShip;
import ca.nicho.foundation.entity.EntityMedicShip;
import ca.nicho.foundation.entity.EntityMissile;
import ca.nicho.foundation.entity.EntityPlayer;
import ca.nicho.foundation.packet.ConnectPacket;
import ca.nicho.foundation.packet.EntityPacket;
import ca.nicho.foundation.packet.HealPacket;
import ca.nicho.foundation.packet.LogPacket;
import ca.nicho.foundation.packet.Packet;
import ca.nicho.foundation.packet.PurchasePacket;
import ca.nicho.foundation.packet.SpawnEntityPacket;
import ca.nicho.foundation.packet.SpawnMissilePacket;
import ca.nicho.foundation.packet.TilePacket;
import ca.nicho.foundation.tile.Tile;

public class ServerGameSocket implements Runnable{
		
	public boolean ready = false;
	public Socket socket;
	public DataInputStream in;
	public DataOutputStream out;
	public byte player;
	
	public LinkedBlockingQueue<Packet> queue;
	
	public ServerGameSocket(Socket socket, byte player){
		queue = new LinkedBlockingQueue<Packet>();
		this.player = player;
		this.setSocket(socket);
		new Thread(new PacketSendClock()).start();
	}
	
	public void setSocket(Socket socket){
		this.socket = socket;
		try {
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			//Remove from list of connections
			e.printStackTrace();
		}
	}
	
	public void nullifyStreams(){
		this.socket = null;
		this.in = null;
		this.out = null;
		ready = false;
	}

	private EntityPlayer ship1 = null;
	private EntityPlayer ship2 = null;
	private EntityPlayer ship3 = null;	
	
	@Override
	public void run() {
		byte owner = player;
		//System.out.println("Player joined - assigning entity ID " + id);
		if(ship1 == null){
			int x = (player == 1) ? Game.world.p1SpawnX : Game.world.p2SpawnX;
			int y = (player == 1) ? Game.world.p1SpawnY : Game.world.p2SpawnY;
			ship1 = new EntityMedicShip(x - 100, y, Game.world.entId++);
			ship1.owner = owner;
			ship2 = new EntityBattleship(x + 100, y,  Game.world.entId++);
			ship2.owner = owner;
			ship3 = new EntityCargoShip(x, y - 100,  Game.world.entId++);
			ship3.owner = owner;
			Game.world.spawnEntity(ship1);
			Game.world.spawnEntity(ship2);
			Game.world.spawnEntity(ship3);
		}
		//Spawn the user into the world
		this.sendIntitalWorldState(); //Send the world
		this.sendInitialEntities(); //Send the remaining entities
		queue.add(new ConnectPacket(ship1.id, ship2.id, ship3.id, owner, Game.started)); //Send final connection packet
		while(true){
			try{
				if(socket != null)
					readPacket();
			}catch(IOException e){
				System.out.println("ServerGameSocket: Client disconnected");
				nullifyStreams();
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
				int player = (this == ServerStart.con1) ? 1 : 2;
				ServerStart.sendGlobalPacket(new LogPacket("Player " + player + " Connected"));
				ServerGame.tryStartGame();
				break;
			case Packet.PACKET_SPAWN_ENTITY:
				SpawnEntityPacket packet = new SpawnEntityPacket(data);
				Game.world.entityUpdatePacketRecieved(new EntityPacket(packet.entityType, Game.world.entId++, packet.x, packet.y, packet.health, packet.owner, false));
				break;
			case Packet.PACKET_PURCHASE:
				PurchasePacket purchasePacket = new PurchasePacket(data);
				if(this == ServerStart.con1)
					ServerGame.p1Points -= purchasePacket.amount;
				else if(this == ServerStart.con2)
					ServerGame.p2Points -= purchasePacket.amount;
				break;
			case Packet.PACKET_SPAWN_MISSILE:
				SpawnMissilePacket missilePacket = new SpawnMissilePacket(data);
				EntityMissile missileEnt = new EntityMissile(missilePacket.x, missilePacket.y, Game.world.entId++, missilePacket.dx, missilePacket.dy);
				missileEnt.owner = missilePacket.owner;
				Game.world.spawnEntity(missileEnt);
				break;
			case Packet.PACKET_HEAL:
				System.out.println("Attempting reconnection with player");
				this.sendPacket(new HealPacket());
				break;
			default:
				System.out.println("Bad Packet Received: " + length + " " + type);
				break;
		}
	}	
	
	public void sendIntitalWorldState(){
		for(int pos = 0; pos < Game.world.map.length; pos++){
			Tile t = Game.world.map[pos];
			if(t != null){
				queue.add(new TilePacket(pos, t));
			}
		}
	}
	
	public void sendInitialEntities(){
		for(Map.Entry<Integer, Entity> ent : Game.world.entities.entrySet()){
			queue.add(new EntityPacket(ent.getValue()));
		}
	}
	
	public synchronized void sendPacket(Packet packet){
		if(socket == null)
			return;
		try{
			if(packet.packetType == Packet.PACKET_HEAL)
				out.writeByte(Packet.SYNC_RECOVERY_VALUE);
			else{
				out.writeInt(packet.packetType);
				byte[] data = packet.getPacketData();
				out.writeInt(data.length);
				out.write(data);
			}
		}catch(IOException e){
			e.printStackTrace();
			queue.clear();
			this.nullifyStreams();
		}
	}

	private class PacketSendClock implements Runnable {
		
		public void run(){
			while(true){
				while(!queue.isEmpty()){
					Packet p = queue.poll();
					sendPacket(p);
				}
			}
		}
		
	}
	
}
