package pac_Man;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

public class Eater extends MovingObject{
	public static int life=3;//用于记录命数
	public int time=0;//控制动图速度
	public char udlr='r';//上下左右-u,d,l,r
	public int restart_delay_count=0;

	public Eater(Image img, int x, int y, int width, int height, int xStep, int yStep) {
		super(img, x, y, width, height, xStep, yStep);
		
	}
	public void draw(Graphics g){ //画图
		switch (time%12) {//动图速度
		case 0:
			this.img=Toolkit.getDefaultToolkit().getImage("image/eater_0_"+udlr+".png");
			break;
		case 4:
			this.img=Toolkit.getDefaultToolkit().getImage("image/eater_1_"+udlr+".png");
			break;
		case 8:
			this.img=Toolkit.getDefaultToolkit().getImage("image/eater_2_"+udlr+".png");
			break;

		default:
			break;
		}
		time++;
		g.drawImage(this.img,this.x,this.y,this.width,this.height,null);
	}
	
	public boolean hittedByEnemy(Enemy enemy) {
		return (this.x-enemy.x>=-this.width+4 && this.x-enemy.x<=enemy.width-4 &&
				   this.y-enemy.y>=-this.height+4 && this.y-enemy.y<=enemy.height-4 );
	}
	
	public int getScore(BlockLabel[][] blockLabels, int w, int h){ //得到当前标签的分数并置换为道路
		int y = (this.x+this.width/2-w/2)/w;//16为每个标签的宽度，求出的x，y为eater中心所在标签 的数组下标。每个标签的下标为该标签左上角顶点除以长宽
		int x = (this.y+this.height/2-h/2)/h;
		blockLabels[x][y].setIcon(new ImageIcon("image/blank.png"));//将当前标签的图片替换为道路
		int score = blockLabels[x][y].label_score;//获得当前标签的分数
		blockLabels[x][y].label_score=0;//变成道路后分值为0
		
		if(GamePanel.blocks[x][y]==5){//如果吃到大力豆
			Enemy.isEat=true;//标记为真
			GamePanel.blocks[x][y]=1;//换成普通道路
			music();//调用音效
		}
		return score;
	}
	private void music() {
		try {//吃大力丸的音效
			AudioClip audio_daliwan = Applet.newAudioClip(new File("audio/daliwan.wav").toURI().toURL());
			audio_daliwan.play(); //audio.play();只播放一次   audio.loop();循环播放  audio.stop();停止播放
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		
	}
}



