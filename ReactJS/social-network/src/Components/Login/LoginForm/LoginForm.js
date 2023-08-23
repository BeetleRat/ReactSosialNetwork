import styleClass from "./LoginForm.module.css"
import {Field, Form, reduxForm} from "redux-form";
import ValidatedInput from "../../CommonComponents/ValidatedComponents/ValidateInput/ValidateInput";
import {requiredField} from "../../../Utills/Validators/validators";
import ErrorMessage from "../../CommonComponents/ErrorMessage/ErrorMessage";

const LoginForm = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            <div>
                <label htmlFor="username">Имя пользователя: </label>
                <Field id="username" name="username" type="text" component={ValidatedInput}
                       validate={[requiredField]}/>
            </div>
            <div>
                <label htmlFor="password">Пароль: </label>
                <Field id="password" name="password" type="password" component={ValidatedInput}
                       validate={[requiredField]}/>
            </div>
            <div>
                <label htmlFor="rememberMe">Запомнить меня: </label>
                <Field id="rememberMe" name="rememberMe" type="checkbox" component={'input'}/>
            </div>
            <ErrorMessage error={props.error}/>
            <div>
                <button id="loginButton" name="loginButton">Log in</button>
            </div>
        </Form>
    );
}

export default reduxForm({form: 'login'})(LoginForm);