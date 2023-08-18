const REDUCER_NAME = "TotalProgressReducer/";
const ADD_NEW_TASK = REDUCER_NAME + "ADD-NEW-TASK";
const CHANGE_CURRENT_MINUTES = REDUCER_NAME + "CHANGE-CURRENT-MINUTES";

let initialisationState = {
    tasks: [
        {
            id: 0,
            name: "React путь самурая 1",
            totalMinutes: 3034,
            currenMinutes: 2606
        },
        {
            id: 1,
            name: "React путь самурая 2",
            totalMinutes: 1310,
            currenMinutes: 0
        },
        {
            id: 3,
            name: "HTML + CSS",
            totalMinutes: 986,
            currenMinutes: 0
        }
    ],
    currentTaskID: 0
}

const TotalProgressReducer = (state = initialisationState, action) => {
    switch (action.type) {
        case ADD_NEW_TASK:
            return state;
        case CHANGE_CURRENT_MINUTES:
            return {
                ...state,
                tasks: state.tasks.map(task => {
                    if (task.id === action.id) {
                        return {...task, currenMinutes: action.currenMinutes}
                    } else {
                        return task;
                    }
                })

            }
        default:
    }
    return state;
}

export const ChangeCurrentMinutes = (id, currenMinutes) => {
    return {type: CHANGE_CURRENT_MINUTES, id, currenMinutes};
}
export const AddNewTask = () => {
    return {type: ADD_NEW_TASK};
}
export default TotalProgressReducer;