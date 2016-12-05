package ca.nicho.foundation;

public interface Logger {

	public void sendMessage(String msg, int owner);
	
	public void sendGlobalMessage(String msg);
	
}
