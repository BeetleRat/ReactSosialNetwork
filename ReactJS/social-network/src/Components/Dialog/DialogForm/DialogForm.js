import {Field, reduxForm} from "redux-form";
import ValidatedTextarea from "../../CommonComponents/ValidatedComponents/ValidatedTexarea/ValidatedTextarea";
import {maxLength, requiredField} from "../../../utills/validators/validators";

let maxLength15=maxLength(15);

const DialogForm=(props)=>{
    return(
        <form onSubmit={props.handleSubmit}>
            <div>
                <Field id="newDialogMessage" name="newDialogMessage" placeholder='Ответ' component={ValidatedTextarea}
                       validate={[requiredField, maxLength15]}/>
            </div>
            <div>
                <button id="addNewMessage" name="addNewMessage">Ответить</button>
            </div>
        </form>
        );
}

export default reduxForm({form:'dialog'})(DialogForm)
