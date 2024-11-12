package src.model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TimeSlot implements Serializable, Comparable<TimeSlot> {

    private static final long serialVersionUID = 9L;
    private final LocalDateTime dateTime;

    public TimeSlot(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public LocalDate getDate () {
        return this.dateTime.toLocalDate();
    }

    public String getFormattedDate() {
        return this.dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
    }

    public String getFormattedTime() {
        return this.dateTime.format(DateTimeFormatter.ofPattern("hh:mma"));
    }

    public String getFormattedDateTime() {
        return this.dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mma"));
    }

    public boolean equals(TimeSlot slot) {
        return this.dateTime.equals(slot.getDateTime());
    }

    @Override
    public int compareTo(TimeSlot o) {
        return this.dateTime.compareTo(o.getDateTime());
    }
}