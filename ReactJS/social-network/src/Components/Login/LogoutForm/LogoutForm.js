import {reduxForm} from "redux-form";

const LogoutForm = (props) => {
    return (
        <form onSubmit={props.logout}>
            <h1>Вы вошли в систему</h1>
            <p>Имя пользователя: {props.username}</p>
            <p>Email: {props.email}</p>
            <div>
                <button id="logoutButton" name="logoutButton">Log out</button>
            </div>
        </form>
    );
}

export default reduxForm({form: 'logout'})(LogoutForm);