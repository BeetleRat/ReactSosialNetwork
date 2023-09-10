package ru.beetlerat.socialnetwork.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
public class MessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "message_text")
    private String message;
    @Column(name = "sending_time")
    private Timestamp sendingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserModel author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private PrivateChatModel chat;

    public MessageModel(){

    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getSendingTime() {
        return sendingTime;
    }

    public UserModel getAuthor() {
        return author;
    }

    public PrivateChatModel getChat() {
        return chat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSendingTime(Timestamp sendingTime) {
        this.sendingTime = sendingTime;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public void setChat(PrivateChatModel chat) {
        this.chat = chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageModel that = (MessageModel) o;

        if (id != that.id) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return sendingTime != null ? sendingTime.equals(that.sendingTime) : that.sendingTime == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (sendingTime != null ? sendingTime.hashCode() : 0);
        return result;
    }
}
