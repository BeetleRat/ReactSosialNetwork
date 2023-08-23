import {CODE, ProfileAPI} from "../../ServerAPI/serverInteractionAPI";
import {stopSubmit} from "redux-form";

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
            } else {
                return state;
            }
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
        try {
            let data = await ProfileAPI.getProfile(userID);

            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
                dispatch(setUser(data.user));
            } else {
                alert(`Ошибка при попытке загрузить пользователя:\n${data.resultCode}: ${data.message}`);
                window.location.href = '#/profile';
            }
        } catch (error) {
            alert(`Ошибка при попытке загрузить пользователя:\n${error.code}: ${error.message}`);
            window.location.href = '#/';
        }

    }
}

export const updateStatus = (userID, newStatus) => {
    return async (dispatch) => {
        try {
            let data = await ProfileAPI.updateUserStatus(userID, newStatus);

            dispatch(setUser(data.user));
        } catch (error) {
            alert(`Ошибка при попытке загрузить изображение на сервер:\n${error.code}: ${error.message}`);
        }

    }
}

export const savePhoto = (userID, photo) => {
    return async (dispatch) => {
        try {
            let data = await ProfileAPI.savePhoto(userID, photo);

            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
                dispatch(updatePhoto(data.imgURL));
            } else {
                let showErrorOnForm =
                    stopSubmit("PhotoEditForm", {_error: data.message});
                dispatch(showErrorOnForm);

                return Promise.reject(data.message);
            }
        } catch (error) {
            let showErrorOnForm =
                stopSubmit("PhotoEditForm", {_error: error.message});
            dispatch(showErrorOnForm);

            return Promise.reject(error.message);
        }
    }
}

export const updateUserDataFromForm = (user, formData) => {
    return async (dispatch) => {
        let updatedUser = {
            ...user,
            ...formData
        }

        try {
            let data = await ProfileAPI.updateUser(updatedUser);

            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
                dispatch(setUser(data.user));
            } else {
                let showErrorOnForm = stopSubmit("ProfileForm", {_error: data.message});
                dispatch(showErrorOnForm);
                return Promise.reject(data.message);
            }
        } catch (error) {
            let showErrorOnForm =
                stopSubmit(
                    "ProfileForm",
                    {_error: `${error.code}: ${error.message}`}
                );
            dispatch(showErrorOnForm);
        }
    }
}

export default ProfileReducer;
