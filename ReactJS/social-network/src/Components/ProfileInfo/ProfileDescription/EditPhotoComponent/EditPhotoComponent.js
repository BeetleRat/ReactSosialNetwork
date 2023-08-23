import {useState} from "react";
import PhotoEditForm from "./PhotoEditForm/PhotoEditForm";


const EditPhotoComponent = (props) => {
    let [editMode, setEditMode] = useState(false);

    const updatePhoto = (formData) => {
        let photo = formData.newPhoto[0];
        props.savePhoto(props.authUserID, photo).then(() => setEditMode(false));
    }

    return (
        <div>
            {
                editMode
                    ? <div>
                        <PhotoEditForm onSubmit={updatePhoto}/>
                        <button onClick={() => setEditMode(false)}>Выйти из редактирования</button>
                    </div>

                    : <button onClick={() => setEditMode(true)}>Редактировать фото</button>
            }
        </div>
    );
}

export default EditPhotoComponent;