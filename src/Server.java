import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Properties;

import static java.net.InetAddress.getLocalHost;

public class Server {

    public static final int PORT = 8080;
    public static LinkedList<Handler> serverList = new LinkedList<>();
    static final String[] AVAILABLE_COMMANDS = {"dir", "cd", "pwd", "!", "exit"};
    static String currentDirectory = "";

    static {
        currentDirectory = new File(System.getProperty("user.dir")).getAbsolutePath();
    }

    public static String parseConfigXml(String commandName) throws IOException {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream("config.xml"))
        {
            p.loadFromXML(fis);
            String value = p.getProperty(commandName);
            return value;
        }

    }

    static void send(String msg, BufferedWriter out) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getLocalHost());
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = server.accept();
                System.out.println("SIZE = " + serverList.size());
                try {
                    serverList.add(new Handler(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
