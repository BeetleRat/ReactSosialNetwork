import {ProfileType} from "../Types/Types";
import {ResponseFromBack, serverRequest} from "./serverInteractionAPI";

type ProfileResponseType = ResponseFromBack & {
    user: ProfileType
}
type StatusResponseType = ResponseFromBack & {
    status: string
}
type ImageURLResponseType = ResponseFromBack & {
    imgURL: string
}
export const ProfileAPI = {
    getProfile(userID: number) {
        return serverRequest.get<ProfileResponseType>("profile/" + userID)
            .then(response => response.data);
    },
    getProfileStatus(userID: number) {
        return serverRequest.get<StatusResponseType>("profile/status/" + userID)
            .then(response => response.data);
    },
    updateUser(user: ProfileType) {
        return serverRequest.put<ProfileResponseType>("profile/", user)
            .then(response => response.data);
    },
    updateUserStatus(userID: number, status: string) {
        return serverRequest.put<ProfileResponseType>(`profile/status/${userID}`, {status})
            .then(response => response.data);
    },
    savePhoto(userID: number, photo: File) {
        const formData = new FormData();
        formData.append(`id`, userID.toString());
        formData.append(`file`, photo);

        return serverRequest.put<ImageURLResponseType>("profile/photo/",
            formData,
            {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            }
        ).then(response => response.data);
    }
}