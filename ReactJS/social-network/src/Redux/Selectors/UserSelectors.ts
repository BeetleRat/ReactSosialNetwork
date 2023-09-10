import {GlobalStateType} from "../store";

export const getUsers = (state: GlobalStateType) => {
    return state.userPage.users;
}

export const getPageSize = (state: GlobalStateType) => {
    return state.userPage.pageSize;
}

export const getTotalUsersCount = (state: GlobalStateType) => {
    return state.userPage.totalUsersCount;
}

export const getCurrentPage = (state: GlobalStateType) => {
    return state.userPage.currentPage;
}

export const getFilter = (state: GlobalStateType) => {
    return state.userPage.filter;
}

export const getFetchingUsers = (state: GlobalStateType) => {
    return state.userPage.fetchingUsers;
}

