import styleClass from "./Navigation.module.css"
import {NavLink} from "react-router-dom";

let Navigation = (props) => {
    return (
        <div className={styleClass.navStyle}>
            <div>
                <NavLink to='/profile' className={(isActive) => isActive ? styleClass.active : styleClass.navStyle}>
                    Профиль
                </NavLink>
            </div>
            <div>
                <NavLink to='/users' className={(isActive) => isActive ? styleClass.active : styleClass.navStyle}>
                    Пользователи
                </NavLink>
            </div>
            <div>
                <NavLink to='/dialogs' className={(isActive) => isActive ? styleClass.active : styleClass.navStyle}>
                    Сообщения
                </NavLink>
            </div>
            <div>
                <NavLink to='/news' className={(isActive) => isActive ? styleClass.active : styleClass.navStyle}>
                    Новости
                </NavLink>
            </div>
            <div>
                <NavLink to='/music' className={(isActive) => isActive ? styleClass.active : styleClass.navStyle}>
                    Музыка
                </NavLink>
            </div>
            <div>
                <NavLink to='/settings' className={(isActive) => isActive ? styleClass.active : styleClass.navStyle}>
                    Настройки
                </NavLink>
            </div>
        </div>
    );
}

export default Navigation;