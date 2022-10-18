

import java.awt.image.BufferedImage;

public class ThreePeater extends Plant implements Shoot{

	public ThreePeater() {
		super(73, 80);
		live=7;
	}

	@Override
	public Bullet[] shoot() {
		Bullet[] bs=new Bullet[3];
		bs[0]=new PeaBullet(this.x+this.width, this.y+this.height/2-5);
		bs[1]=new PeaBullet(this.x+this.width, this.y+this.height/2-20);
		bs[2]=new PeaBullet(this.x+this.width, this.y+this.height/2-35);
		return bs;
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[6];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/plants/ThreePeater"+i+".png");
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
