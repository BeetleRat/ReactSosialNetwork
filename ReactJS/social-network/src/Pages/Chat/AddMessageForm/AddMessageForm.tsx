import React, {useState} from "react";
import {Button} from "antd";
import TextArea from "antd/lib/input/TextArea";

type PropsType = {
    sendMessage: (message: string) => void
}
const AddMessageForm: React.FC<PropsType> = ({sendMessage}) => {
    const [currentMessage, setCurrentMessage] = useState<string>("");
    const changeCurrentMessage = (element: React.ChangeEvent<HTMLTextAreaElement>) => {
        setCurrentMessage(element.currentTarget.value);
    }

    const sendMessageFromLocalState = () => {
        sendMessage(currentMessage);
        setCurrentMessage("");
    }

    return (
        <div>
            <div>
                <TextArea onChange={changeCurrentMessage}></TextArea>
            </div>
            <div>
                <Button onClick={sendMessageFromLocalState}>Отправить сообщение</Button>
            </div>
        </div>
    );
}

export default AddMessageForm;