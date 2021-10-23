package main;

import java.util.ArrayList;

import com.google.gson.Gson;

import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet implements OnMessageListener{
	
	
		private PImage img1;
		
		private PImage img2;
	
		private PImage img3;
	
		private PImage img4;
		
		private ArrayList<Order> orders;
		UDPConnection udp;
		
	public static void main(String[] args) {
		PApplet.main(Main.class.getName());
	}

	public void settings() {
		size(400, 600);

	}

	public void setup() {
		udp= new UDPConnection();
		udp.setObserver(this);
		udp.start();
		img1= loadImage("../img/pizza.png");
		img2= loadImage("../img/papas.jpg");
		img3= loadImage("../img/nachos.jpg");
		img4= loadImage("../img/cocacola.jpg");
		orders= new ArrayList<>();
		/*
		orders.add(new Order("Pizza",1,"14:22",100,25));
		orders.add(new Order("Fries",2,"14:22",100,175));
		orders.add(new Order("Coca-cola",3,"14:22",100,325));
		orders.add(new Order("Nachos",4,"14:22",100,475));*/
	
	}

	public void draw() {
		background(255);
		fill(0);
		for(int i=0; i<orders.size(); i++) {
			
			switch (orders.get(i).getItem()) {
			case "Pizza":
				text("Order #"+orders.get(i).getNumber(), orders.get(i).getX()+125,orders.get(i).getY()+20);
				text("Hour: "+orders.get(i).getHour(), orders.get(i).getX()+125,orders.get(i).getY()+40);
				image(img1,orders.get(i).getX(),orders.get(i).getY(),100,100);
				break;
			case "Fries":
				text("Order #"+orders.get(i).getNumber(), orders.get(i).getX()+125,orders.get(i).getY()+20);
				text("Hour: "+orders.get(i).getHour(), orders.get(i).getX()+125,orders.get(i).getY()+40);
				image(img2,orders.get(i).getX(),orders.get(i).getY(),100,100);
				break;
			case "Nachos":
				text("Order #"+orders.get(i).getNumber(), orders.get(i).getX()+125,orders.get(i).getY()+20);
				text("Hour: "+orders.get(i).getHour(), orders.get(i).getX()+125,orders.get(i).getY()+40);
				image(img3,orders.get(i).getX(),orders.get(i).getY(),100,100);
				break;
			case "Coca-cola":
				text("Order #"+orders.get(i).getNumber(), orders.get(i).getX()+125,orders.get(i).getY()+20);
				text("Hour: "+orders.get(i).getHour(), orders.get(i).getX()+125,orders.get(i).getY()+40);
				image(img4,orders.get(i).getX(),orders.get(i).getY(),100,100);
				break;
			}
			
		}
		
		
		
	}
	
	public void mousePressed() {
		for(int i=0; i<orders.size(); i++) {
			if(mouseX>orders.get(i).getX() && mouseX<orders.get(i).getX()+150
					&& mouseY>orders.get(i).getY() && mouseY<orders.get(i).getY()+100) {
				udp.sendMessage("Order #"+orders.get(i).getNumber()+" "+orders.get(i).getItem()+ " Ready");
				orders.remove(i);
				for(int j=0; j<orders.size(); j++) {
					orders.get(j).setY((j*150)+25);
					
				}
				
			}
		}
		
	}
	
	

	@Override
	public void onMsg(String msg) {
		Gson gson = new Gson();
		Order o= gson.fromJson(msg, Order.class);
		if(orders.size()<4) {
			if(orders.isEmpty()) {
				o.setNumber(1);
			}else {
				o.setNumber(orders.get(orders.size()-1).getNumber()+1);
			}
			
			o.setY((orders.size()*150)+25);
			orders.add(o);
		}else {
			udp.sendMessage("Orders Full");
		}
		
	}

}
