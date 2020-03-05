import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ParsedLogicalCommand {
    private ParsedCommand firstCommand;
    private ParsedCommand secondCommand;
    private boolean isLogical;
    private String logicalOperator; //  (" && " / " || " / none)
    BufferedReader in;
    BufferedWriter out;
    Directory currentHandlerDirectory;

    ParsedCommand getFirstCommand() {
        return firstCommand;
    }

    ParsedCommand getSecondCommand() {
        if (isLogical) {
            return secondCommand;
        } else {
            return firstCommand;
        }
    }

    boolean getIsLogical() {
        return isLogical;
    }

    String getLogicalOperator() {
        return logicalOperator;
    }

    public ParsedLogicalCommand(String line, BufferedReader in, BufferedWriter out, Directory dir) {
        this.in = in;
        this.out = out;
        currentHandlerDirectory = dir;
        if (line.contains("&&")) {
            isLogical = true;
            logicalOperator = "&&";
        } else if (line.contains("||")) {
            isLogical = true;
            logicalOperator = "||";
        } else {
            isLogical = false;
            logicalOperator = null;
        }
        if (isLogical) {
            String commands[];
            if (logicalOperator == "&&") {
                commands = line.split(" " + logicalOperator + " ");
            } else {
                commands = line.split(" \\|\\| ");
            }
            firstCommand = new ParsedCommand(commands[0], in, out, currentHandlerDirectory);
            secondCommand = new ParsedCommand(commands[1], in, out, currentHandlerDirectory);
        } else {
            firstCommand = new ParsedCommand(line, in, out, currentHandlerDirectory);
        }
    }

    public int run() throws IllegalAccessException, InvocationTargetException, IOException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        int exitCode1, exitCode2;
        if (isLogical) {
            if (logicalOperator == "&&") {
                exitCode1 = firstCommand.run();
                if (exitCode1 == 0) {
                    exitCode2 = secondCommand.run();
                    return exitCode2;
                } else {
                    return 1;
                }
            } else if (logicalOperator == "||") {
                exitCode1 = firstCommand.run();
                if (exitCode1 == 0) {
                    return 0;
                } else {
                    exitCode2 = secondCommand.run();
                    return exitCode2;
                }
            } else {
                return 1;
            }
        } else {
            exitCode1 = firstCommand.run();
            return exitCode1;
        }
    }
}