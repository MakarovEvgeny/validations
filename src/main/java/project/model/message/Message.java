package project.model.message;

import project.model.*;

import java.util.Objects;

/** Сообщение пользователю об ошибке. */
public class Message extends BaseVersionAwareModel {

    /** Текст сообщения. */
    private String text;

    @SuppressWarnings("unused")
    public Message() {
        //for spring
    }

    public Message(String id, String text, int version, String commentary) {
        this.id = id;
        this.text = text;
        this.version = version;
        this.commentary = commentary;
    }


    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Message message = (Message) o;
        return Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }
}
