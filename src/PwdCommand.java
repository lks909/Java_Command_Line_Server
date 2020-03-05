import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class PwdCommand implements Command{
    @Override
    public int execute(ArrayList<String> args, BufferedReader in, BufferedWriter out, Directory dir) {
        if (dir.getDir().length() == 0) {
            Server.send("Error: Incorrect type of directory", out);
            return 1;
        } else {
            Server.send("Current direcroty: " + dir.getDir(), out);
            return 0;
        }
    }
}
