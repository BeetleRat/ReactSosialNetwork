import styleClass from "./Header.module.css"
import {NavLink, useNavigate} from "react-router-dom";
import React from "react";
import {TaskType} from "../../Types/Types";
import {Avatar, Button, theme} from "antd";
import {Header} from "antd/es/layout/layout";
import {UserOutlined} from "@ant-design/icons";
import Space from "../CommonComponents/Space/Space";
import AvatarComponent from "../CommonComponents/AvatarComponent/AvatarComponent";

type PropsType = {
    tasks: Array<TaskType>,
    currentTaskID: number,
    username: string | null,
    email: string | null,
    imgURL: string | null,
    redirectToLogin: () => void,
    logout: () => void
}

const HeaderComponent: React.FC<PropsType> = (props) => {
    return (

        <Header style={{display: 'flex', alignItems: 'center'}}>
            <div className="demo-logo"/>

            <table width="100%" className={styleClass.headerText}>
                <tbody>
                {props.tasks.filter(task => task.id === props.currentTaskID).map((task) => {
                    return (
                        <tr key={task.id}>
                            <td>
                                <Avatar style={{backgroundColor: '#87d068', width: '60px', height: '58px'}}
                                        icon={
                                            <img
                                                src='https://sun1-15.userapi.com/impf/c847221/v847221232/76efc/Bmuk7mY7XoQ.jpg?size=50x0&quality=88&crop=0,0,400,400&sign=3fc15abe2df1d94da7e8192701de0520&ava=1'/>
                                        }
                                />
                            </td>
                            <td>
                                {task.name}
                            </td>
                            <td>
                                {`${task.currenMinutes}/${Math.floor(task.currenMinutes / (task.totalMinutes / 100))}%`}
                            </td>
                            <td>
                                <progress max={task.totalMinutes} value={task.currenMinutes}>
                                    Задача выполнена на {task.currenMinutes / (task.totalMinutes / 100)}%
                                </progress>
                            </td>
                            <td>
                                <div>

                                    <NavLink to="/login">
                                        {/*{props.username ? props.email : "Login"}*/}
                                        {props.username
                                            ? <span>
                                                <AvatarComponent imgURL={props.imgURL}/>
                                                <Space count={3}/>
                                            <Button onClick={props.logout}>Logout</Button>
                                        </span>
                                            : <Button onClick={props.redirectToLogin}>Login</Button>}

                                    </NavLink>
                                </div>
                            </td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
        </Header>
    );
}

export default HeaderComponent;