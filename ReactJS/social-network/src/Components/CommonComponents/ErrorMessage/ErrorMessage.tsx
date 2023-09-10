import styleClass from "./ErrorMessage.module.css"
import React from "react";

type PropsType = {
    error: string
}
const ErrorMessage: React.FC<PropsType> = ({error}) => {
    return (
        <span>
            {
                // Если есть свойство props.error вывести этот блок
                error &&
                <div className={styleClass.formSummaryError}>
                    <p>{error}</p>
                </div>
            }
        </span>
    );
}

export default ErrorMessage;