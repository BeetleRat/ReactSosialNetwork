import React from "react";
import ChatMessages from "./ChatMessages/ChatMessages";
import AddMessageForm from "./AddMessageForm/AddMessageForm";
import {ChatMessageType} from "./ChatPage";

type PropsType = {
    messageArray: ChatMessageType[],
    sendMessage: (message: string) => void
}
const Chat: React.FC<PropsType> = (props) => {

    return (
        <div>
            <ChatMessages messageArray={props.messageArray}/>
            <hr/>
            <AddMessageForm sendMessage={props.sendMessage}/>
        </div>
    );
}

export default Chat;