import styleClass from "./ValidateInput.module.css"


const ValidatedTextarea = ({input, meta, ...props}) => {
    let isValidationFailed = meta.touched && meta.error;
    return (
        <div className={styleClass.formControl + " " + (isValidationFailed ? styleClass.error : "")}>
            <div>
                <input {...input} {...props}/>
            </div>
            {isValidationFailed && <span>{meta.error}</span>}
        </div>
    );
}

export default ValidatedTextarea;