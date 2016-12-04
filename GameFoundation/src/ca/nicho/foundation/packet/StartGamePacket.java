package ca.nicho.foundation.packet;

public class StartGamePacket extends Packet {

	public StartGamePacket(){
		super(Packet.PACKET_GAME_START);
	}

	@Override
	public byte[] getPacketData() {
		return new byte[0];
	}
	
}
