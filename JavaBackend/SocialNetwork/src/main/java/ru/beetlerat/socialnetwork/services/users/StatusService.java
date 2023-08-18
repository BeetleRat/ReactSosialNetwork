package ru.beetlerat.socialnetwork.services.users;

public interface StatusService {
    String getStatus(int userId);
    void updateStatus(int userId, String newStatus);
}
