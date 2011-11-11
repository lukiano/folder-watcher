package com.lucho;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {

    private Main() {}

    public static void main(String[] args) throws AWTException, IOException {
        new ConsoleHandler();
        Logger logger = Logger.getGlobal();
        if (args.length > 0) {
            Path directory = Paths.get(args[0]);
            if (Files.exists(directory)) {
                JFrame.setDefaultLookAndFeelDecorated(true);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                SwingUtilities.invokeLater(new GUIStarter(executorService, directory));
            } else {
                logger.log(Level.SEVERE, "Directory " + directory + " does not exist.");
            }
        } else {
            logger.log(Level.SEVERE, "No directory specified.");
        }
    }

}
