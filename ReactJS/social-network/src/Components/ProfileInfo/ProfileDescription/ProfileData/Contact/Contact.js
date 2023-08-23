import styleClass from "./Contact.module.css"

const Contact = ({contactTitle,contactValue}) => {
    return (
        <div className={styleClass.contact}>
            <b>{contactTitle}</b>: {contactValue}
        </div>
    );
}

export default Contact;