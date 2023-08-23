import {CODE, FollowAPI, UsersAPI} from "../../ServerAPI/serverInteractionAPI";
import users from "../../Components/Users/Users";

const REDUCER_NAME = "UserReducer/";
const FOLLOW_USER = REDUCER_NAME + "FOLLOW-USER";
const UNFOLLOW_USER = REDUCER_NAME + "UNFOLLOW-USER";
const SET_USERS = REDUCER_NAME + "SET-USERS";
const CHANGE_CURRENT_PAGE = REDUCER_NAME + "CHANGE-CURRENT-PAGE";
const TOGGLE_LOADING = REDUCER_NAME + "TOGGLE-LOADING";
const TOGGLE_FETCHING_USER = REDUCER_NAME + "TOGGLE-FETCHING-USER";
const SET_FOLLOWED_USERS = REDUCER_NAME + "SET-FOLLOWED-USERS";

let initialisationState = {
    users: [],
    fetchingUsers: [],
    pageSize: 5,
    totalUsersCount: 0,
    currentPage: 1,
    isLoading: false
};

const UserReducer = (state = initialisationState, action) => {
    switch (action.type) {
        case FOLLOW_USER:
            return {
                ...state,
                users: state.users.map(user => {
                    if (user.id === action.id) {
                        return {...user, isFollow: true}
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
                        return {...user, isFollow: false}
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
            };
        case SET_FOLLOWED_USERS:
            return {
                ...state,
                users: users.map(user => {
                    user.isFollow = action.followedUsersArray.contain(user.id);

                    return user;
                })
            }

        default:
    }

    return state;
}

// Actions
export const commitFollow = (id) => {
    return {type: FOLLOW_USER, id}
}
export const commitUnfollow = (id) => {
    return {type: UNFOLLOW_USER, id}
}
export const setUsers = (users, totalUsersCount) => {
    return {type: SET_USERS, users, totalUsersCount}
}
export const setCurrentPage = (currentPage) => {
    return {type: CHANGE_CURRENT_PAGE, currentPage}
}
export const toggleLoading = (isLoading) => {
    return {type: TOGGLE_LOADING, isLoading}
}
export const toggleFetchingUser = (isFetching, userID) => {
    return {type: TOGGLE_FETCHING_USER, isFetching, userID}
}

export const setFollowedUsers = (followedUsersArray) => {
    return {type: SET_FOLLOWED_USERS, followedUsersArray}
}

// Thunks
export const requestUsersFromServer = (page, pageSize) => {
    return async (dispatch) => {
        // Включить preloader
        dispatch(toggleLoading(true));

        try {
            // Запрос на сервер
            let data = await UsersAPI.getUsersFromServer(page, pageSize);
            dispatch(setUsers(
                data.users.map(
                    user => {
                        return {
                            id: user.userID,
                            imgURL: user.imgURL,
                            followed: false,
                            name: user.nickname,
                            status: user.status,
                            location: {country: user.country, city: user.city},
                            isFollow: user.follow
                        }
                    }
                ),
                data.totalUsers));

            // Выключить preloader
            dispatch(toggleLoading(false));
        } catch (error) {
            alert(`Ошибка при запросе на сервер:\n${error.code}: ${error.message}`);
        }
    }
}

const followUnfollowUser = async (dispatch, currentUserID, userID, apiMethod, commitMethod) => {
    dispatch(toggleFetchingUser(true, userID));

    try {
        let data = await apiMethod(currentUserID, userID);

        if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
            dispatch(commitMethod(userID));
        }
    } catch (error) {
        alert(`Ошибка при попытке осуществить подписку/отписку:\n${error.code}: ${error.message}`);
    }

    dispatch(toggleFetchingUser(false, userID));
}

export const followUser = (currentUserID, userID) => {
    return async (dispatch) => {
        let apiMethod = FollowAPI.followUser.bind(FollowAPI);

        await followUnfollowUser(dispatch, currentUserID, userID, apiMethod, commitFollow);
    }
}

export const unfollowUser = (currentUserID, userID) => {
    return async (dispatch) => {
        let apiMethod = FollowAPI.unfollowUser.bind(FollowAPI);

        await followUnfollowUser(dispatch, currentUserID, userID, apiMethod, commitUnfollow);
    }
}

export default UserReducer;