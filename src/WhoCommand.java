import java.util.ArrayList;

public class WhoCommand implements Command {
    @Override
    public int execute(ArrayList<String> args, Handler handler) {
        try {
            Server.send("Connected users in system:", handler.getWriter());
            for (Handler h : Server.serverList) {
                Server.send("User" + h.getId(), handler.getWriter());
            }
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
