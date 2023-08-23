import styleClass from "./ValidateInput.module.css"
import classNames from "classnames"

const ValidatedInput = ({input, meta, ...props}) => {
    let isValidationFailed = meta.touched && meta.error;

    return (
        <div className={classNames(styleClass.formControl,{[styleClass.error]:isValidationFailed})}>
            <div>
                <input {...input} {...props}/>
            </div>
            {isValidationFailed && <span>{meta.error}</span>}
        </div>
    );
}

export default ValidatedInput;