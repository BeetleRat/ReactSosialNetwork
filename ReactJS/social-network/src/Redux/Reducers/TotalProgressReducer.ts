import {InferActionsType, TaskType} from "../../Types/Types";

const REDUCER_NAME = "TotalProgressReducer/";
const ADD_NEW_TASK = `${REDUCER_NAME}ADD-NEW-TASK` as const;
const CHANGE_CURRENT_MINUTES = `${REDUCER_NAME}CHANGE-CURRENT-MINUTES` as const;


let initialisationState = {
    tasks: [
        {
            id: 0,
            name: "React путь самурая 1",
            totalMinutes: 3034,
            currenMinutes: 3034
        },
        {
            id: 1,
            name: "React путь самурая 2",
            totalMinutes: 1310,
            currenMinutes: 927
        },
        {
            id: 3,
            name: "HTML + CSS",
            totalMinutes: 986,
            currenMinutes: 0
        },
        {
            id: 4,
            name: "Современный JavaScript",
            totalMinutes: 3960,
            currenMinutes: 0
        }
    ] as Array<TaskType>,
    currentTaskID: 1
}

export type TotalProgressType = typeof initialisationState;

const TotalProgressReducer = (state = initialisationState, action: ActionTypes): TotalProgressType => {
    switch (action.type) {
        case ADD_NEW_TASK:
            return state;
        case CHANGE_CURRENT_MINUTES:
            return {
                ...state,
                tasks: state.tasks.map(task => {
                    if (task.id == action.id) {
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

// actions
export const actions = {
    ChangeCurrentMinutes: (id: number, currenMinutes: number) => {
        return {type: CHANGE_CURRENT_MINUTES, id, currenMinutes};
    },
    AddNewTask: () => {
        return {type: ADD_NEW_TASK};
    }
}

type ActionTypes = InferActionsType<typeof actions>;

export default TotalProgressReducer;