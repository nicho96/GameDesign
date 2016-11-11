package ca.nicho.foundation.packet;

public abstract class Packet {
	
	public static final int PACKET_ENTITY = 0;
	public static final int PACKET_CONNECT = 1;
	public static final int PACKET_TILE = 2;
	public static final int PACKET_SPAWN_ENTITY = 3;
	public static final int PACKET_KILL_ENTITY = 4;
	public static final int PACKET_POINTS = 5;
	public static final int PACKET_PURCHASE = 6;
	
	public int packetType;
	public byte[] data;
	
	public Packet(int packetType){
		this.packetType = packetType;
	}
	
	public abstract byte[] getPacketData();
	
}
