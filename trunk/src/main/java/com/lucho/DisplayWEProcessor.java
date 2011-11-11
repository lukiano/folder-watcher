package com.lucho;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

class DisplayWEProcessor implements WatchEventProcessor {

    private final Path rootPath;

    private final Container contentPane;

    public DisplayWEProcessor(final Container aContentPane, final Path aRootPath) {
        this.contentPane = aContentPane;
        this.rootPath = aRootPath;
    }

    @Override
    public void process(final WatchEvent<?> event, final Path path) {
        SwingUtilities.invokeLater(new AddEvent(event, this.rootPath, path, this.contentPane));
    }

}
