package pac_Man;

import java.awt.Graphics;
import java.awt.Image;

public class MovingObject {
	public Image img; //图片	
	public int x; //画的x坐标
	public int y; //画的y坐标
	public int width; //对象画的宽度 
	public int height; //对象画的高度
	public int xStep; //移动时x坐标的增长幅度
	public int yStep; //移动时y坐标的增长幅度
	public int arrayX,arrayY;//此时对象所在下方对应的标签行列
	public int last_xStep;//用于记录此前移动状态
	public int last_yStep;
	public MovingObject(Image img, int x, int y, int width, int height, int xStep, int yStep) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xStep = xStep;
		this.yStep = yStep;
	}
	public void draw(Graphics g){ //画图
		g.drawImage(this.img,this.x,this.y,this.width,this.height,null);
	}

	public  void move(){
		if(this.x > GamePanel.LABEL_WIDTH*GamePanel.TOTAL_COLS-this.width  && this.xStep > 0)
			this.x=0-this.width/2+GamePanel.LABEL_WIDTH/2;
		else if(this.x <= 0 && this.xStep < 0)
			this.x=GamePanel.LABEL_WIDTH*GamePanel.TOTAL_COLS-GamePanel.LABEL_WIDTH/2-this.width/2;
		if(this.y > GamePanel.LABEL_HEIGHT*GamePanel.TOTAL_ROWS-this.height && this.yStep > 0)
			this.y=0-this.height/2+GamePanel.LABEL_HEIGHT/2;
		else if(this.y<=0 && this.yStep < 0)
			this.y=GamePanel.LABEL_HEIGHT*GamePanel.TOTAL_ROWS-GamePanel.LABEL_HEIGHT/2-this.height/2;
		this.x += xStep;
		this.y += yStep;
	}

	public void preventOutOfWall(BlockLabel[][] blockLabels,int w,int h){//仅当object的中心与某个标签中心重合时才能正确运行该方法,该方法可以判断是否撞墙并停止运动
		//数组下标的x，y与像素点位置y，x对应
		int y = (this.x+this.width/2-w/2)/w;//16为每个标签的宽度，求出的x，y为object中心所在标签 的数组下标。每个标签的下标为该标签左上角顶点除以长宽
		int x = (this.y+this.height/2-h/2)/h;
		if(this.xStep < 0){//若object在向左移动
			y--;//调整为左方标签
			if(! blockLabels[x][y].isGone){//若不能通过
				this.xStep=0;//停下
				this.yStep=last_yStep;//继续之前的竖向移动				
			}
		}
		else if(this.xStep > 0){//若object向右方移动
			y++;//调整为右方标签
			if(! blockLabels[x][y].isGone){//若不能通过
				this.xStep=0;//停下
				this.yStep=last_yStep;
			}
		}
		else if(this.yStep < 0){//若object向上移动
			x--;//调整为上方标签
			if(! blockLabels[x][y].isGone){//若不能通过
				this.yStep=0;//停下
				this.xStep=last_xStep;
			}
		}
		else if(this.yStep > 0){//若object向下移动
			x++;//调整为下方标签
			if(! blockLabels[x][y].isGone){//若不能通过
				this.yStep=0;//停下
				this.xStep=last_xStep;

			}
		}
	}
}
