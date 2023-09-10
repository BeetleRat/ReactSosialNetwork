import styleClass from "./ValidateInput.module.css"
import classNames from "classnames"
import React from "react";
import {ValidatorComponentType} from "../../../../Types/Types";

const ValidatedInput: React.FC<ValidatorComponentType> = ({input, meta, ...props}) => {
    let isValidationFailed = meta.touched && meta.error;

    return (
        <div className={classNames(styleClass.formControl, {[styleClass.error]: isValidationFailed})}>
            <div>
                <input {...input} {...props}/>
            </div>
            {isValidationFailed && <span>{meta.error}</span>}
        </div>
    );
}

export default ValidatedInput;