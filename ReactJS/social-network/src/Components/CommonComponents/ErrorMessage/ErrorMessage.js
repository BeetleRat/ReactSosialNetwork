import styleClass from "./ErrorMessage.module.css"

const ErrorMessage = ({error}) => {
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