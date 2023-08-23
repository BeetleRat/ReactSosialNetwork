import styleClass from "./Navigation.module.css"
import {NavLink} from "react-router-dom";

let Navigation = (props) => {
    return (
        <div className={styleClass.navStyle}>
            <div>
                <NavLink to='/profile' activeClassName={styleClass.active}>
                    Профиль
                </NavLink>
            </div>
            <div>
                <NavLink to='/users' activeClassName={styleClass.active}>
                    Пользователи
                </NavLink>
            </div>
            <div>
                <NavLink to='/dialogs' activeClassName={styleClass.active}>
                    Сообщения
                </NavLink>
            </div>
            <div>
                <NavLink to='/news' activeClassName={styleClass.active}>
                    Новости
                </NavLink>
            </div>
            <div>
                <NavLink to='/music' activeClassName={styleClass.active}>
                    Музыка
                </NavLink>
            </div>
            <div>
                <NavLink to='/settings' activeClassName={styleClass.active}>
                    Настройки
                </NavLink>
            </div>
        </div>
    );
}

export default Navigation;