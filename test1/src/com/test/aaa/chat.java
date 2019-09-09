package com.test.aaa;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.zip.InflaterInputStream;

import javax.jws.Oneway;
import javax.swing.JFrame;
import javax.swing.border.Border;

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
	PrintStream printStream = null;
	public chat() throws HeadlessException{
		super();
		init();
	}
	
	public chat(String string) throws HeadlessException{
		super();
		init();
	}
	
	public void init(){
		this.add(textArea);
		panel1.add(new Label("地址"));
		panel1.add(address);
		panel1.add(new Label("端口号"));
		panel1.add(port);
		panel1.add(new Label("昵称"));
		panel1.add(nickname);
		panel1.add(connBtn);
		panel1.add(textField);
		panel1.add(sendBtn);
		sendBtn.addActionListener(new MyListener());
		connBtn.addActionListener(new connBtnListener());
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.SOUTH);
		System.out.println(this.getTitle());
		this.pack();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				PrintStream printStream;
				try {
					printStream = new PrintStream(socket.getOutputStream());
					printStream.println("quit");
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				close();
				System.exit(-1);
			}
		});
		this.setVisible(true);
		connect();
	}
	
	public void close() {
		if (printStream != null) {
			printStream.close();
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public void connect() {
		try {
			socket = new Socket(address.getText(), Integer.parseInt(port.getText()));
			this.setTitle(nickname.getText());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void recive() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String string = "";
			while ((string = br.readLine()) != null) {
				textArea.append(string + "\n");
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.exit(-1);;
		}
	}
	
	public void sendName() {
		String name = nickname.getText();
		PrintStream printStream;
		try {
			printStream = new PrintStream(socket.getOutputStream());
			printStream.println(name);
			textArea.append("i said:" + textField.getText() + "\n");
			textField.setText("");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
	
	class connBtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			connect();
			Recive r = new Recive();
			new Thread(r).start();
		}
		
	}
	
	class Recive implements Runnable {
		@Override
		public void run() {
			recive();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args){
		chat c = new chat("客户端");
	}
	
}
