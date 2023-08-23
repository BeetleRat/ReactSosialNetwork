import Login from "./Login";
import {connect} from "react-redux";
import {login, logout} from "../../Redux/Reducers/AurhReducer";
import {
    getAuthExceptionMessage,
    getAuthUsersEmail,
    getAuthUsersUsername,
    getIsAuth
} from "../../Redux/Selectors/AuthSelectors";
import {setFollowedUsers} from "../../Redux/Reducers/UserReducer";

const mapStateToProps = (state) => {
    return {
        authExceptionMessage: getAuthExceptionMessage(state),
        isAuth: getIsAuth(state),
        username: getAuthUsersUsername(state),
        email: getAuthUsersEmail(state)
    }
}

export default connect(mapStateToProps, {login, logout, setFollowedUsers})(Login)