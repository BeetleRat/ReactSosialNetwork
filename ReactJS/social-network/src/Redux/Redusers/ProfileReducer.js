import {ProfileAPI} from "../../api/serverInteractionAPI";

const SET_USER = "SET-USER";
const ADD_NEW_POST = "ADD-NEW-POST"
const SET_STATUS = "SET-STATUS"

let initialisationStat = {
    user: null,
    postsArray: [
        {id: '1', text: "Хапнул пива", likes: '25'},
        {id: '2', text: "Лифт", likes: '1'},
        {id: '3', text: "Бурятки", likes: '123'},
        {id: '4', text: "Еще один пост", likes: '2'},
        {id: '5', text: "Лай", likes: '5'}
    ]
}

const ProfileReducer = (state = initialisationStat, action) => {
    switch (action.type) {
        case SET_USER:
            return {
                ...state,
                user: action.user
            }
        case ADD_NEW_POST:
            if (action.newPost !== "") {
                return {
                    ...state,
                    postsArray: [...state.postsArray, {id: state.postsArray.length, text: action.newPost, likes: 1}]
                }
            }
            break;
        case SET_STATUS:
            return {
                ...state,
                user: {
                    ...state.user,
                    status: action.status
                }
            }
        default:
    }
    return state;
}

// Actions
export const setUser = (user) => {
    return {type: SET_USER, user};
}

export const setStatus = (status) => {
    return {type: SET_STATUS, status};
}

export const addPost = (newPost) => {
    return {type: ADD_NEW_POST, newPost}
}

// Thunks
export const setProfile = (userID) => {
    return (dispatch) => {
        ProfileAPI.getProfile(userID).then(data => {
            dispatch(setUser(data));
        });
    }
}

export const updateStatus = (newStatus) => {
    return (dispatch) => {
        ProfileAPI.updateLoggedUserStatus(newStatus).then(data => {
            dispatch(setUser(data));
        });
    }
}

export default ProfileReducer;
