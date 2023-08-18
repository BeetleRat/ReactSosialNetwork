import OneSpace from "./OneSpace";

const Space = (props) => {

    let indexes =
        Array.from(
            {length: props.count},
            (_, index) => index + 1
        );

    return (
        <span>
            {
                indexes.map(index => <OneSpace key={index}/>)
            }
        </span>
    );
}

export default Space;