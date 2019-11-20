package com.server.test2;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.net.ServerSocket;
import java.net.Socket;
//import java.security.Provider.Service;

public class CServer extends Frame {
	TextArea textArea = new TextArea(20, 50);
	Socket socket = null;
	Socket socket1 = null;
	Hashtable hashTable = new Hashtable();
	public CServer() throws HeadlessException {
		super();
		init();
	}
	
	public CServer(String string) throws HeadlessException{
		super();
		init();
	}
	
	private void init() {
		this.add(textArea);
		this.pack();
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.setVisible(true);
		startServer();
	}
	
	// ¹ã²¥
	public void boradCast(String str, Socket self) {
		Enumeration enumeration = hashTable.keys();
		System.out.println("this char room have" + hashTable.size() + "people");
		PrintStream printStream = null;
		textArea.append(str);
		while (enumeration.hasMoreElements()) {
			String s = (String) enumeration.nextElement();
			socket1 = (Socket)hashTable.get(s);
			if (socket1 != self) {
				try {
					printStream = new PrintStream(socket1.getOutputStream());
					printStream.println(str);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}
	
	class Service implements Runnable{

		Socket socket = null;
		String name;

		public Service(Socket socket) {
			// TODO Auto-generated constructor stub
			this.socket = socket;
			try {
				BufferedReader b1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				name = b1.readLine();
				hashTable.put(name, socket);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		public Service() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				boradCast(name + "enter the chat room\n", socket);
				while (true) {
					String str = "";
					System.out.println("server" + socket.isClosed());
					if ( (str = br.readLine()) != null ) {
						boradCast(name + "speak:" + str + "\n", socket);
					}
					
					if ("quit".equals(str)) {
						hashTable.remove(name);
						break;
					}
					
				}
				br.close();
				socket.close();
				textArea.append("close the link" + name);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}

	}
	
	public void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(3333);
			while (true) {
				socket = serverSocket.accept();
				Service ser = new Service(socket);
				new Thread(ser).start();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CServer cServer = new CServer("server");
	}
}
