package pac_Man;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GameFrame extends JFrame{ //游戏窗体
	public static final int FRAMEWIDTH=448+6;//窗体宽
	public static final int FRAMEHEIGHT=496+35+50; //窗体高
	public AudioClip audio;//背景音乐
	
	public GameFrame(){ //构造方法初始界面
		this.setTitle("吃豆人");
		 //448,496为游戏面板大小 448,50为得分面板大小
		this.setSize(FRAMEWIDTH,FRAMEHEIGHT);  //窗体大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ScorePanel sPanel =new ScorePanel(); //新建得分面板
		this.add(sPanel,BorderLayout.CENTER); //添加得分面板到窗体上
		GamePanel gPanel=new GamePanel(this,sPanel); //新建游戏面板，同时传入得分面板对象
		this.add(gPanel,BorderLayout.NORTH); //添加游戏面板到窗体上
		this.setResizable(false); //设置窗体不可改变大小
		this.setLocationRelativeTo(null); //窗体居中
		this.setVisible(true);//显示窗体
		//播放 “BGM” 在窗口面板中，新游戏开始时从头播放
		try {
			audio=Applet.newAudioClip(new File("audio/BGM.wav").toURI().toURL());
			audio.loop(); //audio.play();只播放一次
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		gPanel.action();//调用游戏面板程序
//		sPanel.action();//调用得分面板重画
		for(;;){//循环结束后等待用户输入空格
			if (gPanel.direction==-1)//空格赋值为-1
				break;
			try {
				Thread.sleep(1);//防止循环空转
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}				
	}	
}
