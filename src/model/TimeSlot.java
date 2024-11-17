package src.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a time slot, encapsulating a specific date and time. 
 * Provides methods for formatting and comparing time slots.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class TimeSlot implements Serializable, Comparable<TimeSlot> {

    /** Serialization identifier for the TimeSlot class. */
    private static final long serialVersionUID = 9L;

    /** The date and time of the time slot. */
    private final LocalDateTime dateTime;

    /**
     * Constructs a new TimeSlot with the specified date and time.
     * @param dateTime The date and time for the time slot.
     */
    public TimeSlot(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the date and time of the time slot.
     * @return the date and time of the time slot.
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Gets the date of the time slot.
     * @return the date of the time slot.
     */
    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }

    /**
     * Gets the formatted date of the time slot in the format "dd/MM/yy".
     * @return the formatted date of the time slot.
     */
    public String getFormattedDate() {
        return this.dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
    }

    /**
     * Gets the formatted time of the time slot in the format "hh:mma".
     * @return the formatted time of the time slot.
     */
    public String getFormattedTime() {
        return this.dateTime.format(DateTimeFormatter.ofPattern("hh:mma"));
    }

    /**
     * Gets the formatted date and time of the time slot in the format "dd/MM/yy hh:mma".
     * @return the formatted date and time of the time slot.
     */
    public String getFormattedDateTime() {
        return this.dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy hh:mma"));
    }

    /**
     * Checks if the current time slot is equal to another time slot.
     * @param slot The other time slot to compare with.
     * @return true if the time slots are equal, otherwise false.
     */
    public boolean equals(TimeSlot slot) {
        return this.dateTime.equals(slot.getDateTime());
    }

    /**
     * Compares the current time slot with another time slot.
     * @param o The other time slot to compare with.
     * @return a negative integer, zero, or a positive integer as this time slot
     *         is less than, equal to, or greater than the specified time slot.
     */
    @Override
    public int compareTo(TimeSlot o) {
        return this.dateTime.compareTo(o.getDateTime());
    }
}
