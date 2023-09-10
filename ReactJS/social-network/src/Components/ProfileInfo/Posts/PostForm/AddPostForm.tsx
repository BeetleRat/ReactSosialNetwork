import {Form, InjectedFormProps, reduxForm} from "redux-form";
import {maxLength, requiredField} from "../../../../Utills/Validators/validators";
import ValidatedTextarea from "../../../CommonComponents/ValidatedComponents/ValidatedTexarea/ValidatedTextarea";
import React from "react";
import {PostFormValuesType} from "../../../../Types/FormsTypes";
import TypedInputField from "../../../CommonComponents/FieldCreators/FieldCreators";

let maxLength15 = maxLength(15);

type PropsType = {}
const AddPostForm: React.FC<InjectedFormProps<PostFormValuesType, PropsType> & PropsType> = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            {TypedInputField<PostFormValuesType>("newPostText","text",ValidatedTextarea,[requiredField, maxLength15],{placeholder:"Текст"})}
            <div>
                <button id="addPostButton" name="addPostButton">Опубликовать пост</button>
            </div>
        </Form>
    );

}

export default reduxForm<PostFormValuesType, PropsType>({form: 'post'})(AddPostForm)