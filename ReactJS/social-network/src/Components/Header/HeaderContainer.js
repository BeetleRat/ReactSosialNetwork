import React from "react";
import Header from "./Header";
import {connect} from "react-redux";

class HeaderContainer extends React.Component {
    componentDidMount() {

    }

    render() {
        return (<Header userID={this.props.userID} email={this.props.email} username={this.props.username}
                        isAuth={this.props.isAuth} tasks={this.props.tasks} currentTaskID={this.props.currentTaskID}/>);
    }
}

const mapStateToProps = (state) => {
    return {
        userID: state.auth.userID,
        email: state.auth.email,
        username: state.auth.username,
        isAuth: state.auth.isAuth,
        token: state.auth.token,
        tasks: state.progressPage.tasks,
        currentTaskID: state.progressPage.currentTaskID
    }

}
export default connect(mapStateToProps, {})(HeaderContainer);
