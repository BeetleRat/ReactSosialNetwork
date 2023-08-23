import styleClass from "./OnePostModule.css"

const OnePost = ({text,likes}) => {
    return(
        <div className={styleClass.postStyle}>
            <div>
                {text}
            </div>
            <div>
                Лайки: {likes}
            </div>
        </div>
    );
}
export default OnePost;