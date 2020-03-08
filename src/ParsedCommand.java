import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ParsedCommand {
    private String command;
    private ArrayList<String> args;
    Handler handler;

    String getCommand() {
        return command;
    }

    ArrayList<String> getArgs() {
        return args;
    }


    public ParsedCommand(String line, Handler handler) {
        this.handler = handler;
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
        Method method = c.getMethod("execute", ArrayList.class, Handler.class);
        Object methodRes = method.invoke(obj, args, handler);
        int exitCode = (int) methodRes;
        return exitCode;
    }
}

