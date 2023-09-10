import styleClass from "./ProfileInfo.module.css"
import Posts from "./Posts/Posts";
import Preloader from "../CommonComponents/Preloader/Preloader";
import ProfileDescription from "./ProfileDescription/ProfileDescription";
import React from "react";
import {PostType, ProfileType} from "../../Types/Types";
import {ProfileEditValuesType} from "../../Types/FormsTypes";

type PropsType = {
    profile?: ProfileType,
    isOwner: boolean,
    authUserID: number,
    posts: Array<PostType>,
    updateUserDataFromForm: (profile: ProfileType, formData: ProfileEditValuesType) => void,
    savePhoto: (userID: number, photo: File) => void,
    updateStatus: (userID: number, newStatus: string) => void
    addPost: (newPost: string) => void
}

const ProfileInfo: React.FC<PropsType> = (props) => {
    if (!props.profile) {
        return <Preloader/>;
    }

    return (
        <div>
            <ProfileDescription profile={props.profile}
                                isOwner={props.isOwner}
                                updateStatus={props.updateStatus}
                                authUserID={props.authUserID}
                                savePhoto={props.savePhoto}
                                updateUserDataFromForm={props.updateUserDataFromForm}/>

            <Posts posts={props.posts} addNewPost={props.addPost}/>
        </div>
    );
}

export default ProfileInfo;