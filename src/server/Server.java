package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class Server
{
  private List<ClientHandler> clientHandlers = new ArrayList<>();
  private static final Logger logger = Logger.getLogger(Server.class.getName());

  public Server()
  {
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    ExecutorService executorService = null;
    Handler h = null;

    try
    {
      serverSocket = new ServerSocket(8888);
      h = new FileHandler("server.log");
      h.setFormatter(new SimpleFormatter());
      logger.addHandler(h);
      System.out.println("Server launched");
      logger.log(Level.INFO, "Server launched");
      executorService = Executors.newFixedThreadPool(2);

      while (true)
      {
        clientSocket = serverSocket.accept();
        ClientHandler client = new ClientHandler(clientSocket, this);
        // ClientHandler объявлен как public class ClientHandler implements Runnable
        executorService.execute(client);
        clientHandlers.add(client);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        executorService.shutdown();
        serverSocket.close();
        clientSocket.close();
        System.out.println("Server finished");
        logger.log(Level.INFO, "Server finished");
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  public void notificationAllClientWithNewMessage(String msg)
  {
    for (ClientHandler clientHandler : clientHandlers)
    {
      clientHandler.sendMessage(msg);
    }
  }

  public void removeClient(ClientHandler clientHandler)
  {
    clientHandlers.remove(clientHandler);
  }

  public boolean notificationClientByNickWithNewMessage(String nick, String msg)
  {
    for (ClientHandler clientHandler : clientHandlers)
    {
      if (clientHandler.getNickName() == nick) {
        clientHandler.sendMessage(msg);
        return true;
      }
    }
    return false;
  }

  public void authenticationClient(ClientHandler clientHandler, String nickName)
  {
    clientHandler.setNickName(nickName);
  }
}
