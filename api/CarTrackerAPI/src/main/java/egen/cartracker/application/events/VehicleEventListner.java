package egen.cartracker.application.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Gaurav saxena
 */
@Component
public class VehicleEventListner {

    static final Logger logger = LoggerFactory.getLogger(VehicleEventListner.class);

    enum Priority {
        NONE, LOW, MEDIUM, HIGH
    }

    static class ReminderCreatedEvent {

        private String title;
        private Priority priority;

        public ReminderCreatedEvent(String title, Priority priority) {
            this.title = title;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "ReminderCreatedEvent{" +
                    "title='" + title + '\'' +
                    ",priority='" + priority + '\'' +
                    '}';
        }

        public Priority getPriority() {
            return priority;
        }
    }

    static class ReminderActivatedEvent {

        private String title;
        private Date date;

        public ReminderActivatedEvent(String title, Date date) {
            this.title = title;
            this.date = date;
        }

        @Override
        public String toString() {
            return "ReminderActivatedEvent{" +
                    "title='" + title + '\'' +
                    ",date='" + date + '\'' +
                    '}';
        }
    }
}

