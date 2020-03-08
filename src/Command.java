import java.util.ArrayList;

public interface Command {
    int execute(ArrayList<String> args, Handler handler);
}
