import styleClass from "./UserName.module.css"

const UserName = (props) =>{
    return(
        <div className={styleClass.userNameStyle}>
            {props.name}
        </div>
    );
}

export default UserName;