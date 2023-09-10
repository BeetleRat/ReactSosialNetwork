import {Breadcrumb} from "antd";
import React from "react";

type PropsType = {
    storyArray: string[]
}
const BreadcrumbComponent: React.FC<PropsType> = (props) => {
    return (
        <Breadcrumb style={{margin: '16px 0'}}>
            {props.storyArray.map(story=><Breadcrumb.Item key={story}>{story}</Breadcrumb.Item>)}
        </Breadcrumb>
    );
}
export default BreadcrumbComponent;