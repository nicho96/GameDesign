package ca.nicho.foundation.packet;

import java.nio.ByteBuffer;

import ca.nicho.foundation.entity.EntityMissile;

public class SpawnMissilePacket extends Packet {

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
		this.x = ent.locX;
		this.y = ent.locY;
		this.dx = ent.dx;
		this.dy = ent.dy;
		this.owner = ent.owner;
	}
	
	
	public SpawnMissilePacket(byte data[]){
		super(Packet.PACKET_SPAWN_MISSILE);
		ByteBuffer buffer = ByteBuffer.wrap(data); //Store the packet data into a buffer for further reading
		x = buffer.getFloat();
		y = buffer.getFloat();
		dx = buffer.getFloat();
		dy = buffer.getFloat();
		owner = buffer.get();
	}
	
	public SpawnMissilePacket(float x, float y, float dx, float dy, byte owner){
		super(Packet.PACKET_SPAWN_MISSILE);
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.owner = owner;
	}
	
	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(17); // 4 * 5 + 1 byte primitives
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(dx);
		buffer.putFloat(dy);
		buffer.put(owner);
		return buffer.array();
	}
	
}
