import {WrappedFieldProps} from "redux-form";
import {ThunkAction, ThunkDispatch} from "redux-thunk";
import {GlobalStateType} from "../Redux/store";
import {Action, AnyAction} from "redux";

export type InferActionsType<T> = T extends { [keys: string]: (...any: any[]) => infer U } ? U : never;

export type ThunkCreatorType<A extends Action, R = Promise<void>> = ThunkAction<R, GlobalStateType, unknown, A>;

export type ThunkDispatcher = ThunkDispatch<GlobalStateType, any, AnyAction>;


export type ValidatorComponentType = WrappedFieldProps & {
    props: any
}

export type StringKey<T> = Extract<keyof T, string>;

export type ContactsType = {
    facebook: string,
    github: string,
    instagram: string,
    mainlink: string,
    twitter: string,
    vk: string,
    website: string,
    youtube: string
}

export type ProfileType = {
    userID: number,
    nickname: string,
    email: string,
    imgURL: string,
    status: string,
    country: string,
    city: string,
    contacts: ContactsType,
    lookingForAJob: boolean,
    searchJobDescription?: string,
    follow: boolean
}

export type PostType = {
    id: number,
    text: string, likes: number
}

export type UserType = {
    id: number,
    name: string,
    status: string,
    imgURL: string,
    location: {
        city: string,
        country: string
    },
    followed: boolean
};

export type UserDTOType = {
    userID: number,
    nickname: string,
    status: string,
    imgURL: string,
    city: string,
    country: string,
    follow: boolean
};

export type NameType = {
    id: number,
    name: string
}

export type MessageType = {
    id: number,
    text: string
}

export type TaskType = {
    id: number,
    name: string,
    totalMinutes: number,
    currenMinutes: number
}

export type BooleanStringType = `true` | `false` | 'null';