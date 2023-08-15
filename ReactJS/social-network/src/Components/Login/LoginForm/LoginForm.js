import styleClass from "./LoginForm.module.css"
import styleError from "./../../CommonComponents/ValidatedComponents/ValidateInput/ValidateInput.module.css"
import {Field, reduxForm} from "redux-form";
import ValidatedTextarea from "../../CommonComponents/ValidatedComponents/ValidateInput/ValidateInput";
import {requiredField} from "../../../utills/validators/validators";

const LoginForm = (props) => {
    return (
        <form onSubmit={props.handleSubmit}>
            <div>
                <label htmlFor="username">Имя пользователя: </label>
                <Field id="username" name="username" type="text" component={ValidatedTextarea}
                       validate={[requiredField]}/>
            </div>
            <div>
                <label htmlFor="password">Пароль: </label>
                <Field id="password" name="password" type="password" component={ValidatedTextarea}
                       validate={[requiredField]}/>
            </div>
            <div>
                <label htmlFor="rememberMe">Запомнить меня: </label>
                <Field id="rememberMe" name="rememberMe" type="checkbox" component={'input'}/>
            </div>
            {
                // Если есть свойство props.error вывести этот блок
                props.error &&
                <div className={styleError.formSummaryError}>
                    <p>{props.error}</p>
                </div>
            }
            <div>
                <button id="loginButton" name="loginButton">Log in</button>
            </div>
        </form>
    );
}

export default reduxForm({form: 'login'})(LoginForm);