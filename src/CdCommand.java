import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CdCommand implements Command {
    @Override
    public int execute(ArrayList<String> args, BufferedReader in, BufferedWriter out, Directory dir) {
        try {
            Path path = Paths.get(args.get(0));
            if (Files.exists(path)) {
                dir.setDir(args.get(0));
                return 0;
            } else {
                Server.send("Error: Incorrect path", out);
                return 1;
            }
        } catch(IndexOutOfBoundsException ex) {
            Server.send("Error: "  + ex, out);
            return 1;
        }
    }
}
