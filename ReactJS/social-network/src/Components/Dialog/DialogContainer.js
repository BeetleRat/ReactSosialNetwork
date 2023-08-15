import {connect} from "react-redux";
import {sendNewMessage} from "../../Redux/Redusers/DialogReducer";
import Dialog from "./Dialog";
import {withAuthRedirect} from "../../HOC/WithAuthRedirect";
import {compose} from "redux";

let MapStateToProps = (state) => {
    return {
        names: state.dialogPage.nameArray,
        messageArray: state.dialogPage.messageArray
    };
};

export default compose(
    connect(MapStateToProps, {sendNewMessage}),
    withAuthRedirect
)(Dialog);