package ca.nicho.client.packet;

import java.nio.ByteBuffer;

public class SpawnEntityPacket extends Packet {

	public float x;
	public float y;
	public int entityType;
	
	public SpawnEntityPacket(float x, float y, int entityType) {
		super(Packet.PACKET_SPAWN_ENTITY);
		this.x = x;
		this.y = y;
		this.entityType = entityType;
	}
	
	public SpawnEntityPacket(byte[] data){
		super(Packet.PACKET_SPAWN_ENTITY);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.x = buffer.getFloat();
		this.y = buffer.getFloat();
		this.entityType = buffer.getInt();
	}
	
	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(12);
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putInt(entityType);
		return buffer.array();
	}

}
