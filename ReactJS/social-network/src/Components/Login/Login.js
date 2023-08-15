import styleClass from "./Login.module.css"
import LoginForm from "./LoginForm/LoginForm";
import LogoutForm from "./LogoutForm/LogoutForm";

const Login = (props) => {
    const handleLoginFormSubmit = (formData) => {
        props.login(formData.username, formData.password, formData.rememberMe);
    }

    return (
        <div>
            {
                props.isAuth
                    ? <LogoutForm {...props} onSubmit={props.logout}/>

                    : <div>
                        <h1>Логин</h1>
                        <LoginForm {...props} onSubmit={handleLoginFormSubmit}/>
                    </div>

            }

        </div>
    );
}

export default Login;