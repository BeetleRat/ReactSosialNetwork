import Login from "./Login";
import {useDispatch, useSelector} from "react-redux";
import {login, logout} from "../../Redux/Reducers/AurhReducer";
import {
    getAuthExceptionMessage,
    getAuthUsersEmail,
    getAuthUsersUsername,
    getIsAuth
} from "../../Redux/Selectors/AuthSelectors";
import React from "react";
import {ThunkDispatcher} from "../../Types/Types";


type PropsType = {};
const LoginContainer: React.FC<PropsType> = (props) => {

    const authExceptionMessage = useSelector(getAuthExceptionMessage);
    const isAuth = useSelector(getIsAuth);
    const username = useSelector(getAuthUsersUsername);
    const email = useSelector(getAuthUsersEmail);

    const dispatch=useDispatch<ThunkDispatcher>();

    const loginRequest = (username: string, password: string, rememberMe: boolean) => {
        dispatch(login(username, password, rememberMe));
    }

    const logoutRequest = () => {
        dispatch(logout);
    }

    return (
        <Login authExceptionMessage={authExceptionMessage} isAuth={isAuth} username={username} email={email}
               login={loginRequest} logout={logoutRequest}/>
    );
}

export default React.memo(LoginContainer) as React.ComponentType;