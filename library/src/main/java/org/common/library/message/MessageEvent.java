package org.common.library.message;

/**
 * Created by peter on 2016/3/28.
 */
public class MessageEvent {
    public final String message;
    public final Object object;

    public MessageEvent(String message) {
        this.message = message;
        this.object = null;
    }

    public MessageEvent(String message, Object object) {
        this.message = message;
        this.object = object;
    }
}
