package pac_Man;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;
//得分面板
@SuppressWarnings("serial")
public class ScorePanel extends JPanel{
	public Image img=Toolkit.getDefaultToolkit().getImage("image/eater_0_l.png");
	public int life_count=5; //命数
	public int score;//分数
	public ScorePanel(){
		Eater.life=5;//初始化生命
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("",Font.BOLD,20));
		//1.背景
		setBackground(Color.BLACK);

		//2.得分
		g.drawString("Scroe:", 15,30);
		g.drawString(""+(score-10), 80, 30);//第一颗球不计分

		//3.生命
		g.drawString("Lives:",210 , 30);
		for(int i=0;i<Eater.life;i++){
			g.drawImage(img, 270+i*35,8, 30,30,null);
		}

	} 
	public void action(){
		repaint();
	}
}
