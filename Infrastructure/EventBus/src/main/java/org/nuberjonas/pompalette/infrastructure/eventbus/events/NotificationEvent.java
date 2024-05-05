package org.nuberjonas.pompalette.infrastructure.eventbus.events;

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

    public static NotificationEvent error(String message, Throwable error){
        return new NotificationEvent(new Notification(Notification.Type.ERROR, message, error));
    }

    public static final class Notification {
        public enum Type{
            INFO,
            WARN,
            ERROR;
        }

        private final Type eventType;
        private final String message;
        private final Throwable error;

        public Notification(Type eventType, String message, Throwable error) {
            this.eventType = eventType;
            this.message = message;
            this.error = error;
        }

        public Notification(Type eventType, String message){
            this(eventType, message, null);
        }

        public Type getEventType() {
            return eventType;
        }

        public String getMessage() {
            return message;
        }

        public Throwable getErrorMessage() {
            return error;
        }
    }
}
