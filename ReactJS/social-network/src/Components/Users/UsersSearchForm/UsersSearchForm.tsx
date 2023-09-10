import styleClass from "./UserSearchForm.module.css"
import React from "react";
import {Formik, Form, Field} from "formik";

import {FilterFormValues} from "../../../Redux/Reducers/UserReducer";
import {BooleanStringType} from "../../../Types/Types";

type PropsType = {
    filter: FilterFormValues,
    onFilterChanged: (filter: FilterFormValues) => void
}

type FormType = {
    term: string,
    friend: BooleanStringType
}

const usersSearchFormValidate = (value: any) => {
    const errors = {};
    return errors;
}

const UsersSearchForm: React.FC<PropsType> = (props) => {
    const submit = (values: FormType, {setSubmitting}: {
        setSubmitting: (isSubmitting: boolean) => void
    }) => {
        const FormValue = {
            term: values.term,
            friend: (values.friend == null || values.friend === "null") ? null : values.friend === "true"
        }

        props.onFilterChanged(FormValue);

        setSubmitting(false);
    }

    return (
        <Formik
            enableReinitialize={true}
            initialValues={{term: props.filter.term, friend: String(props.filter.friend) as BooleanStringType}}
            validate={usersSearchFormValidate}
            onSubmit={submit}
        >
            {
                ({isSubmitting}) => (
                    <Form>
                        <Field type="text" name="term"/>
                        <Field name="friend" as="select" placeholder="Friends only">
                            <option value="null">Все</option>
                            <option value="true">Только друзья</option>
                            <option value="false">Кроме друзей</option>
                        </Field>
                        <button type="submit" disabled={isSubmitting}>
                            Find
                        </button>

                    </Form>
                )
            }
        </Formik>
    );
}

export default UsersSearchForm;