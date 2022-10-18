

public class Glass {
	private int x;
	private int y;
	public static final int width=80;
	public static final int height=100;
	
	public Glass(int x,int y) {
		this.x=x;
		this.y=y;	
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	public static final int EMPTY=0;
	public static final int HOLD=1;
	
	public int state=EMPTY;
	public boolean isEmpty() {
		return state==EMPTY;
	}
	public boolean isHold() {
		return state==HOLD;
	}
	public void goEmpty() {
		state=EMPTY;
	}
	public void goHold() {
		state=HOLD;
	}
}
