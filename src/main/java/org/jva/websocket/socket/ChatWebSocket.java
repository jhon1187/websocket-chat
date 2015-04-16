package org.jva.websocket.socket;

import java.util.Set;

import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;

public class ChatWebSocket implements OnTextMessage {

	private Connection connection;

	private static Set<ChatWebSocket> users;

	public ChatWebSocket() {
		super();
	}

	public ChatWebSocket(Set<ChatWebSocket> users) {
		ChatWebSocket.users = users;
	}

	public void onMessage(String data) {
		sendMessage(data);
	}

	public static void sendMessage(String data) {
		if(users != null){
			for (ChatWebSocket user : users) {
				try {
					user.connection.sendMessage(data);
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	public void onOpen(Connection connection) {
		this.connection = connection;
		users.add(this);
	}

	@Override
	public void onClose(int closeCode, String message) {
		users.remove(this);
	}

}