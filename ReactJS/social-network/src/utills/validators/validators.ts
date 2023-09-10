export type FieldValidatorType = (value: string) => string | undefined;

export const requiredField: FieldValidatorType = (value) => {
    if (value) {
        return undefined;
    }

    return "Обязательное поле";
}

export const maxLength = (maxLength: number): FieldValidatorType => {
    return (value) => {
        if (value && value.length > maxLength) {
            return `Максимальная длинна ${maxLength} символов`;
        }

        return undefined;
    }
}