package ru.beetlerat.socialnetwork.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.beetlerat.socialnetwork.dto.dbrequest.PaginationRequestDTO;
import ru.beetlerat.socialnetwork.models.MessageModel;
import ru.beetlerat.socialnetwork.utill.exceptions.message.MessageNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class MessageDAO {
    @PersistenceContext
    private EntityManager entityManager;


    public MessageDAO() {
    }


    private List<MessageModel> getAllMessagesFromChat(int chatID) {
        String HQL = "select m from MessageModel as m order by m.sendingTime";

        return entityManager.createQuery(HQL, MessageModel.class).getResultList();
    }

    public List<MessageModel> getChatMessagesPaginationData(int chatID, PaginationRequestDTO paginationRequestDTO) {
        List<MessageModel> messageModelList = getAllMessagesFromChat(chatID);

        return getPageSublist(messageModelList,paginationRequestDTO);
    }

    private List<MessageModel> getPageSublist(List<MessageModel> messages, PaginationRequestDTO paginationRequestDTO) {
        int startIndex = paginationRequestDTO.getStartIndex();
        int endIndex = paginationRequestDTO.getEndIndex();

        int maxIndex = messages.size();

        if (startIndex >= maxIndex) {
            throw new MessageNotFoundException();
        }

        return messages.subList(startIndex, Math.min(endIndex, maxIndex));
    }
}
