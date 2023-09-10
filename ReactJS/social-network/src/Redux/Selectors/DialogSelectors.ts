import {GlobalStateType} from "../store";

export const getNameArray = (state: GlobalStateType) => {
    return state.dialogPage.nameArray;
}

export const getMessageArray = (state: GlobalStateType) => {
    return state.dialogPage.messageArray;
}