

import java.awt.image.BufferedImage;

public class Blover extends Plant{
	
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[6];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/plants/Blover"+i+".png");
		}
	}
	public Blover() {
		super(90, 80);
		live=3;
	}
	int index=1;
	@Override
	public BufferedImage getImage() {
		if(isWait() || isStop() || isMove()) {
			return imgs[5];
		}else if(isLife() || isClick()) {
			return imgs[index++%5];
		}else {
			return null;
		}
	}
	public static final int CLICK=5;
	public boolean isClick() {
		return state==CLICK;
	}
	public void goClick() {
		state=CLICK;
	}
}
