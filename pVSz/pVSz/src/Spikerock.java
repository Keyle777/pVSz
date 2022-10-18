

import java.awt.image.BufferedImage;

public class Spikerock extends Plant{

	public Spikerock() {
		super(84, 80);
		live=5;
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[6];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/plants/Spikerock"+i+".png");
		}
	}
	int index=1;
	@Override
	public BufferedImage getImage() {
		if(isWait() || isMove() || isStop()) {
			return imgs[5];
		}else if(isLife()) {
			return imgs[index++%5];
		}
		return null;
	}

}
