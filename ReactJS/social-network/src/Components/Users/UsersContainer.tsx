import {useDispatch, useSelector} from "react-redux";
import {
    actions,
    FilterFormValues,
    followUser,
    requestUsersFromServer,
    unfollowUser
} from "../../Redux/Reducers/UserReducer";
import React, {useEffect} from "react";
import Users from "./Users";
import Preloader from "../CommonComponents/Preloader/Preloader";
import {
    getCurrentPage,
    getFetchingUsers,
    getFilter,
    getPageSize,
    getTotalUsersCount,
    getUsers
} from "../../Redux/Selectors/UserSelectors";
import {getIsLoading} from "../../Redux/Selectors/LoadingSelectors";
import {getAuthUsersID, getIsAuth} from "../../Redux/Selectors/AuthSelectors";
import {compose} from "redux";
import {withAuthRedirect} from "../../HOC/WithAuthRedirect";
import {BooleanStringType, ThunkDispatcher} from "../../Types/Types";
import {useSearchParams} from "react-router-dom";


type PropsType = {};
type URLParamsType = {
    page?: string,
    term?: string,
    friend?: string
}

const UsersContainer: React.FC<PropsType> = (props) => {

    const totalUsersCount = useSelector(getTotalUsersCount);
    const users = useSelector(getUsers);
    const fetchingUsers = useSelector(getFetchingUsers);
    const pageSize = useSelector(getPageSize);
    const currentPage = useSelector(getCurrentPage);
    const filter = useSelector(getFilter);
    const isLoading = useSelector(getIsLoading);
    const isAuth = useSelector(getIsAuth);
    const currentUserID = useSelector(getAuthUsersID);

    const dispatch = useDispatch<ThunkDispatcher>();

    const [searchParams, setSearchParams] = useSearchParams();


    useEffect(
        () => {
            const actualPage = searchParams.get("page") == null ? currentPage : Number(searchParams.get("page"));
            const actualTerm = searchParams.get("term") == null ? filter.term : searchParams.get("term") as string;
            const actualFriend = searchParams.get("friend") == null ? filter.friend : (searchParams.get("friend") === 'true');

            getUsersFromServer(actualPage - 1, {term: actualTerm, friend: actualFriend})
        },
        []
    );

    useEffect(
        () => {
            const urlParams: URLParamsType = {};

            if (filter.term !== "") {
                urlParams.term = filter.term;
            }
            if (filter.friend !== null) {
                urlParams.friend = String(filter.friend) as BooleanStringType
            }
            if (currentPage !== 1) {
                urlParams.page = String(currentPage);
            }

            setSearchParams(urlParams);
        },
        [filter, currentPage]);

    const getUsersFromServer = (page: number, filter: FilterFormValues) => {
        dispatch(requestUsersFromServer(page, pageSize, filter));
    };

    const changePage = (currentPage: number) => {
        dispatch(actions.setCurrentPage(currentPage));
        getUsersFromServer(currentPage - 1, filter);
    }

    const onFilterChanged = (filter: FilterFormValues) => {
        dispatch(actions.setFilter(filter));
        getUsersFromServer(0, filter);
    }

    const followUserRequest = (currentUserID: number | null, userID: number) => {
        dispatch(followUser(currentUserID, userID));
    }

    const unfollowUserRequest = (currentUserID: number | null, userID: number) => {
        dispatch(unfollowUser(currentUserID, userID));
    }

    return (
        <div>
            {
                // Отобразить Preloader, если нужно
                isLoading && <Preloader/>
            }
            <Users filter={filter} users={users}
                   fetchingUsers={fetchingUsers} totalUsers={totalUsersCount}
                   pageSize={pageSize} currentPage={currentPage}
                   isAuth={isAuth} currentUserID={currentUserID}
                   changePage={changePage}
                   followUser={followUserRequest} unfollowUser={unfollowUserRequest}
                   onFilterChanged={onFilterChanged}/>
        </div>
    );
}

export default compose<React.ComponentType>(
    withAuthRedirect,
    React.memo
)(UsersContainer);