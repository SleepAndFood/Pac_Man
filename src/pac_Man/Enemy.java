package pac_Man;

import java.awt.Image;
import java.awt.Toolkit;

public class Enemy extends MovingObject {
	public int count=0;
	public int rcd=0;//保证先出的了监狱
	public static boolean isEat=false;//Easter是否吃了大力丸
	public static int time=0;//计时
	public Image img_old;//存档变换之前的图片
	public boolean isDie=false;//被吃掉的新鬼
	public Enemy(Image img, int x, int y, int width, int height, int xStep, int yStep) {
		super(img, x, y, width, height, xStep, yStep);
		arrayX =15;
		arrayY =13;
	}
	
	public  void isEat(){//当Easter吃了大力丸之后的行为
		if(time==1){
			img_old=img;//存档旧图
			xStep*=-1;yStep*=-1;
		}
		img=Toolkit.getDefaultToolkit().getImage("image/enemy_0.png");
	}
	
	public void randomChangeDirect(){//用于随机改变enemy的运动方向
		arrayY = (this.x+this.width/2-GamePanel.LABEL_WIDTH/2)/GamePanel.LABEL_WIDTH;//x，y为eater中心所在标签 的数组下标。
		arrayX = (this.y+this.height/2-GamePanel.LABEL_HEIGHT/2)/GamePanel.LABEL_HEIGHT;	
		if(GamePanel.blocks[arrayX][arrayY]==3){//判定为拐点标签	
			if(rcd<2)
				rcd++;//出监狱的拐点不判定
			else{
				if(xStep!=0){//横向移动时
					if(xStep>0){//初始右向
						if(GamePanel.blocks[arrayX][arrayY+1]!=0){//右边可通过
							xStep=2-(int)(Math.random()*2)*2;//只有停止或者继续走
						}else xStep=0;//不可通过置零
					}else if(xStep<0){//初始左向
						if(GamePanel.blocks[arrayX][arrayY-1]!=0){//左边可通过
							xStep=-2+(int)(Math.random()*2)*2;//只有停止或者继续走
						}else xStep=0;
					}
					if(xStep==0){
						if(GamePanel.blocks[arrayX-1][arrayY]!=0 && GamePanel.blocks[arrayX+1][arrayY]!=0)//此时上下均可通行
							yStep=-2+(int)(Math.random()*2)*4;//结果为-2或2
						else if(GamePanel.blocks[arrayX-1][arrayY]==0)//向上不通，则向下
							yStep=2;
						else yStep=-2;//都不通则向下
					}
				}else if(yStep!=0){//纵向移动时
					if(yStep>0){//初始下向
						if(GamePanel.blocks[arrayX+1][arrayY]!=0){//右边可通过
							yStep=2-(int)(Math.random()*2)*2;//只有停止或者继续走
						}else yStep=0;
					}else if(yStep<0){//初始上向
						if(GamePanel.blocks[arrayX-1][arrayY]!=0){//右边可通过
							yStep=-2+(int)(Math.random()*2)*2;//只有停止或者继续走
						}else yStep=0;
					}
					if(yStep==0){
						if(GamePanel.blocks[arrayX][arrayY-1]!=0 && GamePanel.blocks[arrayX][arrayY+1]!=0)//此时左右均可通行
							xStep=-2+(int)(Math.random()*2)*4;//结果为-2或2
						else if(GamePanel.blocks[arrayX][arrayY+1]==0)//向右不通，则向左
							xStep=-2;
						else xStep=2;//都不通则向右
					}
				}
			}
		}
	}
}
