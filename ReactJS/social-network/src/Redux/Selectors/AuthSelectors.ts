import {GlobalStateType} from "../store";

export const getAuthUsersID = (state: GlobalStateType) => {
    return state.auth.userID;
}

export const getAuthUsersUsername = (state: GlobalStateType) => {
    return state.auth.username;
}

export const getAuthUsersEmail = (state: GlobalStateType) => {
    return state.auth.email;
}

export const getIsAuth = (state: GlobalStateType) => {
    return state.auth.isAuth;
}

export const getAuthUserImgURL = (state: GlobalStateType) => {
    return state.auth.imgURL;
}

export const getAccessToken = (state: GlobalStateType) => {
    return state.auth.accessToken;
}

export const getRefreshToken = (state: GlobalStateType) => {
    return state.auth.refreshToken;
}

export const getAuthExceptionMessage = (state: GlobalStateType) => {
    return state.auth.authExceptionMessage;
}