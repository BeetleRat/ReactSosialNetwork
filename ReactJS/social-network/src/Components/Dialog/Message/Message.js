import styleClass from "./Message.module.css"

const Message = ({text}) => {
    return(
        <div className={styleClass.messageStyle}>
            {text}
        </div>
    );
}

export default Message;