package ca.nicho.client.packet;

import java.nio.ByteBuffer;

import ca.nicho.client.Game;
import ca.nicho.client.entity.Entity;

public class EntityPacket extends Packet {
	
	public int type;
	public int id;
	public float x;
	public float y;
	public byte owner;
	
	/**
	 * Constructor used to create a packet for sending
	 * @param ent
	 */
	public EntityPacket(Entity ent){
		super(Packet.PACKET_ENTITY);
		this.type = ent.sprites[0].type;
		this.id = ent.id;
		this.x = ent.locX;
		this.y = ent.locY;
		this.owner = ent.owner;
	}
	
	/**
	 * Constructor used to create the appropriate entity out of the packet
	 * @param data
	 */
	public EntityPacket(byte data[]){
		super(Packet.PACKET_ENTITY);
		ByteBuffer buffer = ByteBuffer.wrap(data); //Store the packet data into a buffer for further reading
		type = buffer.getInt();
		id = buffer.getInt();
		x = buffer.getFloat();
		y = buffer.getFloat();
		owner = buffer.get();
	}
	
	public EntityPacket(int type, int id, float x, float y, byte owner){
		super(Packet.PACKET_ENTITY);
		this.type = type;
		this.id = id;
		this.x = x;
		this.y = y;
		this.owner = owner;
	}
	
	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(17); // 3 * 4 + 1 byte primitives
		buffer.putInt(type);
		buffer.putInt(id);
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.put(owner);
		return buffer.array();
	}

}
