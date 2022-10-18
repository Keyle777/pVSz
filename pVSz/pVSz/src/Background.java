

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	private int x;
	private int y;
	private int width;
	private int heigth;
	
	public Background(int width,int height,int x,int y) {
		this.width=width;
		this.heigth=height;
		this.x=x;
		this.y=y;
	}
	public static BufferedImage loadImage(String fileName) {
		try {			
			BufferedImage img=ImageIO.read(Background.class.getResource(fileName));
			return img;
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	private static BufferedImage[] images;
	
	static {
		images=new BufferedImage[3];
		images[0]=loadImage("bg0.jpg");
		images[1]=loadImage("bg1.jpg");
		images[2]=loadImage("bg2.jpg");
	}
	
	public BufferedImage getImage() {
		if(GamePlay.state==GamePlay.START) {
			return images[0];
		}
		if(GamePlay.state==GamePlay.RUNNING) {
			return images[1];
		}
		if(GamePlay.state==GamePlay.GAME_OVER) {
			return images[2];
		}
		return null;
	}
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y,width,heigth, null);
	}
}
