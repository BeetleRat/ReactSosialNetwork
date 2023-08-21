import {CODE, ProfileAPI} from "../../api/serverInteractionAPI";

const REDUCER_NAME = "ProfileReducer/";
const SET_USER = REDUCER_NAME + "SET-USER";
const ADD_NEW_POST = REDUCER_NAME + "ADD-NEW-POST"
const SET_STATUS = REDUCER_NAME + "SET-STATUS"
const DELETE_POST = REDUCER_NAME + "DELETE-POST"
const UPDATE_PHOTO = REDUCER_NAME + "UPDATE-PHOTO"

let initialisationState = {
    user: null,
    postsArray: [
        {id: '1', text: "Хапнул пива", likes: '25'},
        {id: '2', text: "Лифт", likes: '1'},
        {id: '3', text: "Бурятки", likes: '123'},
        {id: '4', text: "Еще один пост", likes: '2'},
        {id: '5', text: "Лай", likes: '5'}
    ]
}

const ProfileReducer = (state = initialisationState, action) => {
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
        case DELETE_POST:
            return {
                ...state,
                postsArray: state.postsArray.filter(post => post.id != action.postID)
            }
        case UPDATE_PHOTO:
            return {
                ...state,
                user: {
                    ...state.user,
                    imgURL: action.imgURL
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

export const deletePost = (postID) => {
    return {type: DELETE_POST, postID}
}

export const updatePhoto = (imgURL) => {
    return {type: UPDATE_PHOTO, imgURL}
}

// Thunks
export const setProfile = (userID) => {
    return async (dispatch) => {
        let data = await ProfileAPI.getProfile(userID);

        dispatch(setUser(data));
    }
}

export const updateStatus = (userID, newStatus) => {
    return async (dispatch) => {
        let data = await ProfileAPI.updateUserStatus(userID, newStatus);

        dispatch(setUser(data));
    }
}

export const savePhoto = (userID, photo) => {
    return async (dispatch) => {
        let data = await ProfileAPI.savePhoto(userID, photo);

        if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
            dispatch(updatePhoto(data.imgURL));
        }
    }
}

export default ProfileReducer;
