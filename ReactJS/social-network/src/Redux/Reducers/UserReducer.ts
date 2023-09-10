import {CODE, ResponseFromBack} from "../../ServerAPI/serverInteractionAPI";
import {InferActionsType, ThunkCreatorType, UserDTOType, UserType} from "../../Types/Types";
import {GetGlobalStateType, GlobalStateType} from "../store";
import {Action, Dispatch} from "redux";
import {ThunkAction} from "redux-thunk";
import {UsersAPI} from "../../ServerAPI/userAPI";
import {FollowAPI} from "../../ServerAPI/followAPI";
import {FormAction} from "redux-form";

const REDUCER_NAME = "UserReducer/";
const FOLLOW_USER = `${REDUCER_NAME}FOLLOW-USER` as const;
const UNFOLLOW_USER = `${REDUCER_NAME}UNFOLLOW-USER` as const;
const SET_USERS = `${REDUCER_NAME}SET-USERS` as const;
const CHANGE_CURRENT_PAGE = `${REDUCER_NAME}CHANGE-CURRENT-PAGE` as const;
const TOGGLE_LOADING = `${REDUCER_NAME}TOGGLE-LOADING` as const;
const TOGGLE_FETCHING_USER = `${REDUCER_NAME}TOGGLE-FETCHING-USER` as const;
const SET_FILTER = `${REDUCER_NAME}SET-FILTER` as const;


let initialisationState = {
    users: [] as Array<UserType>,
    fetchingUsers: [] as Array<number>,
    pageSize: 5 as number,
    totalUsersCount: 0 as number,
    currentPage: 1 as number,
    isLoading: false as boolean,
    filter: {
        term: "",
        friend: null as null | boolean
    }
};

export type UsersState = typeof initialisationState;
export type FilterFormValues = typeof initialisationState.filter;

const UserReducer = (state = initialisationState, action: ActionTypes): UsersState => {
    switch (action.type) {
        case FOLLOW_USER:
            return {
                ...state,
                users: state.users.map(user => {
                    if (user.id === action.id) {
                        return {...user, followed: true}
                    } else {
                        return user;
                    }
                })
            }
        case UNFOLLOW_USER:
            return {
                ...state,
                users: state.users.map(user => {
                    if (user.id === action.id) {
                        return {...user, followed: false}
                    } else {
                        return user;
                    }
                })
            }
        case SET_USERS:
            return {
                ...state,
                users: [...action.users],
                totalUsersCount: action.totalUsersCount
            }
        case CHANGE_CURRENT_PAGE:
            return {
                ...state,
                currentPage: action.currentPage
            }
        case TOGGLE_LOADING:
            return {
                ...state,
                isLoading: action.isLoading
            }
        case TOGGLE_FETCHING_USER:
            return {
                ...state,
                fetchingUsers:
                    action.isFetching
                        ? [...state.fetchingUsers, action.userID]
                        : state.fetchingUsers.filter(id => id != action.userID)
            }
        case SET_FILTER:
            return {
                ...state,
                filter: {
                    ...state.filter,
                    ...action.filter
                }
            }
        default:
    }

    return state;
}

// Actions
export const actions = {
    commitFollow: (id: number) => {
        return {type: FOLLOW_USER, id}
    },
    commitUnfollow: (id: number) => {
        return {type: UNFOLLOW_USER, id}
    },
    setUsers: (users: Array<UserType>, totalUsersCount: number) => {
        return {type: SET_USERS, users, totalUsersCount}
    },
    setCurrentPage: (currentPage: number) => {
        return {type: CHANGE_CURRENT_PAGE, currentPage}
    },
    toggleLoading: (isLoading: boolean) => {
        return {type: TOGGLE_LOADING, isLoading}
    },
    toggleFetchingUser: (isFetching: boolean, userID: number) => {
        return {type: TOGGLE_FETCHING_USER, isFetching, userID}
    },
    setFilter: (filter: FilterFormValues) => {
        return {type: SET_FILTER, filter}
    }
}

type ActionTypes = InferActionsType<typeof actions>;
// Thunks
export const requestUsersFromServer = (page: number, pageSize: number, filter: FilterFormValues): ThunkCreatorType<ActionTypes> => {
    return async (dispatch) => {
        // Включить preloader
        dispatch(actions.toggleLoading(true));

        try {
            // Запрос на сервер
            let data = await UsersAPI.getUsersFromServer(page, pageSize, filter.term, filter.friend);
            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED || data.resultCode === CODE.NOT_FOUND) {
                dispatch(actions.setUsers(
                    data.users.map(
                        (user: UserDTOType) => {
                            return {
                                id: user.userID,
                                imgURL: user.imgURL,
                                name: user.nickname,
                                status: user.status,
                                location: {country: user.country, city: user.city},
                                followed: user.follow
                            }
                        }
                    ),
                    data.totalUsers));
            } else {
                alert(`Сервер вернул ошибку:\n${data.resultCode}: ${data.message}`);
            }


            // Выключить preloader
            dispatch(actions.toggleLoading(false));
        } catch (error: any) {
            alert(`Ошибка при запросе на сервер:\n${error.code}: ${error.message}`);
        }
    }
}

const followUnfollowUser = async (dispatch: Dispatch<ActionTypes>, currentUserID: number | null, userID: number, apiMethod: (currentUserID: number, userID: number) => Promise<ResponseFromBack>, commitMethod: (userID: number) => ActionTypes) => {
    if (currentUserID == null) {
        return;
    }

    dispatch(actions.toggleFetchingUser(true, userID));

    try {
        let data = await apiMethod(currentUserID, userID);

        if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
            dispatch(commitMethod(userID));
        }
    } catch (error: any) {
        alert(`Ошибка при попытке осуществить подписку/отписку:\n${error.code}: ${error.message}`);
    }

    dispatch(actions.toggleFetchingUser(false, userID));
}

export const followUser = (currentUserID: number | null, userID: number): ThunkCreatorType<ActionTypes> => {
    return async (dispatch) => {
        let apiMethod = FollowAPI.followUser.bind(FollowAPI);

        await followUnfollowUser(dispatch, currentUserID, userID, apiMethod, actions.commitFollow);
    }
}

export const unfollowUser = (currentUserID: number | null, userID: number): ThunkCreatorType<ActionTypes> => {
    return async (dispatch) => {
        let apiMethod = FollowAPI.unfollowUser.bind(FollowAPI);

        await followUnfollowUser(dispatch, currentUserID, userID, apiMethod, actions.commitUnfollow);
    }
}

export default UserReducer;