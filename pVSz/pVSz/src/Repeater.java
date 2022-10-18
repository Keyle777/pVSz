

import java.awt.image.BufferedImage;

public class Repeater extends Plant implements Shoot{

	public Repeater() {
		super(73, 71);
		live=3;
	}

	@Override
	public Bullet[] shoot() {
		Bullet[] bs=new Bullet[1];
		bs[0]=new PeaBullet(this.x+this.width, this.y+this.height/2-30);
		return bs;
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[6];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/plants/Repeater"+i+".png");
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
