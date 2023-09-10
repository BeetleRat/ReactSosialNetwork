import {ProfileType} from "../Types/Types";
import {ResponseFromBack, serverRequest} from "./serverInteractionAPI";

type UsersResponseType = ResponseFromBack & {
    totalUsers: number,
    users: Array<ProfileType>
}
export const UsersAPI = {
    getUsersFromServer(page = 1, pageSize = 5, term = "", friend = null as null | boolean) {
        let friendParam = "";
        debugger;
        if (friend != null) {
            friendParam = `&friend=${friend}`;
        }

        if (term.length === 0) {
            return serverRequest.get<UsersResponseType>(`users?page=${page}&count=${pageSize}&pageSize=${pageSize}${friendParam}`)
                .then(response => response.data);
        } else {
            return serverRequest.get<UsersResponseType>(`users?page=${page}&count=${pageSize}&pageSize=${pageSize}&term=${term}${friendParam}`)
                .then(response => response.data);
        }
    }
};