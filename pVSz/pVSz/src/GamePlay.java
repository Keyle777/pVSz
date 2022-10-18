

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePlay extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=1400;
	public static final int HEIGHT=600;
	public static final int START=0;
	public static final int RUNNING=1;
	public static final int GAME_OVER=2;
	
	public static int state=RUNNING;
	
	Background start=new Background(800, 533, 300, 50);
	Background running=new Background(WIDTH, HEIGHT, 0, 0);
	Background gameOver=new Background(WIDTH, HEIGHT, 0, 0);
	
	Vector<Zombie> zombies=new Vector<Zombie>();
	
	Vector<Plant> plants=new Vector<>();
	Vector<Plant> plantsLife=new Vector<>();
	Vector<Bullet> bullets=new Vector<>();
	List<Glass> glasses=new ArrayList<>();
	
	List<Shovel> shovels=new ArrayList<>();
	
	public void shovelEnterAction() {
		if(shovels.size()==0) {
			shovels.add(new Shovel());
		}
	}
	public void checkShovelAction() {
		if(plantsLife.size()==0) {
			Iterator<Shovel> its=shovels.iterator();
			while(its.hasNext()) {
				Shovel s=its.next();
				if(s.isMove() && shovelCheck) {
					its.remove();
				}
			}
		}
	}
	int glassX=260;
	int glassY=80;
	public void glassEnterAction() {
		for(int i=0;i<9;i++) {
			int x=glassX+i*Glass.width;
			for(int j=0;j<5;j++) {
				int y=glassY+j*Glass.height;
				glasses.add(new Glass(x, y));
			}
		}
	}
	public void glassCheckAction() {
		for(Glass g : glasses) {
			g.goEmpty();
			int x1=g.getX();
			int y1=g.getY();
			for(Plant p : plantsLife) {
				if(p.isLife() || (p instanceof Blover && ((Blover)p).isClick())) {
					int x=p.getX();
					int y=p.getY();
					if(x1==x && y1==y) {
						g.goHold();
						break;
					}
				}
			}
		}
	}
	public Zombie nextOneZombie() {
		Random rand=new Random();
		int type=rand.nextInt(20);
		if(type<5) {
			return new Zombie0();
		}else if(type<11) {
			return new Zombie1();
		}else if(type<17) {
			return new Zombie2();
		}else {
			return new Zombie3();
		}
	}
	int zombieEnterTime=0;
	public void zombieEnterAction() {
		zombieEnterTime++;
		if(zombieEnterTime%300==0) {
			zombies.add(nextOneZombie());
		}
	}
	int zombieStepTime=0;
	public void zombieStepAction() {
		if(zombieStepTime++%6==0) {
			for(Zombie z : zombies) {
				if(z.isLife()) {
					z.step();
				}
			}
		}
	}
	int spikerockHitTime=0;
	public void zombieMoveToSpikerockAction() {
		if(spikerockHitTime++%20==0) {
			for(Plant p : plantsLife) {
				if(p instanceof Spikerock) {
					if(p.isLife()) {
						int x1=p.getX();
						int y1=p.getY();
						int x2=p.getX()+p.getWidth();
						int y2=p.getY()+p.getHeight();
						for(Zombie z : zombies) {
							if(z.isLife() || z.isAttack()) {
								int x=z.getX();
								int y=z.getY();
								if(x>x1 && y>y1 && x<x2 && y<y2) {
									z.loseLive();
								}
							}
						}
					}
				}
			}
		}
	}
	int zombieHitTime=0;
	public void zombieHitAction() {
		if(zombieHitTime++%100==0) {
			for(Zombie z : zombies) {
				if(!z.isDead()) {
					z.goLife();
				}
				for(Plant p : plantsLife) {
					if(z.isLife() && (p.isLife() || (p instanceof Blover && ((Blover)p).isClick())) && z.zombieHit(p) && !(p instanceof Spikerock)) {
						z.goAttack();
						p.loseLive();
					}
				}
			}
		}
	}
	int timeStop=1; //停止2�?
	public void checkZombieAction() {
		Iterator<Zombie> it=zombies.iterator();
		while(it.hasNext()) {
			Zombie z=it.next();
			if(z.getLive()<=0) {
				if(z instanceof Award && !z.isDead()) {
					Award a=(Award)z;
					int type=a.getAwardType();
					switch (type) {
					case Award.CLEAR:
						for(Zombie zo : zombies) {
							zo.goDead();
						}
						break;
					case Award.STOP:
						for(Zombie zo : zombies) {
							zo.goStop();
							timeStop=1;
						}
						break;
					}
				}
				z.goDead();
				//it.remove();
				z.loseDeadTm();
				
			}else {
				if(z.isDead()) {
					z.loseDeadTm();
				}
			}
			if(z.outOfBounds()) {
				gameLife--;
				it.remove();
			}
		}
	}
	public void checkDeadZombieRemoveAction() {
		Iterator<Zombie> it=zombies.iterator();
		while(it.hasNext()) {
			Zombie z=it.next();
			if(z.isDead() && z.getDeadTm()<=0) {
				it.remove();
			}
		}
	}
	public void zombieGoLife() {
		if(timeStop++%250==0) {
			for(Zombie zo : zombies) {
				zo.goRun();						
			}
		}
	}
	int gameLife=1;
	public void checkGameAction() {
		if(gameLife<=0) {
			state=GAME_OVER;
			plants.clear();
			zombies.clear();
			bullets.clear();
			plantsLife.clear();
			shovels.clear();
		}
	}
	public Plant nextOnePlant() {
		Random rand=new Random();
		int type=rand.nextInt(35);
		if(type<5) {
			return new Repeater();
		}else if(type<10) {
			return new ThreePeater();
		}else if(type<15) {
			return new SnowPea();
		}else if(type<20) {
			return new Blover();
		}else if(type<25) {
			return new Spikerock();
		}else if(type<30){
			return new WallNut();
		}else {
			return new Torchwood();
		}
	}
	int plantEnterTm=0;
	public void plantEnterAction() {
		plantEnterTm++;
		if(plantEnterTm%150==0) {
			plants.add(nextOnePlant());
		}
	}
	public void plantStepAction() {
		for(Plant p : plants) {
			if(p.isWait()) {
				p.step();
			}
		}
	}
	public void plantBangAction() {
		for(int i=1;i<plants.size();i++) {
			if(plants.get(0).getY()>0 && plants.get(0).isStop()) {
				plants.get(0).goWait();
			}
			if((plants.get(i).isStop() || plants.get(i).isWait()) && (plants.get(i-1).isStop() || plants.get(i-1).isWait())
				&& plants.get(i).getY()<=plants.get(i-1).getY()+plants.get(i-1).getHeight()) {
				plants.get(i).goStop();
			}
			if(plants.get(i).isStop()  && plants.get(i).getY()>plants.get(i-1).getY()+plants.get(i-1).getHeight()) {
				plants.get(i).goWait();
			}
		}
	}
	public void checkPlantAction() {
		Iterator<Plant> it=plants.iterator();
		while(it.hasNext()) {
			Plant p=it.next();
			if(p.isMove() || p.isLife()) {
				plantsLife.add(p);
				it.remove();
			}
		}
	}
	public void checkPlantLifeAction() {
		Iterator<Plant> it=plantsLife.iterator();
		while(it.hasNext()) {
			Plant p=it.next();
			if(p.getLive()<=0) {
				p.goDead();
				it.remove();
			}
		}
	}
	int bloverTm=1;
	public void checkBloverAction() {
		if(bloverTm++%200==0) {
			for(Plant p : plantsLife) {
				if(p instanceof Blover && p.isLife()) {
					((Blover)p).goClick();
				}
			}
		}
	}
	int bulletTm=0;
	public void bulletShootAction() {
		if(bulletTm++%80==0) {
			for(Plant p : plantsLife) {
				if(p instanceof Shoot && p.isLife()) {
					Shoot s=(Shoot) p;
					bullets.addAll(Arrays.asList(s.shoot()));
				}
			}
		}
	}
	public void bulletStepAction() {
		for(Bullet b : bullets) {
			b.step();
		}
	}
	public void bulletZombieHitAction() {
		for(Zombie z : zombies) {
			if(z.isAttack() || z.isLife()) {
				for(Bullet b : bullets) {
					if((b.isLife() || b.isFire()) && b.hit(z) && z.getX()<GamePlay.WIDTH) {
						if(b instanceof SnowBullet && b.isLife()) {
							z.goSlowDown();
						}
						if(b.isLife()) {
							z.loseLive();
						}
						z.loseLive();
						b.goDead();
					}
				}
			}
		}
	}
	public void bulletFireHitAction() {
		for(Plant p : plantsLife) {
			if(p instanceof Torchwood && p.isLife()) {
				Torchwood t=(Torchwood)p;
				for(Bullet b : bullets) {
					if(b.isLife() && b.hitFire(t)) {
						b.goFire();
					}
				}
			}
		}
	}
	public void bulletCheckAction() {
		Iterator<Bullet> it=bullets.iterator();
		while(it.hasNext()) {
			Bullet b=it.next();
			if(b.outOfBounds()) {
				it.remove();
			}else if(b.isDead()){
				if(!(b instanceof PeaBullet)){
					it.remove();
				}else {
					b.loseDeadTm();
					if(b.getDeadTm()<=0) {
						it.remove();
					}
				}		
			}
		}
	}
	boolean plantCheck=false;
	boolean shovelCheck=false;
	
	public void action() {
		glassEnterAction();
		
		MouseAdapter l=new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mX=e.getX();
				int mY=e.getY();
				if(state==RUNNING) {
					f:for(Plant p : plantsLife) {
						if(p.isMove() && plantCheck) {
							for(Glass g : glasses) {
								int x1=g.getX();
								int x2=g.getX()+g.getWidth();
								int y1=g.getY();
								int y2=g.getY()+g.getHeight();
								if(mX>x1 && mX<x2 && mY>y1 && mY<y2 && g.isEmpty()) {
									p.setX(x1);
									p.setY(y1);
									g.goHold();
									p.goLife();
									plantCheck=false;
									if(p instanceof Blover) {
										bloverTm=0;
									}
									break f;
								}
							}
						}
					}
					Iterator<Shovel> it=shovels.iterator();
					Iterator<Plant> itp=plantsLife.iterator();
					while(it.hasNext()) {
						Shovel s=it.next();
						if(s.isMove() && shovelCheck) {
							while(itp.hasNext()) {
								Plant p=itp.next();
								int x1=p.getX();
								int x2=p.getX()+p.getWidth();
								int y1=p.getY();
								int y2=p.getY()+p.getHeight();
								if((p.isLife() || (p instanceof Blover && ((Blover)p).isClick())) && mX>x1 && mX<x2 && mY>y1 && mY<y2) {
									itp.remove();
									it.remove();
									shovelCheck=false;
									break;
								}
							}
						}
					}
					for(Plant p : plants) {
						if((p.isStop() || p.isWait()) && !plantCheck && !shovelCheck) {
							int x1=p.getX();
							int x2=p.getX()+p.getWidth();
							int y1=p.getY();
							int y2=p.getY()+p.getHeight();
							if(mX>x1 && mX<x2 && mY>y1 && mY<y2) {
								p.goMove();
								plantCheck=true;
								break;
							}
						}
					}
					Iterator<Shovel> its=shovels.iterator();
					if(plantsLife.size()>0) {
						while(its.hasNext()) {
							Shovel s = its.next();
							int x1 = s.getX();
							int x2 = s.getX()+s.getWidth();
							int y1 = s.getY();
							int y2 = s.getY()+s.getHeight();
							if(s.isWait() && mX>x1 && mX<x2 && mY>y1 && mY<y2 && !plantCheck) {
								s.goMove();
								shovelCheck=true;
							}
						}
					}
					for(Plant p : plantsLife) {
						if(p instanceof Blover) {
							int x1=p.getX();
							int x2=p.getX()+p.getWidth();
							int y1=p.getY();
							int y2=p.getY()+p.getHeight();
							if(((Blover)p).isClick() && mX>x1 && mX<x2 && mY>y1 && mY<y2 && !plantCheck && !shovelCheck) {
								p.goDead();
								for(Zombie z : zombies) {
									if(z.isAttack()) {
										z.goLife();
									}
									z:for(int i=0;i<10;i++) {
										z.goOut();
										if(z.getX()>=GamePlay.WIDTH-z.getWidth()) {
											z.goRun();
											break z;
										}
									}
								}
							}
						}
					}
				}
				if(state==START) {
					int x1 = 720;
					int x2 = 990;
					int y1 = 210;
					int y2 = 320;
					if(mX>=x1&&mX<=x2&&mY>=y1&&mY<=y2) {
						state = RUNNING;
					}
				}
				if(state==GAME_OVER) {
					int x1 = 480;
					int x2 = 950;
					int y1 = 100;
					int y2 = 540;
					if(mX>=x1&&mX<=x2&&mY>=y1&&mY<=y2) {
						state = START;
						gameLife = 1;
					}
				}
			}
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {
					for(Plant p : plantsLife) {
						if(p.isMove()) {
							int x=e.getX();
							int y=e.getY();
							p.moveTo(x, y);
							break;
						}
					}
					for(Shovel s : shovels) {
						if(s.isMove()) {
							int x=e.getX();
							int y=e.getY();
							s.moveTo(x, y);
							break;
						}
					}
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer=new Timer();
		int interval=10;
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(state==RUNNING) {
					shovelEnterAction();
					checkShovelAction();
					zombieEnterAction();
					zombieStepAction();
					zombieMoveToSpikerockAction();
					zombieHitAction();
					plantEnterAction();
					plantStepAction();
					plantBangAction();
					zombieGoLife();
					bulletShootAction();
					bulletStepAction();
					bulletZombieHitAction();
					checkBloverAction();
					checkPlantAction();
					checkPlantLifeAction();
					checkZombieAction();
					bulletCheckAction();
					glassCheckAction();
					checkGameAction();		
					checkDeadZombieRemoveAction();
					bulletFireHitAction();
				}				
			}
		}, interval, interval);
	}
	public void refreshAction() {
		Timer timer=new Timer();
		int interval=80;
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				repaint();		
			}
		}, 0,interval);
	}
	public void paint(Graphics g) {
		if(state==START) {
			start.paintObject(g);
		}else if(state==RUNNING) {
			running.paintObject(g);
		}else if(state==GAME_OVER) {
			gameOver.paintObject(g);
		}		
		synchronized (plants) {
			for(Plant p:plants) {
				p.paintObject(g);
			}
		}
		synchronized (plantsLife) {
			for(Plant p:plantsLife) {	
				p.paintObject(g);
			}
		}
		synchronized (zombies) {
			for(Zombie z:zombies) {
				z.paintObject(g);
				if(z.isDead()) {
					z.paintHead(g);
				}
			}
		}
		synchronized (bullets) {
			for(Bullet b:bullets) {
				if(b.isFire()) {
					b.paintObjectFire(g);
				}else if(b.isDead()){
					b.paintObjectDead(g);
				}else {
					b.paintObject(g);
				}
				
			}	
		}
		synchronized (shovels) {
			for(Shovel s:shovels) {
				s.paintObject(g);
			}
		}	
	}
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		GamePlay play=new GamePlay();
		frame.add(play);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT+40);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); 
		frame.setTitle("植物大战僵尸");
		play.action();
		play.refreshAction();
		Runnable r = new ZombieAubio("bgm.wav");
		Thread t = new Thread(r);
		t.start();
	}
}
