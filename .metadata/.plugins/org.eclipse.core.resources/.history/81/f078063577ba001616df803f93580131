package ca.nicho.foundation.packet;

import java.awt.image.DataBuffer;
import java.nio.ByteBuffer;

public class HealPacket  extends Packet {

	public int id;
	public int amount;
	
	public HealPacket(int id, int amount) {
		super(Packet.PACKET_HEAL);
		this.id = id;
		this.amount = amount;
	}
	
	public HealPacket(byte[] data){
		super(Packet.PACKET_HEAL);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getInt();
		amount = buffer.getInt();
	}

	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putInt(id);
		buffer.putInt(amount);
		return buffer.array();
	}
}
