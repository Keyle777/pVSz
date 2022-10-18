

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class Zombie {
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected int live;
	protected int xSpeed;
	protected int deadTm;
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[25];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]= i<=13 ? loadImage("/ZombieDie/Frame"+i+".png") : loadImage("/ZombieHead/Frame"+(i-14)+".png");
		}
	}
	int index=0;
	int index1=0;
	public BufferedImage getDeadImg() {
		if(index==13) {
			return imgs[13];
		}
		return imgs[index++%14];
	}
	public BufferedImage getDeadHeadImg() {
		if(index1==10) {
			return imgs[24];
		}
		return imgs[index1++%11+14];
	}
	public Zombie(int width,int height) {
		this.width=width;
		this.height=height;
		Random rand=new Random();
		this.x=GamePlay.WIDTH-100;
		//this.y=rand.nextInt(GamePlay.HEIGHT-this.height);
		this.y=rand.nextInt(5)*100+46;
		this.deadTm=80;
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
	public int getSpeed() {
		return xSpeed;
	}
	public int getLive() {
		return live;
	}
	public void loseLive() {
		live--;
	}
	public void goSlowDown() {
		xSpeed=1;
	}
	public void goOut() {
		xSpeed=2;
	}
	// 移动方式
	public abstract void step();
	
	public boolean zombieHit(Plant p) {
		int x1=this.x-p.getWidth()+50;
		int x2=this.x+this.width-15;
		int y1=this.y-p.getHeight()+30;
		int y2=this.y+this.height-20;
		int x=p.getX();
		int y=p.getY();
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	public boolean outOfBounds() {
		return this.x<=0;
	}
	public static final int LIFE=0;
	public static final int ATTACK=1;
	public static final int DEAD=2;
	
	protected int state=LIFE;
	
	public boolean isLife() {
		return state==LIFE;
	}
	public boolean isAttack() {
		return state==ATTACK;
	}
	public boolean isDead() {
		return state==DEAD;
	}
	public void goLife() {
		state=LIFE;
	}
	public void goAttack() {
		state=ATTACK;
	}
	public void goDead() {
		state=DEAD;
	}
	public void goStop() {
		xSpeed=0;
	}
	public abstract void goRun();
	
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img=ImageIO.read(Zombie.class.getResource(fileName));
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
	public void paintHead(Graphics g) {
		g.drawImage(getDeadHeadImg(), x-10, y-30, null);
		
	}
}
