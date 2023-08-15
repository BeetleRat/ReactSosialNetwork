import styleClass from "./Header.module.css"
import {NavLink} from "react-router-dom";

const Header = (props) => {
    return (
        <div className={styleClass.headerStyle}>
            <header>
                <table width="100%">
                    <tbody>
                    {props.tasks.filter(task => task.id === props.currentTaskID).map((task) => {
                        return (
                            <tr key={task.id}>
                                <td>
                                    <img
                                        src='https://sun1-15.userapi.com/impf/c847221/v847221232/76efc/Bmuk7mY7XoQ.jpg?size=50x0&quality=88&crop=0,0,400,400&sign=3fc15abe2df1d94da7e8192701de0520&ava=1'/>
                                </td>
                                <td>
                                    {task.name}
                                </td>
                                <td>
                                    <textarea id={task.id}
                                              value={`${task.currenMinutes}/${Math.floor(task.currenMinutes / (task.totalMinutes / 100))}%`}
                                              readOnly></textarea>
                                </td>
                                <td>
                                    <progress max={task.totalMinutes} value={task.currenMinutes}>
                                        Задача выполнена на {task.currenMinutes / (task.totalMinutes / 100)}%
                                    </progress>
                                </td>
                                <td>
                                    <div className={styleClass.loginBlock}>
                                        <NavLink to="/login">{props.username ? props.email : "Login"} </NavLink>
                                    </div>
                                </td>
                            </tr>
                        )
                    })}
                    </tbody>
                </table>

            </header>
        </div>
    );
}

export default Header;