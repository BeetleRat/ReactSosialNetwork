import Login from "./Login";
import {connect} from "react-redux";
import {login, logout} from "../../Redux/Redusers/AurhReducer";

const mapStateToProps = (state) => {
    return {
        authExceptionMessage: state.auth.authExceptionMessage,
        isAuth: state.auth.isAuth,
        username: state.auth.username,
        email: state.auth.email
    }
}

export default connect(mapStateToProps, {login, logout})(Login)