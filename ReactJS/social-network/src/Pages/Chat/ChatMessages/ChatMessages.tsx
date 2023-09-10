import React from "react";
import OneChatMessage from "./OneChatMessage/OneChatMessage";
import {ChatMessageType} from "../ChatPage";

type PropsType = {
    messageArray: ChatMessageType[]
}
const ChatMessages: React.FC<PropsType> = (props) => {

    return (
        <div style={{height: "200px", overflowY: "auto"}}>
            {props.messageArray.map((message, key) =>
                <div key={key}>
                    <OneChatMessage username={message.userName}
                                    userAvatarImageURL={message.imgURL}
                                    message={message.message}/>

                </div>
            )
            }
        </div>
    );
}

export default ChatMessages;