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
import javax.swing.text.DefaultCaret;

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
  private String nickName;
  private Listing listFile = new Listing("list.txt");

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
    // Добавляем свойство автоскроллирования в область сообщений
    DefaultCaret caret = (DefaultCaret)jTextAreaMsg.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    JScrollPane jScrollPane = new JScrollPane(jTextAreaMsg);
    add(jScrollPane, BorderLayout.CENTER);

    JLabel labelCountOfClient = new JLabel("Count of client in chat : ");
    add(labelCountOfClient, BorderLayout.NORTH);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    add(bottomPanel, BorderLayout.SOUTH);

    JButton sendButton = new JButton("SEND");
    bottomPanel.add(sendButton, BorderLayout.EAST);

    jtfMsg = new JTextField();
    bottomPanel.add(jtfMsg, BorderLayout.CENTER);

    lblName = new JLabel("Noname");
    bottomPanel.add(lblName, BorderLayout.WEST);

    jtfMsg.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          String msg = jtfMsg.getText().trim();

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
                  destroy();
                  dispose();
                  return;
                }
                else if (words[0].equals("/clr")) {
                    jTextAreaMsg.setText("");
                }
                else if (words[0].equals("/a")) {
                    jTextAreaMsg.setText(listFile.read());
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
          outMsg.println(nickName + " exited from chat.");
        destroy();
      }
    });
    setVisible(true);
    jtfMsg.grabFocus();
//    init();
  }

  private void sendMsg()
  {
    outMsg.println(jtfMsg.getText());
    outMsg.flush();
    jtfMsg.setText("");
  }

//  private void init() {
//    System.out.println("Init...");
//  }

    void destroy()
    {
        listFile.write(jTextAreaMsg.getText());
//        outMsg.println("QUIT");
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
}
