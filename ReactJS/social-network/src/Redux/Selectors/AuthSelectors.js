export const getAuthUsersID = (state) => {
    return state.auth.userID;
}

export const getAuthUsersUsername = (state) => {
    return state.auth.username;
}

export const getAuthUsersEmail = (state) => {
    return state.auth.email;
}

export const getIsAuth = (state) => {
    return state.auth.isAuth;
}

export const getAccessToken = (state) => {
    return state.auth.token;
}

export const getAuthExceptionMessage = (state) => {
    return state.auth.authExceptionMessage;
}