import styleClass from "./LoginForm.module.css"
import {Field, Form, InjectedFormProps, reduxForm} from "redux-form";
import ValidatedInput from "../../CommonComponents/ValidatedComponents/ValidateInput/ValidateInput";
import {requiredField} from "../../../Utills/Validators/validators";
import ErrorMessage from "../../CommonComponents/ErrorMessage/ErrorMessage";
import {LoginFormValuesType} from "../../../Types/FormsTypes";
import React from "react";
import TypedInputField from "../../CommonComponents/FieldCreators/FieldCreators";

type PropsType = {}
const LoginForm: React.FC<InjectedFormProps<LoginFormValuesType, PropsType> & PropsType> = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            {TypedInputField<LoginFormValuesType>("username",  "text", ValidatedInput, [requiredField],{label:"Имя пользователя: "})}
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

export default reduxForm<LoginFormValuesType, PropsType>({
    form: 'login'
})(LoginForm);