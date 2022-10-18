

import java.awt.image.BufferedImage;
import java.util.Random;


public class Zombie3 extends Zombie implements Award{
	//足球
	private int awardType;
	public Zombie3() {
		super(154, 160);
		live=45;
		xSpeed=4;
		Random rand=new Random();
		awardType=rand.nextInt(3);
	}

	@Override
	public void goRun() {
		xSpeed=4;		
	}
	private static BufferedImage[] imgs;
	static {
		imgs=new BufferedImage[11];
		for(int i=0;i<imgs.length;i++) {
			imgs[i]=loadImage("/zombie/zombie3"+i+".png");
		}
	}
	int index=0;
	
	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return xSpeed>0 ? imgs[index++%6] : imgs[index%6];
		}else if(isAttack()) {
			return xSpeed>0 ? imgs[index++%5+6] : imgs[index%5+6];
		}else if(isDead()) {
			return getDeadImg();
		}
		return null;
	}
	
	public int getAwardType() {
		return awardType;
	}
	public void step() {
		this.x-=xSpeed;
	}
}
