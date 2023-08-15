import {AuthAPI, CODE} from "../../api/serverInteractionAPI";
import {stopSubmit} from "redux-form";

const SET_USER_DATA = "SET-USER-DATA";
const SET_AUTH = "SET-AUTH";
const SET_ACCESS_TOKEN = "SET-ACCESS-TOKEN";
const SET_REFRESH_TOKEN = "SET-REFRESH-TOKEN";
const SET_EXCEPTION_MESSAGE = "SET-EXCEPTION-MESSAGE";


let initialisationState = {
    userID: null,
    email: null,
    username: null,
    isAuth: false,
    accessToken: "",
    refreshToken: "",
    authExceptionMessage: ""
}

const AuthReducer = (state = initialisationState, action) => {
    switch (action.type) {
        case SET_USER_DATA:
            return {
                ...state,
                ...action.user
            };
        case SET_AUTH:
            return {
                ...state,
                isAuth: action.isAuth,
                userID: action.isAuth ? state.userID : null,
                email: action.isAuth ? state.email : null,
                username: action.isAuth ? state.username : null
            }
        case SET_ACCESS_TOKEN:
            return {
                ...state,
                accessToken: action.accessToken
            }
        case SET_REFRESH_TOKEN:
            return {
                ...state,
                refreshToken: action.refreshToken
            }
        case SET_EXCEPTION_MESSAGE:
            return {
                ...state,
                authExceptionMessage: action.authExceptionMessage
            }
        default:
    }

    return state;
}

// Actions
export const setUserIDUsernameEmail = (userID, username, email) => {
    return {type: SET_USER_DATA, user: {userID, username, email}};
}
export const setAuth = (isAuth) => {
    return {type: SET_AUTH, isAuth};
}
export const setAccessToken = (accessToken) => {
    return {type: SET_ACCESS_TOKEN, accessToken};
}

export const setRefreshToken = (refreshToken) => {
    return {type: SET_REFRESH_TOKEN, refreshToken};
}

export const setAuthExceptionMessage = (authExceptionMessage) => {
    return {type: SET_EXCEPTION_MESSAGE, authExceptionMessage};
}

// Thunks

export const login = (username, password, rememberMe) => {
    return (dispatch) => {
        AuthAPI.login(username, password, rememberMe).then(data => {

                if (data.resultCode !== CODE.AUTHORIZED && data.resultCode !== CODE.NEW_TOKEN_RECEIVED) {
                    let errorMessage =
                        data.message.length > 0
                            ? data.message
                            : "Incorrect username or password";

                    let showErrorOnForm =
                        stopSubmit(
                            "login",
                            {_error: errorMessage}
                        );

                    dispatch(showErrorOnForm);
                    return;
                }

                if (data.accessToken != null && data.accessToken !== "") {
                    dispatch(setAccessToken(data.accessToken));
                    AuthAPI.setAccessToken(data.accessToken);
                    dispatch(
                        setUserIDUsernameEmail(
                            data.userInfo.userID,
                            data.userInfo.username,
                            data.userInfo.email
                        )
                    )
                    dispatch(setAuth(true));
                }

                if (data.refreshToken != null && data.refreshToken !== "") {
                    dispatch(setRefreshToken(data.refreshToken));
                }
            }
        );
    }
}

export const logout = () => {
    return (dispatch) => {
        AuthAPI.logout().then(data => {
                if (data.resultCode === CODE.NOT_AUTHORIZED) {
                    dispatch(setAuth(false));
                    dispatch(setAccessToken(""));
                    dispatch(setRefreshToken(""));
                    setUserIDUsernameEmail(
                        null,
                        null,
                        null
                    );
                }
            }
        );
    }
}
export default AuthReducer;