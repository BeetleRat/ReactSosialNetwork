import {Field, reduxForm} from "redux-form";
import {maxLength, requiredField} from "../../../../utills/validators/validators";
import ValidatedTextarea from "../../../CommonComponents/ValidatedComponents/ValidatedTexarea/ValidatedTextarea";

let maxLength15 = maxLength(15);
const AddPostForm = (props) => {
    return (
        <form onSubmit={props.handleSubmit}>
            <div>
                <Field id="newPostText" name="newPostText" placeholder='Ответ' component={ValidatedTextarea}
                       validate={[requiredField, maxLength15]}/>
            </div>
            <div>
                <button id="addPostButton" name="addPostButton">Опубликовать пост</button>
            </div>
        </form>
    );

}

export default reduxForm({form: 'post'})(AddPostForm)