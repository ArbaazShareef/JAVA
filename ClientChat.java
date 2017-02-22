import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ClientChat extends JFrame implements ActionListener
{
JButton send = new JButton("SEND");
JTextArea area = new JTextArea(20,20);
JScrollPane jsp = new JScrollPane(area);
JTextField text = new JTextField(20);
JLabel label = new JLabel("Enter Ur Text :: ");
JPanel panel = new JPanel();
Socket client;
DataInputStream din;
PrintWriter pw;
public ClientChat()
{
super("Client Window");
panel.add(label);
panel.add(text);
panel.add(send);
panel.setBorder(new LineBorder(Color.red,2,true));
add(panel,BorderLayout.SOUTH);
jsp.setBorder(new EmptyBorder(10,10,10,10));
add(jsp);
send.addActionListener(this);
text.addActionListener(this);
area.setFont(new Font("TimesRoman",Font.PLAIN,15));
area.setEditable(false);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setSize(500,500);
setLocationRelativeTo(null);
setVisible(true);
try
{
client = new Socket("localhost",7000);
pw = new PrintWriter(client.getOutputStream());
din = new DataInputStream(client.getInputStream());
}
catch(Exception e)
{
System.out.println("\n\tException " + e);
}
}
public void receive()
{
try
{
String s;
while((s=din.readLine())!=null)
{
String temp = "\nServer : " + s ;
area.append(temp);
}
}
catch(Exception e)
{
System.out.println("Exception " + e);
}
}
public void actionPerformed(ActionEvent ae)
{
try
{
String s = "\nClient : " + text.getText() ;
area.append(s);
pw.println(text.getText());
pw.flush();
text.setText("");
}
catch(Exception e)
{
System.out.println("Exception  " + e);
}
}
public static void main(String args[])
{
new ClientChat().receive();
}
}