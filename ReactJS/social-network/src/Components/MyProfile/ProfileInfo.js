// Подключение класса стилей
import styleClass from "./ProfileInfo.module.css"
import Posts from "./Posts/Posts";
import Preloader from "../CommonComponents/Preloader/Preloader";
import defaultAvatar from "../../assets/images/defaultAvatar.png";
import ProfileStatus from "./ProfileStatus/ProfileStatus";

const ProfileInfo = (props) => {
    if (!props.user) {
        return <Preloader/>;
    }

    return (
        /*Использование стиля contentStyle
        из класса стилей styleClass*/
        <div className={styleClass.contentStyle}>

            {/*<div>*/}
            {/*    <img name="wallpaper" className={styleClass.contentWallpaperStyle}*/}
            {/*         src='https://glass-vector.com/static2/preview2/stock-vector-kuhonnyy-fartuk-1644-14813.jpg'*/}
            {/*         alt="Обои"/>*/}
            {/*</div>*/}
            <div name="profileInfo">
                <h3 name="nickname">{props.user.nickname}</h3>
                <img name="photo" className={styleClass.photoStyle}
                     src={props.user.imgURL === "" ? defaultAvatar : props.user.imgURL} alt="Фото профиля"/>

                <ProfileStatus status={props.user.status} setStatus={props.setStatus} updateStatus={props.updateStatus}/>
                <p name="country">Страна: {props.user.country}</p>
                <p name="city">Город: {props.user.city}</p>
                {
                    props.user.lookingForAJob ? <p>Ищу работу: {props.user.searchJobDescription}</p> : ""
                }
            </div>

            <Posts posts={props.posts} addNewPost={props.addPost}/>
        </div>
    );
}
export default ProfileInfo;