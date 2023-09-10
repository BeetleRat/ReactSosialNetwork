import styleClass from "./OneChatMessage.module.css";
import React from "react";
import AvatarComponent from "../../../../Components/CommonComponents/AvatarComponent/AvatarComponent";

type PropsType = {
    username: string,
    userAvatarImageURL: string | null,
    message: string
}

const OneChatMessage: React.FC<PropsType> = (props) => {
    return (
        <div style={{height: "70px"}}>
            <table>
                <tbody>
                <tr>
                    <td align="center">
                        <AvatarComponent imgURL={props.userAvatarImageURL}/>
                    </td>
                    <td width="80%">
                        {props.message}
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <b>{props.username}</b>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );
}

export default OneChatMessage;