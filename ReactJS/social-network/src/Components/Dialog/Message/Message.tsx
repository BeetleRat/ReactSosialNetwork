import styleClass from "./Message.module.css"
import React from "react";

type PropsType = {
    text: string
}

const Message: React.FC<PropsType> = ({text}) => {
    return (
        <div className={styleClass.messageStyle}>
            {text}
        </div>
    );
}

export default Message;