import Contact from "./Contact/Contact";
import React from "react";
import {ContactsType, ProfileType, StringKey} from "../../../../Types/Types";

type PropsType = {
    isOwner: boolean,
    profile: ProfileType,
    editModeON: () => void
}

const ProfileData: React.FC<PropsType> = ({isOwner, profile, editModeON}) => {
    return (
        <div>
            {
                isOwner &&
                <button onClick={editModeON}>Редактировать</button>
            }
            <p ><b>Страна</b>: {profile.country}</p>
            <p ><b>Город</b>: {profile.city}</p>
            {
                profile.lookingForAJob ? <p><b>Ищу работу</b>: {profile.searchJobDescription}</p> : ""
            }
            <div ><b>Контакты</b>:
                {
                    Object.keys(profile.contacts)
                        .filter((key) => profile.contacts[key as keyof ContactsType].length > 0)
                        .map((key) => {
                                return <Contact key={key} contactTitle={key} contactValue={profile.contacts[key as keyof ContactsType]}/>;
                            }
                        )
                }
            </div>
        </div>
    );
}

export default ProfileData;