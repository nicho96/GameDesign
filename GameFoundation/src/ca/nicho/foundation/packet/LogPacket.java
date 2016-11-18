package ca.nicho.foundation.packet;

public class LogPacket extends Packet {

	public String message;
	
	public LogPacket(String msg){
		super(Packet.PACKET_LOG);
		this.message = msg;
	}

	public LogPacket(byte[] data){
		super(Packet.PACKET_LOG);
		this.message = new String(data);
	}
	
	@Override
	public byte[] getPacketData() {
		return message.getBytes();
	}
	
}
