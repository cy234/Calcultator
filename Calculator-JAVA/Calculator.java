
/*
 * Java实现计算器界面 
 *                 
 * 操作说明：
 * 1、在单操作数运算(包括“%","√","x^2","1/x"，"+/-"） 时，如4开方，先按数字键，再按运算符 ！！！
 * 2、本运算器不能输出长于15数位的数（无法显示完整）！！！
 * 
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener{
	
	public static void main(String args[]){
		Calculator m=new Calculator();
	}
	
	 String[]M= {"MC","MR","M+","M-","MS"};   //M键
	 String[]KEYS= {"%","√","x^2","1/x","CE","C",
			"<-","/","7","8","9","*",
			"4","5","6","-","1","2",
			"3","+","+/-","0",".","="};      //数字运算符键集
    JButton []m=new JButton [5];
    JButton []Keys=new JButton[KEYS.length];
    JTextField text=new JTextField("0"); //结果框
    String operator= "=";                //当前运算的运算符
    boolean FirstDigit=true;             //标志用户输入的双操作数  前者为true  后者为false
    double result=0.0;                   //存储结果
    boolean flag=true;                   //判断用户操作是否合法
    double  cache;                       //存储M键
    
   public Calculator() {
		super("CALCULATOR");
		init();
		setPreferredSize(new Dimension(350, 420));  
	        setVisible(true);
		setResizable(false);               //设置窗口大小不可变
		pack();	                           // 调整外部容器大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void init() {
		text.setHorizontalAlignment(JTextField.RIGHT);      //文本框右对齐
		text.setEditable(false);
		text.setBackground(new Color(135,206,235));
		text.setFont(new Font("alias",Font.BOLD,35));
	        Font f=new Font("alias",Font.BOLD,20);
	    
		JPanel KEYPanel=new JPanel();            //数值键
	        KEYPanel.setLayout(new GridLayout(6,4,0,0));
	        KEYPanel.setSize(340,300);
		for(int i=0;i<KEYS.length;i++) {
			Keys[i]=new JButton(KEYS[i]);
			KEYPanel.add(Keys[i]);
			if(i==0||i==1||i==2||i==3||i==7||i==11||i==15||i==19||i==23)
			    Keys[i].setForeground(new Color(135,206,235));   
			else
			    Keys[i].setForeground(Color.GRAY);
			Keys[i].setFont(f);
		        Keys[i].setPreferredSize(new Dimension(85,50));  // 设置大小
	                Keys[i].setMargin(null);    //去边界
			Keys[i].setVisible(true);
			
		}

		JPanel MPanel=new JPanel();     //M系列键
		MPanel.setLayout(new GridLayout(1,5,0,0));
		MPanel.setSize(350,50);
		for(int i=0;i<M.length;i++) {
			m[i]=new JButton(M[i]);
			MPanel.add(m[i]);
			m[i].setForeground(Color.GRAY);
	
		}
		 
		
		JPanel TPanel=new JPanel();  //文本框
		TPanel.setLayout(new BorderLayout());
		TPanel.add("Center",text);
		getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add("North",TPanel);
		getContentPane().add("Center",MPanel);
		getContentPane().add("South",KEYPanel);
		
		for(int i=0;i<Keys.length;i++)   //循环添加事件监听器
			Keys[i].addActionListener(this);
		
		for(int i=0;i<m.length;i++)
			m[i].addActionListener(this);
	}
	/*
	 * M键: MC(清除存储）MR（读取存储）M+（记忆加） M-（记忆减） MS（存入存储）
	 */
	private void  HMC() {
		cache=0;	
		text.setText("");
	}
	
	private void HMR() {	
		long r1=(long)cache;
		double r2=cache-r1;
		if(r2==0){
			text.setText(String.valueOf(r1));
			}
		else  if(String.valueOf(result).length()>15){ 
			text.setText(String.valueOf(result).substring(0,15));    
			}
		else{
			text.setText(String.valueOf(cache));
			}	
		}
	
    private void HM1( ) {   //M+
    	FirstDigit=false;
    	String s=text.getText();
    	double d=Double.parseDouble(s);
        cache=cache+d;
	}
    private void  HM0() {
      	FirstDigit=false;
      	String s=text.getText();
      	double d=Double.parseDouble(s);
	cache=cache-d;
	}
    private void  HMS() {
    	 String s=text.getText();
    	 int n=s.indexOf("=");
    	 if(n==-1) {
    		 double d=Double.parseDouble(s);
    		 cache=d;
    	         text.setText("M");
    	 }
    	 else
    	 {
    		 s=s.substring(n+1);
    		 double d=Double.parseDouble(s);
    		 cache=d;
    		 text.setText("M");
    	 }   
    }
	
	private void HC() {  //C键：清除所有数值
		text.setText("0");
		FirstDigit=true;
		operator="=";
	}
	
	private void HCE() {  //CE键：清除当前输入 
		 text.setText("0");
	}
	
	private void HBACK() { //BACK键：退格
		String t=text.getText();
		int i=t.length();
		if(i>0) {
			t=t.substring(0,i-1);  //去text的最后一个字符
			if(t.length()==0) {    //只有一个字符的情况
				text.setText("0");
				FirstDigit=true;
				operator="=";
			}
			else
				text.setText(t);	
		}
	}
	
	
	
	private void HN(String key) {  //N:数字键
		if(FirstDigit) {  
			text.setText(key);
		}
		if(!text.getText().contains(".")) {  //小数点只出现一次，其他忽略
		if(key.equals("."))
			text.setText(text.getText()+".");		
		}else if(!key.equals(".")) {
			text.setText(text.getText()+key); 
		}
		FirstDigit=false;
	}
	
	private void HO(String key) {       //O运算符键
		String t=text.getText();
		double d=Double.parseDouble(t);
		if(operator.equals(KEYS[0])) {     
				result=result / 100;    //百分号运算
		}
		else if(operator.equals(KEYS[1])) {
			if(d<0.0)
			{
				flag=false;
				text.setText("负数不能开平方");
			}
			else
			result=Math.sqrt(result);
		}
		else if(operator.equals(KEYS[2])) {
			result=result*result;		
		}
		else if(operator.equals(KEYS[3]))  {
			if(d==0.0) 
			{
				flag=false;
				text.setText("不能除以0");
			}else 
				result=1/result;
		}
		else if (operator.equals(KEYS[7])) {
			if(d==0.0) 
			{
				flag=false;
				text.setText("不能除以0");
			}else 
				result=result/d;
			
		}
		else if(operator.equals(KEYS[11])) {
			result=result*d;	
		}
		else if(operator.equals(KEYS[15])){
			result=result-d;
		}
		else if(operator.equals(KEYS[19])) {
			result=result+d;
		}
		else if(operator.equals(KEYS[23])) {
			result=d;
		}
		else if(operator.equals(KEYS[20])) {   //正负数
			result=result*(-1);
		}
		else if(operator.equals(KEYS[23]))
		{
			result=d;
		}
		       
		                          
			long r1=(long)result;
			double r2=result-r1;
			if(r2==0) {                      //结果2.0 的输出 2 
				text.setText(String.valueOf(r1));
			}else  if(String.valueOf(result).length()>15)
			{ 
				text.setText(String.valueOf(result).substring(0,15));     //结果过长截取15个数位
			}
			else
			{
				text.setText(String.valueOf(result));
			}	
			
		operator=key;    //运算符依赖用户按键
		FirstDigit=true;
		flag=true;
	}
	
	public void actionPerformed(ActionEvent e)
    {
    	String s=e.getActionCommand();    //记录按键
    	if(s.equals(M[0]))
    		HMC();
    	else if(s.equals(M[1]))
    		HMR();
    	else if(s.equals(M[2]))
    		HM1();
    	else if(s.equals(M[3]))
    		HM0();
    	else if(s.equals(M[4]))
    		HMS();
    	else if(s.equals(KEYS[4]))  
    		HCE();
    	else if(s.equals(KEYS[5]))
    		HC();
    	else if(s.equals(KEYS[6]))
    		HBACK();
    	else if("0123456789.".indexOf(s)>=0)
    	    HN(s);
    	else
    		HO(s);
    }

}


