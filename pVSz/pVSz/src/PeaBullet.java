

import java.awt.image.BufferedImage;

public class PeaBullet extends Bullet{

	public PeaBullet(int x, int y) {
		super(24, 24, x, y);
		xSpeed=3;
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[6];
		for(int i=0;i<imgs.length-1;i++) {
			imgs[i]=loadImage("/plants/PeaBullet"+i+".png");
		}
	}
	
	int index=0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return imgs[index++%4];
		}else if(isFire()) {
			return getFireBulletImg();
		}else if(isDead()) {
			return imgs[index++%2+4];
		}
		return null;
	}

}
