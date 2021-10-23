package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPConnection extends Thread{

	
	private DatagramSocket socket;
	private OnMessageListener observer;
	
	
	
	

	public void setObserver(OnMessageListener observer) {
		this.observer = observer;
	}

	@Override
	public void run() {
		try {
			//1. Escuchar
			socket= new DatagramSocket(5000);
			
			//2 Esperar datagram
			while(true) {
				//2 params
				byte[] buffer= new byte[100];
				DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
				System.out.println("Esperando Datagrama");
				socket.receive(packet);
				String msg= new String(packet.getData()).trim();
				System.out.println(" Datagrama+ "+msg);
				
				observer.onMsg(msg);
				
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(String msg) {
		
		new Thread(()->{
			//4 params
			try {
				InetAddress ip = InetAddress.getByName("192.168.0.9");
				DatagramPacket packet= new DatagramPacket(msg.getBytes(),msg.getBytes().length,ip,6000);
				socket.send(packet);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
		
		
	}
	

	
	
	
	
}
