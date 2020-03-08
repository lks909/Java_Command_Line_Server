import java.util.ArrayList;

public class PwdCommand implements Command{
    @Override
    public int execute(ArrayList<String> args, Handler handler) {
        if (handler.getCurrentDirectory().getDir().length() == 0) {
            Server.send("Error: Incorrect type of directory", handler.getWriter());
            return 1;
        } else {
            Server.send("Current direcroty: " + handler.getCurrentDirectory().getDir(), handler.getWriter());
            return 0;
        }
    }
}
