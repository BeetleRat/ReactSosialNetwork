import React from "react";
import Header from "./Header";
import {connect} from "react-redux";
import {
    getAuthUsersUsername,
    getAuthUsersEmail,
    getAuthUsersID,
    getIsAuth,
    getAccessToken
} from "../../Redux/Selectors/AuthSelectors";
import {getCurrentTaskID, getTasks} from "../../Redux/Selectors/ProgressSelectors";

class HeaderContainer extends React.Component {

    render() {
        return (<Header userID={this.props.userID} email={this.props.email} username={this.props.username}
                        isAuth={this.props.isAuth} tasks={this.props.tasks} currentTaskID={this.props.currentTaskID}/>);
    }
}

const mapStateToProps = (state) => {
    return {
        userID: getAuthUsersID(state),
        email: getAuthUsersEmail(state),
        username:getAuthUsersUsername(state),
        isAuth: getIsAuth(state),
        token: getAccessToken(state),
        tasks: getTasks(state),
        currentTaskID: getCurrentTaskID(state)
    }

}
export default connect(mapStateToProps, {})(HeaderContainer);
