import {GlobalStateType} from "../store";

export const getTasks = (state: GlobalStateType) => {
    return state.progressPage.tasks;
}

export const getCurrentTaskID = (state: GlobalStateType) => {
    return state.progressPage.currentTaskID;
}