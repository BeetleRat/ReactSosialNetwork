import styleClass from "./NotFoundPage.module.css"
import NotFoundImage from "./../../../Assets/Images/NotFoundImage.jpg"

const NotFoundPage = (props) => {
    return (
        <div>
            <img className={styleClass.imageStyle} src={NotFoundImage} alt={"Изображение \"Страница не найдена\""}/>
            <p>404 PAGE NOT FOUND</p>
        </div>
    );
}

export default NotFoundPage;