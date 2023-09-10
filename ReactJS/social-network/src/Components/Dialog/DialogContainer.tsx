import {useDispatch, useSelector} from "react-redux";
import Dialog from "./Dialog";
import {withAuthRedirect} from "../../HOC/WithAuthRedirect";
import {compose} from "redux";
import {getMessageArray, getNameArray} from "../../Redux/Selectors/DialogSelectors";
import React from "react";
import {ThunkDispatcher} from "../../Types/Types";
import {actions} from "../../Redux/Reducers/DialogReducer";

type PropsType = {}

const DialogContainer: React.FC<PropsType> = (props) => {
    const names = useSelector(getNameArray);
    const messageArray = useSelector(getMessageArray);

    const dispatch = useDispatch<ThunkDispatcher>();

    const sendNewMessage = (message: string) => {
        dispatch(actions.sendNewMessage(message))
    }

    return (
        <Dialog names={names} messageArray={messageArray} sendNewMessage={sendNewMessage}/>
    );
}
export default compose<React.ComponentType>(
    withAuthRedirect,
    React.memo
)(DialogContainer);