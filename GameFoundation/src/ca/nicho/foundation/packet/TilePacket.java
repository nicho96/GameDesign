package ca.nicho.foundation.packet;

import java.nio.ByteBuffer;

import ca.nicho.foundation.tile.Tile;

public class TilePacket extends Packet{

	public int pos;
	public int type;
	
	/**
	 * This packet should only be sent from server. It defines the position and type
	 * of a tile on the map
	 * @param t the Tile to be sent
	 */
	public TilePacket(int pos, Tile t){
		super(Packet.PACKET_TILE);
		this.pos = pos;
		type = t.sprites[0].type;
	}
	
	/**
	 * This will construct the packet from its data form
	 * @param data
	 */
	public TilePacket(byte[] data){
		super(Packet.PACKET_TILE);
		ByteBuffer buffer = ByteBuffer.wrap(data);
		this.pos = buffer.getInt();
		this.type = buffer.getInt();
	}

	@Override
	public byte[] getPacketData() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putInt(pos);
		buffer.putInt(type);
		return buffer.array();
	}
	
}
