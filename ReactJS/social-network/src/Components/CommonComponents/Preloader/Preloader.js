import styleClass from "./Preloader.module.css"
import image from "../../../Assets/Images/preloder.gif"

const Preloader = (props) => {
    return (
        <div className={styleClass.preloaderStyle}>
            <img src={image} alt={"Loading image"}/>
        </div>
    )
}

export default Preloader;