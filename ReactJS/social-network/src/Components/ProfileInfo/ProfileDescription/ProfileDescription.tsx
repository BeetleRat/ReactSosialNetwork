import styleClass from "./ProfileDescription.module.css";
import defaultAvatar from "../../../Assets/Images/defaultAvatar.png";
import ProfileStatusContainer from "./ProfileData/ProfileStatus/ProfileStatusContainer";
import ProfileData from "./ProfileData/ProfileData";
import ProfileEditForm from "./ProfileEditForm/ProfileEditForm";
import React, {useState} from "react";
import EditPhotoComponent from "./EditPhotoComponent/EditPhotoComponent";
import {ProfileType} from "../../../Types/Types";
import {ProfileEditValuesType} from "../../../Types/FormsTypes";

type PropsType = {
    isOwner: boolean,
    authUserID: number,
    profile: ProfileType,
    updateUserDataFromForm: (profile: ProfileType, formData: ProfileEditValuesType) => void,
    savePhoto: (userID: number, photo: File) => void,
    updateStatus: (userID: number, newStatus: string) => void
}
const ProfileDescription: React.FC<PropsType> = (props) => {

    let [editMode, setEditMode] = useState<boolean>(false);


    const editModeON = () => {
        setEditMode(true);
    }

    const editModeOFF = (formData: ProfileEditValuesType) => {

        props.updateUserDataFromForm(props.profile, formData);
        setEditMode(false);
            // .then(
            //     () => {
            //         setEditMode(false);
            //     }
            // );
    }

    return (
        <div>
            <h3>{props.profile.nickname}</h3>
            <img className={styleClass.photoStyle}
                 src={props.profile.imgURL === "" ? defaultAvatar : props.profile.imgURL} alt="Фото профиля"/>
            {
                props.isOwner &&
                <EditPhotoComponent savePhoto={props.savePhoto} authUserID={props.authUserID}/>

            }
            <ProfileStatusContainer status={props.profile.status} updateStatus={props.updateStatus}
                                    authUserID={props.authUserID}/>
            {
                editMode
                    ? <ProfileEditForm isOwner={props.isOwner} profile={props.profile}
                                       initialValues={props.profile} onSubmit={editModeOFF}/>
                    : <ProfileData isOwner={props.isOwner} profile={props.profile} editModeON={editModeON}/>

            }
        </div>
    );
}

export default ProfileDescription;