import OneSpace from "./OneSpace";

const Space = ({count}) => {

    let indexes =
        Array.from(
            {length: count},
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