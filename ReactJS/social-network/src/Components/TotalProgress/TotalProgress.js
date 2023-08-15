import {ChangeCurrentMinutes} from "../../Redux/Redusers/TotalProgressReducer";

const TotalProgress = (props) => {
    const changeCurrentMinutes = (event) => {
        // Получение значения из элемента
        // который вызвал данную функцию
        let text = event.target.value;
        let id=event.target.id;
        // Метод изменения текста в textArea
        return props.ChangeCurrentMinutes(id,text);
    }

    return (
        <div>
            <table>
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Всего минут просмотренно</th>
                    <th>Прогресс</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {props.tasks.map((task) => {
                    return (
                        <tr key={task.id}>
                            <td>
                                {task.name}
                            </td>
                            <td>
                                <textarea id={task.id} onChange={changeCurrentMinutes} value={task.currenMinutes}></textarea>
                            </td>
                            <td>
                                <progress max={task.totalMinutes} value={task.currenMinutes}>
                                    Задача выполнена на {task.currenMinutes / (task.totalMinutes / 100)}%
                                </progress>
                            </td>
                            <td></td>
                        </tr>
                    )
                })}
                </tbody>
            </table>

        </div>
    );
}

export default TotalProgress;