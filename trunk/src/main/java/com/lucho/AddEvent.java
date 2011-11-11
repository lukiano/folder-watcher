package com.lucho;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

class AddEvent implements Runnable {

    private final Logger logger = Logger.getLogger(AddEvent.class.toString());

    private final WatchEvent<?> event;

    private final Path rootPath;

    private final Path path;

    private final Container contentPane;

    private static final String ACTION_COMMAND = "openFolder";

    public AddEvent(final WatchEvent<?> anEvent, final Path aRootPath, final Path aPath, final Container aContentPane) {
        this.contentPane = aContentPane;
        this.rootPath = aRootPath;
        this.path = aPath;
        this.event = anEvent;
    }

    private JButton buildOpenButton() {
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(path.toFile());
        JButton button = new JButton(icon);
        button.setActionCommand(ACTION_COMMAND);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (ACTION_COMMAND.equals(e.getActionCommand()) && Files.exists(path)) {
                    try {
                        Desktop.getDesktop().open(path.toFile());
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                    }
                }
            }
        });
        return button;
    }

    @Override
    public void run() {
        //JPanel rowPanel = new JPanel();
        //rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        if (!StandardWatchEventKinds.ENTRY_DELETE.equals(this.event.kind())) {
            JButton button = this.buildOpenButton();
            contentPane.add(button);
        }
        JLabel label = new JLabel(rootPath.relativize(path).toString());
        JLabel eventLabel = new JLabel(event.kind().name());
        eventLabel.setFont(eventLabel.getFont().deriveFont(Font.BOLD));
        contentPane.add(label);
        contentPane.add(eventLabel);

        //contentPane.add(buildRow());
        contentPane.revalidate();
    }

}
