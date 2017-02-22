import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class ServerChat extends JFrame implements ActionListener
{
	JButton send = new JButton("Send");
	JTextArea area = new JTextArea(20,20);
	JScrollPane jsp = new JScrollPane(area);
	JTextField  text = new JTextField(20);
	JPanel panel = new JPanel();
	ServerSocket server;
	Socket client;
	DataInputStream din;
	PrintWriter pw;

	public ServerChat(){
		super("ServerWindow");
		panel.add(text);
		panel.add(send);
		panel.setBorder(new LineBorder(Color.red,2,true));
		area.setFont(new Font("TimesRoman",Font.PLAIN,16));
		area.setEditable(false);

		add(panel,BorderLayout.SOUTH);
		send.addActionListener(this);
		text.addActionListener(this);
		jsp.setBorder(new EmptyBorder(10,10,10,10));
		add(jsp);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(500,500);

		try{
		server = new ServerSocket(7000);
		area.append("\nWaiting For Client");
		client = server.accept();
		area.append("\nConnected to Client");
		pw = new PrintWriter(client.getOutputStream());
		din = new DataInputStream(client.getInputStream());
	}
	catch(Exception e){
		System.out.println("Exception "+ e);
	}
	}
	public void recieve()
	{
		try{
			String s ;
			while((s=din.readLine())!= null){
				String temp = "\nClient:-"+s;
				area.append(temp);
			}

		}
		catch(Exception e){
		System.out.println("Exception "+ e);
	}
	}
	public void actionPerformed(ActionEvent ae){
		try{
			String s = "\n Server " + text.getText();
			area.append(s);
			pw.println(s);
			pw.flush();
			text.setText("");
		}
		catch(Exception e){
		System.out.println("Exception "+ e);
	}
	}
	public static void main(String args[]){
		new ServerChat().recieve();
	}
} 