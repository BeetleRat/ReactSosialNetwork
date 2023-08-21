import styleClass from "./ProfileDescription.module.css";
import defaultAvatar from "../../../assets/images/defaultAvatar.png";
import ProfileStatusWithHooks from "../ProfileStatus/ProfileStatusWithHooks";

const ProfileDescription = (props) => {

    const onMainPhotoSelected = (element) => {
        if (element.target.files.length > 0) {
            let photo = element.target.files[0];

            props.savePhoto(props.authUserID, photo);
        }
    }

    return (
        <div name="profileInfo">
            <h3 name="nickname">{props.user.nickname}</h3>
            <img name="photo" className={styleClass.photoStyle}
                 src={props.user.imgURL === "" ? defaultAvatar : props.user.imgURL} alt="Фото профиля"/>
            {props.isOwner &&
                <div>
                    <input type={"file"} onChange={onMainPhotoSelected}/>
                </div>
            }


            <ProfileStatusWithHooks status={props.user.status} setStatus={props.setStatus}
                                    updateStatus={props.updateStatus} authUserID={props.authUserID}/>
            <p name="country">Страна: {props.user.country}</p>
            <p name="city">Город: {props.user.city}</p>
            {
                props.user.lookingForAJob ? <p>Ищу работу: {props.user.searchJobDescription}</p> : ""
            }
        </div>
    );
}
export default ProfileDescription;