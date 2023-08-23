import styleClass from "./ProfileDescription.module.css";
import defaultAvatar from "../../../Assets/Images/defaultAvatar.png";
import ProfileStatusContainer from "./ProfileData/ProfileStatus/ProfileStatusContainer";
import ProfileData from "./ProfileData/ProfileData";
import ProfileEditForm from "./ProfileEditForm/ProfileEditForm";
import {useState} from "react";
import EditPhotoComponent from "./EditPhotoComponent/EditPhotoComponent";

const ProfileDescription = (props) => {

    let [editMode, setEditMode] = useState(false);


    const editModeON = () => {
        setEditMode(true);
    }

    const editModeOFF = (formData) => {
        props.updateUserDataFromForm(props.user, formData)
            .then(
                () => {
                    setEditMode(false);
                }
            );
    }

    return (
        <div name="profileDescription">
            <h3 name="nickname">{props.user.nickname}</h3>
            <img name="photo" className={styleClass.photoStyle}
                 src={props.user.imgURL === "" ? defaultAvatar : props.user.imgURL} alt="Фото профиля"/>
            {
                props.isOwner &&
                <EditPhotoComponent savePhoto={props.savePhoto} authUserID={props.authUserID}/>

            }
            <ProfileStatusContainer status={props.user.status} setStatus={props.setStatus}
                                    updateStatus={props.updateStatus} authUserID={props.authUserID}/>
            {
                editMode
                    ? <ProfileEditForm isOwner={props.isOwner} user={props.user}
                                       initialValues={props.user} onSubmit={editModeOFF}/>
                    : <ProfileData isOwner={props.isOwner} user={props.user} editModeON={editModeON}/>

            }
        </div>
    );
}

export default ProfileDescription;