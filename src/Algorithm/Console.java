package Algorithm;

import javax.swing.*;
import java.io.*;

public class Console implements Runnable {
    JTextArea displayPane;
    BufferedReader reader;
    private volatile boolean exit = false;

    private Console(JTextArea displayPane, PipedOutputStream pos) {
        this.displayPane = displayPane;

        try {
            PipedInputStream pis = new PipedInputStream(pos);
            reader = new BufferedReader(new InputStreamReader(pis));
        } catch (IOException e) {
        }
    }

    public static void redirectOutput(JTextArea displayPane) {
        Console.redirectOut(displayPane);
        Console.redirectErr(displayPane);
    }

    public static void redirectOut(JTextArea displayPane) {
        PipedOutputStream pos = new PipedOutputStream();
        System.setOut(new PrintStream(pos, true));

        Console console = new Console(displayPane, pos);
        new Thread(console).start();
    }

    public static void redirectErr(JTextArea displayPane) {
        PipedOutputStream pos = new PipedOutputStream();
        System.setErr(new PrintStream(pos, true));

        Console console = new Console(displayPane, pos);
        new Thread(console).start();
    }

    public void run() {
        String line = null;

        while(!exit){
            try {
                while ((line = reader.readLine()) != null) {
                    displayPane.append(line + "\n");
                    displayPane.setCaretPosition(displayPane.getDocument().getLength());
                }

                exit = true;
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(null,
                        "Error redirecting output : " + ioe.getMessage());
            }
        }




    }

}