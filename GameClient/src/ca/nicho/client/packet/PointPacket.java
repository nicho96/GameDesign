package ca.nicho.client.packet;

import java.nio.ByteBuffer;

public class PointPacket extends Packet{

	public int points;
	
	public PointPacket(int points){
		super(PointPacket.PACKET_POINTS);
		this.points = points;
	}
	
	public PointPacket(byte[] data){
		super(PointPacket.PACKET_POINTS);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.points = buffer.getInt();
	}

	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(points);
		return buffer.array();
	}
	
}
