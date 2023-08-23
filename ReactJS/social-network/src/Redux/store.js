import * as Redux from "redux";
import DialogReducer from "./Reducers/DialogReducer";
import UserReducer from "./Reducers/UserReducer";
import TotalProgressReducer from "./Reducers/TotalProgressReducer";
import ProfileReducer from "./Reducers/ProfileReducer";
import AuthReducer from "./Reducers/AurhReducer";
import thunkMiddleware from "redux-thunk"
import {reducer as formReducer} from "redux-form"


/*Создание state из комбинации reducer-ов*/
let reducers =
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

// Необходимо для работы с расширением Redux DevTools
const composeEnhancers =
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || Redux.compose;

let store =
    Redux.createStore(
        reducers,
        composeEnhancers(
            Redux.applyMiddleware(thunkMiddleware)
        )
    );


export default store;