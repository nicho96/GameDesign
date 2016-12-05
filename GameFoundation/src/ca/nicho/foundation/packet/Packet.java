package ca.nicho.foundation.packet;

public abstract class Packet {
	
	public static final byte SYNC_RECOVERY_VALUE = -128;
	
	public static final int PACKET_ENTITY = 0;
	public static final int PACKET_CONNECT = 1;
	public static final int PACKET_TILE = 2;
	public static final int PACKET_SPAWN_ENTITY = 3;
	public static final int PACKET_KILL_ENTITY = 4;
	public static final int PACKET_POINTS = 5;
	public static final int PACKET_PURCHASE = 6;
	public static final int PACKET_LOG = 7;
	public static final int PACKET_SPAWN_MISSILE = 8;
	public static final int PACKET_GAME_START = 9;
	public static final int PACKET_END_GAME = 10;
	
	public int packetType;
	public byte[] data;
	
	public Packet(int packetType){
		this.packetType = packetType;
	}
	
	public abstract byte[] getPacketData();
	
}
