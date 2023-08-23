import styleClass from "./EasyComponent.module.css"

const EasyComponent = ({someText, someNumber, someFunction}) => {

    const useFunctionFromProps = () => {
        const textFromProps = someText;
        const numberFromProps = someNumber;

        let resultText = "";

        for (let i = 0; i < numberFromProps; i++) {
            resultText = `${resultText}${textFromProps}`;
        }

        someFunction(resultText);
    }

    return (
        <div className={styleClass.textStyle}>
            <p onClick={useFunctionFromProps}>Какой-нибудь текст.</p>
            <p><b>Текст из props</b>: {someText}</p>
            <p><b>Число из props</b>: {someNumber}</p>
        </div>
    );
}

export default EasyComponent;