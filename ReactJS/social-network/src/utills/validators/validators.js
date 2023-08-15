export const requiredField = (value) => {
    if (value) {
        return undefined;
    }

    return "Обязательное поле";
}

export const maxLength = (maxLength) => {
    return (value) => {
        if (value && value.length > maxLength) {
            return `Максимальная длинна ${maxLength} символов`;;
        }

        return undefined;
    }
}