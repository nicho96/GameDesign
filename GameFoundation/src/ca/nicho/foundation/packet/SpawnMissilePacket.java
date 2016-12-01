package ca.nicho.foundation.packet;

import java.nio.ByteBuffer;

import ca.nicho.foundation.entity.EntityMissile;

public class SpawnMissilePacket extends Packet {

	public int type;
	public int id;
	public float x;
	public float y;
	public float dx;
	public float dy;
	public byte owner;
	
	/**
	 * Constructor used to create a packet for sending
	 * @param ent
	 */
	public SpawnMissilePacket(EntityMissile ent){
		super(Packet.PACKET_SPAWN_MISSILE);
		this.type = ent.sprites[0].type;
		this.id = ent.id;
		this.x = ent.locX;
		this.y = ent.locY;
		this.dx = ent.dx;
		this.dy = ent.dy;
		this.owner = ent.owner;
	}
	
	
	public SpawnMissilePacket(byte data[]){
		super(Packet.PACKET_SPAWN_MISSILE);
		ByteBuffer buffer = ByteBuffer.wrap(data); //Store the packet data into a buffer for further reading
		type = buffer.getInt();
		id = buffer.getInt();
		x = buffer.getFloat();
		y = buffer.getFloat();
		dx = buffer.getFloat();
		dy = buffer.getFloat();
		owner = buffer.get();
	}
	
	public SpawnMissilePacket(int type, int id, float x, float y, float dx, float dy, int health, byte owner){
		super(Packet.PACKET_SPAWN_MISSILE);
		this.type = type;
		this.id = id;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.owner = owner;
	}
	
	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(25); // 4 * 5 + 1 byte primitives
		buffer.putInt(type);
		buffer.putInt(id);
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(dx);
		buffer.putFloat(dy);
		buffer.put(owner);
		return buffer.array();
	}
	
}
