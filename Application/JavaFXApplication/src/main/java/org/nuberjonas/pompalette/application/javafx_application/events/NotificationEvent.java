package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public final class NotificationEvent extends Event<NotificationEvent.Notification> {

    private NotificationEvent(Notification data) {
        super(data);
    }

    public static NotificationEvent info(String message){
        return new NotificationEvent(new Notification(Notification.Type.INFO, message));
    }

    public static NotificationEvent warn(String message){
        return new NotificationEvent(new Notification(Notification.Type.WARN, message));
    }

    public static NotificationEvent error(String message){
        return new NotificationEvent(new Notification(Notification.Type.ERROR, message));
    }

    public static NotificationEvent error(String message, String errorMessage){
        return new NotificationEvent(new Notification(Notification.Type.ERROR, message, errorMessage));
    }

    public static final class Notification {
        public enum Type{
            INFO,
            WARN,
            ERROR;
        }

        private final Type eventType;
        private final String message;
        private final String errorMessage;

        public Notification(Type eventType, String message, String errorMessage) {
            this.eventType = eventType;
            this.message = message;
            this.errorMessage = errorMessage;
        }

        public Notification(Type eventType, String message){
            this(eventType, message, "");
        }

        public Type getEventType() {
            return eventType;
        }

        public String getMessage() {
            return message;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
