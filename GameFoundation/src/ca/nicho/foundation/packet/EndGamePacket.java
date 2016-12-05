package ca.nicho.foundation.packet;

import java.nio.ByteBuffer;

public class EndGamePacket extends Packet {

	public int winnerID;
	
	public EndGamePacket(int winnerID) {
		super(Packet.PACKET_END_GAME);
		this.winnerID = winnerID;
	}
	
	public EndGamePacket(byte[] data){
		super(Packet.PACKET_END_GAME);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		winnerID = buffer.getInt();
	}

	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(winnerID);
		return buffer.array();
	}
	
}
