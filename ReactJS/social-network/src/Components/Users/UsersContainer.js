import {connect} from "react-redux";
import {
    followUser, requestUsersFromServer,
    setCurrentPage,
    toggleFetchingUser,
    unfollowUser
} from "../../Redux/Redusers/UserReducer";
import React from "react";
import Users from "./Users";
import Preloader from "../CommonComponents/Preloader/Preloader";
import {

    getCurrentPage, getFetchingUsers,
    getPageSize,
    getTotalUsersCount,
    getUsers
} from "../../Redux/Selectors/UserSelectors";
import {getIsLoading} from "../../Redux/Selectors/LoadingSelectors";
import {getIsAuth, getAuthUsersID} from "../../Redux/Selectors/AuthSelectors";
import {compose} from "redux";
import {withAuthRedirect} from "../../HOC/WithAuthRedirect";

// Классовая компонента, отвечающая за взаимодействие с REST сервисом_______________________
class UsersContainer extends React.Component {

    // Метод жизненного цикла
    // вызываемый при вставке классового компонента в общую разметку
    // данный метод выполняется 1 раз.
    componentDidMount() {
        // Вызов метода взаимодействия с сервером
        this.GetUsersFromServer(this.props.currentPage - 1);
    }

    // Метод взаимодействия с сервером
    // Получить список пользователей со страницы page
    GetUsersFromServer(page) {
        this.props.getUsersFromServer(page, this.props.pageSize);
    };

    // Метод нажания на "кнопку" страницы
    // Если метод вызываетя внутри разметки,
    // то он должен описываться через стрелочную функцию
    changePage = (newPage) => {
        this.props.setCurrentPage(newPage);
        this.GetUsersFromServer(newPage - 1);
    }

    // Перегрузка метода суперкласса
    // метод возвращающий JSX разметку
    // Поскольку данная компонента тоже является контейнерной,
    // то в данном методе возвращается презентационная компонента
    render() {

        return (
            <div>
                {
                    // Отобразить Preloader, если нужно
                    this.props.isLoading ? <Preloader/> : ""
                }
                <Users users={this.props.users} fetchingUsers={this.props.fetchingUsers}
                       totalUsers={this.props.totalUsersCount}
                       pageSize={this.props.pageSize} currentPage={this.props.currentPage}
                       isAuth={this.props.isAuth} currentUserID={this.props.currentUserID}
                       changePage={this.changePage}
                       followUser={this.props.followUser} unfollowUser={this.props.unfollowUser}
                       toggleFetchingUser={this.props.toggleFetchingUser}/>
            </div>
        );
    };
};
// Конец классовой компоненты___________________________________________________________

const MapStateToProps = (state) => {
    return {

        users: getUsers(state),
        fetchingUsers: getFetchingUsers(state),
        pageSize: getPageSize(state),
        totalUsersCount: getTotalUsersCount(state),
        currentPage: getCurrentPage(state),
        isLoading: getIsLoading(state),
        isAuth: getIsAuth(state),
        currentUserID: getAuthUsersID(state)
    };
}

export default compose(
    withAuthRedirect,
    connect(
        MapStateToProps,
        {
            followUser,
            unfollowUser,
            setCurrentPage,
            toggleFetchingUser,
            getUsersFromServer: requestUsersFromServer
        }
    )
)(UsersContainer);