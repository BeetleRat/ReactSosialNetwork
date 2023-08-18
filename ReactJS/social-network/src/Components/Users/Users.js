import styleClass from "./Users.module.css"
import User from "./User/User";
import PageNumbers from "../CommonComponents/PageNumbers/PageNumbers";


const Users = (props) => {
    const followUser = (id) => {
        return props.followUser(props.currentUserID, id);
    }

    const unfollowUser = (id) => {
        return props.unfollowUser(props.currentUserID, id);
    }

    return (
        <div className={styleClass.usersStyle}>

            <PageNumbers totalItems={props.totalUsers} pageSize={props.pageSize}
                         currentPage={props.currentPage} changePage={props.changePage}/>
            <hr/>

            {props.users.map(user =>
                <User key={user.id} id={user.id} name={user.name} fetchingUsers={props.fetchingUsers}
                      isFollow={user.isFollow}
                      status={user.status} imgURL={user.imgURL} isAuth={props.isAuth}
                      followUser={followUser} unfollowUser={unfollowUser}/>
            )}

            <PageNumbers totalItems={props.totalUsers} pageSize={props.pageSize}
                         currentPage={props.currentPage} changePage={props.changePage}/>
        </div>
    );
}
export default Users;