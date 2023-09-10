import React, {useEffect, useState} from "react";
import Chat from "./Chat";
import {compose} from "redux";
import {withAuthRedirect} from "../../HOC/WithAuthRedirect";
import {SERVER_ADDRESS} from "../../ServerAPI/serverInteractionAPI";


debugger;
const webSocketChannel = new WebSocket('ws://localhost:8080/ws');
type PropsType = {}
export type ChatMessageType = {
    userID: number,
    userName: string,
    imgURL: string | null,
    message: string
}
const ChatPage: React.FC<PropsType> = (props) => {

    const [messageArray, setMessageArray] = useState<ChatMessageType[]>([]);

    useEffect(
        () => {
            webSocketChannel.addEventListener(
                'message',
                (response) => {
                    let newMessages = JSON.parse(response.data);
                    console.log(newMessages);
                    setMessageArray(
                        (prevMessages) => [...prevMessages, ...newMessages]
                    );
                }
            );
        },
        []
    );

    const sendMessage = (message: string) => {
        debugger;
        webSocketChannel.send(message);
    }

    return (
        <div>
            <Chat messageArray={messageArray} sendMessage={sendMessage}/>
        </div>
    );
}

export default compose<React.ComponentType>(
    withAuthRedirect,
    React.memo
)(ChatPage);