import styleClass from "./Message.module.css"

const Message = (props) => {
    return(
        <div className={styleClass.messageStyle}>
            {props.text}
        </div>
    );
}

export default Message;