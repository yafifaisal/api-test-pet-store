package utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.ArrayList;
import java.util.List;

public class InMemoryAppender extends AppenderBase<ILoggingEvent> {

    private static final List<String> logs = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent event) {
        logs.add(event.toString());
    }

    public static String getLogs() {
        StringBuilder sb = new StringBuilder();
        for (String log : logs) {
            sb.append(log).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void clearLogs() {
        logs.clear();
    }
}
