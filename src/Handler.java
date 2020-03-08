import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Arrays;

class Handler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Directory currentHandlerDirectory;


    public Handler(Socket socket) throws IOException {
        currentHandlerDirectory = new Directory(Server.currentDirectory);
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    Directory getCurrentDirectory() {
        return currentHandlerDirectory;
    }

    BufferedWriter getWriter() {
        return out;
    }

    BufferedReader getReader() {
        return in;
    }

    Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        String command;
        try {
            Server.send(">Java Command Line", out);
            while (true) {
                Server.send("Enter command:", out);
                command = in.readLine();
                ParsedLogicalCommand plc = new ParsedLogicalCommand(command, this);
                if (command != null && !Arrays.asList(Server.AVAILABLE_COMMANDS).contains(plc.getFirstCommand().getCommand())
                        && !Arrays.asList(Server.AVAILABLE_COMMANDS).contains(plc.getSecondCommand().getCommand())) {
                    Server.send("Error: Unknown command.", out);
                    continue;
                }
                if (plc.getFirstCommand().getCommand().equals("exit")) {
                    Server.serverList.remove(this);
                    break;
                }
                int commandExitCode = plc.run();
                Server.send("---------------------------", out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}