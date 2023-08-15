// Подключение класса стиля
import styleClass from './Dialog.module.css'
// Подключение компонентов
import UserName from "./UserName/UserName";
import Message from "./Message/Message";
import DialogForm from "./DialogForm/DialogForm";

// Перезнтационный компонент(тупой).
// Такой компанент не взаимодействует с данными из store
// - он только отображает пришедшие в него данные и вызывает пришедшие в него callback-и
const Dialog=(props)=>{

    const AddNewMessage=(formData)=>{
        return props.sendNewMessage(formData.newDialogMessage);
    };

    return(
        <div className={styleClass.dialogsStyle}>
            <div className={styleClass.userNameStyle}>
                {/*Полная запись метода map создающего массив компонентов UserName*/}
                {props.names.map((name)=>{
                    return <UserName key={name.id} id={name.id} name={name.name}/>;
                })}
            </div>
            <div className={styleClass.messagesStyle}>
                <div>
                    {/*Сокращенная запись метода map создающего массив компонентов Message*/}
                    {props.messageArray.map(message=><Message key={message.id} text={message.text}/>)}
                </div>
                <div>
                   <DialogForm onSubmit={AddNewMessage}/>
                </div>
            </div>

        </div>
    );
}
export default Dialog;