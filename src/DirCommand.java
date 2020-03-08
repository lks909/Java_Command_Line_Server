import java.io.File;
import java.util.ArrayList;

public class DirCommand implements Command {
    @Override
    public int execute(ArrayList<String> args, Handler handler) {
        try {
            File directory = new File(handler.getCurrentDirectory().getDir());
            File[] arrFiles = directory.listFiles();
            for (File file : arrFiles) {
                Server.send(file.getName(), handler.getWriter());
            }
            return 0;
        } catch(NullPointerException ex) {
            Server.send("Error: " + ex, handler.getWriter());
            return 1;
        } catch(SecurityException ex) {
            Server.send("Error: " + ex, handler.getWriter());
            return 1;
        }
    }
}

