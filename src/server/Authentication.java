package server;

import java.sql.*;
import java.util.Scanner;

public class Authentication {
    private static ClientHandler client;
    private int id;

    public Authentication(ClientHandler client) {
        this.client = client;
    }

    public static void init () throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    public static Connection getConnection (String dbName) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }

    public String auth () throws Exception {
        ResultSet rs = null;

        client.sendMessage("Нажмите 1, если у Вас уже есть логин, или 2, если Вы хотите зарегистрироваться");
        Integer answer = Integer.parseInt(client.getAnswer());
        client.sendMessage("Введите логин:");
        String login = client.getAnswer();
        client.sendMessage("Введите пароль:");
        String password = client.getAnswer();

        init();
        try (Connection conn = getConnection("chat.db"); Statement st = conn.createStatement()) {
            switch (answer) {
                case 1:
                    rs = st.executeQuery("select * from users where login='"+login+"'");
                    if (rs.getString("password").equals(password))
                    {
                        id = rs.getInt("id");
                        return login;
                    }
                    else client.sendMessage("Введен неверный пароль");
                    break;
                case 2:
                    rs = st.executeQuery("select count(*) from users where login='"+login+"'");
                    if (rs.getInt(1) > 0) client.sendMessage("Пользователь с таким логином уже зарегистрирован");
                    else if (st.executeUpdate("insert into users (login, password) values ('"+login+"','"+password+"')") > 0)
                    {
                        rs = st.executeQuery("select * from users where login='"+login+"'");
                        id = rs.getInt("id");
                        return login;
                    }
                    break;
            }
            rs.close();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            client.sendMessage("/a");
        }
    }

    public boolean update(String nick)
    {
        ResultSet rs = null;
        try (Connection conn = getConnection("chat.db"); Statement st = conn.createStatement()) {
            rs = st.executeQuery("select count(*) from users where login='"+nick+"'");
            if (rs.getInt(1) > 0) {
                client.sendMessage("Пользователь с таким логином уже зарегистрирован");
                return false;
            }
            if (st.executeUpdate("update users set login='"+nick+"' where id="+id) > 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
