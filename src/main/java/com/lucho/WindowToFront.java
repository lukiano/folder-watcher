package com.lucho;

import java.awt.*;

class WindowToFront implements Runnable {

    private final Window window;

    public WindowToFront(final Window aWindow) {
        this.window = aWindow;
    }

    @Override
    public void run() {
        window.setAlwaysOnTop(true);
        window.toFront();
        window.repaint();
        window.setAlwaysOnTop(false);
    }
}
