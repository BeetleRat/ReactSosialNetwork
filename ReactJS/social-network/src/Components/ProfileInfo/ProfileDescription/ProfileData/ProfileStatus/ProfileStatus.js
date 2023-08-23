import styleClass from "./ProfileStatus.module.css"
import React from "react";

const ProfileStatus = (props) => {
    return (
        <div>
            {
                props.editMode // Используем переменную из useState
                    ? <div>
                        <label htmlFor="statusInput"><b>Статус</b>: </label>
                        <input id="statusInput" name="statusInput" type="text"
                               value={props.status} // Используем переменную из useState
                               onChange={props.changeInputTextStatus} onBlur={props.editModeOFF}
                               autoFocus={true}/>
                    </div>
                    : <div>
                        <p name="status" onDoubleClick={props.editModeON}>
                            {/*Используем переменную из useState*/}
                            <b>Статус</b>: {props.status ? props.status : "No status."}
                        </p>
                    </div>
            }
        </div>
    );
}

export default ProfileStatus;