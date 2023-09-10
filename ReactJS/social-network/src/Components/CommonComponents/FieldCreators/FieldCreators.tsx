import {Field} from "redux-form";
import {FieldValidatorType} from "../../../Utills/Validators/validators";
import React, {ChangeEvent} from "react";
import {StringKey, ValidatorComponentType} from "../../../Types/Types";
import {FieldTypes, InputFieldTypes} from "../../../Types/FormsTypes";

export type OptionalAttributesType = {
    label?: string,
    placeholder?: string,
    onChange?: (element: ChangeEvent<HTMLInputElement>) => void
}

function TypedInputField<T>(
    name: StringKey<T>,
    type: InputFieldTypes,
    component: React.FC<ValidatorComponentType> | FieldTypes,
    validators: Array<FieldValidatorType>,
    specialAttributes?: OptionalAttributesType
): React.ReactElement {

    const onChangeFunction = (element: ChangeEvent<HTMLInputElement>) => {
        if (specialAttributes?.onChange) {
            specialAttributes?.onChange(element);
        }
    }

    return (
        <span>
            {specialAttributes?.label && <label htmlFor={name}>{specialAttributes?.label}</label>}
            <Field name={name} type={type} component={component}
                   validate={validators}
                   placeholder={specialAttributes?.placeholder}
                   onChange={onChangeFunction}/>
        </span>
    );
}

export default TypedInputField;

