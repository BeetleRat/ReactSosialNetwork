import {Field, Form, InjectedFormProps, reduxForm} from "redux-form";
import ErrorMessage from "../../../CommonComponents/ErrorMessage/ErrorMessage";
import React, {ChangeEvent, useState} from "react";
import {ProfileType} from "../../../../Types/Types";
import {ProfileEditValuesType} from "../../../../Types/FormsTypes";
import TypedInputField from "../../../CommonComponents/FieldCreators/FieldCreators";

type PropsType = {
    isOwner: boolean,
    profile: ProfileType
};

const ProfileEditForm: React.FC<InjectedFormProps<ProfileEditValuesType, PropsType> & PropsType> = (props) => {
    let [lookingForAJob, setLookingForAJob] = useState<boolean | undefined>(props.initialValues.lookingForAJob);

    const updateLookingForAJob = (element: ChangeEvent<HTMLInputElement>) => {
        let lookingForAJobNewState = element.currentTarget.checked;
        setLookingForAJob(lookingForAJobNewState);
    }

    return (
        <Form onSubmit={props.handleSubmit}>
            {
                props.isOwner &&
                <button>Закончить редактирование</button>
            }
            <ErrorMessage error={props.error}/>
            <div>
                <label htmlFor="country"><b>Страна</b>: </label>
                {TypedInputField<ProfileEditValuesType>("country", "text", 'input', [])}
            </div>
            <div>
                <label htmlFor="city"><b>Город</b>: </label>
                {TypedInputField<ProfileEditValuesType>("city", "text", 'input', [])}
            </div>
            <div>
                <label htmlFor="lookingForAJobEdit"><b>Ищу работу</b>: </label>
                <Field id="lookingForAJobEdit" name="lookingForAJob" type="checkbox" component={'input'}
                       onChange={updateLookingForAJob}/>
                {
                    lookingForAJob &&
                    <span>
                    <Field id="searchJobDescriptionEdit" name="searchJobDescription" type="text" component={'input'}/>
                </span>
                }
            </div>

            <div><b>Контакты</b>:
                {
                    Object.keys(props.profile.contacts)
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

export default reduxForm<ProfileEditValuesType, PropsType>({form: 'ProfileForm'})(ProfileEditForm);