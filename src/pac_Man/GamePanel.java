package pac_Man;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.swing.ImageIcon;
//0是墙  1是路  2是豆子
import javax.swing.JPanel;

//游戏面板
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener{
	public GameFrame gameFrame;//控制背景音乐
	public static final int LABEL_WIDTH=16;//标签长度
	public static final int LABEL_HEIGHT=16;//标签宽度
	public static final int TOTAL_ROWS=31; //标签行数
	public static final int TOTAL_COLS=28; //标签列数
	public static final int GAMEPANEL_WIDTH=448;
	public static final int GAMEPANEL_HEIGHT=496;
	public static int ENEMY_COUNT=4;//敌人的最大数量
	public static int size_x=26;//图形长宽
	public static int size_y=26;
	public Vector<Enemy> enemys=new Vector<Enemy>();//敌人的容器
	public Image back_img=Toolkit.getDefaultToolkit().getImage("image/background.png");//背景图
	public BlockLabel[][] blockLabels=new BlockLabel[TOTAL_ROWS][TOTAL_COLS];//标签数组
	public Eater eater;//构建eater对象
	boolean flag=false; //延时标记
	boolean eater_isHitted=false; //标记eater是否碰撞标志
	public ScorePanel sPanel;//连接ScorePanel面板
	public AudioClip audio_end,audio_success,audio_hit,audio_respawn,audio_chigui,audio_daliwan;//结束音乐，通关音乐，被吃掉的音乐
	public boolean isGameOver=false; //游戏是否结束
	public boolean isWin=false; //游戏是否成功
	public boolean egg,egg_0,egg_1,egg_2=false;
	public int direction = 0;//eater接下来要走的方向，1，2，3，4，分别为上，下，左，右,0为停下
	public static int blocks[][]={//0为墙，1为普通道路，2为有豆子的路，3为判断拐点，5为大力豆
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,3,2,2,2,2,3,2,2,2,2,2,3,0,0,3,2,2,2,2,2,3,2,2,2,2,3,0},
			{0,2,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,2,0},
			{0,5,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,5,0},
			{0,2,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,2,0},
			{0,3,2,2,2,2,3,2,2,3,2,2,3,2,2,3,2,2,3,2,2,3,2,2,2,2,3,0},
			{0,2,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,2,0},
			{0,2,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,2,0},
			{0,3,2,2,2,2,3,0,0,3,2,2,3,0,0,3,2,2,3,0,0,3,2,2,2,2,3,0},
			{0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,3,2,2,3,3,3,3,2,2,3,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,2,0,0,0,1,1,0,0,0,2,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,2,0,3,3,3,3,3,3,0,2,0,0,2,0,0,0,0,0,0},
			{1,1,1,1,1,1,3,2,2,3,0,3,1,1,1,1,3,0,3,2,2,3,1,1,1,1,1,1},
			{0,0,0,0,0,0,2,0,0,2,0,3,3,3,3,3,3,0,2,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,3,2,2,2,2,2,2,2,2,3,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0},
			{0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0},
			{0,3,2,2,2,2,3,2,2,3,2,2,3,0,0,3,2,2,3,2,2,3,2,2,2,2,3,0},
			{0,2,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,2,0},
			{0,2,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,2,0},
			{0,3,5,3,0,0,3,2,2,3,2,2,3,2,2,3,2,2,3,2,2,3,0,0,3,5,3,0},
			{0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,2,0,0,2,0,0,0},
			{0,0,0,2,0,0,2,0,0,2,0,0,0,0,0,0,0,0,2,0,0,2,0,0,2,0,0,0},
			{0,3,2,3,2,2,3,0,0,3,2,2,3,0,0,3,2,2,3,0,0,3,2,2,3,2,3,0},
			{0,2,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,2,0},
			{0,2,0,0,0,0,0,0,0,0,0,0,2,0,0,2,0,0,0,0,0,0,0,0,0,0,2,0},
			{0,3,2,2,2,2,2,2,2,2,2,2,3,2,2,3,2,2,2,2,2,2,2,2,2,2,3,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	}; //你绝望吗

	public GamePanel(GameFrame gameFrame,ScorePanel sPanel){ //初始化游戏面板
		blocks[3][1]=5;blocks[3][26]=5;blocks[23][2]=5;blocks[23][25]=5;//将大力丸重置
		this.sPanel=sPanel;
		this.gameFrame=gameFrame;
		this.setLayout(new GridLayout(TOTAL_ROWS,TOTAL_COLS)); //设置面板布局为表格布局
		assign(blocks);//初始化BolckLabel数组
		eater = new Eater(Toolkit.getDefaultToolkit().getImage("image/eater_0_r.png"), LABEL_HEIGHT+LABEL_HEIGHT/2-size_x/2,LABEL_HEIGHT+LABEL_HEIGHT/2-size_y/2, size_x, size_y, 0, 0);//初始化eater
		for (int i = 1; i <= ENEMY_COUNT; i++) {//初始化敌人
			enemys.add(new Enemy(Toolkit.getDefaultToolkit().getImage("image/enemy0"+i+".png"), LABEL_HEIGHT*13+LABEL_HEIGHT/2-size_x/2, LABEL_HEIGHT*15+LABEL_HEIGHT/2-size_x/2, size_x, size_y, 0, -1));
		}
		this.addKeyListener(this);//附加键盘监听器
		this.setFocusable(true);//得到焦点
	}

	@Override
	public void paint(Graphics g) {
		// TODO 自动生成的方法存根
		super.paint(g);
		//1.画游戏背景
		g.drawImage(back_img, 0,0,448, 496, this);

		//2.画eater
		eater.draw(g);

		//3.画所有敌人
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).draw(g);
		}

		//4.碰撞后重置画面
		if(flag&&!isGameOver){
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.BOLD,30));
			g.drawString("ready!",180 , 240);
			flag=false;
		}

		//5.失败画面
		if(isGameOver){
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.BOLD,60));
			g.drawString("GAME OVER", GAMEPANEL_WIDTH/2-180, GAMEPANEL_HEIGHT/2);
		}

		//6.成功画面
		if(isWin){
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.BOLD,60));
			g.drawString("SUCCESS", GAMEPANEL_WIDTH/2-145, GAMEPANEL_HEIGHT/2);
		}
		
		//7.彩蛋画面
		if(egg){
			g.setColor(Color.WHITE);
			g.setFont(new Font("",Font.BOLD,30));
			g.drawString("阿姆斯特朗小队制作", GAMEPANEL_WIDTH/2-145, GAMEPANEL_HEIGHT/2);
		}

	}
	public void action(){
		while(!isGameOver && !isWin){//当游戏结束或者胜利时停止一切动作，退出循环
			if((eater.x+eater.width/2-LABEL_WIDTH/2)%LABEL_WIDTH == 0 && (eater.y+eater.height/2-LABEL_HEIGHT/2)%LABEL_HEIGHT == 0){//当eater中心点与方块标签中心点重合时
				eater.last_xStep=eater.xStep;
				eater.last_yStep=eater.yStep;
				changeDirect(direction);//改变方向
				eater.preventOutOfWall(blockLabels,LABEL_WIDTH,LABEL_HEIGHT);//检查是否撞墙,顺便吃掉豆子
				eater.preventOutOfWall(blockLabels,LABEL_WIDTH,LABEL_HEIGHT);//假如上一次检测失败，矫正位置后再次检测
				sPanel.score += eater.getScore(blockLabels,LABEL_WIDTH,LABEL_HEIGHT);//得分面板改变分数
				sPanel.action();//得分面板的重绘
			}
			eater.move();//传送门检查
			if(Enemy.isEat){//是否执行吃掉大力丸的操作
				if(Enemy.time>300){//计时
					Enemy.isEat=false;
					Enemy.time=0;
				}
				else {Enemy.time++;
					for (int i = 0; i < enemys.size(); i++) { 
						enemys.get(i).isEat();					
						if(Enemy.time>=300 || enemys.get(i).isDie){//结束前换回去图片
							enemys.get(i).img=enemys.get(i).img_old;
						}
					}
				}
			}
			
			for (int i = 0; i < enemys.size(); i++) { 
				if((enemys.get(i).x+enemys.get(i).width/2-LABEL_WIDTH/2)%LABEL_WIDTH == 0 && (enemys.get(i).y+enemys.get(i).height/2-LABEL_HEIGHT/2)%LABEL_HEIGHT == 0){//当enemy中心点与方块标签中心点重合时
					enemys.get(i).randomChangeDirect();//enemy的随机改变方向方法
					enemys.get(i).preventOutOfWall(blockLabels,LABEL_WIDTH,LABEL_HEIGHT);//检查是否撞墙
					enemys.get(i).preventOutOfWall(blockLabels,LABEL_WIDTH,LABEL_HEIGHT);//检查是否撞墙
				}
				enemys.get(i).move();//enemy的move方法
			}

			//碰撞检测
			for(int i=0;i<enemys.size();i++){
				Enemy enemy=enemys.get(i);
				
				if(eater.hittedByEnemy(enemy) && !isGameOver &&!isWin){ //游戏没有结束被撞
				if(Enemy.isEat){//大力豆状态
						//移除吃掉的鬼
						enemy.img=enemy.img_old;
						enemy.isDie=true;
						enemy.x=LABEL_HEIGHT*13+LABEL_HEIGHT/2-size_x/2;
						enemy.y=LABEL_HEIGHT*15+LABEL_HEIGHT/2-size_x/2;
						try {//吃掉鬼的音效
							audio_chigui=Applet.newAudioClip(new File("audio/chigui.wav").toURI().toURL());
							audio_chigui.play(); //audio.play();只播放一次   audio.loop();循环播放  audio.stop();停止播放
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}else
						eater_isHitted=true;
				}
				if(eater_isHitted){
					Eater.life--; //eater生命减1
					eater.x=LABEL_HEIGHT+LABEL_HEIGHT/2-size_x/2;
					eater.y=LABEL_HEIGHT+LABEL_HEIGHT/2-size_y/2;
					if(Eater.life==0){
						isGameOver=true; //游戏结束状态为true
						eater.y=-100; //移除边界外
						eater.yStep=0;
						//游戏结束音效
						try {
							gameFrame.audio.stop();//背景音乐停
							audio_end=Applet.newAudioClip(new File("audio/dead.wav").toURI().toURL());
							audio_end.play(); //audio.play();只播放一次   audio.loop();循环播放  audio.stop();停止播放
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}

					}else{
						try {//被怪物吃掉的音效
							audio_hit=Applet.newAudioClip(new File("audio/ghost_eaten.wav").toURI().toURL());
							audio_hit.play(); //audio.play();只播放一次   audio.loop();循环播放  audio.stop();停止播放
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
					eater_isHitted=false;
					flag=true; 
					sPanel.action(); //ScorePanel重画出生命值减1
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					enemys.clear();
					for (int j = 1; j <= ENEMY_COUNT; j++) {//初始化敌人
						enemys.add(new Enemy(Toolkit.getDefaultToolkit().getImage("image/enemy0"+j+".png"), LABEL_HEIGHT*13+LABEL_HEIGHT/2-size_x/2, LABEL_HEIGHT*15+LABEL_HEIGHT/2-size_x/2, size_x, size_y, 0, -1));
					}
					repaint();

					try {//重新出生的音效
						audio_respawn=Applet.newAudioClip(new File("audio/respawn.wav").toURI().toURL());
						audio_respawn.play(); //audio.play();只播放一次   audio.loop();循环播放  audio.stop();停止播放
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
			}

			if(sPanel.score==2880-10){//成功的判定
				isWin=true;
				try {//胜利通关的音效
					audio_success=Applet.newAudioClip(new File("audio/win.wav").toURI().toURL());
					audio_success.play(); //audio.play();只播放一次   audio.loop();循环播放  audio.stop();停止播放
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			
			//彩蛋
			if(eater.x==251 && eater.y==203){
				egg_0=true;
			}
			if(egg_0 && eater.x==251 && eater.y==235 ){
				egg_0=false;
				egg_1=true;
			}
			if(egg_1 && eater.x==171 && eater.y==235){
				egg_2=true;
				egg_1=false;
			}
			if(egg_2 && eater.x==171 && eater.y==203){
				egg=true;
				egg_2=false;
				repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {						
					e.printStackTrace();
				}
			}
			egg=false;
			repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void assign(int[][] blocks){//用block数组对blockLabels数组进行赋值
		for(int i=0;i<TOTAL_ROWS;i++){
			for(int j=0;j<TOTAL_COLS;j++){
				blockLabels[i][j]=new BlockLabel(i,j);//新建BlockLabel对象
				if(blocks[i][j]==0){//若为0,赋值为墙且不可通过
					blockLabels[i][j].setIcon(new ImageIcon("image/blank.png"));
					blockLabels[i][j].isGone = false;
					blockLabels[i][j].label_score=0;
				}else if(blocks[i][j]==2){//若为2，赋值为食物且可通过
					blockLabels[i][j].setIcon(new ImageIcon("image/food.png"));
					blockLabels[i][j].isGone = true;
					blockLabels[i][j].label_score=10;
				}
				else if(blocks[i][j]==1){//若为1，则可通过
					blockLabels[i][j].isGone = true;
					blockLabels[i][j].label_score=0;
				}
				else if(blocks[i][j]==3){//若为3，赋值为食物且可通过,用作拐点判断
					blockLabels[i][j].setIcon(new ImageIcon("image/food.png"));
					blockLabels[i][j].isGone = true;
					blockLabels[i][j].label_score=10;
				}else if(blocks[i][j]==5){//若为5，赋值为”大“食物且可通过
					blockLabels[i][j].setIcon(new ImageIcon("image/food_B.png"));
					blockLabels[i][j].isGone = true;
					blockLabels[i][j].label_score=10;
				}
				this.add(blockLabels[i][j]);//将标签添加进面板

			}
		}
	}

	public void changeDirect(int direction){//该方法用于改变方向
		switch(direction){
		case 0://0为不动
			eater.xStep=0;
			eater.yStep=0;
			break;
		case 1://1为向上移动
			eater.xStep=0;
			eater.yStep=-2;
			eater.udlr='u';
			break;
		case 2://2为向下移动
			eater.xStep=0;
			eater.yStep=2;
			eater.udlr='d';
			break;
		case 3://3为向左移动
			eater.xStep=-2;
			eater.yStep=0;
			eater.udlr='l';
			break;
		case 4://4为向右移动
			eater.xStep=2;
			eater.yStep=0;
			eater.udlr='r';
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isWin && !isGameOver){
			switch(e.getKeyCode()){//当键盘按下时获得数据并为direction赋值，"上下左右"按键分别对应"1234"
			case KeyEvent.VK_UP:
				direction = 1;
				break;
			case KeyEvent.VK_DOWN:
				direction = 2;
				break;
			case KeyEvent.VK_LEFT:
				direction = 3;
				break;
			case KeyEvent.VK_RIGHT:
				direction = 4;
				break;
			case KeyEvent.VK_W:
				direction = 1;
				break;
			case KeyEvent.VK_S:
				direction = 2;
				break;
			case KeyEvent.VK_A:
				direction = 3;
				break;
			case KeyEvent.VK_D:
				direction = 4;
				break;

			}
		}else{
			switch(e.getKeyCode()){//当键盘获取值为空格时，赋值direction为-1
			case KeyEvent.VK_SPACE:
				direction = -1;
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


}
