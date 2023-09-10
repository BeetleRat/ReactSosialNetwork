import {CODE} from "../../ServerAPI/serverInteractionAPI";
import {FormAction, stopSubmit} from "redux-form";
import {
    PostType,
    ProfileType,
    InferActionsType,
    ThunkCreatorType
} from "../../Types/Types";
import {ProfileEditValuesType} from "../../Types/FormsTypes";
import {ProfileAPI} from "../../ServerAPI/profileAPI";
import {useNavigate} from "react-router-dom";

const REDUCER_NAME = "ProfileReducer/";
const SET_USER = `${REDUCER_NAME}SET-USER` as const;
const ADD_NEW_POST = `${REDUCER_NAME}ADD-NEW-POST` as const;
const SET_STATUS = `${REDUCER_NAME}SET-STATUS` as const;
const DELETE_POST = `${REDUCER_NAME}DELETE-POST` as const;
const UPDATE_PHOTO = `${REDUCER_NAME}UPDATE-PHOTO` as const;

let initialisationState = {
    profile: null as ProfileType | null,
    postsArray: [
        {id: 1, text: "Хапнул пива", likes: 25},
        {id: 2, text: "Лифт", likes: 1},
        {id: 3, text: "Бурятки", likes: 123},
        {id: 4, text: "Еще один пост", likes: 2},
        {id: 5, text: "Лай", likes: 5}
    ] as Array<PostType>
}

export type ProfileState = typeof initialisationState;

const ProfileReducer = (state = initialisationState, action: ActionTypes): ProfileState => {

    switch (action.type) {
        case SET_USER:
            return {
                ...state,
                profile: action.profile
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
                profile: {
                    ...state.profile, // Может быть null
                    status: action.status
                } as ProfileType
            }
        case DELETE_POST:
            return {
                ...state,
                postsArray: state.postsArray.filter(post => post.id != action.postID)
            }
        case UPDATE_PHOTO:
            return {
                ...state,
                profile: {
                    ...state.profile,
                    imgURL: action.imgURL
                } as ProfileType
            }
        default:
    }

    return state;
}

// Actions
export const actions = {
    setProfile: (profile: ProfileType) => {
        return {type: SET_USER, profile};
    },
    setStatus: (status: string) => {
        return {type: SET_STATUS, status};
    },
    addPost: (newPost: string) => {
        return {type: ADD_NEW_POST, newPost}
    },
    deletePost: (postID: number) => {
        return {type: DELETE_POST, postID}
    },
    updatePhoto: (imgURL: string) => {
        return {type: UPDATE_PHOTO, imgURL}
    }
}


type ActionTypes = InferActionsType<typeof actions>;

// Thunks
export const requestProfileFromServer = (userID: number): ThunkCreatorType<ActionTypes> => {
    return async (dispatch) => {
        try {

            let data = await ProfileAPI.getProfile(userID);

            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
                dispatch(actions.setProfile(data.user));
            } else {
                alert(`Ошибка при попытке загрузить пользователя:\n${data.resultCode}: ${data.message}`);
                // Совершаем перенаправление пользователя
                window.location.href = '#/profile';
            }
        } catch (error: any) {
            alert(`Ошибка при попытке загрузить пользователя:\n${error.code}: ${error.message}`);
            // Совершаем перенаправление пользователя
            window.location.href = '#/';
        }
    }
}

export const updateStatus = (userID: number, newStatus: string): ThunkCreatorType<ActionTypes> => {
    return async (dispatch) => {
        try {
            let data = await ProfileAPI.updateUserStatus(userID, newStatus);
            dispatch(actions.setProfile(data.user));
        } catch (error: any) {
            alert(`Ошибка при попытке загрузить изображение на сервер:\n${error.code}: ${error.message}`);
        }

    }
}

export const savePhoto = (userID: number, photo: File): ThunkCreatorType<ActionTypes | FormAction> => {
    return async (dispatch) => {
        try {
            let data = await ProfileAPI.savePhoto(userID, photo);
            debugger;
            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
                dispatch(actions.updatePhoto(data.imgURL));
            } else {
                let showErrorOnForm =
                    stopSubmit("PhotoEditForm", {_error: data.message});
                dispatch(showErrorOnForm);

                return Promise.reject(data.message);
            }
        } catch (error: any) {
            let showErrorOnForm =
                stopSubmit("PhotoEditForm", {_error: error.message});
            dispatch(showErrorOnForm);

            return Promise.reject(error.message);
        }
    }
}


export const updateUserDataFromForm = (user: ProfileType, formData: ProfileEditValuesType): ThunkCreatorType<ActionTypes | FormAction> => {
    return async (dispatch) => {
        let updatedUser = {
            ...user,
            ...formData
        } as ProfileType;

        try {
            let data = await ProfileAPI.updateUser(updatedUser);
            debugger;
            if (data.resultCode === CODE.AUTHORIZED_AND_COMPLETED) {
                dispatch(actions.setProfile(data.user));
            } else {
                let showErrorOnForm = stopSubmit("ProfileForm", {_error: data.message});
                dispatch(showErrorOnForm);
                return Promise.reject(data.message);
            }
        } catch (error: any) {
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
