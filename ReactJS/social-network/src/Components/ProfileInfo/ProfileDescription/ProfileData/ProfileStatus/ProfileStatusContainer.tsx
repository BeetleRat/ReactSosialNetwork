import React, {ChangeEvent} from "react";
import {useEffect, useState} from "react";
import ProfileStatus from "./ProfileStatus";

type PropsType = {
    authUserID: number,
    status: string,
    updateStatus: (authUserID: number, status: string) => void
}

const ProfileStatusContainer: React.FC<PropsType> = (props) => {
    // Локальный state через hook-и
    let [editMode, setEditMode] = useState<boolean>(false);
    let [status, setStatus] = useState<string>(props.status);

    // Перерисовка комопоненты в случае изменения props через hook-и
    useEffect(
        () => setStatus(props.status),
        [props.status]
    );

    const editModeON = () => {
        // Используем setter из useState
        setEditMode(true);
    }

    const editModeOFF = (element: ChangeEvent<HTMLInputElement>) => {
        // Используем setter из useState
        setEditMode(false);

        let status = element.target.value;
        props.updateStatus(props.authUserID, status);
    }

    const changeInputTextStatus = (element: ChangeEvent<HTMLInputElement>) => {
        let status = element.target.value;
        // Используем setter из useState
        setStatus(status);
    }

    return (
        <ProfileStatus editMode={editMode} status={status}
                       changeInputTextStatus={changeInputTextStatus}
                       editModeOFF={editModeOFF} editModeON={editModeON}/>
    );
}


export default React.memo(ProfileStatusContainer);