import styleClass from "./Login.module.css"
import LoginForm from "./LoginForm/LoginForm";
import LogoutForm from "./LogoutForm/LogoutForm";
import React from "react";
import {LoginFormValuesType} from "../../Types/FormsTypes";

type PropsType = {
    authExceptionMessage: string,
    isAuth: boolean,
    username: string | null,
    email: string | null,
    login: (username: string, password: string, rememberMe: boolean) => void,
    logout: () => void
}


const Login: React.FC<PropsType> = (props) => {
    const handleLoginFormSubmit = (formData: LoginFormValuesType) => {
        props.login(formData.username, formData.password, formData.rememberMe);
    }

    return (
        <div>
            {
                props.isAuth
                    ? <LogoutForm username={props.username} email={props.email}
                                  onSubmit={props.logout}/>

                    : <div>
                        <h1>Логин</h1>
                        <LoginForm {...props} onSubmit={handleLoginFormSubmit}/>
                    </div>

            }

        </div>
    );
}

export default Login;