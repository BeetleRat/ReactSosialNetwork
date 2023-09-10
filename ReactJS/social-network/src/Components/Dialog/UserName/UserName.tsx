import styleClass from "./UserName.module.css"
import React from "react";

type PropsType = {
    name: string
}

const UserName:React.FC<PropsType> = ({name}) =>{
    return(
        <div className={styleClass.userNameStyle}>
            {name}
        </div>
    );
}

export default UserName;