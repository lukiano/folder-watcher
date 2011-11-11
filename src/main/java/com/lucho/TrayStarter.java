package com.lucho;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

class TrayStarter implements Runnable {

    private final Logger logger = Logger.getLogger(TrayStarter.class.toString());

    private final Window window;

    public TrayStarter(final Window aWindow) {
        this.window = aWindow;
    }

    private void addToTray() {
        if (SystemTray.isSupported()) {
            try {
                InputStream is = Main.class.getResourceAsStream("/java.png");
                if (is == null) {
                   logger.log(Level.WARNING, "System Tray image not found.");
                } else {
                    Image image = ImageIO.read(is);
                    if (image == null) {
                        logger.log(Level.WARNING, "System Tray image not found.");
                    } else {
                        this.reallyAddToTray(image);
                    }
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "System Tray image not found.");
            } catch (AWTException e) {
                logger.log(Level.WARNING, "System Tray not supported.");
            }
        } else {
            logger.log(Level.WARNING, "System Tray not supported.");
        }
    }

    private void reallyAddToTray(final Image image) throws AWTException {
        TrayIcon ti = new TrayIcon(image, "Folder Watcher");
        ti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new WindowToFront(window));
            }
        });
        SystemTray.getSystemTray().add(ti);
    }

    @Override
    public void run() {
        this.addToTray();
    }
}
