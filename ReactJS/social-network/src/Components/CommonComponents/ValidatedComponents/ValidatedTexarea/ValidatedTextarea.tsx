import styleClass from "./ValidatedTextarea.module.css"
import classNames from "classnames"
import React from "react";
import {ValidatorComponentType} from "../../../../Types/Types";

const ValidatedTextarea: React.FC<ValidatorComponentType> = ({input, meta, ...props}) => {
    let isValidationFailed = meta.touched && meta.error;
    return (
        <div className={classNames(styleClass.formControl, {[styleClass.error]: isValidationFailed})}>
            <div>
                <textarea  {...input} {...props}/>
            </div>
            {isValidationFailed && <span>{meta.error}</span>}
        </div>
    );
}

export default ValidatedTextarea;