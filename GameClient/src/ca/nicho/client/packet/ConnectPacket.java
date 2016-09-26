package ca.nicho.client.packet;

import java.nio.ByteBuffer;

public class ConnectPacket extends Packet {

	public int id;
	
	/**
	 * The packet sent from the server to the client to indicate
	 * they have connected successfully (this is the first form of contact)
	 * 
	 * @param id the player entity id, the client can return any value
	 */
	public ConnectPacket(int id) {
		super(Packet.PACKET_CONNECT);
		this.id = id;
	}
	
	/**
	 * The client will respond with the same packet to signify it is ready
	 * to receive more packets
	 * 
	 * @param data the data of the packet
	 */
	public ConnectPacket(byte[] data){
		super(Packet.PACKET_CONNECT);
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
