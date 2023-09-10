import React from "react";
import BreadcrumbComponent from "./BreadcrumbComponent";
import {useLocation} from "react-router-dom";

type PropsType = {}
const BreadcrumbContainer: React.FC<PropsType> = (props) => {
    const location = useLocation();
    const storyArray = location.pathname.split('/');

    return (
        <BreadcrumbComponent storyArray={storyArray}/>
    );
}

export default React.memo(BreadcrumbContainer);