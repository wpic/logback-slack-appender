package com.wpic.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.LayoutBase;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SlackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private final static String API_URL = "https://slack.com/api/chat.postMessage";

    private String token;

    private String channel;

    private Layout layout;

    @Override
    protected void append(final ILoggingEvent evt) {
        try {
            final URL url = new URL(API_URL);

            final StringWriter w = new StringWriter();
            w.append("token=").append(token).append("&");
            if(channel != null) {
                w.append("channel=").append(URLEncoder.encode(channel, "UTF-8")).append("&");
            }
            if(layout != null) {
                w.append("text=").append(URLEncoder.encode(layout.doLayout(evt), "UTF-8"));
            } else {
                w.append("text=").append(URLEncoder.encode(defaultLayout.doLayout(evt), "UTF-8"));
            }

            final byte[] bytes = w.toString().getBytes("UTF-8");

            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            final OutputStream os = conn.getOutputStream();
            os.write(bytes);

            os.flush();
            os.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            addError("Error to post log to Slack.com (" + channel + "): " + evt, ex);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(final String channel) {
        this.channel = channel;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(final Layout layout) {
        this.layout = layout;
    }

    private Layout defaultLayout = new LayoutBase<ILoggingEvent>() {

        @Override
        public String doLayout(ILoggingEvent event) {
            StringBuffer sbuf = new StringBuffer(128);
            sbuf.append("-- ");
            sbuf.append("[");
            sbuf.append(event.getLevel());
            sbuf.append("]");
            sbuf.append(event.getLoggerName());
            sbuf.append(" - ");
            sbuf.append(event.getFormattedMessage().replaceAll("\n", "\n\t"));
            return sbuf.toString();
        }

    };

}
