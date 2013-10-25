package org.dkhurtin.makesimulator.impl;

import org.apache.commons.lang3.Validate;
import org.dkhurtin.makesimulator.api.CommandExecutor;

import java.io.*;
import java.util.Scanner;

public class LinuxCommandExecutor implements CommandExecutor {

    @Override
    public void execute(String command) throws CommandExecutionException {
        System.out.println("Execution: " + command);

        Validate.notEmpty(command, "Command line must be not empty.");

        Process process = null;
        Throwable throwable = null;

        try {
            process = Runtime.getRuntime().exec(command);

            startReaderThread(process.getInputStream(), System.out, "STDOUT: ");
            startReaderThread(process.getErrorStream(), System.err, "STDERR: ");

            int returnCode = process.waitFor();
            if (returnCode != 0) {
                throw new IllegalStateException(String.format("Command '%s' return %d\n", command, returnCode));
            }

        } catch (IOException e) {
            throwable = e;
            throw new CommandExecutionException(e);
        } catch (InterruptedException e) {
            throwable = e;
            process.destroy();
            throw new CommandExecutionException(e);
        } finally {
            try {
                closeProcessStreams(process);
            } catch (IOException e) {
                if (throwable == null) {
                    throw new CommandExecutionException(e);
                }
            }

        }
    }

    private static void startReaderThread(final InputStream inputStream, final PrintStream printStream, final String prefix) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(inputStream)));

                while (scanner.hasNext()) {
                    printStream.println(prefix + scanner.nextLine());
                }
            }
        });

        thread.start();
    }

    private static void closeProcessStreams(Process process) throws IOException {
        if (process != null) {
            process.getInputStream().close();
            process.getOutputStream().close();
            process.getErrorStream().close();
        }
    }
}
