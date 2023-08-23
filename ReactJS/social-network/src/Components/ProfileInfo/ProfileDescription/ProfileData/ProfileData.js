import Contact from "./Contact/Contact";

const ProfileData = ({isOwner, user, editModeON}) => {
    return (
        <div>
            {
                isOwner &&
                <button onClick={editModeON}>Редактировать</button>
            }
            <p name="country"><b>Страна</b>: {user.country}</p>
            <p name="city"><b>Город</b>: {user.city}</p>
            {
                user.lookingForAJob ? <p><b>Ищу работу</b>: {user.searchJobDescription}</p> : ""
            }
            <div name="contacts"><b>Контакты</b>:
                {
                    Object.keys(user.contacts)
                        .filter(key => user.contacts[key].length > 0)
                        .map(key => {
                                return <Contact key={key} contactTitle={key} contactValue={user.contacts[key]}/>;
                            }
                        )
                }
            </div>
        </div>
    );
}

export default ProfileData;