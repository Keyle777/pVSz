

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Plant {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int ySpeed;
	protected int live;
	
	protected int fixLive;
	
	public Plant(int width,int height) {
		this.width=width;
		this.height=height;
		this.x=0;
		this.y=GamePlay.HEIGHT;
		ySpeed=1;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLive() {
		return live;
	}
	public void loseLive() {
		this.live--;
	}
	public void moveTo(int x,int y) {
		this.x=x-this.width/2;
		this.y=y-this.height/2;
	}
	public void step() {
		y-=ySpeed;
		if(y<=0) {
			state=STOP;
		}
	}
	
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img=ImageIO.read(Plant.class.getResource(fileName));
			return img;
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	public abstract BufferedImage getImage();
	
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, width,height,null);
	}
	public static final int WAIT=0;
	public static final int STOP=1;
	public static final int MOVE=2;
	
	public static final int LIFE=3;
	
	public static final int DEAD=4;
	
	protected int state=WAIT;
	
	public void setState(int state) {
		this.state=state;
	}
	public boolean isWait() {
		return state==WAIT;
	}
	public boolean isStop() {
		return state==STOP;
	}
	public boolean isMove() {
		return state==MOVE;
	}
	public boolean isLife() {
		return state==LIFE;
	}
	public boolean isDead() {
		return state==DEAD;
	}
	public void goWait() {
		state=WAIT;
	}
	public void goStop() {
		state=STOP;
	}
	public void goMove() {
		state=MOVE;
	}
	public void goLife() {
		state=LIFE;
	}
	public void goDead() {
		state=DEAD;
	}
}
