import styleClass from "./Users.module.css"
import User from "./User/User";


const Users = (props) => {
    const followUser = (id) => {
        return props.followUser(id);
    }

    const unfollowUser = (id) => {
        return props.unfollowUser(id);
    }

    let maxPage = Math.ceil(props.totalUsers / props.pageSize);
    let pages = [];
    for (let i = 1; i <= maxPage; i++) {
        pages.push(i);
    }
    let count = 0;
    return (
        <div className={styleClass.usersStyle}>
            {pages.map((page) =>
                <span onClick={() => props.changePage(page)}
                      className={props.currentPage === page ? styleClass.selectedPage : ""}> {page} </span>
            )}
            {props.users.map(user =>
                <div>
                    <User key={user.id} id={user.id} name={user.name} fetchingUsers={props.fetchingUsers}
                          isFollow={user.isFollow}
                          status={user.status} imgURL={user.imgURL} isAuth={props.isAuth}
                          followUser={followUser} unfollowUser={unfollowUser}/>
                    <hr/>
                </div>
            )}
        </div>
    );
}
export default Users;