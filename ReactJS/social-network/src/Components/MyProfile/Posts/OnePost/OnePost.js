import styleClass from "./OnePostModule.css"

const OnePost = (props) => {
    return(
        <div className={styleClass.postStyle}>
            <div>
                {props.text}
            </div>
            <div>
                Лайки: {props.likes}
            </div>
        </div>
    );
}
export default OnePost;