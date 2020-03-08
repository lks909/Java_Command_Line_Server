import java.util.ArrayList;

public class WriteCommand implements Command {
    @Override
    public int execute(ArrayList<String> args, Handler handler) {
        String msg = "";
        if (args.isEmpty()) {
            Server.send("Error: The message is empty!", handler.getWriter());
            return 1;
        }
        msg += "User" + handler.getId() + " has sent the message: ";
        try {
            for (String word: args) {
                msg += word + " ";
            }
            for (Handler h: Server.serverList) {
                Server.send(msg, h.getWriter());
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

}
