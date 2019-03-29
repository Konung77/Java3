package lesson2;

import java.sql.*;
import java.util.Scanner;

public class Authentication {
    // Здесь должна быть процедура запуска сетевого чата
    static void Chat () {
        System.out.println("Чат запущен");
    }

    public static void init () throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    public static Connection getConnection (String dbName) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ResultSet rs = null;

        System.out.println("Нажмите 1, если у Вас уже есть логин, или 2, если Вы хотите зарегистрироваться");
        Integer answer = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите логин:");
        String login = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        init();
        try (Connection conn = getConnection("chat.db"); Statement st = conn.createStatement()) {
            switch (answer) {
                case 1:
                    rs = st.executeQuery("select password from users where login='"+login+"'");
                    if (rs.getString("password").equals(password)) Chat();
                    else System.out.println("Введен неверный пароль");
                    break;
                case 2:
                    rs = st.executeQuery("select count(*) from users where login='"+login+"'");
                    if (rs.getInt(1) > 0) System.out.println("Пользователь с таким логином уже зарегистрирован");
                    else if (st.executeUpdate("insert into users (login, password) values ('"+login+"','"+password+"')") > 0)
                        Chat();
                    break;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
