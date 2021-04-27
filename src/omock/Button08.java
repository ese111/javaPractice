package omock;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Node {
	int y;
	int x;
	int num;
	int size;
}

class MyPanel extends JPanel implements ActionListener {

	final int SIZE = 10;
	
	JButton resetBtn;
	JButton winnerBtn;
	Node[][] list;
	JButton[][] btns;
	boolean turn;
	
	public MyPanel() {
		setLayout(null);
		
		basicSetting();
	}
	
	public void basicSetting() {
		resetBtn = new JButton("처음부터");
		resetBtn.setBounds(600, 50, 120, 50);
		resetBtn.addActionListener(this);
		add(resetBtn);
		
		winnerBtn = new JButton("winner");
		winnerBtn.setBounds(600, 100, 120, 50);
		winnerBtn.addActionListener(this);
		add(winnerBtn);
		
		list = new Node[SIZE][SIZE];
		btns = new JButton[SIZE][SIZE];
		
		for(int i=0; i<SIZE; i++) {
			for(int j=0; j<SIZE; j++) {
				list[i][j] = new Node();
				
				list[i][j].num = 0;
				
				list[i][j].size = 50;
				
				list[i][j].y = 50 + list[i][j].size*i;
				list[i][j].x = 50 + list[i][j].size*j;
			}
		}
		
		for(int i=0; i<SIZE; i++) {
			for(int j=0; j<SIZE; j++) {
				btns[i][j] = new JButton();
				
				btns[i][j].setBackground(Color.LIGHT_GRAY);
				btns[i][j].setBounds(list[i][j].x, list[i][j].y, list[i][j].size, list[i][j].size);
				btns[i][j].addActionListener(this);
				
				add(btns[i][j]);
			}
		}
	}
	
	public void resetGame() {
		turn = false;
		winnerBtn.setText("winner");
		for(int i=0; i<SIZE; i++) {
			for(int j=0; j<SIZE; j++) {
				list[i][j].num = 0;
				
				btns[i][j].setBackground(Color.WHITE);
				btns[i][j].setOpaque(false);
				btns[i][j].setBorderPainted(true);
			}
		}
	}

	int totalTester() {
		int win = 0;
		for(int i=0; i<SIZE; i++) {
			int cnt1 = 0;
			int cnt2 = 0;
			for(int j=0; j<SIZE; j++) {
				if(list[i][j].num == 1) {
					cnt1++;
					if(cnt1==5) {
						win = 1;
						break;
					}
				}else {
					cnt1=0;
				}
				if(list[i][j].num == 2) {
					cnt2++;
					if(cnt2==5) {
						win = 2;
						break;
					}
				}else {
					cnt2=0;
				}
			}
		}
		for(int i=0; i<SIZE; i++) {
			int cnt1 = 0;
			int cnt2 = 0;
			for(int j=0; j<SIZE; j++) {
				if(list[j][i].num == 1) {
					cnt1++;
					if(cnt1>=5) {
						win = 1;
						break;
					}
				}else {
					cnt1=0;
				}
				if(list[j][i].num == 2) {
					cnt2++;
					if(cnt2>=5) {
						win = 2;
						break;
					}
				}else {
					cnt2=0;
				}
			}
		}
		for(int i=0; i<SIZE - 4; i++) {
			for(int j=0; j<SIZE - 4; j++) {
				if(	list[i][j].num == 1 
					&& list[i + 1][j + 1].num == 1
					&& list[i + 2][j + 2].num == 1
					&& list[i + 3][j + 3].num == 1
					&& list[i + 4][j + 4].num == 1) {
					win = 1;
				}
				
				if(	list[i][j].num == 2 
					&& list[i + 1][j + 1].num == 2
					&& list[i + 2][j + 2].num == 2
					&& list[i + 3][j + 3].num == 2
					&& list[i + 4][j + 4].num == 2) {
					win = 2;
				}
			}
		}
		for(int i=0; i<SIZE - 4; i++) {
			for(int j=4; j<SIZE; j++) {
				if(	list[i][j].num == 1 
					&& list[i + 1][j - 1].num == 1
					&& list[i + 2][j - 2].num == 1
					&& list[i + 3][j - 3].num == 1
					&& list[i + 4][j - 4].num == 1) {
					win = 1;
				}
				
				if(	list[i][j].num == 2 
					&& list[i + 1][j - 1].num == 2
					&& list[i + 2][j - 2].num == 2
					&& list[i + 3][j - 3].num == 2
					&& list[i + 4][j - 4].num == 2) {
					win = 2;
				}
			}
		}
		return win;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(resetBtn == e.getSource()) {
			resetGame();
		}
		
		for(int i=0; i<SIZE; i++) {
			for(int j=0; j<SIZE; j++) {
				if(btns[i][j] == e.getSource()) {
					if(list[i][j].num == 0) {
						if(!turn) {
							list[i][j].num = 1;
							btns[i][j].setBackground(Color.WHITE);
							btns[i][j].setOpaque(true);
							btns[i][j].setBorderPainted(false);
						}else {
							list[i][j].num = 2;
							btns[i][j].setBackground(Color.BLACK);
							btns[i][j].setOpaque(true);
							btns[i][j].setBorderPainted(false);
						}
					
						turn = !turn;
					}
				}
			}
			totalTester();
			if(totalTester()==1) {
				winnerBtn.setText("p1 win!!");
			}else if(totalTester()==2) {
				winnerBtn.setText("p2 win!!");
			}
		}
	}
	
}

public class Button08 {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("오목 게임");
		
		frame.setBounds(100, 100, 800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setContentPane(new MyPanel());

		frame.setVisible(true);
		
		frame.revalidate();
		
	}
}