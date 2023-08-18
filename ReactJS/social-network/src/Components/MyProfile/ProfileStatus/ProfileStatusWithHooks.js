import styleClass from "./ProfileStatus.module.css"
import React from "react";
import {useEffect, useState} from "react";


const ProfileStatusWithHooks =
    React.memo(
        (props) => {

            // Локальный state через hook-и
            let [editMode, setEditMode] = useState(false);
            let [status, setStatus] = useState(props.status);

            useEffect(
                () => setStatus(props.status),
                [props.status]
            );

            const editModeON = () => {
                // Используем setter из useState
                setEditMode(true);
            }

            const editModeOFF = (event) => {
                // Используем setter из useState
                setEditMode(false);

                let status = event.target.value;
                props.updateStatus(props.authUserID, status);
            }

            const changeInputTextStatus = (event) => {
                let status = event.target.value;
                // Используем setter из useState
                setStatus(status);
            }

            return (
                <div>
                    {
                        editMode // Используем переменную из useState
                            ? <div>
                                <label htmlFor="statusInput">Статус: </label>
                                <input id="statusInput" name="statusInput" type="text"
                                       value={status} // Используем переменную из useState
                                       onChange={changeInputTextStatus} onBlur={editModeOFF}
                                       autoFocus={true}/>
                            </div>
                            : <div>
                                <p name="status" onDoubleClick={editModeON}>
                                    {/*Используем переменную из useState*/}
                                    Статус: {status ? status : "No status."}
                                </p>
                            </div>
                    }

                </div>
            );
        }
    )

export default ProfileStatusWithHooks;