import axios from "axios";

export const SERVER_ADDRESS = "localhost:8080" as const;

export enum CODE {
    AUTHORIZED_AND_COMPLETED = 0,
    NEW_TOKEN_RECEIVED = 1,
    NOT_AUTHORIZED = 2,
    NOT_VALID = 3,
    NOT_FOUND = 4,
    CAPCHA_REQUIRED = 10,
    EXCEPTION = 99
};

const AUTH_HEADER = "Authorization";


// Создаем объект запросов на сервер
// В данном объекте указываем настройки запросов
export let serverRequest = axios.create({
    baseURL: `http://${SERVER_ADDRESS}/api/`,
    withCredentials: true,
    headers: {
        "Access-Control-Allow-Headers": AUTH_HEADER
    }
});

export type ResponseFromBack = {
    message: string,
    resultCode: CODE
}

//export const webSocketChannel = new WebSocket(`wss://${SERVER_ADDRESS}/handlers/ChatHandler.ashx`);

