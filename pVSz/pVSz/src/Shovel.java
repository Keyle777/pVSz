

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Shovel {
	
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img=ImageIO.read(Shovel.class.getResource(fileName));
			return img;
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	private static BufferedImage image;
	static {
		image=loadImage("plants/shovel.png");
	}
	public BufferedImage getImage() {
		if(isWait() || isMove()) {
			return image;
		}
		return null;
	}
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, width, height, null);
	}
	private int x;
	private int y;
	private int width;
	private int height;
	public Shovel() {
		this.x=90;
		this.y=0;
		this.width=76;
		this.height=34;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public static final int WAIT=0;
	public static final int MOVE=1;
	public static final int REMOVE=2;
	public int state=WAIT;
	
	public boolean isWait() {
		return state==WAIT;
	}
	public boolean isMove() {
		return state==MOVE;
	}
	public boolean isRemove() {
		return state==REMOVE;
	}
	public void goMove() {
		state=MOVE;
	}
	public void goRemove() {
		state=REMOVE;
	}
	public void moveTo(int x,int y) {
		this.x=x-this.width/2;
		this.y=y-this.height/2;
	}
	
}
