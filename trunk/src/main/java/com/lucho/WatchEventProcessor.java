package com.lucho;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public interface WatchEventProcessor {

    void process(WatchEvent<?> event, Path path);

}
