import React from "react";
import {UserOutlined} from "@ant-design/icons";
import {Avatar} from "antd";


type PropsType = {
    imgURL: string | null
}

const AvatarComponent: React.FC<PropsType> = ({imgURL}) => {
    return (
        <Avatar style={{backgroundColor: '#87d068'}}
                icon={
                    imgURL
                        ? <img src={imgURL}/>
                        : <UserOutlined/>
                }
        />
    );
}

export default AvatarComponent;