

import java.awt.image.BufferedImage;

public class SnowBullet extends Bullet{

	public SnowBullet(int x, int y) {
		super(24, 24, x, y);
		xSpeed=3;
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[4];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/plants/SnowBullet"+i+".png");
		}
	}
	int index=0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return imgs[index++%4];
		}else if(isFire()) {
			return getFireBulletImg();
		}
		return null;
	}
	
}
