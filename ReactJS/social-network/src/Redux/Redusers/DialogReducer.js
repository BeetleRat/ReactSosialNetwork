const REDUCER_NAME = "DialogReducer/";
const SEND_NEW_MESSAGE = REDUCER_NAME + 'SEND-NEW-MESSAGE';

let initialisationState = {
    nameArray: [
        {id: '1', name: "Никита"},
        {id: '2', name: "Стас"},
        {id: '3', name: "Гена"},
        {id: '4', name: "Дюша Метелкин"}
    ],
    messageArray: [
        {id: '1', text: "Привет"},
        {id: '2', text: "Не пиши мне"},
        {id: '3', text: "Ок"}
    ]
};


const DialogReducer = (state = initialisationState, action) => {
    // По правилу чистых функций, мы не имеем права изменять входные параметры,
    // а потому создаем копию объекта state
    let newState = {...state};
    // При создании копии внимательно следите за скобочками
    // обычные объекты spread-ятся в {}, объекты массивов spread-ятся в []
    newState.nameArray = [...state.nameArray];
    newState.messageArray = [...state.messageArray];

    /*Методы манипуляций с данными в копии state*/
    const sendNewMessage = () => {
        // Сохранение текста нового сообщения в массив данных
        const newMessage = {id: state.messageArray.length, text: state.newMessageText};
        newState.messageArray.push(newMessage);
        // Обнуляем текст нового сообщения
        newState.newMessageText = '';
    };


    /*action это объект, поля которого используются для определения
    какую функцию манипуляций с данными в state вызвать*/
    switch (action.type) {
        case SEND_NEW_MESSAGE:
            if (action.newMessageText !== "") {
                return {
                    ...state,
                    messageArray: [...state.messageArray, {id: state.messageArray.length, text: action.newMessageText}]
                }
            }
            break;
        default:
    }
    // Если изменений не произошло, то возвращаем входные данные, а НЕ их копию
    return state;
};

/*Методы создания объектов, поля которого используются для определения
какой метод манипуляций с данными в state вызвать*/
export const sendNewMessage = (newMessageText) => {
    return {type: SEND_NEW_MESSAGE, newMessageText};
};

export default DialogReducer;