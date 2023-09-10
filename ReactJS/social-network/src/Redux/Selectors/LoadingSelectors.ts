import {GlobalStateType} from "../store";

export const getIsLoading = (state: GlobalStateType) => {
    return state.userPage.isLoading;
}