import styleClass from "./Preloader.module.css"
import image from "../../../assets/images/preloder.gif"
const Preloader= (props)=>{
    return(
        <div className={styleClass.preloaderStyle}>
            <img src={image}/>
        </div>
    )
}

export default Preloader;