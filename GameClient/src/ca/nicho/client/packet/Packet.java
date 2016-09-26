package ca.nicho.client.packet;

public abstract class Packet {
	
	public static final int PACKET_ENTITY = 0;
	public static final int PACKET_CONNECT = 1;
	public static final int PACKET_TILE = 2;
	
	public int packetType;
	public byte[] data;
	
	public Packet(int packetType){
		this.packetType = packetType;
	}
	
	public abstract byte[] getPacketData();
	
}
