package client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class Client extends JFrame
{
  private final String SERVER_HOST = "localhost";
  private final int SERVER_PORT = 8888;
  private Socket clientSocket;
  private Scanner inMsg;
  private PrintWriter outMsg;
  private JTextField jtfMsg;
  private JLabel lblName;
  private JTextArea jTextAreaMsg;
  String nickName;

  public Client() throws HeadlessException
  {
    try
    {
      clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
      inMsg = new Scanner(clientSocket.getInputStream());
      outMsg = new PrintWriter(clientSocket.getOutputStream());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    setBounds(500, 300, 500, 400);
    setTitle("Client of chat");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    jTextAreaMsg = new JTextArea();
    jTextAreaMsg.setEditable(false);
    jTextAreaMsg.setLineWrap(true);

    JScrollPane jScrollPane = new JScrollPane(jTextAreaMsg);
    add(jScrollPane, BorderLayout.CENTER);

    JLabel labelCountOfClient = new JLabel("Count of client in chat : ");
    add(labelCountOfClient, BorderLayout.NORTH);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    add(bottomPanel, BorderLayout.SOUTH);

//    JLabel countsOfClientLabel = new JLabel("Counts of clients in chat :");
//    add(countsOfClientLabel, BorderLayout.NORTH);

    JButton sendButton = new JButton("SEND");
    bottomPanel.add(sendButton, BorderLayout.EAST);

    jtfMsg = new JTextField();//"Please input your msg");
    bottomPanel.add(jtfMsg, BorderLayout.CENTER);

//    Scanner scanner = new Scanner(System.in);
//    System.out.println("Enter your nickname:");
//    nickName = scanner.nextLine().split(" ",2)[0];
//    sendMsg("/n "+ nickName);
    lblName = new JLabel("Noname");
    //jtfName = new JTextField(nickName);
    bottomPanel.add(lblName, BorderLayout.WEST);

    jtfMsg.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          String msg = jtfMsg.getText().trim();
//          String name = lblName.getText().trim();

//          if (!msg.isEmpty() && !name.isEmpty())
          if (!msg.isEmpty())
          {
            sendMsg();
            jtfMsg.grabFocus();
          }
        }
      }
    });

    sendButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        String msg = jtfMsg.getText().trim();

//        if (!msg.isEmpty() && !name.isEmpty())
        if (!msg.isEmpty())
        {
          sendMsg();
          jtfMsg.grabFocus();
        }
      }
    });

    jtfMsg.addFocusListener(new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        jtfMsg.setText("");
      }
    });

//    jtfName.addFocusListener(new FocusAdapter()
//    {
//      @Override
//      public void focusGained(FocusEvent e)
//      {
//        jtfName.setText("");
//      }
//    });

    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        while (true)
        {
          if (inMsg.hasNext())
          {
            String msg = inMsg.nextLine();
            String clientInChat = "Count of clients in chat: ";
            if (msg.indexOf(clientInChat) == 0)
            {
              labelCountOfClient.setText(msg);
            }
            else
            {
              // Команды сервера
              if (msg.startsWith("/")) {
                String[] words = msg.split(" ",3);
                if (words[0].equals("/n")) {
                  nickName = words[1];
                  lblName.setText(nickName);
                }
                else if (words[0].equals("/q")) {
                  //disableEvents(new WindowEvent());
                  dispose();
                  return;
                }
              }
              // Данные
              else {
                jTextAreaMsg.append(msg);
                jTextAreaMsg.append("\n");
              }
            }
          }
        }
      }
    }).start();

    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        super.windowClosing(e);
//        String clientName = jtfName.getText();
//        if (!clientName.isEmpty() && !clientName.equalsIgnoreCase("Your name"))
//        {
          outMsg.println(nickName + " exited from chat.");
//        }
//        else
//        {
//          outMsg.println("Anonymous client exited from our chat");
//        }

        outMsg.println("QUIT");
        outMsg.flush();
        outMsg.close();
        inMsg.close();
        try
        {
          clientSocket.close();
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
      }
    });
    setVisible(true);
    jtfMsg.grabFocus();
    init();
//    Timer timer = new Timer();
//    timer.schedule(testAnonym,120*1000);
  }

  private void sendMsg()
  {
//    String msg;
//    String msg = jtfName.getText() + ":" + jtfMsg.getText();
//    if (jtfMsg.getText().startsWith("/"))
//    msg = jtfMsg.getText();
//    else msg = nickName + ":" + jtfMsg.getText();
    outMsg.println(jtfMsg.getText());
    outMsg.flush();
    jtfMsg.setText("");
  }

//  private void sendMsg(String message)
//  {
//    outMsg.println(message);
//    outMsg.flush();
//  }

  private void init() {
    System.out.println("Init...");
  }

//  TimerTask testAnonym = new TimerTask() {
//      @Override
//      public void run() {
//          String clientName = jtfName.getText();
//          if (clientName.isEmpty() || clientName.equalsIgnoreCase("Your name")) dispose();
//      }
//  };
}
