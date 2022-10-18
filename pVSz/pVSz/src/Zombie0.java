

import java.awt.image.BufferedImage;

public class Zombie0 extends Zombie{
	//铁桶
	public Zombie0() {
		super(166, 144);
		live=40;
		xSpeed=2;
	}

	@Override
	public void goRun() {
		xSpeed=2;		
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[10];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/zombie/zombie0"+i+".png");
		}
	}
	int index=0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return xSpeed>0 ? imgs[index++%5] : imgs[index%5];
		}else if(isAttack()) {
			return xSpeed>0 ? imgs[index++%5+5] : imgs[index%5+5];
		}else if(isDead()) {
			return getDeadImg();
		}
		return null;
	}
	public void step() {
		this.x-=xSpeed;
	}
}
