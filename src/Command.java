import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

public interface Command {
    int execute(ArrayList<String> args, BufferedReader in, BufferedWriter out, Directory dir);
}
