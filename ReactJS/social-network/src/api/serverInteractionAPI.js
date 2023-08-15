import axios from "axios";

const AUTH_HEADER = "Authorization";
const AUTH_HEADER_PREFIX = "Bearer ";
const TOKEN_KEY = "token";

export const CODE = {
    AUTHORIZED: 0,
    NEW_TOKEN_RECEIVED:1,
    NOT_AUTHORIZED:2,
    NOT_VALID:3,
    NOT_FOUND: 4,
    CAPCHA_REQUIRED:10,
    EXCEPTION:99
};


// Создаем объект запросов на сервер
// В данном объекте указываем настройки запросов
let serverRequest = axios.create({
    baseURL: 'http://localhost:8080/api/',
    withCredentials: true,
    headers: {
        "Access-Control-Allow-Headers": AUTH_HEADER
    }
});


export const ProfileAPI = {
    getProfile(userID) {
        return serverRequest.get("profile/" + userID)
            .then(response => response.data);
    },
    getProfileStatus(userID) {
        return serverRequest.get("profile/status/" + userID)
            .then(response => response.data);
    },
    updateLoggedUserStatus(status) {
        return serverRequest.put("profile/", {status})
            .then(response => response.data);
    }
}

export const UsersAPI = {
    getUsersFromServer(page = 1, pageSize = 5) {
        // Обратите внимание, что возвращается не весь response, а только response.data
        return serverRequest.get(`users?page=${page}&count=${pageSize}`)
            .then(response => response.data);

    }
};

export const FollowAPI = {
    followUser(id) {
        return serverRequest.post('follow/' + id).then(response => response.data);
    },
    unfollowUser(id) {
        return serverRequest.delete('follow/' + id).then(response => response.data);
    }
};

export const AuthAPI = {
    authMe() {
        return serverRequest.get('auth/me').then(response => response.data);
    },
    login(username, password, rememberMe) {
        return serverRequest.post('auth/login', {
            username,
            password,
            rememberMe
        }).then(response => {
                return response.data;
            }
        );
    },
    setAccessToken(accessToken) {
        serverRequest.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
    },
    logout() {
        return serverRequest.delete('auth/login').then(response => response.data);
    }
}
