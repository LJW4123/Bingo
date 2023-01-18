package Bingo;


import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Bingo1 extends JFrame {
	double WinNum;
	String Winner;
	int play;
	ArrayList<String> rate = new ArrayList<>();
	
	Container frame = this.getContentPane();
	Container frame1= this.getContentPane();
	Random random = new Random();
	JPanel [] panel;
	JLabel[] label;
	int WordNum=0;
	Scanner scan = new Scanner(System.in);
	
		int Size=0;
		int bingoCount=0;
		int C_bingoCount=0;
		private static String com [][];
		private String[][]  computer;
		private String [][]  player;
		private Map<String,String> spell = new HashMap<>();
		private String[] Value ;
		
	void parts() {
		
		File file = new File("quiz.txt");
		try {	
				FileReader reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
			while(true) {
				
				String str = br.readLine();
				WordNum++;
				if(str==null) {
					break;}
				String[] temp = str.split("\t");
				this.addWord(new Word(temp[0].trim(), temp[1].trim()));
				
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("파일을 다시 확인해 주세요.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("o");
		}
		setRandom();
		
	}



	private void addWord(Word word) {
		// TODO Auto-generated method stub
		spell.put(word.eng,word.kor);
		
	}


	private void setRandom() {
		
		String temp[]=new String[spell.size()];
		 com=new String [Size][Size];
		 int index=0;
		Value = new String[Size*Size];
		label = new JLabel[Size*Size];
		panel= new JPanel[Size*Size];
		Iterator<String> keys = spell.keySet().iterator();
		int rand [] = new int [Size*Size];
		for(int q=0; q<rand.length;q++) {
			rand[q]=999;
		}
		int i =0;
        while (keys.hasNext()){
        	temp[i]=keys.next();
        	i++;
        }
		
        for(int j=0; j<Size*Size;j++) {
        	int randomNum=random.nextInt(spell.size());
        	for(int z=0; z<rand.length; z++) {
        		if (rand[z]==randomNum) {
        			randomNum=random.nextInt(spell.size());
        			z=0;
        		}
        	}
        	Value[j]=temp[randomNum];
        	rand[j]=randomNum;
        }
        
        for(int q=0;q<Size;q++) {
        	for(int w=0;w<Size;w++) {
        		com[q][w]="o";
        	}
        }
        
        String C_temp[] = new String[Size*Size];
        for(int q=0;q<C_temp.length;q++) {
        	C_temp[q]="O";
        }
        
        for(int q=0;q<C_temp.length;q++) {
        	int randomNum=random.nextInt(spell.size());
        	String randomWord=temp[randomNum];
        	C_temp[q]=randomWord;
        	for(int w=0;w<q;w++) {
        	if(C_temp[w]==C_temp[q]) {
        		q=q-1;
        		break;
        	}
        	
        }
        }
        
        for(int j=0; j<Size; j++) {
        	for(int k=0; k<Size;k++) {
        		com[j][k]=C_temp[index];
        		index++;
        	}
        }
       
        
	}
	
	public Bingo1(String title) {
		super(title);
		play++;
		System.out.print("빙고 크기를 정해주세요(3~6):");
		for(int i=0; i<1; i++) {
			try {
		Size= scan.nextInt();
		if((Size<3)||(Size>6)) {
			System.out.println("다시 입력해 주세요 :");
			i--;
		}
		}catch(InputMismatchException e) {
			System.out.print("잘못된 입력입니다. 다시 입력하세요 : ");
			i--;
			scan.nextLine();
		}
		}
		scan.nextLine();
		computer = new String[Size+1][Size+1];
		player = new String[Size+1][Size+1];
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		parts();
		init();
		this.setVisible(true);
		
		
		
		while(true) {
		C_choose();
		choose();
		checkBingo();
		if(checkBingo()==true) {
			break;
		}
		}
		
		if(bingoCount>C_bingoCount) {
		Winner="Player 승리";
		
		}else if(bingoCount<C_bingoCount) {
			Winner="Computer 승리";
		}
		else {
			Winner="무승부";
		}
		
		
		
		try {
			File file = new File("storage.txt");
			BufferedWriter BufferedWriter = new BufferedWriter(new FileWriter("storage.txt",true));
			if(bingoCount>C_bingoCount) {
				
				BufferedWriter.write("\n"+"1");
				
				}else {
					
					BufferedWriter.write("\n"+"0");
					
				}
			
			BufferedWriter.close();
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		
		
		try {	
				FileReader reader = new FileReader("storage.txt");
				BufferedReader br = new BufferedReader(reader);
				
				while(true) {
					
					String str = br.readLine();
					
					if(str==null) {
						break;}
					
					rate.add(str);
					
				
				}
				
		}catch(FileNotFoundException e) {
			System.out.println("파일을 다시 확인해 주세요.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("X");;}
		
		double q=0;
		
		
		for(int j =0; j<rate.size(); j++) {
			
			if (rate.get(j).trim().equals("1")) {
				WinNum++;
				
			}
			q++;
		}
		
		double fin=0;
		q=q-1;
		
		
		if(WinNum==0) {
		fin =0;
		
		
		}else {
			fin=(WinNum/q)*100;
			
		}
		
		if(Winner=="Computer 승리") {
			fin=100-fin;
		}
		
		
		if(Winner!="무승부") {
		JOptionPane.showMessageDialog(this,"결과는"+Winner+"\n승률은"+fin+"%"+"입니다.","Message",JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this,"결과는"+Winner+"입니다.","Message",JOptionPane.INFORMATION_MESSAGE);
		}
		
		System.exit(0);
	}


	

	 boolean checkBingo() {
		// TODO Auto-generated method stub
		this.bingoCount=0;
		int garocnt=0;//가로
		int seroCnt = 0; //세로
		int crossCnt1 = 0; //대각선
		int corssCnt2 = 0;//역대각
		this.C_bingoCount=0;
		int C_garocnt=0;
		int C_seroCnt = 0; 
		int C_crossCnt1 = 0; 
		int C_corssCnt2 = 0;
		for(int i = 0; i <  player.length; i++){
			   for(int j = 0; j < player[i].length; j++){
			     if(player[i][j] == "O"){//가로검사
			      garocnt++;
			  
			     }
			     if(player[j][i]=="O"){//세로검사
			      seroCnt++;
			     
			     }
			     if( i==j && player[i][j]=="O"){//대각선검사
			       crossCnt1++;
			       
			     }
			     if( ((player.length - 1)-i) == j  && player[i][j]=="O"){//역대각선 검사
			      corssCnt2++;
			      
			      
			     }
			   }
		
			   if(garocnt == player.length-1) bingoCount++;
			   if(seroCnt == player.length-1) bingoCount++;
			   
			   garocnt = 0;
			   seroCnt = 0;
			  }
			   if(crossCnt1 == player.length-1) bingoCount++;
			   if(corssCnt2 == player.length-1) bingoCount++;
			   
			   //com 검사
			   
			   for(int i = 0; i <  computer.length; i++){
				   for(int j = 0; j < computer[i].length; j++){//가로검사
				     if(computer[i][j] == "O"){
				      C_garocnt++;
				  
				     }
				     if(computer[j][i]=="O"){//세로검사
				    	 C_seroCnt++;
				     
				     }
				     if( i==j && computer[i][j]=="O"){//대각선검사
				    	 C_crossCnt1++;
				       
				     }
				     if( ((computer.length - 1)-i) == j  && computer[i][j]=="O"){//역대각선 검사
				      C_corssCnt2++;
				      
				      
				     }
				   }
				   if(C_garocnt == computer.length-1) C_bingoCount++;
				   if(C_seroCnt == computer.length-1) C_bingoCount++;
				   C_garocnt = 0;
				   C_seroCnt = 0;
				  }
			   if(C_crossCnt1 == computer.length-1) C_bingoCount++;
			   if(C_corssCnt2 == computer.length-1) C_bingoCount++; 
			  
			   boolean bingo=false;
			   
			   if((bingoCount>C_bingoCount)||(bingoCount<C_bingoCount)) {
				   bingo=true;
			   }
			   
			   int count=0;
			   int C_count=0;
			   
			  for(int j=0; j<Size; j++) {
				  for(int k=0;k<Size; k++) {
					  if(com[j][k].equals("O")){
						  C_count++;
					  }
				  }
			  }
			  
			  for(int j=0; j<Size; j++ ) {
				  if(Value[j].equals("O")) {
					  count++;
				  }
			  }
			  
			   if((count==Size*Size)||(C_count==Size*Size)) {
				   bingo=true;
			   }
			   
			   
			  return bingo;
	}



	private void C_choose() {
		// TODO Auto-generated method stub
		int row=0,col=0;
		int num=0;
		int alpha=0;
		int ad_garo=0;
		int ad_sero=0;
		int C_garocnt=0;
		int C_seroCnt = 0; 
		int C_crossCnt1 = 0; 
		int C_crossCnt2 = 0;
		
		
		
		if(C_crossCnt1==computer.length-1) {
			C_crossCnt1=0;
		}
		if(C_crossCnt2==computer.length-1) {
			C_crossCnt2=0;
		}
		if((C_crossCnt1==computer.length-1)||(C_crossCnt2==computer.length-1)) {
			row=0;
			col=1;
			num=5;
			if(com[0][1]=="O") {
				col=2;
			}
		}
		
		for(int i = 0; i <  computer.length; i++){
			   for(int j = 0; j < computer[i].length; j++){//가로검사
			     if(computer[i][j] == "O"){
			      C_garocnt++;
			  alpha=j;
			     }
			     if(computer[j][i]=="O"){//세로검사
			      C_seroCnt++;
			     alpha=j;
			     }
			     if( i==j && computer[i][j]=="O"&&C_crossCnt1!=computer.length){//대각선검사
			       C_crossCnt1++;
			       
			     }
			     if( ((Size - 1)-i) == j  && computer[i][j]=="O"&&C_crossCnt2!=computer.length){//역대각선 검사
			      C_crossCnt2++;
			      
			      
			     }
			   }
			   
			   if(C_crossCnt1==computer.length-1) {
					C_crossCnt1=0;
				}
				if(C_crossCnt2==computer.length-1) {
					C_crossCnt2=0;
				}
			   if(C_garocnt>ad_garo) {
				   ad_garo=C_garocnt;
				   row=i;
				   col=alpha;
			   }
			   if(C_seroCnt>ad_sero) {
				   ad_sero=C_seroCnt;
				   row=alpha;
				   col=i;
			   }
			   
			   C_garocnt=0;
			   C_seroCnt=0;
		}
		
		if(C_crossCnt1==computer.length-1) {
			C_crossCnt1=0;
		}
		if(C_crossCnt2==computer.length-1) {
			C_crossCnt2=0;
		}
	
		
		if(num!=0) {
			num=num;
		}
		else if((ad_garo>=ad_sero)&&(ad_garo>C_crossCnt1)&&(ad_garo>C_crossCnt2)) {
			num=1;
		}else if((ad_sero> ad_garo)&&(ad_sero>C_crossCnt1)&&(ad_sero>C_crossCnt2)) {
			num=2;
		}else if((C_crossCnt1>= ad_sero)&&(C_crossCnt1>=ad_garo)&&(C_crossCnt1>=C_crossCnt2)) {
			num=3;
		}else if((C_crossCnt2>= ad_sero)&&(C_crossCnt2>C_crossCnt1)&&(C_crossCnt2>=ad_garo)) {
			num=4;
		}
		//1가로,2세로,3대각,4역대각
		
		switch(num) {
		
		case 1: if((ad_garo>=1)&&(ad_garo<Size)) {
			
					   if((col+1<Size)&&(computer[row][col] == "O")&&(computer[row][col+1]!="O")){
						   row=row;
						   col=col+1;
					   }else if((col-1>=0)&&(computer[row][col] == "O")&&(computer[row][col-1]!="O")){
						   row=row;
						   col=col-1;
						   
						   
						   
				   }
					   
			String ComNum=com[row][col];
			if(ComNum=="O") {
				for(int j=0; j<1; j++) {
					 row=random.nextInt(Size);
					 col=random.nextInt(Size);
					 if(com[row][col]=="O") {
						 j--;
					 }
					}
			}
			ComNum=com[row][col];
			com[row][col]="O";
			computer[row][col]="O";
			
			for(int q=0; q<Value.length;q++) {
			if(ComNum.equals(Value[q])) {
				label[q].setText("O");
				Value[q]="O";
				int a=0,b=0;
				if (q!=0) {
				a=q/Size;
				b=(q%Size);
				}
				if(b==-1) {
					b=Size;
					a=a-1;
				}
				
				player[a][b]="O";
				
				
			}
			}
			
			System.out.println("=================");
			break;
			
		}
		
		
		case 2: if((ad_sero>=1)&&(ad_sero<Size)) {
			
					   if((row+1<Size)&&(computer[col][row] == "O")&&(computer[col][row+1]!="O")){
						   row=col;
						   col=row+1;
					   }else if((row-1>=0)&&(computer[col][row] == "O")&&(computer[col][row-1]!="O")){
						   row=col;
						   col=row-1;
				   }
			String ComNum=com[row][col];
			if(ComNum=="O") {
				for(int j=0; j<1; j++) {
					 row=random.nextInt(Size);
					 col=random.nextInt(Size);
					 if(com[row][col]=="O") {
						 j--;
					 }
					}
			}
			ComNum=com[row][col];
			com[row][col]="O";
			computer[row][col]="O";
			
			for(int q=0; q<Value.length;q++) {
			if(ComNum.equals(Value[q])) {
				label[q].setText("O");
				Value[q]="O";
				int a=0,b=0;
				if (q!=0) {
				a=q/Size;
				b=(q%Size);
				}
				if(b==-1) {
					b=Size;
					a=a-1;
				}
				
				player[a][b]="O";
				
				
			}
			}
			System.out.println("Com이  "+ComNum+"("+spell.get(ComNum)+")"+"를 선택했습니다.");
			System.out.println("=================");
			break;
		}
		
		case 3: if((C_crossCnt1>=0)&&(C_crossCnt1<Size)) {
			for(int i = 0; i <  computer.length; i++){
				   for(int j = 0; j < computer[i].length; j++){
					   if((i==j)&&(i+1<Size)&&(computer[i][j] == "O")&&(computer[i+1][j+1]!="O")){
						   row=i+1;
						   col=j+1;
					   }else if((i==j)&&(i-1>=0)&&(computer[i][j] == "O")&&(computer[i-1][j-1]!="O")){
						   row=i-1;
						   col=j-1;
				   }else {
					   continue;
				   }
				   }
			}
			String ComNum=com[row][col];
			if(ComNum=="O") {
				for(int j=0; j<1; j++) {
					 row=random.nextInt(Size);
					 col=random.nextInt(Size);
					 if(com[row][col]=="O") {
						 j--;
					 }
					}
			}
			ComNum=com[row][col];
			com[row][col]="O";
			computer[row][col]="O";
			
			for(int q=0; q<Value.length;q++) {
			if(ComNum.equals(Value[q])) {
				label[q].setText("O");
				Value[q]="O";
				int a=0,b=0;
				if (q!=0) {
				a=q/Size;
				b=(q%Size);
				}
				if(b==-1) {
					b=Size;
					a=a-1;
				}
				
				player[a][b]="O";
				
				
			}
			}
			System.out.println("Com이  "+ComNum+"("+spell.get(ComNum)+")"+"를 선택했습니다.");
			System.out.println("=================");
			break;
		}
		
		case 4 : if((C_crossCnt2>=0)&(C_crossCnt2<Size)) {
			for(int i = 0; i <  computer.length; i++){
				   for(int j = 0; j < computer[i].length; j++){
					   if((((computer.length - 1)-i) == j)&&(i+1<Size)&&(j-1>=0)&&(computer[i][j] == "O")&&(computer[i+1][j-1]!="O")){
						   row=i+1;
						   col=j-1;
					   }else  if((((computer.length - 1)-i) == j)&&(i-1>=0)&&(j+1<Size)&&(computer[i][j] == "O")&&(computer[i-1][j+1]!="O")){
						   row=i-1;
						   col=j+1;
				   }else {
					   continue;
				   }
				   }
			}
			String ComNum=com[row][col];
			if(ComNum=="O") {
				for(int j=0; j<1; j++) {
					 row=random.nextInt(Size);
					 col=random.nextInt(Size);
					 if(com[row][col]=="O") {
						 j--;
					 }
					}
			}
			ComNum=com[row][col];
			com[row][col]="O";
			computer[row][col]="O";
			
			for(int q=0; q<Value.length;q++) {
			if(ComNum.equals(Value[q])) {
				label[q].setText("O");
				Value[q]="O";
				int a=0,b=0;
				if (q!=0) {
				a=q/Size;
				b=(q%Size);
				}
				if(b==-1) {
					b=Size;
					a=a-1;
				}
				
				player[a][b]="O";
				
				
			}
			}
			System.out.println("Com이  "+ComNum+"("+spell.get(ComNum)+")"+"를 선택했습니다.");
			System.out.println("=================");
			break;
		}
		
		case 5 :
			String ComNum=com[row][col];
			com[row][col]="O";
			computer[row][col]="O";
			
			for(int q=0; q<Value.length;q++) {
			if(ComNum.equals(Value[q])) {
				label[q].setText("O");
				Value[q]="O";
				int a=0,b=0;
				if (q!=0) {
				a=q/Size;
				b=(q%Size);
				}
				if(b==-1) {
					b=Size;
					a=a-1;
				}
				
				player[a][b]="O";
				
				
			}
			}
			System.out.println("Com이  "+ComNum+"("+spell.get(ComNum)+")"+"를 선택했습니다.");
			System.out.println("=================");
			
			break;
		
		default :
		for(int j=0; j<1; j++) {
		 row=random.nextInt(Size);
		 col=random.nextInt(Size);
		 if(com[row][col]=="O") {
			 j--;
		 }
		}
		
		
		
		String ComNum1=com[row][col];
		com[row][col]="O";
		computer[row][col]="O";
		
		for(int q=0; q<Value.length;q++) {
		if(ComNum1.equals(Value[q])) {
			label[q].setText("O");
			Value[q]="O";
			int a=0,b=0;
			if (q!=0) {
			a=q/Size;
			b=(q%Size);
			}
			if(b==-1) {
				b=Size;
				a=a-1;
			}
			
			player[a][b]="O";
			
			
		}
		}
		System.out.println("Com이  "+ComNum1+"("+spell.get(ComNum1)+")"+"를 선택했습니다.");
		System.out.println("=================");
		break;
	}
	}
	


	private void showCom() {
		// TODO Auto-generated method stub
		for(int i =0; i<Size; i++) {
			for(int j=0;j<Size;j++) {
				
					System.out.print(com[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("==========");
	}



	private void init() {
		// TODO Auto-generated method stub
		this.setLayout(new GridLayout(Size,Size));
		for(int i=0; i<Size*Size; i++) {
			label[i] = new JLabel(Value[i]);
			
			panel[i] = new JPanel();
			panel[i].add(label[i]);
			
			this.add(panel[i]);
			
		}
		
		
	}
	
	public void choose() {
		System.out.println("단어를 입력하세요 : ");
		String choose = scan.nextLine();
		choose.trim();
		for(int j=0; j<Value.length;j++) {
		if(choose.equals(Value[j].trim())) {
			label[j].setText("O");
			
			int row=0,col=0;
			if(j!=0) {
			row=j/Size;
			col=(j%Size);
			}
			if(col==-1) {
				col=Size;
				row=row-1;
			}
			
			player[row][col]="O";//세로,가로
			System.out.println(Value[j]+"("+spell.get(Value[j])+")"+"를"+"선택하셨습니다.");
			Value[j]="O";
		}
		
			
		
		}
		for(int i =0; i<Size; i++) {
			for(int q=0;q<Size;q++) {
				if(choose.equals(com[i][q])) {
					com[i][q]="O";
					computer[i][q]="O";
				}
				
			
	}
	}
		
	
	
	
	
	
	}
}
 class testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bingo1 bingo = new Bingo1("202211946 이재원");
	}

}
