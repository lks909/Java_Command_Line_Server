import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ParsedCommand {
    private String command;
    private ArrayList<String> args;
    BufferedReader in;
    BufferedWriter out;
    Directory currentHandlerDirectory;

    String getCommand() {
        return command;
    }

    ArrayList<String> getArgs() {
        return args;
    }


    public ParsedCommand(String line, BufferedReader in, BufferedWriter out, Directory dir) {
        this.in = in;
        this.out = out;
        currentHandlerDirectory = dir;
        args = new ArrayList<String>();
        String parts[] = line.split(" ");
        if (parts != null) {
            command = parts[0];
            if (parts.length > 1) {
                for (int i = 1; i < parts.length; i++) {
                    args.add(parts[i]);
                }
            }
        }
    }

    public int run() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String className = Server.parseConfigXml(command);
        Class c = Class.forName(className);
        Object obj = c.newInstance();
        Method method = c.getMethod("execute", ArrayList.class, BufferedReader.class, BufferedWriter.class, Directory.class);
        Object methodRes = method.invoke(obj, args, in, out, currentHandlerDirectory);
        int exitCode = (int) methodRes;
        return exitCode;
    }
}

