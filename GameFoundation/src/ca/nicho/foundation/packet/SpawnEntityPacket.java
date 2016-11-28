package ca.nicho.foundation.packet;

import java.nio.ByteBuffer;

public class SpawnEntityPacket extends Packet {

	public float x;
	public float y;
	public int health;
	public int entityType;
	public byte owner;
	
	public SpawnEntityPacket(float x, float y, int health, int entityType, byte owner) {
		super(Packet.PACKET_SPAWN_ENTITY);
		this.x = x;
		this.y = y;
		this.health = health;
		this.entityType = entityType;
		this.owner = owner;
	}
	
	public SpawnEntityPacket(byte[] data){
		super(Packet.PACKET_SPAWN_ENTITY);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.x = buffer.getFloat();
		this.y = buffer.getFloat();
		this.health = buffer.getInt();
		this.entityType = buffer.getInt();
		this.owner = buffer.get();
	}
	
	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(17);
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putInt(health);
		buffer.putInt(entityType);
		buffer.put(owner);
		return buffer.array();
	}

}
