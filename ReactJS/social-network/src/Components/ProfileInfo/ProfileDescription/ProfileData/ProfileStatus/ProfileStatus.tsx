import styleClass from "./ProfileStatus.module.css"
import React, {ChangeEvent} from "react";

type PropsType = {
    editMode: boolean,
    status: string,
    editModeON: () => void,
    editModeOFF: (element: ChangeEvent<HTMLInputElement>) => void,
    changeInputTextStatus: (element: ChangeEvent<HTMLInputElement>) => void
}

const ProfileStatus: React.FC<PropsType> = (props) => {
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
                        <p onDoubleClick={props.editModeON}>
                            {/*Используем переменную из useState*/}
                            <b>Статус</b>: {props.status ? props.status : "No status."}
                        </p>
                    </div>
            }
        </div>
    );
}

export default ProfileStatus;