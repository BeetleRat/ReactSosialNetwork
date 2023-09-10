import {GlobalStateType} from "../store";

export const getProfileUser = (state: GlobalStateType) => {
    return state.profilePage.profile;
}

export const getProfilePosts = (state: GlobalStateType) => {
    return state.profilePage.postsArray;
}