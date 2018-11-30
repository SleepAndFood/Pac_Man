package pac_Man;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BlockLabel extends JLabel{
	public boolean isGone;//标签是否能通过
	public int rowNum; //标签所在方格的行号
	public int colNum; //标签所在方格的列号
	public int label_score;//表示该标签的分值
	public BlockLabel(int rowNum, int colNum) {
		this.rowNum = rowNum;
		this.colNum = colNum;
	}
}
