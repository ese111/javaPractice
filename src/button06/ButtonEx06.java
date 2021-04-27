package button06;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class StopWatch extends Thread{
   static String timeText;
   
   long time = 0l; long preTime = 0l;   
    static boolean play;
   
    public void run() {
      preTime = System.currentTimeMillis();
      while(play) {
         try {
            sleep(10);
            time = System.currentTimeMillis() - preTime;
            int m = (int)(time / 1000.0 / 60.0);
            int s = (int)(time % (1000.0 * 60) / 1000.0);
            int ms = (int)(time % 1000 / 10.0);
            
            timeText = m + " : " + s + " : " + ms;
            
            
            if(MyPanel.gameStart) {
               MyPanel.time.setText("버튼을 누르면 스탑와치가 시작됩니다.");
            }else {
               MyPanel.time.setText(StopWatch.timeText);
            }
         } catch (InterruptedException e) {         
            e.printStackTrace();
         }
      }
   }
}

class MyPanel extends JPanel implements ActionListener {

   static boolean gameStart = true;
   int k;
   JButton mainPanel;
   JButton reStart;
   static JButton time;
   int gameNum;

   JButton[][] btns;
   int[] front;
   int[] back;

   public MyPanel() {
      setLayout(null);
      
      basicSetting();
      numShuffle();
      setTextOfBtn();
   }
   
   public void basicSetting() {
      
      gameNum = 1;
      
      mainPanel = new JButton("NEXT NUM = " + gameNum);
      mainPanel.setBounds(50, 10, 200, 40);
      mainPanel.setBackground(Color.WHITE);
      add(mainPanel);
      
      reStart = new JButton("게임 시작");
      reStart.setBounds(450, 600, 100, 30);
      reStart.addActionListener(this);
      add(reStart);
      
      time = new JButton();
      time.setBounds(300, 10, 250, 40);
      time.setBackground(Color.WHITE);
      add(time);
      
      
      btns = new JButton[5][5];
      front = new int[25];
      back = new int[25];
      
      for(int i=0; i<25; i++) {
         front[i] = i + 1;
         back[i] = i + 26;
      }
      
      for(int i=0; i<5; i++) {
    	 for(int j=0; j<5; j++) {
	         btns[i][j] = new JButton("");
	         btns[i][j].setSize(100, 100);
	         btns[i][j].setLocation(50 + 100*j, 50 + 100*i);
	         btns[i][j].addActionListener(this);
	         
	         add(btns[i][j]);
    	 }
      }
      
   }

   public void numShuffle() {
      Random ran = new Random();
      
      for(int i=0;i <25; i++) {
         int r = ran.nextInt(25);
         
         int temp = front[0];
         front[0] = front[r];
         front[r] = temp;
      }

      for(int i=0;i <25; i++) {
         int r = ran.nextInt(25);
         
         int temp = back[0];
         back[0] = back[r];
         back[r] = temp;
      }
   }
   
   public void setTextOfBtn() {
	   int x =0;
      for(int i=0; i<5; i++) {
    	  for(int j=0; j<5; j++) {
    		  btns[i][j].setText(front[x] + "");
    		  x++;
    	  }
      }
   }
   
   
   @Override
   public void actionPerformed(ActionEvent e) {
      
      if(gameStart) {
         StopWatch.play = true;

         StopWatch  st = new StopWatch ();
         st.start();
         
         gameStart = false;
      }
      
      if(reStart == e.getSource()) {
         gameStart = true;
         gameNum = 1;
         mainPanel.setText("NEXT NUM = " + gameNum);
         
         numShuffle();
         setTextOfBtn();
         StopWatch.play = false;
         
         MyPanel.time.setText("버튼을 누르면 스탑와치가 시작됩니다.");
      }
      for(int i=0; i<5; i++) {
    	  for(int j=0; j<5; j++) {
    		 if(btns[i][j] == e.getSource()) {
	            if(btns[i][j].getText().equals(gameNum + "")) {
	               if(1 <= gameNum && gameNum <= 25) {
	                  btns[i][j].setText(back[k] + "");
	                  k++;
	               }else {
	                  btns[i][j].setText("");
	               }
	               
	               gameNum++;
	               
	               mainPanel.setText("NEXT NUM = " + gameNum);
	            }
            }
         }
      }
      
      if(gameNum > 50) {
         StopWatch.play = false;
         mainPanel.setText("게임이 종료되었습니다.");
      }
      
   }
   
}


public class ButtonEx06 {
   public static void main(String[] args) {
      
      // 이 예제를 통해 1 to 50을 직접 만들어 보세요.
      
      
      JFrame frame = new JFrame("1 to 50");
      
      frame.setBounds(100, 100, 600, 700);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      frame.setContentPane(new MyPanel());

      frame.setVisible(true);
      
      frame.revalidate();
   }
}