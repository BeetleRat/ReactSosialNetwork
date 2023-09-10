import styleClass from "./NotFoundPage.module.css"
import NotFoundImage from "./../../../Assets/Images/NotFoundImage.jpg"
import React from "react";

type PropsType = {}

const NotFoundPage: React.FC<PropsType> = (props) => {
    return (
        <div>
            <img className={styleClass.imageStyle} src={NotFoundImage} alt={"Изображение \"Страница не найдена\""}/>
            <p>404 PAGE NOT FOUND</p>
        </div>
    );
}

export default NotFoundPage;