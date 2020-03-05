import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DirCommand implements Command {
    @Override
    public int execute(ArrayList<String> args, BufferedReader in, BufferedWriter out, Directory dir) {
        try {
            File directory = new File(dir.getDir());
            File[] arrFiles = directory.listFiles();
            for (File file : arrFiles) {
                Server.send(file.getName(), out);
            }
            return 0;
        } catch(NullPointerException ex) {
            Server.send("Error: " + ex, out);
            return 1;
        } catch(SecurityException ex) {
            Server.send("Error: " + ex, out);
            return 1;
        }
    }
}

