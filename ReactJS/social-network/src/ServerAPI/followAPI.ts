import {ResponseFromBack, serverRequest} from "./serverInteractionAPI";

export const FollowAPI = {
    followUser(currentUserID: number, followedUserID: number) {
        let followUserDTO = {
            userID: currentUserID,
            followedUserID: followedUserID,
            follow: true
        }

        return serverRequest.post<ResponseFromBack>('follow', {...followUserDTO}).then(response => response.data);
    },
    unfollowUser(currentUserID: number, followedUserID: number) {
        let followUserDTO = {
            userID: currentUserID,
            followedUserID: followedUserID,
            follow: false
        }

        return serverRequest.post<ResponseFromBack>('follow', {...followUserDTO}).then(response => response.data);
    }
};