import styleClass from "./OnePost.module.css"
import React from "react";

type PropsType = {
    text: string,
    likes: number
}

const OnePost: React.FC<PropsType> = ({text, likes}) => {
    return (
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