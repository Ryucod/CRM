package CustomerManager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CustomerManager extends Frame implements KeyListener, ActionListener, ItemListener{

	private static final long serialVersionUID = 1L;
	//멤버필드
	//화면에 필요한 구성요소-----------------------------------------
	private TextField nametf = new TextField(25);//이름 텍스트필드
	
	private TextField jumin1tf = new TextField(6);//주민번호 텍스트필드
	private TextField jumin2tf = new TextField(7);
	
	private Choice telch = new Choice();//전화번호 텍스트필드
		private String[] tstr = {"010", "011", "016", "017", "019"};
	private TextField tel1tf = new TextField(4);
	private TextField tel2tf = new TextField(4);
	
	private CheckboxGroup cg = new CheckboxGroup();//성별 체크박스
	private Checkbox man = new Checkbox("남성", true, cg);
	private Checkbox woman = new Checkbox("여성", false, cg);
	
	private String[] hstr = {"영화", "음악", "독서", "게임", "쇼핑"};
	private Checkbox[] hobby = new Checkbox[hstr.length];//취미 체크박스
	
	private Button addbt = new Button("등록");
	private Button dispbt = new Button("분석");
	private Button updatebt = new Button("수정");
	private Button delbt = new Button("삭제");
	private Button initbt = new Button("입력모드");
	
	private java.awt.List li = new java.awt.List();
	
	private TextArea infota = new TextArea();
	
	//다이얼로그에 필요한 구성요소------------------------------------------
	private Dialog dialog = new Dialog(this, "Version", true);
	private Label dlabel = new Label("Cusotmer Manager V1.0", Label.CENTER);
	private Button dbt = new Button("Check");
	
	// 메뉴에 필요한 구성요소 -------------------------------------------------------------
	private MenuBar mb = new MenuBar();
	private Menu mfile = new Menu("File");
		private MenuItem mfnew = new MenuItem("새파일");
		private MenuItem mfopen = new MenuItem("불러오기");
		private MenuItem mfsave = new MenuItem("저장하기");
		private MenuItem mfsaveas = new MenuItem("새 이름으로 저장하기");
		private MenuItem mfexit = new MenuItem("종료");

	private Menu mhelp = new Menu("Help");
	private MenuItem mhver = new MenuItem("Version");
	
	// 처리에 필요한 구성요소--------------------------------------------------------------
	private java.util.List<Customer> data = new ArrayList<Customer>();
	private FileDialog fd = null;
	private File file = null;
	
	//생성자
	public CustomerManager() {
		super("CustomerManager");
		setMenu();// 메뉴 구성
		setDialog();// 다이얼로그 구성
		setEvent();// 이벤트 구성
		buildGUI();// 화면 구성
	}
	//화면구현
	private void buildGUI() {
		setBackground(new Color(180,211,211));
		setLayout(new BorderLayout(3,3));
		add("North", new Label());
		add("South", new Label());
		add("West", new Label());
		add("East", new Label());
		
			Panel mainPanel = new Panel(new BorderLayout(3,3));
				Panel cPanel = new Panel(new BorderLayout(3,3));
					cPanel.add("North", new Label("고객정보입력", Label.CENTER));
					
					Panel cwPanel = new Panel(new GridLayout(5,1,3,3));
						cwPanel.add(new Label("이름: ", Label.RIGHT));
						cwPanel.add(new Label("주민번호: ", Label.RIGHT));
						cwPanel.add(new Label("전화번호: ", Label.RIGHT));
						cwPanel.add(new Label("성별: ", Label.RIGHT));
						cwPanel.add(new Label("취미: ", Label.RIGHT));
					cPanel.add("West", cwPanel);
					
					cPanel.add("East", new Label());
					
					Panel csPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
						csPanel.add(addbt);
						csPanel.add(dispbt);
						csPanel.add(updatebt);
						csPanel.add(delbt);
						csPanel.add(initbt);
					cPanel.add("South", csPanel);
					
					Panel ccPanel = new Panel(new GridLayout(5,1,3,3));
						Panel p1 = new Panel(new FlowLayout(FlowLayout.LEFT));
							p1.add(nametf);
						ccPanel.add(p1);
					
						Panel p2 = new Panel(new FlowLayout(FlowLayout.LEFT));
							p2.add(jumin1tf);
							p2.add(new Label("-", Label.CENTER));
							p2.add(jumin2tf);
						ccPanel.add(p2);
					
						Panel p3 = new Panel(new FlowLayout(FlowLayout.LEFT));
							for(String phoneNumber : tstr)
								telch.add(phoneNumber);//전화번호등록
							p3.add(telch);
							p3.add(new Label("-", Label.CENTER));
							p3.add(tel1tf);
							p3.add(new Label("-", Label.CENTER));
							p3.add(tel2tf);
						ccPanel.add(p3);
					
						Panel p4 = new Panel(new FlowLayout(FlowLayout.LEFT));
							p4.add(man);
							p4.add(woman);
						ccPanel.add(p4);
					
						Panel p5 = new Panel(new FlowLayout(FlowLayout.LEFT));
							for(int i=0; i<hobby.length; i++) {
								hobby[i] = new Checkbox(hstr[i]);//취미를 초기화
								p5.add(hobby[i]);
							}
							p5.add(new Label()); //화면조정을 위하여...원래 필요없는거
						ccPanel.add(p5);
					cPanel.add("Center", ccPanel);
				mainPanel.add("Center", cPanel);
				
			Panel eastPanel = new Panel(new BorderLayout(3,3));
				eastPanel.add("North", new Label("고객목록", Label.CENTER));
				eastPanel.add("Center", li);
			mainPanel.add("East", eastPanel);
			
			Panel southPanel = new Panel(new BorderLayout(3,3));
				southPanel.add("North", new Label("고객정보분석결과", Label.CENTER));
				southPanel.add("Center", infota);
			mainPanel.add("South", southPanel);
		add("Center", mainPanel);
		
		pack();	
						
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();//화면크기
		Dimension my = getSize();
						
		setLocation(scr.width/2-my.width/2, scr.height/2-my.height/2);
		setVisible(true);
						
		setResizable(false);
		setButton(true);//버튼 사용여부 결정
		setForm(true);
		nametf.requestFocus();
	}

	public void clrscr() {//화면지우기
		nametf.setText("");
		jumin1tf.setText("");
		jumin2tf.setText("");
		telch.select(0);
		tel1tf.setText("");
		tel2tf.setText("");
		man.setState(true);
		for(Checkbox myHobby : hobby) myHobby.setState(false);
		infota.setText("");
		nametf.requestFocus();
	}
	
	//텍스트 쓸 수 있는지 여부 결정
	private void setForm(boolean state) {
		nametf.setEnabled(state);
		jumin1tf.setEnabled(state);
		jumin2tf.setEnabled(state);
		man.setEnabled(state);
		woman.setEnabled(state);
		infota.setEditable(false);
	}

	private void setButton(boolean state) {
		addbt.setEnabled(state);
		dispbt.setEnabled(!state);
		updatebt.setEnabled(!state);
		delbt.setEnabled(!state);
		initbt.setEnabled(!state);
	}

	private void setEvent() {// 이벤트 연결
		mfsaveas.addActionListener(this);
		mfsave.addActionListener(this);
		mfopen.addActionListener(this);
		mfnew.addActionListener(this);
		dbt.addActionListener(this);
		mhver.addActionListener(this);
		mfexit.addActionListener(this);
		initbt.addActionListener(this);
		delbt.addActionListener(this);
		updatebt.addActionListener(this);
		dispbt.addActionListener(this);
		li.addItemListener(this);
		addbt.addActionListener(this);
		tel1tf.addActionListener(this);
		telch.addItemListener(this);
		jumin2tf.addKeyListener(this);
		jumin1tf.addKeyListener(this);
		nametf.addActionListener(this);
		
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialog.setVisible(false);
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void setDialog() {
		dialog.setLayout(new BorderLayout(3,3));
		dialog.setFont(new Font("Sans-Serif", Font.BOLD, 20));
		dbt.setFont(new Font("Sans-Serif", Font.BOLD, 20));
		dialog.add("West", new Label(" "));
		dialog.add("East", new Label(" "));
		dialog.add("Center", dlabel);
		dialog.add("South", dbt);
		dialog.setSize(300,150);
		
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension mySize = dialog.getSize();
		
		dialog.setLocation(scr.width/2- mySize.width/2, scr.height/2-mySize.height/2);
		dialog.setResizable(false);
	}

	private void setMenu() {
		setMenuBar(mb);
		mb.add(mfile);
			mfile.add(mfnew);
			mfile.addSeparator();
			mfile.add(mfopen);
			mfile.add(mfsave);
			mfile.add(mfsaveas);
			mfile.addSeparator();
			mfile.add(mfexit);
			
		mb.add(mhelp);
			mhelp.add(mhver);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		//전화번호 선택시 다음 필드로 이동
		if(e.getSource() == telch) {
			tel1tf.requestFocus(); return;
		}
		if(e.getSource() == li) {//회원목록에서 특정 회원을 선택하면
			int index = li.getSelectedIndex();//리스트에서 선택한 위치를 얻어옴.
			
			Customer myCustomer = data.get(index);//그 위치에 해당하는 고객정보을 얻음.
			
			nametf.setText(myCustomer.getName());
			jumin1tf.setText(myCustomer.getJumin().substring(0, 6));
	        jumin2tf.setText(myCustomer.getJumin().substring(6));
	        String[] myTel = myCustomer.getTel().split("-");
	        telch.select(myTel[0]);
	        tel1tf.setText(myTel[1]);
	        tel2tf.setText(myTel[2]);
	        man.setState(myCustomer.isGender());
	        woman.setState(!myCustomer.isGender());
	        
	        for(Checkbox myHobby : hobby) myHobby.setState(false);
	        StringTokenizer st = new StringTokenizer(myCustomer.getHobby(), "-");
	        while(st.hasMoreTokens()){
	           String imsi = st.nextToken();
	           for(int i=0; i<hobby.length; i++){
	              if(imsi.equals(hobby[i].getLabel())){
	                 hobby[i].setState(true); break;
	              }
	           }
	        }//end while
	        
	        setButton(false);
	        setForm(false);
	        telch.requestFocus();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nametf) {//이름 입력
			jumin1tf.requestFocus();
			return;
		}
		if (e.getSource() == tel1tf) {//전화번호 입력
			tel2tf.requestFocus();
			return;
		}
		// 등록버튼
		if (e.getSource() == addbt) {
			String name = nametf.getText().trim();
			String jumin = jumin1tf.getText().trim() + jumin2tf.getText().trim();
			String tel = telch.getSelectedItem() + "-" + tel1tf.getText().trim() + "-" + tel2tf.getText().trim();
			boolean gender = man.getState();
			String myHobby = "";
			for (int i = 0; i < hobby.length; i++) {
				if (hobby[i].getState())
					myHobby += hobby[i].getLabel() + "-";
			}
			if (myHobby.length() == 0) {// 취미를 하나도 체크 안한경우
				myHobby = "없음";
			} else {// 일부체크한 경우 : 영화-음악-
				myHobby = myHobby.substring(0, myHobby.length() - 1);
			}

			Customer myCustomer = new Customer(name, jumin, tel, gender, myHobby);
			data.add(myCustomer);
			li.add(myCustomer.toString());
			infota.setText("\n\t성공적으로 등록되었습니다.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
			clrscr();// 화면지우기
		} // end 등록
		// 분석버튼
		if (e.getSource() == dispbt) {
			// 주민번호 분석
			int year = 1900;
			String area = "";
			String jumin = jumin1tf.getText().trim() + jumin2tf.getText().trim();
			double hap = 0.0;
			double su = 2.0;
			for(int i = 0; i < jumin.length() - 1; i++) {
				if(i == 8) su = 2.0;
				hap += (int)(jumin.charAt(i)-48) * su;
				su++; 
			}
			
			double temp = 11.0 * (int)(hap/11.0) + 11.0 - hap;
			double total = temp - 19.9 * (int)(temp/10.0);
			
			if(total == (int)(jumin.charAt(jumin.length() -1)-48)) {
				switch(jumin.charAt(6)) {
				case '9' :
				case '0' :
					year = 1900; break;
				case '3':
				case '4':
					year = 2000;
				}
				
				switch(jumin.charAt(7)) {
				case '0': area = "서울"; break;
				case '1': area = "경기"; break;
				case '2': area = "강원"; break;
				case '3': area = "충북"; break;
				case '4': area = "충남"; break;
				case '5': area = "전북"; break;
				case '6': area = "전남"; break;
				case '7': area = "경북"; break;
				case '8': area = "경남"; break;
				case '9': area = "제주"; break;
				}
				
				year += (int)(jumin.charAt(0)-48)*10 + (int)(jumin.charAt(1)-48);
				int month = (int)(jumin.charAt(2)-48)*10 + (int)(jumin.charAt(3)-48);
				int day = (int)(jumin.charAt(4)-48)*10 + (int)(jumin.charAt(5)-48);
				
				infota.setText("\n\t" + nametf.getText().trim() + "님의 개인정보 분석결과" + 
								"\n\t이      름 : " + nametf.getText().trim() +
								"\n\t생년월일 : " + year + "년 " +  month + "월 " + day + "일" + 
								"\n\t나      이 : " + (Calendar.getInstance().get(Calendar.YEAR) - year + 1) + "세" + 
								"\n\t성      별 : " + ((int)(jumin.charAt(6)-48) % 2 != 0 ? "남성" : "여성") +
								"\n\t출생지역 : " + area + "출생");
			}else infota.setText("\n\t 잘못된 주민번호 입니다.");
			return;
		} // end 분석
		// 수정버튼
		if (e.getSource() == updatebt) {
			int index = li.getSelectedIndex();

			Customer myCustomer = data.get(index);

			String tel = telch.getSelectedItem() + "-" + tel1tf.getText().trim() + "-" + tel2tf.getText().trim();

			String myHobby = "";
			for (int i = 0; i < hobby.length; i++) {
				if (hobby[i].getState())
					myHobby += hobby[i].getLabel() + "-";
			}
			if (myHobby.length() == 0) {// 취미를 하나도 체크 안한 경우
				myHobby = "없음";
			} else {// 일부체크한경우 : 영화-음악-
				myHobby = myHobby.substring(0, myHobby.length() - 1);
			}

			myCustomer.setTel(tel);
			myCustomer.setHobby(myHobby);

			infota.setText("\n\t성공적으로 수정되었습니다.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
			setButton(true);
			setForm(true);
			clrscr();
		} // end 수정
		// 삭제버튼
		if (e.getSource() == delbt) {
			int index = li.getSelectedIndex();

			li.remove(index);
			data.remove(index);
			infota.setText("\n\t성공적으로 삭제되었습니다.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
			setButton(true);
			setForm(true);
			clrscr();
		} // end 삭제
		// 입력모드전환
		if (e.getSource() == initbt) {

			setButton(true);
			setForm(true);
			clrscr();
		} // end 입력모드

		if (e.getSource() == mfexit) {// 메뉴 : file-exit
			System.exit(0);
		}
		if (e.getSource() == mhver) {// 메뉴 : 다이알로그 띄우기
			dialog.setVisible(true);
		}
		if (e.getSource() == dbt) {// 다이알로그 버튼
			dialog.setVisible(false);
		}
		if (e.getSource() == mfnew) {// 새파일
			li.removeAll();
			data.clear();
			file = null;
			setForm(true);
			setButton(true);
			clrscr();
			return;
		}
		if (e.getSource() == mfopen) {// 불러오기
			fd = new FileDialog(this, "고객정보저장", FileDialog.LOAD);
			fd.setVisible(true);
			String fileName = fd.getFile();
			String folder = fd.getDirectory();
			if (fileName == null || folder == null)
				return;
			file = new File(folder, fileName);
			dataLoad();
		}
		if (e.getSource() == mfsave) {// 저장하기
			if (file == null) {
				fd = new FileDialog(this, "고객정보저장", FileDialog.SAVE);
				fd.setVisible(true);
				String fileName = fd.getFile();
				String folder = fd.getDirectory();
				if (fileName == null || folder == null)
					return;
				file = new File(folder, fileName);
			}
			dataSave();//파일저장
		}
		if (e.getSource() == mfsaveas) {// 새이름으로저장하기
			fd = new FileDialog(this, "고객정보저장", FileDialog.SAVE);
			fd.setVisible(true);
			String fileName = fd.getFile();
			String folder = fd.getDirectory();
			if (fileName == null || folder == null)
				return;
			file = new File(folder, fileName);
			dataSave();//파일저장

		}
	}// end actionPerformed

	public void dataLoad() {// 파일불러오기
		li.removeAll();
		data.clear();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String str = "";
			while((str = br.readLine()) != null) {
				Boolean gen = false;
				String[] str1 = str.split("/");
				if(str1[3].equals("남성")) gen = true;
				Customer myCustomer = new Customer(str1[0], str1[1], str1[2], gen, str1[4]);
				li.add(myCustomer.toString());
				data.add(myCustomer);
			}
			infota.setText("\n\t성공적으로 로딩하였습니다.");
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			try { if(br != null) br.close(); }catch(IOException ioe) {}
			try { if(fr != null) fr.close(); }catch(IOException ioe) {}
		}
	}

	public void dataSave() {// 파일저장
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw, true);
			
			for(int i = 0; i < data.size(); i++) {
				Customer myCustomer = data.get(i);
				String str = "";
				str = myCustomer.getName() + "/" + myCustomer.getJumin() + 
						"/" + myCustomer.getTel() + "/" + (myCustomer.isGender() ? "남성" : "여성") + "/" +
						myCustomer.getHobby();
				pw.println(str);
			}
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}finally {
			if(pw != null) pw.close();
			try { if(bw !=null) bw.close(); }catch(IOException ioe) {}
			try { if(fw !=null) fw.close(); }catch(IOException ioe) {}
		}
	}

	// 키 이벤트 메서드
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() == jumin1tf) {
			if(jumin1tf.getText().trim().length() == 6)
				jumin2tf.requestFocus(); return;
		}
		if(e.getSource() == jumin2tf) {
			if(jumin2tf.getText().trim().length() == 7)
				telch.requestFocus(); return;
		}
	}
	//-----------------------------------------------------
	public static void main(String[] args) {
		new CustomerManager();
	}

}
