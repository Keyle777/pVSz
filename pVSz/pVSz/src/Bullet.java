

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Bullet {
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected int xSpeed;
	protected int deadTm;
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[2];
		imgs[0]=loadImage("/plants/fire0.png");
		imgs[1]=loadImage("/plants/fire1.png");
	}
	int index=0;
	public BufferedImage getFireBulletImg() {
		return imgs[index++%2];
	}
	public Bullet(int width,int height,int x,int y) {
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;
		this.deadTm=50;
	}
	public int getDeadTm() {
		return deadTm;
	}
	public void loseDeadTm() {
		deadTm--;
	}
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static final int LIFE=0;
	public static final int FIRE=2; 
	
	public static final int DEAD=1;
	
	protected int state=LIFE;
	
	public boolean isLife() {
		return state==LIFE;
	}
	public boolean isFire() {
		return state==FIRE;
	}
	public boolean isDead() {
		return state==DEAD;
	}
	public void goFire() {
		state=FIRE;
	}
	public void goDead() {
		state=DEAD;
	}
	public void step() {
		x+=xSpeed;
	}
	public boolean hitFire(Torchwood t) {
		int x1=this.x-t.width+10;
		int x2=this.x+this.width-10;
		int y1=this.y-t.height+45;
		int y2=this.y+this.height-20;
		int x=t.getX();
		int y=t.getY();
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	public boolean hit(Zombie z) {
		int x1=this.x-z.width+10;
		int x2=this.x+this.width-40;		
		int y1=this.y-z.height;
		int y2=this.y+this.height;
		int x=z.getX();
		int y=z.getY();
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	public boolean outOfBounds() {
		return this.x>=GamePlay.WIDTH;
	}
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img=ImageIO.read(Bullet.class.getResource(fileName));
			return img;
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	public abstract BufferedImage getImage();
	
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, width, height, null);
	}
	public void paintObjectFire(Graphics g) {
		g.drawImage(getImage(), x, y, width+20, height+10, null);
	}
	public void paintObjectDead(Graphics g) {
		g.drawImage(getImage(), x, y, width+25, height+20, null);
	}
}
