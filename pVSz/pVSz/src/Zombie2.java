

import java.awt.image.BufferedImage;

public class Zombie2 extends Zombie{
	//旗子
	public Zombie2() {
		super(166, 144);
		live=30;
		xSpeed=3;
	}

	@Override
	public void goRun() {
		xSpeed=3;		
	}
	
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[10];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/zombie/zombie2"+i+".png");
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
