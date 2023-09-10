import React, {useState} from "react";
import PhotoEditForm from "./PhotoEditForm/PhotoEditForm";
import {PhotoEditFromValuesType} from "../../../../Types/FormsTypes";

type PropsType = {
    savePhoto: (authUserID: number, photo: File) => void,
    authUserID: number
}
const EditPhotoComponent: React.FC<PropsType> = (props) => {
    let [editMode, setEditMode] = useState<boolean>(false);

    const updatePhoto = (formData: PhotoEditFromValuesType) => {
        let photo = formData.newPhoto[0];
        props.savePhoto(props.authUserID, photo);
        setEditMode(false);
            //.then(() => setEditMode(false));
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