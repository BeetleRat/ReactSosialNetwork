import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import ProfileInfo from "./ProfileInfo";
import {
    actions,
    requestProfileFromServer,
    savePhoto,
    updateStatus,
    updateUserDataFromForm
} from "../../Redux/Reducers/ProfileReducer";
import {Navigate, useLocation, useNavigate, useSearchParams} from "react-router-dom";
import {compose} from "redux";
import {getProfilePosts, getProfileUser} from "../../Redux/Selectors/ProfileSelectors";
import {getAuthUsersID, getIsAuth} from "../../Redux/Selectors/AuthSelectors";
import {ProfileType, ThunkDispatcher} from "../../Types/Types";
import {ProfileEditValuesType} from "../../Types/FormsTypes";
import queryString from "querystring";


type QueryParamsType = {
    userID?: string
}

type PropsType = {};


const ProfileInfoContainer: React.FC<PropsType> = (props) => {
    // Локальный state
    let [localStateUserID, setUserID] = useState<number>(23);
    let [isOwner, setIsOwner] = useState<boolean>(false);
    let [needRedirect, setNeedRedirect] = useState<boolean>(false);

    const profile = useSelector(getProfileUser);
    const posts = useSelector(getProfilePosts);
    const authUserID = useSelector(getAuthUsersID);
    const isAuth = useSelector(getIsAuth);

    const dispatch = useDispatch<ThunkDispatcher>();

    const [searchParams, setSearchParams] = useSearchParams();

    const location = useLocation();
    useEffect(
        () => refreshProfile(),
        [location.search]
    );

    const refreshProfile = () => {
        // Берем параметр userID из URL адреса
        // Приводим его к типу number

        let userIDFromURL: number | null = Number(searchParams.get("userID"));
        let doNeedRedirect = false;
        // Если в URL нет такого параметра(userID==undefined)
        if (!userIDFromURL) {
            if (isAuth && authUserID) {
                userIDFromURL = authUserID;
            } else {
                userIDFromURL = 23;
                doNeedRedirect = true;
            }
        }

        setUserID(userIDFromURL);
        setIsOwner(userIDFromURL === authUserID);
        setNeedRedirect(doNeedRedirect);

        if (!doNeedRedirect) {
            dispatch(requestProfileFromServer(userIDFromURL));
        }
    };

    const addPostRequest = (newPost: string) => {
        dispatch(actions.addPost(newPost))
    }

    const updateStatusRequest = (userID: number, newStatus: string) => {
        dispatch(updateStatus(userID, newStatus));
    }
    const savePhotoRequest = (userID: number, photo: File) => {
        dispatch(savePhoto(userID, photo));
    }

    const updateUserDataFromFormRequest = (user: ProfileType, formData: ProfileEditValuesType) => {
        dispatch(updateUserDataFromForm(user, formData))
    }


    if (needRedirect
        || profile == null
        || authUserID == null) {

        return (<Navigate to='/login'/>);
    }

    return (
        <ProfileInfo profile={profile} posts={posts}
                     isOwner={isOwner}
                     authUserID={authUserID}
                     addPost={addPostRequest}
                     updateStatus={updateStatusRequest}
                     savePhoto={savePhotoRequest}
                     updateUserDataFromForm={updateUserDataFromFormRequest}/>
    )
}

export default compose<React.ComponentType>(
    React.memo
)(ProfileInfoContainer);