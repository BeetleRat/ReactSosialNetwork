import React from "react";

import {useDispatch, useSelector} from "react-redux";
import {
    getAccessToken,
    getAuthUserImgURL,
    getAuthUsersEmail,
    getAuthUsersUsername
} from "../../Redux/Selectors/AuthSelectors";
import {getCurrentTaskID, getTasks} from "../../Redux/Selectors/ProgressSelectors";
import HeaderComponent from "./Header";
import {useNavigate} from "react-router-dom";
import {ThunkDispatcher} from "../../Types/Types";
import {logout} from "../../Redux/Reducers/AurhReducer";

type PropsType = {};

const HeaderContainer: React.FC<PropsType> = (props) => {
    const email = useSelector(getAuthUsersEmail);
    const username = useSelector(getAuthUsersUsername);
    const tasks = useSelector(getTasks);
    const currentTaskID = useSelector(getCurrentTaskID);
    const imgURL = useSelector(getAuthUserImgURL);

    const navigate = useNavigate();

    const dispatch = useDispatch<ThunkDispatcher>();

    const redirectToLogin = () => {
        navigate("/login");
    }

    const logoutRequest = () => {
        dispatch(logout());
    }

    return (
        <HeaderComponent email={email} username={username}
                         tasks={tasks} currentTaskID={currentTaskID}
                         imgURL={imgURL}
                         redirectToLogin={redirectToLogin}
                         logout={logoutRequest}/>
    );
}

export default React.memo(HeaderContainer) as React.ComponentType;
