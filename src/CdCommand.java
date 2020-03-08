import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CdCommand implements Command {
    @Override
    public int execute(ArrayList<String> args, Handler handler) {
        try {
            Path path = Paths.get(args.get(0));
            if (Files.exists(path)) {
                handler.getCurrentDirectory().setDir(args.get(0));
                return 0;
            } else {
                Server.send("Error: Incorrect path", handler.getWriter());
                return 1;
            }
        } catch(IndexOutOfBoundsException ex) {
            Server.send("Error: "  + ex, handler.getWriter());
            return 1;
        }
    }
}
