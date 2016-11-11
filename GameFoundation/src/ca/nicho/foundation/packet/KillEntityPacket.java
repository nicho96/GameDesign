package ca.nicho.foundation.packet;

import java.nio.ByteBuffer;

public class KillEntityPacket extends Packet {

	public int id;
	
	public KillEntityPacket(int id){
		super(Packet.PACKET_KILL_ENTITY);
		this.id = id;
	}

	public KillEntityPacket(byte[] data){
		super(Packet.PACKET_KILL_ENTITY);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.id = buffer.getInt();
	}
	
	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(id);
		return buffer.array();
	}
	
}
