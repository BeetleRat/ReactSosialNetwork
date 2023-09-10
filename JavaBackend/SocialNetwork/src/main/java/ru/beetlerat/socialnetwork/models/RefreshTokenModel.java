package ru.beetlerat.socialnetwork.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "refresh_token")
public class RefreshTokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "token")
    private String token;
    @Column(name = "expiry_date")
    private Timestamp expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserModel user;

    public RefreshTokenModel() {
    }

    public RefreshTokenModel(int id, String token, Timestamp expiryDate, UserModel user) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.user = user;
        user.addRefreshToken(this);
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public UserModel getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUser(UserModel user) {
        this.user = user;
        user.addRefreshToken(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefreshTokenModel that = (RefreshTokenModel) o;

        if (id != that.id) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        return expiryDate != null ? expiryDate.equals(that.expiryDate) : that.expiryDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        return result;
    }
}
