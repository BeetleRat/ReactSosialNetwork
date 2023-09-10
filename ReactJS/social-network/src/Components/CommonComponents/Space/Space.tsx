import OneSpace from "./OneSpace";
import React from "react";

type PropsType = {
    count: number
}

const Space: React.FC<PropsType> = ({count}) => {

    let indexes =
        Array.from(
            {length: count},
            (_, index) => index + 1
        );

    return (
        <span>
            {
                indexes.map(index => <OneSpace key={index}/>)
            }
        </span>
    );
}

export default Space;