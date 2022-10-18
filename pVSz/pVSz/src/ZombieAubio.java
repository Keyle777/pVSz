

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class ZombieAubio implements Runnable{
	private String fileName;
	
	public ZombieAubio(String fileName) {
		this.fileName=fileName;
	}
	@Override
	public void run() {
		File soundFile=new File(fileName);
		AudioInputStream ais=null;
		try {
			ais=AudioSystem.getAudioInputStream(soundFile);
		}catch (Exception e) {
			e.printStackTrace();
		}
		AudioFormat format=ais.getFormat();
		SourceDataLine auline=null;
		DataLine.Info info=new DataLine.Info(SourceDataLine.class, format);
		try{
			auline=(SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		auline.start();
		int nBytesRead=0;
	    //缓冲
	    byte[] abData=new byte[1024];

	    try{
	        while(nBytesRead!=-1){
	            nBytesRead=ais.read(abData,0,abData.length);
	            if(nBytesRead>=0)
	                auline.write(abData, 0, nBytesRead);
	        }
	    }catch(Exception e){
	        e.printStackTrace();
	        return;
	    }finally{
	        auline.drain();
	        auline.close();
	    }
	}

}
