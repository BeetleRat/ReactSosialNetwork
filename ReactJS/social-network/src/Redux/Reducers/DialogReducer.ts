import {InferActionsType, MessageType, NameType} from "../../Types/Types";

const REDUCER_NAME = "DialogReducer/";
const SEND_NEW_MESSAGE = `${REDUCER_NAME}SEND-NEW-MESSAGE` as const;

let initialisationState = {
    nameArray: [
        {id: 1, name: "Никита"},
        {id: 2, name: "Стас"},
        {id: 3, name: "Гена"},
        {id: 4, name: "Дюша Метелкин"}
    ] as Array<NameType>,
    messageArray: [
        {id: 1, text: "Привет"},
        {id: 2, text: "Не пиши мне"},
        {id: 3, text: "Ок"}
    ] as Array<MessageType>
};

export type DialogState = typeof initialisationState;

const DialogReducer = (state = initialisationState, action: ActionTypes): DialogState => {

    /*action это объект, поля которого используются для определения
    какую функцию манипуляций с данными в state вызвать*/
    switch (action.type) {
        case SEND_NEW_MESSAGE:
            if (action.newMessageText !== "") {
                return {
                    ...state,
                    messageArray: [...state.messageArray, {id: state.messageArray.length, text: action.newMessageText}]
                }
            } else {
                return state;
            }
        default:
    }

    // Если изменений не произошло, то возвращаем входные данные, а НЕ их копию
    return state;
};

// Actions
export const actions = {
    sendNewMessage: (newMessageText: string) => {
        return {type: SEND_NEW_MESSAGE, newMessageText};
    }
}

type ActionTypes = InferActionsType<typeof actions>;

export default DialogReducer;