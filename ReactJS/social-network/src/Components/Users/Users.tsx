import styleClass from "./Users.module.css"
import User from "./User/User";
import PageNumbers from "../CommonComponents/PageNumbers/PageNumbers";
import {UserType} from "../../Types/Types";
import React from "react";
import UsersSearchForm from "./UsersSearchForm/UsersSearchForm";
import {FilterFormValues} from "../../Redux/Reducers/UserReducer";


type PropsType = {
    filter:FilterFormValues,
    users: Array<UserType>,
    isAuth: boolean,
    currentUserID: number | null,
    totalUsers: number,
    currentPage: number,
    pageSize: number,
    fetchingUsers: Array<number>,
    changePage: (newPage: number) => void,
    followUser: (currentUserID: number | null, id: number) => void,
    unfollowUser: (currentUserID: number | null, id: number) => void,
    onFilterChanged: (filter: FilterFormValues) => void
}

const Users: React.FC<PropsType> = (props) => {
    const followUser = (id: number) => {
        return props.followUser(props.currentUserID, id);
    }

    const unfollowUser = (id: number) => {
        return props.unfollowUser(props.currentUserID, id);
    }

    return (
        <div className={styleClass.usersStyle}>
            <UsersSearchForm filter={props.filter} onFilterChanged={props.onFilterChanged}/>
            <hr/>
            <PageNumbers totalItems={props.totalUsers} pageSize={props.pageSize}
                         currentPage={props.currentPage} changePage={props.changePage}/>
            <hr/>

            {
                props.users.map(user =>
                    <User key={user.id} id={user.id} name={user.name} fetchingUsers={props.fetchingUsers}
                          followed={user.followed}
                          status={user.status} imgURL={user.imgURL} isAuth={props.isAuth}
                          followUser={followUser} unfollowUser={unfollowUser}/>
                )
            }

            <PageNumbers totalItems={props.totalUsers} pageSize={props.pageSize}
                         currentPage={props.currentPage} changePage={props.changePage}/>
        </div>
    );
}

export default Users;