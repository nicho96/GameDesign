package ca.nicho.client.packet;

import java.nio.ByteBuffer;

public class ConnectPacket extends Packet {

	public int ship1ID;
	public int ship2ID;
	public int ship3ID;
	public int ship4ID;
	
	/**
	 * The packet sent from the server to the client to indicate
	 * they have connected successfully (this is the first form of contact)
	 * 
	 * @param id the player entity id, the client can return any value
	 */
	public ConnectPacket(int ship1ID, int ship2ID, int ship3ID, int ship4ID) {
		super(Packet.PACKET_CONNECT);
		this.ship1ID = ship1ID;
		this.ship2ID = ship2ID;
		this.ship3ID = ship3ID;
		this.ship4ID = ship4ID;
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
		this.ship1ID = buffer.getInt();
		this.ship2ID = buffer.getInt();
		this.ship3ID = buffer.getInt();
		this.ship4ID = buffer.getInt();
	}

	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putInt(ship1ID);
		buffer.putInt(ship2ID);
		buffer.putInt(ship3ID);
		buffer.putInt(ship4ID);
		return buffer.array();
	}

}
