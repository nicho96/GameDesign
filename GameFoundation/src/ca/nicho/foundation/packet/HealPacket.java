package ca.nicho.foundation.packet;

import java.awt.image.DataBuffer;
import java.nio.ByteBuffer;

public class HealPacket  extends Packet {
	
	public HealPacket() {
		super(Packet.PACKET_HEAL);
	}

	@Override
	public byte[] getPacketData() {
		return new byte[0];
	}
}
