

import java.awt.image.BufferedImage;

public class WallNut extends Plant{
	
	
	public WallNut() {
		super(65, 73);
		live=30;
		fixLive=live;
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[32];
		for(int i=0;i<imgs.length;i++) {
			if(i<6) {
				imgs[i]=loadImage("/plants/WallNut"+i+".png");
			}else if(i<17) {
				imgs[i]=loadImage("/Wallnut_cracked1/Wallnut_cracked1_"+(i-6)+".png");
			}else {
				imgs[i]=loadImage("/Wallnut_cracked2/Wallnut_cracked2_"+(i-17)+".png");
			}
			
		}
	}
	int index=1;
	@Override
	public BufferedImage getImage() {
		if(isWait() || isMove() || isStop()) {
			return imgs[5];
		}else if(isLife()) {
			if(live<=fixLive/2) {
				return imgs[index++%11+6];
			}
			if(live<=fixLive/4) {
				return imgs[index++%15+17];
			}
			return imgs[index++%5];
		}
		return null;
	}
	
}
