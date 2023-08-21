import React from "react";
import {connect} from "react-redux";
import ProfileInfo from "./ProfileInfo";
import {addPost, savePhoto, setProfile, setStatus, updateStatus} from "../../Redux/Redusers/ProfileReducer";
import {Redirect, withRouter} from "react-router-dom";
import {compose} from "redux";
import {getProfilePosts, getProfileUser} from "../../Redux/Selectors/ProfileSelectors";
import {getAuthUsersID, getIsAuth} from "../../Redux/Selectors/AuthSelectors";


// Классовая контейнерная компонента
// Осуществляет взаимодействие с сервером

class ProfileInfoContainer extends React.Component {
    state = {
        userID: 23,
        isOwner: false,
        needRedirect: false
    }

    // Метод жизненного цикла
    // вызываемый при вставке классового компонента в общую разметку
    // данный метод выполняется 1 раз.
    componentDidMount() {
        this.refreshProfile();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.match.params.userID !== prevProps.match.params.userID) {
            this.refreshProfile();
        }
    }

    refreshProfile() {
        // Берем параметр userID из URL адреса
        let userID = this.props.match.params.userID;
        let needRedirect = false;
        // Если в URL нет такого параметра(userID==undefined)
        if (!userID) {
            if (this.props.isAuth) {
                userID = this.props.authUserID;
            } else {
                userID = 23;
                needRedirect = true;
            }
        }

        this.setState(
            {
                userID: userID,
                isOwner: userID === this.props.authUserID,
                needRedirect: needRedirect
            }
        )
        this.props.setProfile(userID)
    };

    render() {
        if(this.state.needRedirect){
            return (<Redirect to='/login'/>);
        }

        return (
            <ProfileInfo user={this.props.user} posts={this.props.posts}
                         isOwner={this.state.isOwner}
                         addPost={this.props.addPost}
                         setStatus={this.props.setStatus} updateStatus={this.props.updateStatus}
                         authUserID={this.props.authUserID}
                         savePhoto={this.props.savePhoto}/>
        )
    }
}

// Стрелочная функция возвращающая
// объект данных
// передаваемых в презентационную компоненту
// (в данном случае данные сначала прокидываются в другую контейнерную компоненту,
// а из нее в презентационную)
let MapStateToProps = (state) => {
    return {
        user: getProfileUser(state),
        posts: getProfilePosts(state),
        authUserID: getAuthUsersID(state),
        isAuth: getIsAuth(state)
    }
}

export default compose(
    withRouter,
    connect(MapStateToProps, {addPost, setProfile, updateStatus, setStatus, savePhoto})
)(ProfileInfoContainer);