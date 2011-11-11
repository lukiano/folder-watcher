package com.lucho;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

class GUIStarter implements Runnable {

    private final Executor executor;

    private final Path directoryToMonitor;

    private final Logger logger = Logger.getLogger(GUIStarter.class.toString());

    public GUIStarter(final Executor anExecutor, final Path aDirectoryToMonitor) {
        this.executor = anExecutor;
        this.directoryToMonitor = aDirectoryToMonitor;
    }

    @Override
    public void run() {
        //Create and set up the window.
        JFrame window = new JFrame();
        window.setTitle("File Operations");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 3));
        JScrollPane scrollPane = new JScrollPane(jPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        window.add(scrollPane);

        //jPanel.add(Box.createHorizontalBox());

        JLabel label = new JLabel("Operations:");
        jPanel.add(label);

        //Display the window.
        window.pack();
        window.setVisible(true);
        final int width = 600;
        final int height = 800;
        window.setSize(width, height);
        window.setResizable(false);

        WatchEventProcessor wep = new DisplayWEProcessor(jPanel, this.directoryToMonitor);
        TrayStarter trayStarter = new TrayStarter(window);
        this.executor.execute(trayStarter);
        try {
            WatchDir wd = new WatchDir(directoryToMonitor, wep);
            this.executor.execute(wd);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            System.exit(1);
        }
    }

}
