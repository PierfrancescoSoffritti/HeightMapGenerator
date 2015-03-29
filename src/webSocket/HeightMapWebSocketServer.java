package webSocket;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;

public class HeightMapWebSocketServer extends WebSocketServer {
	
	private final Gson gson = new Gson();
	private boolean readyToSend;
	private WebSocket ws;
	private String status;

    public HeightMapWebSocketServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        System.out.println("Websocket server running... Listening on port " + port);
        readyToSend = false;
        status = "Disconnected";
    }

    @Override
    public void onOpen(WebSocket ws, ClientHandshake ch) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("");
        System.out.println(dateFormat.format(date) + " : " + ws.getRemoteSocketAddress() + " connected!");
        System.out.println("");
        this.status = "Connected";
    }

    @Override
    public void onClose(WebSocket ws, int i, String string, boolean bln) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date) + " : " + ws.getRemoteSocketAddress() + " disconnected!");
        this.status = "Disconnected";
    }

    @Override
    public void onError(WebSocket ws, Exception excptn) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date) + " : " + ws.getRemoteSocketAddress() + " error!");
        this.status = "Error";
    }
    
    @Override
    public void onMessage(WebSocket ws, String string) {
    	readyToSend = true;
    	this.ws = ws;
    	status = "Ready";
    	System.out.println(status);
    	
    }
    
    public boolean sendHeightMap(float heightMap[][]) {
    	if(readyToSend) {
    		ws.send(gson.toJson(heightMap));
    		status = "Not ready";
    	}
    	
    	return readyToSend;
    }
    
    public String getStatus() {
    	return this.status;
    }
}
