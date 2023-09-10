import styleClass from "./ValidatedFile.module.css"
import classNames from "classnames"
import React from "react";
import {ValidatorComponentType} from "../../../../Types/Types";


const ValidatedFile: React.FC<ValidatorComponentType> = ({input: {value: omitValue, ...inputProps}, meta: omitMeta, ...props}) => {
    let isValidationFailed = omitMeta.touched && omitMeta.error;

    return (
        <div className={classNames(styleClass.formControl, {[styleClass.error]: isValidationFailed})}>
            <div>
                <input type='file' {...inputProps} {...props} />
            </div>
            {isValidationFailed && <span>{omitMeta.error}</span>}
        </div>
    );
}

export default ValidatedFile;