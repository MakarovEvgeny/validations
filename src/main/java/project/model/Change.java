package project.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** Изменение. */
public class Change {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /** Дата + время со смещением относительно GMT. */
    private String date;

    private String username;

    private String commentary;

    public Change() {
    }

    public Change(ZonedDateTime date, String username, String commentary) {
        this.date = date.format(formatter);
        this.username = username;
        this.commentary = commentary;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentary() {
        return commentary;
    }

}
