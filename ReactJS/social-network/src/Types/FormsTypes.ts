import {ContactsType} from "./Types";
import UsersSearchForm from "../Components/Users/UsersSearchForm/UsersSearchForm";

export type InputFieldTypes =
    "text" | "button" | "checkbox" | "color"
    | "date" | "datetime-local" | "email" | "file"
    | "hidden" | "image" | "month" | "number"
    | "password" | "radio" | "range" | "reset"
    | "search" | "submit" | "tel" | "time"
    | "url" | "week";

export type FieldTypes = "input";

export type LoginFormValuesType = {
    username: string,
    password: string,
    rememberMe: boolean
}

export type ProfileEditValuesType = {
    country: string,
    city: string,
    lookingForAJob: boolean,
    searchJobDescription?: string,
    contacts: ContactsType
}

export type DialogFormValuesType = {
    newDialogMessage: string
}

export type PostFormValuesType = {
    newPostText: string
}

export type PhotoEditFromValuesType = {
    newPhoto: Array<File>
}