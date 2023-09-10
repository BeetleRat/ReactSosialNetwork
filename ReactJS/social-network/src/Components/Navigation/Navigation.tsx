import styleClass from "./Navigation.module.css"
import {NavLink} from "react-router-dom";
import React from "react";

let Navigation: React.FC = () => {
    return (
        <div className={styleClass.navStyle}>
            <div>
                <NavLink to='/profile' className={({isActive}) => isActive ? styleClass.active : ""}>
                    Профиль
                </NavLink>
            </div>
            <div>
                <NavLink to='/users' className={({isActive}) => isActive ? styleClass.active : ""}>
                    Пользователи
                </NavLink>
            </div>
            <div>
                <NavLink to='/dialogs' className={({isActive}) => isActive ? styleClass.active : ""}>
                    Сообщения
                </NavLink>
            </div>
            <div>
                <NavLink to='/news' className={({isActive}) => isActive ? styleClass.active : ""}>
                    Новости
                </NavLink>
            </div>
            <div>
                <NavLink to='/music' className={({isActive}) => isActive ? styleClass.active : ""}>
                    Музыка
                </NavLink>
            </div>
            <div>
                <NavLink to='/settings' className={({isActive}) => isActive ? styleClass.active : ""}>
                    Настройки
                </NavLink>
            </div>
        </div>
    );
}

export default Navigation;