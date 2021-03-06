package ca.nicho.foundation.packet;

import java.nio.ByteBuffer;

public class PurchasePacket extends Packet {

	public int amount;
	
	public PurchasePacket(int amount) {
		super(Packet.PACKET_PURCHASE);
		this.amount = amount;
	}
	
	public PurchasePacket(byte[] data){
		super(PointPacket.PACKET_PURCHASE);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.amount = buffer.getInt();
	}

	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(amount);
		return buffer.array();
	}

	
	
}
