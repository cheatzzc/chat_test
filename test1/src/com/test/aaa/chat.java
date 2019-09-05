package com.test.aaa;

import java.awt.Button;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.Socket;
import javax.jws.Oneway;
import javax.swing.JFrame;
import java.awt.Panel;

public class chat extends JFrame {
	TextArea textArea = new TextArea(18, 50);
	TextField textField = new TextField(40);
	TextField nickname = new TextField(20);
	TextField address = new TextField("localhost", 10);
	TextField port = new TextField("33333", 5);
	Button sendBtn = new Button("send");
	Button connBtn = new Button("connect");
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Socket socket = null;
	public chat() throws HeadlessException{
		super();
	}
	
	public chat(String string) throws HeadlessException{
		super();
		
	}
	
	public void init(){
		this.add(textArea);
		panel1.add(new Label("µØÖ·"));
		panel1.add(address);
		panel1.add(new Label("¶Ë¿ÚºÅ"));
		panel1.add(port);
		panel1.add(new Label("êÇ³Æ"));
		panel1.add(nickname);
		panel1.add(connBtn);
		panel1.add(textField);
		panel1.add(sendBtn);
		sendBtn.addActionListener(new MyListener());
		connBtn.addActionListener(new connBtnListener());
	}
	
	class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				PrintStream printStream = new PrintStream(socket.getOutputStream());
				printStream.println(textField.getText());
				textArea.append("i say" + textField.getText() + "\n");
				textField.setText("");
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		
	}
	
	
}
