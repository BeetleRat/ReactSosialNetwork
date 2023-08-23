import React from "react";
import {useEffect, useState} from "react";
import ProfileStatus from "./ProfileStatus";


const ProfileStatusContainer =
    React.memo(
        (props) => {

            // Локальный state через hook-и
            let [editMode, setEditMode] = useState(false);
            let [status, setStatus] = useState(props.status);

            // Перерисовка комопоненты в случае изменения props через hook-и
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
                <ProfileStatus editMode={editMode} status={status}
                               changeInputTextStatus={changeInputTextStatus}
                               editModeOFF={editModeOFF} editModeON={editModeON}/>
            );
        }
    );

export default ProfileStatusContainer;