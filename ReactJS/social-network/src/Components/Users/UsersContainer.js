import {connect} from "react-redux";
import {
    followUser, getUsersFromServer,
    setCurrentPage,
    toggleFetchingUser,
    unfollowUser
} from "../../Redux/Redusers/UserReducer";
import React from "react";
import Users from "./Users";
import Preloader from "../CommonComponents/Preloader/Preloader";

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
       this.props.getUsersFromServer(page,this.props.pageSize);
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
                       isAuth={this.props.isAuth} changePage={this.changePage}
                       followUser={this.props.followUser} unfollowUser={this.props.unfollowUser}
                       toggleFetchingUser={this.props.toggleFetchingUser}/>
            </div>
        );
    };
};
// Конец классовой компоненты___________________________________________________________
const MapStateToProps = (state) => {
    return {
        users: state.userPage.users,
        fetchingUsers: state.userPage.fetchingUsers,
        pageSize: state.userPage.pageSize,
        totalUsersCount: state.userPage.totalUsersCount,
        currentPage: state.userPage.currentPage,
        isLoading: state.userPage.isLoading,
        isAuth: state.auth.isAuth
    };
}

export default connect(MapStateToProps, {
    followUser,
    unfollowUser,
    setCurrentPage,
    toggleFetchingUser,
    getUsersFromServer
})(UsersContainer);