package client;

import java.io.*;

public class Listing {
    private String fileName;
    private File file;

    public Listing(String fileName) {
        this.fileName = fileName;
        file = new File("list.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод вызывается при закрытии окна чата у клиента
    void write (String buffer)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            writer.write(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод запускается после авторизации клиента в чате
    String read ()
    {
        String buffer = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String str;
            while ((str = reader.readLine()) != null)
            buffer += str+"\n";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
