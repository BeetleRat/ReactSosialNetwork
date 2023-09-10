import {Form, InjectedFormProps, reduxForm} from "redux-form";
import ValidatedTextarea from "../../CommonComponents/ValidatedComponents/ValidatedTexarea/ValidatedTextarea";
import {maxLength, requiredField} from "../../../Utills/Validators/validators";
import TypedInputField from "../../CommonComponents/FieldCreators/FieldCreators";
import {DialogFormValuesType} from "../../../Types/FormsTypes";
import React from "react";

let maxLength15 = maxLength(15);

type PropsType = {}

const DialogForm: React.FC<InjectedFormProps<DialogFormValuesType, PropsType> & PropsType> = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            {TypedInputField<DialogFormValuesType>("newDialogMessage", "text", ValidatedTextarea, [requiredField, maxLength15],{placeholder:"Ответ"})}
            <div>
                <button id="addNewMessage" name="addNewMessage">Ответить</button>
            </div>
        </Form>
    );
}

export default reduxForm<DialogFormValuesType, PropsType>({form: 'dialog'})(DialogForm)
