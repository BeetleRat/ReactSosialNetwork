import {connect} from "react-redux";
import {sendNewMessage} from "../../Redux/Redusers/DialogReducer";
import Dialog from "./Dialog";
import {withAuthRedirect} from "../../HOC/WithAuthRedirect";
import {compose} from "redux";
import {getMessageArray, getNameArray} from "../../Redux/Selectors/DialogSelectors";

let MapStateToProps = (state) => {
    return {
        names: getNameArray(state),
        messageArray: getMessageArray(state)
    };
};

export default compose(
    connect(MapStateToProps, {sendNewMessage}),
    withAuthRedirect
)(Dialog);