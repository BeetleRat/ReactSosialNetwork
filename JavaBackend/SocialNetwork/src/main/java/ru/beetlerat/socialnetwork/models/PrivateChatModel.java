package ru.beetlerat.socialnetwork.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "private_chat")
public class PrivateChatModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "creation_time")
    private Timestamp creationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_1", referencedColumnName = "id")
    private UserModel user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_2", referencedColumnName = "id")
    private UserModel user2;

    public PrivateChatModel(){

    }

    public int getId() {
        return id;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public UserModel getUser1() {
        return user1;
    }

    public UserModel getUser2() {
        return user2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public void setUser1(UserModel user1) {
        this.user1 = user1;
    }

    public void setUser2(UserModel user2) {
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateChatModel that = (PrivateChatModel) o;

        if (id != that.id) return false;
        return creationTime != null ? creationTime.equals(that.creationTime) : that.creationTime == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        return result;
    }
}
