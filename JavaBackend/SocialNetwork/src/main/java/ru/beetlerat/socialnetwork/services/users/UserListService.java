package ru.beetlerat.socialnetwork.services.users;

import ru.beetlerat.socialnetwork.dto.dbrequest.PaginationUserRequestDTO;
import ru.beetlerat.socialnetwork.dto.users.PaginatedUsersListFromDB;


public interface UserListService {

    Long getTotalUsersCount();

    PaginatedUsersListFromDB getList();

    PaginatedUsersListFromDB getFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO);

    PaginatedUsersListFromDB getFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName);

    PaginatedUsersListFromDB getNotFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO);

    PaginatedUsersListFromDB getNotFriendPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName);

    PaginatedUsersListFromDB getPaginationData(PaginationUserRequestDTO paginationUserRequestDTO);

    PaginatedUsersListFromDB getPaginationData(PaginationUserRequestDTO paginationUserRequestDTO, String partOfUsersName);

    void setPageSize(int pageSize);

    int getDefaultPageSize();

    int getPageSize();
}
