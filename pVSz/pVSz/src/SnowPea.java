

import java.awt.image.BufferedImage;

public class SnowPea extends Plant implements Shoot{

	public SnowPea() {
		super(71, 71);
		live=5;
	}

	@Override
	public Bullet[] shoot() {
		Bullet[] bs=new Bullet[1];
		bs[0]=new SnowBullet(this.x+this.width, this.y+this.height/2-25);
		return bs;
	}
	
	private static BufferedImage[] imgs;
	
	static {
		imgs=new BufferedImage[6];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/plants/SnowPea"+i+".png");
		}
	}
	int index=1;
	@Override
	public BufferedImage getImage() {
		if(isWait() || isMove() || isStop()) {
			return imgs[5];
		}else if(isLife()) {
			return imgs[index++%5];
		}else {
			return null;
		}
	}
	
}
