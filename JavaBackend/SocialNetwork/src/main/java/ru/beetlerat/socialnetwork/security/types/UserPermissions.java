package ru.beetlerat.socialnetwork.security.types;

public enum UserPermissions {
    // Права(разрешения)
    // Переменные прав(разрешений) хранят в себе строку с именем данного разрешения.
    // Т.к. для создания права(разрешения) в формате Spring Security требуется строка
    GET_PERMIT("permit:get"),
    POST_PERMIT("permit:post"),
    PATCH_PERMIT("permit:patch"),
    DELETE_PERMIT("permit:delete");

    // Константа строка - название разрешения
    private final String permission;

    // Консструктор права(разрешения).
    // При создании права(создавали выше) упаковываем в него строку с именем этого права
    UserPermissions(String permission) {
        this.permission = permission;
    }

    // Геттер. Получить строку с именем права
    public String getPermission() {
        return permission;
    }
}
