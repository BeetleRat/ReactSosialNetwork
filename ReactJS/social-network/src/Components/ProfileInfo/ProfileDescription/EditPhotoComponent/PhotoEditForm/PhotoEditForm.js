import {Field, Form, reduxForm} from "redux-form";
import {requiredField} from "../../../../../Utills/Validators/validators";
import ValidatedFile from "../../../../CommonComponents/ValidatedComponents/ValidatedFile/ValidatedFile";
import ErrorMessage from "../../../../CommonComponents/ErrorMessage/ErrorMessage";

const PhotoEditForm = (props) => {
    return (
        <Form onSubmit={props.handleSubmit}>
            <div>
                <Field name="newPhoto" component={ValidatedFile} validate={requiredField}/>
            </div>
            <div>
                <button>Загрузить выбранное фото</button>
            </div>
            <ErrorMessage error={props.error}/>
        </Form>
    );
}

export default reduxForm({form: 'PhotoEditForm'})(PhotoEditForm);