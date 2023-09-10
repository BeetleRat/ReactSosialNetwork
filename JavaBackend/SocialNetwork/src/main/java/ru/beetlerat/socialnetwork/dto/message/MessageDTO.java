package ru.beetlerat.socialnetwork.dto.message;

import javax.validation.constraints.NotNull;

public class MessageDTO {
    @NotNull(message = "Message should not be empty")
    private String message;

    public MessageDTO() {
        message = "";
    }

    public MessageDTO(String messageDTO) {
        this.message = messageDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
