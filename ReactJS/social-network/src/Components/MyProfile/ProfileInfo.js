// Подключение класса стилей
import styleClass from "./ProfileInfo.module.css"
import Posts from "./Posts/Posts";
import Preloader from "../CommonComponents/Preloader/Preloader";
import ProfileDescription from "./ProfileDescription/ProfileDescription";

const ProfileInfo = (props) => {
    if (!props.user) {
        return <Preloader/>;
    }

    return (
        <div className={styleClass.contentStyle}>
            <ProfileDescription user={props.user}
                                isOwner={props.isOwner}
                                setStatus={props.setStatus}
                                updateStatus={props.updateStatus}
                                authUserID={props.authUserID}
                                savePhoto={props.savePhoto}/>

            <Posts posts={props.posts} addNewPost={props.addPost}/>
        </div>
    );
}
export default ProfileInfo;