import styleClass from "./Preloader.module.css"
import image from "../../../Assets/Images/preloder.gif"
import React from "react";
type PropsType={

}
const Preloader: React.FC<PropsType> = (props) => {
    return (
        <div className={styleClass.preloaderStyle}>
            <img src={image} alt={"Loading image"}/>
        </div>
    )
}

export default Preloader;