import {CODE} from "../../ServerAPI/serverInteractionAPI";
import {FormAction, stopSubmit} from "redux-form";
import {Dispatch} from "redux";
import {GetGlobalStateType, GlobalStateType} from "../store";
import {ThunkAction} from "redux-thunk";
import {InferActionsType, ThunkCreatorType} from "../../Types/Types";
import {AuthAPI} from "../../ServerAPI/authAPI";

// Константы имен действий совершаемых над store
const REDUCER_NAME = `AuthReducer/`;
const SET_USER_DATA = `${REDUCER_NAME}SET-USER-DATA` as const;
const SET_AUTH = `${REDUCER_NAME}SET-AUTH` as const;
const SET_ACCESS_TOKEN = `${REDUCER_NAME}SET-ACCESS-TOKEN` as const;
const SET_REFRESH_TOKEN = `${REDUCER_NAME}SET-REFRESH-TOKEN` as const;
const SET_EXCEPTION_MESSAGE = `${REDUCER_NAME}SET-EXCEPTION-MESSAGE` as const;

// initialisationState
let initialisationState = {
    userID: null as number | null,
    email: null as string | null,
    username: null as string | null,
    imgURL: null as string | null,
    isAuth: false as boolean,
    accessToken: "" as string,
    refreshToken: "" as string,
    authExceptionMessage: "" as string
}

export type AuthStateType = typeof initialisationState;
type AuthUser = {
    userID: number | null,
    email: string | null,
    username: string | null,
    imgURL: string | null
}

// Reducer - функция преобразующая state
// в зависимости от входного параметра action
const AuthReducer = (state = initialisationState, action: ActionTypes): AuthStateType => {
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
                username: action.isAuth ? state.username : null,
                imgURL: action.isAuth ? state.imgURL : null
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
const actions = {
    setAuthUser: (user: AuthUser) => {
        return {type: SET_USER_DATA, user};
    },
    setAuth: (isAuth: boolean) => {
        return {type: SET_AUTH, isAuth};
    },
    setAccessToken: (accessToken: string) => {
        return {type: SET_ACCESS_TOKEN, accessToken};
    },
    setRefreshToken: (refreshToken: string) => {
        return {type: SET_REFRESH_TOKEN, refreshToken};
    },
    setAuthExceptionMessage: (authExceptionMessage: string) => {
        return {type: SET_EXCEPTION_MESSAGE, authExceptionMessage};
    }
}

type ActionTypes = InferActionsType<typeof actions>;

// Thunks
export const login = (username: string, password: string, rememberMe: boolean): ThunkCreatorType<ActionTypes | FormAction> => {
    return async (dispatch, getState) => {
        try {
            let data = await AuthAPI.login(username, password, rememberMe);
            if (data.resultCode !== CODE.AUTHORIZED_AND_COMPLETED && data.resultCode !== CODE.NEW_TOKEN_RECEIVED) {
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
                dispatch(actions.setAccessToken(data.accessToken));
                AuthAPI.setAccessToken(data.accessToken);
                dispatch(
                    actions.setAuthUser(
                        data.userInfo as AuthUser
                    )
                )
                dispatch(actions.setAuth(true));
            }

            if (data.refreshToken != null && data.refreshToken !== "") {
                dispatch(actions.setRefreshToken(data.refreshToken));
            }
        } catch (error: any) {

            let showErrorOnForm =
                stopSubmit(
                    "login",
                    {_error: `${error.code}: ${error.message}`}
                );

            dispatch(showErrorOnForm);
        }

    }
}

export const logout = (): ThunkCreatorType<ActionTypes | FormAction> => {
    return async (dispatch) => {
        try {

            // Обращаемся к API для совершения запроса на сервер
            let data = await AuthAPI.logout();

            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
                dispatch(actions.setAuth(false));
                dispatch(actions.setAccessToken(""));
                dispatch(actions.setRefreshToken(""));
                actions.setAuthUser(
                    {
                        userID: null,
                        email: null,
                        username: null,
                        imgURL: null
                    }
                );
            }
        } catch (error: any) {

            // Выводим ошибку на форму ввода
            let showErrorOnForm =
                stopSubmit(
                    "logout",
                    {_error: `${error.code}: ${error.message}`}
                );

            dispatch(showErrorOnForm);
        }
    }
}

export default AuthReducer;