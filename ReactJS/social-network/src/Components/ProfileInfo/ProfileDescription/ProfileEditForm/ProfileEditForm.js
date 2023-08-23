import {Field, Form, reduxForm} from "redux-form";
import ErrorMessage from "../../../CommonComponents/ErrorMessage/ErrorMessage";

const ProfileEditForm = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            {
                props.isOwner &&
                <button>Закончить редактирование</button>
            }
            <ErrorMessage error={props.error}/>
            <div>
                <label htmlFor="countryEdit"><b>Страна</b>: </label>
                <Field id="countryEdit" name="country" type="text" component={'input'}/>
            </div>
            <div>
                <label htmlFor="cityEdit"><b>Город</b>: </label>
                <Field id="cityEdit" name="city" type="text" component={'input'}/>
            </div>
            <div>
                <label htmlFor="lookingForAJobEdit"><b>Ищу работу</b>: </label>
                <Field id="lookingForAJobEdit" name="lookingForAJob" type="checkbox" component={'input'}/>
            </div>
            <div name="contacts"><b>Контакты</b>:
                {
                    Object.keys(props.user.contacts)
                        .map(key => {
                                return (
                                    <div id={key}>
                                        <label id={`${key}EditLabel`} htmlFor={`${key}Edit`}><b>{key}</b>: </label>
                                        <Field id={`${key}Edit`} name={`contacts.${key}`} type="text" component={'input'}/>
                                    </div>);
                            }
                        )
                }
            </div>
        </Form>
    );
}

export default reduxForm({form: 'ProfileForm'})(ProfileEditForm);