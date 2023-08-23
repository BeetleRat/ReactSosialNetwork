import styleClass from "./UserName.module.css"

const UserName = ({name}) =>{
    return(
        <div className={styleClass.userNameStyle}>
            {name}
        </div>
    );
}

export default UserName;