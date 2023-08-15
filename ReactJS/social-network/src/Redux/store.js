import * as Redux from "redux";
import DialogReducer from "./Redusers/DialogReducer";
import UserReducer from "./Redusers/UserReducer";
import TotalProgressReducer from "./Redusers/TotalProgressReducer";
import ProfileReducer from "./Redusers/ProfileReducer";
import AuthReducer from "./Redusers/AurhReducer";
import thunkMiddleware from "redux-thunk"
import {applyMiddleware} from "redux";
import {reducer as formReducer} from "redux-form"


/*Создание state из комбинации reducer-ов*/
let reducers = Redux.combineReducers(
    {
        /*Сюда будут записываться reducer-ы*/
        dialogPage: DialogReducer,
        userPage: UserReducer,
        progressPage: TotalProgressReducer,
        profilePage: ProfileReducer,
        auth: AuthReducer,
        form: formReducer
    }
);

let store = Redux.createStore(reducers, applyMiddleware(thunkMiddleware));

export default store;