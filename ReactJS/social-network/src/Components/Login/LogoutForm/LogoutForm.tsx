import {Form, InjectedFormProps, reduxForm} from "redux-form";
import React from "react";


type PropsType = {
    username: string | null,
    email: string | null
};

const LogoutForm: React.FC<InjectedFormProps<{}, PropsType> & PropsType> = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            <h1>Вы вошли в систему</h1>
            <p>Имя пользователя: {props.username}</p>
            <p>Email: {props.email}</p>
            <div>
                <button id="logoutButton" name="logoutButton">Log out</button>
            </div>
        </Form>
    );
}

export default reduxForm<{}, PropsType>({form: 'logout'})(LogoutForm);