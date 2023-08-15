import {FollowAPI, ProfileAPI, UsersAPI} from "../../api/serverInteractionAPI";

const FOLLOW_USER = "FOLLOW-USER";
const UNFOLLOW_USER = "UNFOLLOW-USER";
const SET_USERS = "SET-USERS";
const CHANGE_CURRENT_PAGE = "CHANGE-CURRENT-PAGE";
const TOGGLE_LOADING = "TOGGLE-LOADING";
const TOGGLE_FETCHING_USER = "TOGGLE-FETCHING-USER";

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
            break;
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
            break;
        case SET_USERS:
            return {
                ...state,
                users: [...action.users],
                totalUsersCount: action.totalUsersCount
            }
            break;
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
                        : state.fetchingUsers.filter(id => id !== action.userID)
            };


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

// Thunks
export const getUsersFromServer = (page, pageSize) => {
    return (dispatch) => {
        dispatch(toggleLoading(true)); // Включить preloader
        // Запрос на сервер
        UsersAPI.getUsersFromServer(page, pageSize).then((data) => {
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
                dispatch(toggleLoading(false)); // Выключить preloader
            }
        );
    }
}

export const followUser = (userID) => {
    return (dispatch) => {
        dispatch(toggleFetchingUser(true, userID));
        FollowAPI.followUser(userID).then(response => {
            dispatch(commitFollow(userID));
            dispatch(toggleFetchingUser(false, userID));
        });
    }
}

export const unfollowUser = (userID) => {
    return (dispatch) => {
        dispatch(toggleFetchingUser(true, userID));
        FollowAPI.unfollowUser(userID).then(response => {
            dispatch(commitUnfollow(userID));
            dispatch(toggleFetchingUser(false, userID));
        });
    }
}

export default UserReducer;