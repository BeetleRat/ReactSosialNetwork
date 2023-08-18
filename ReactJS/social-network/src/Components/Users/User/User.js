import styleClass from "./User.module.css"
import {NavLink} from "react-router-dom";
import defaultAvatar from "./../../../assets/images/defaultAvatar.png"

const User = (props) => {
    const toggleFollowing = (props) => {
        if (props.isFollow) {
            props.unfollowUser(props.id);
        } else {
            props.followUser(props.id);
        }
    }

    return (
        <div className={styleClass.userStyle}>
            <NavLink to={"/profile/" + props.id}>
                <img className={styleClass.userPhotoStyle} src={props.imgURL === "" ? defaultAvatar : props.imgURL}/>
            </NavLink>
            <div>
                Имя: {props.name}
            </div>
            <div>
                Статус: {props.status}
            </div>
            <div>
                {props.isAuth ? <button onClick={() => toggleFollowing(props)}
                                        disabled={props.fetchingUsers.some(id => id === props.id)}>{props.isFollow ? "Unfollow" : "Follow"}</button> :
                    <br/>}

            </div>
            <hr/>
        </div>
    );
}

export default User;