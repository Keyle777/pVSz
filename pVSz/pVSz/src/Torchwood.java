


import java.awt.image.BufferedImage;



public class Torchwood extends Plant{

	public Torchwood() {
		super(70, 85);
		live=10;
	}
	private static BufferedImage[] imgs;

	static {
		imgs=new BufferedImage[10];
		imgs[9]=loadImage("/plants/Torchwood.gif");
		for(int i=0;i<imgs.length-1;i++) {
			imgs[i]=loadImage("/plants/torchwood"+i+".png");
		}
	}
	int index=0;
	@Override
	public BufferedImage getImage() {
		if(isWait() || isMove() || isStop()) {
			return imgs[9];
		}else if(isLife()) {
			return imgs[index++%9];
		}
		return null;
	}
}
