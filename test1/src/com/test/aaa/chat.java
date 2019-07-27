package com.test.aaa;

import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.JFrame;

public class chat extends JFrame {
	TextArea textArea = new TextArea(18, 50);
	TextField textField = new TextField(40);
	TextField nickname = new TextField(20);
	
	public chat() throws HeadlessException{
		super();
	}
	
	public static void main(String[] args){
		
	}
}
