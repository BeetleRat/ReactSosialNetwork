import {Field, Form, InjectedFormProps, reduxForm} from "redux-form";
import {requiredField} from "../../../../../Utills/Validators/validators";
import ValidatedFile from "../../../../CommonComponents/ValidatedComponents/ValidatedFile/ValidatedFile";
import ErrorMessage from "../../../../CommonComponents/ErrorMessage/ErrorMessage";
import React from "react";
import {PhotoEditFromValuesType} from "../../../../../Types/FormsTypes";
import TypedInputField from "../../../../CommonComponents/FieldCreators/FieldCreators";

type PropsType = {
}
const PhotoEditForm: React.FC<InjectedFormProps<PhotoEditFromValuesType, PropsType> & PropsType> = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            {TypedInputField<PhotoEditFromValuesType>("newPhoto","file",ValidatedFile,[requiredField])}
            <div>
                <button>Загрузить выбранное фото</button>
            </div>
            <ErrorMessage error={props.error}/>
        </Form>
    );
}

export default reduxForm<PhotoEditFromValuesType, PropsType>({form: 'PhotoEditForm'})(PhotoEditForm);