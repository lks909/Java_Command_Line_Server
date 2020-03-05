import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StartProcess implements Command {

    public static class ErrorRepeater extends Thread {
        InputStream is;
        BufferedWriter out;

        ErrorRepeater(InputStream is, BufferedWriter out) {
            this.out = out;
            this.is = is;
        }

        public void run() {
            try(BufferedReader bw = new BufferedReader(new InputStreamReader(is))) {
                String line = "";
                while ((line = bw.readLine()) != null) {
                    Server.send(line, out);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    public static class InputRepeater extends Thread {
        InputStream is;
        BufferedWriter out;

        InputRepeater(InputStream is, BufferedWriter out) {
            this.out = out;
            this.is = is;
        }

        public void run() {
            try(BufferedReader bw = new BufferedReader(new InputStreamReader(is))) {
                String line = "";
                while ((line = bw.readLine()) != null) {
                    Server.send(line, out);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

    }

    public static class OutputRepeater extends Thread {
        OutputStream os;
        Thread thread;
        BufferedReader in;

        OutputRepeater(OutputStream os, Thread thread, BufferedReader in) {
            this.in = in;
            this.os = os;
            this.thread = thread;
        }

        public void run() {
            int intval=0;
            try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)))
            {
                while (thread.isAlive() && (intval=in.read())!=-1) {
                    bw.write(intval);
                    bw.flush();
                    Thread.sleep(100);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int execute(ArrayList<String> args, BufferedReader in, BufferedWriter out, Directory dir) {
        try {
            ProcessBuilder procBuilder = new ProcessBuilder(args);
            Process process = procBuilder.start();
            InputStream err = process.getErrorStream();
            InputStream stdin = process.getInputStream();
            OutputStream stdout = process.getOutputStream();
            InputRepeater inputRepeater = new InputRepeater(stdin, out);
            OutputRepeater outputRepeater = new OutputRepeater(stdout, inputRepeater, in);
            ErrorRepeater errRepeater = new ErrorRepeater(err, out);
            inputRepeater.start();
            outputRepeater.start();
            errRepeater.start();
            inputRepeater.join();
            errRepeater.join();
            outputRepeater.join();
            int exitVal = process.waitFor();
            stdin.close();
            err.close();
            if (exitVal == 0) {
                Server.send("The program finished successfully", out);
            } else {
                Server.send("Error: The called program has crashed", out);
            }
            return exitVal;
        } catch(IOException ex) {
            System.out.println("Error: " + ex);
            return 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
