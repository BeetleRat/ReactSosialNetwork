import {Layout, Menu, MenuProps} from "antd";
import React from "react";
import {NavLink} from "react-router-dom";

const {Sider} = Layout;

type MenuItem = Required<MenuProps>['items'][number];
type PropsType = {}
const MenuNavigation: React.FC<PropsType> = (props) => {

    const getItem = (
        label: React.ReactNode,
        key: React.Key,
        icon?: React.ReactNode,
        children?: MenuItem[],
    ) => {
        return {
            key,
            icon,
            children,
            label,
        } as MenuItem;
    }

    const items: MenuItem[] = [
        getItem('Профиль', '1', <NavLink to='/profile'/>),
        getItem('Пользователи', '2', <NavLink to='/users'/>),
        getItem('Сообщения', '3', <NavLink to='/dialogs'/>, [
            getItem('Chat', 4, <NavLink to='/chat'/>)
        ]),
        getItem('Новости', '5', <NavLink to='/news'/>),
        getItem('Музыка', '6', <NavLink to='/music'/>),
        getItem('Настройки', '7', <NavLink to='/settings'/>)
    ];

    return (
        <Sider width={200} style={{background: "white"}}>
            <Menu
                mode="inline"
                defaultSelectedKeys={['0']}
                defaultOpenKeys={['sub1']}
                style={{height: '100%', borderRight: 0}}
                items={items}
            />
        </Sider>
    )
}

export default React.memo(MenuNavigation);