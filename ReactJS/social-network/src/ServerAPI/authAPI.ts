import {ResponseFromBack, serverRequest} from "./serverInteractionAPI";

type AuthUserResponseType = ResponseFromBack & {
    accessToken: string,
    refreshToken: string,
    userInfo: {
        email: string,
        userID: number,
        username: string
    }
}
export const AuthAPI = {
    login(username: string, password: string, rememberMe: boolean) {
        return serverRequest.post<AuthUserResponseType>('auth/login', {
            username,
            password,
            rememberMe
        }).then(response => {
                return response.data;
            }
        );
    },
    setAccessToken(accessToken: string) {
        serverRequest.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
    },
    logout() {
        return serverRequest.delete<ResponseFromBack>('auth/login').then(response => response.data);
    }
}