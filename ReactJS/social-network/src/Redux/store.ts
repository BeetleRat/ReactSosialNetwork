import * as Redux from "redux";

import thunkMiddleware from "redux-thunk"
import {reducer as formReducer} from "redux-form"
import DialogReducer from "./Reducers/DialogReducer";
import UserReducer from "./Reducers/UserReducer";
import TotalProgressReducer from "./Reducers/TotalProgressReducer";
import ProfileReducer from "./Reducers/ProfileReducer";
import AuthReducer from "./Reducers/AurhReducer";


/*Создание state из комбинации reducer-ов*/
let rootReducer =
    Redux.combineReducers(
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

type RootReducerType = typeof rootReducer;
export type GlobalStateType = ReturnType<RootReducerType>;
export type GetGlobalStateType = () => GlobalStateType;

// Необходимо для работы с расширением Redux DevTools
// @ts-ignore
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || Redux.compose;

let store =
    Redux.createStore(
        rootReducer,
        composeEnhancers(
            Redux.applyMiddleware(thunkMiddleware)
        )
    );


export default store;