package client;

import java.io.*;

public class Listing {
    private String fileName;
    private File file;

    public Listing(String fileName) {
        this.fileName = fileName;
        file = new File(fileName);
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
        int current_line = 0;
        String buffer = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String str;
            while ((str = reader.readLine()) != null) {
                if (current_line < 100) buffer += str+"\n";
                else {
                    String[] temp = buffer.split("\n",2);
                    buffer = temp[1] + str + "\n";
                }
                current_line++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
