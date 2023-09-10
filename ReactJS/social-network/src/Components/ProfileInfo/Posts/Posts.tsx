import styleClass from "./Posts.module.css"
import OnePost from "./OnePost/OnePost";
import AddPostForm from "./PostForm/AddPostForm";
import React from "react";
import {PostType} from "../../../Types/Types";

type PropsType = {
    posts: Array<PostType>,
    addNewPost: (newPost: string) => void
}

const Posts: React.FC<PropsType> = ({addNewPost, posts}) => {
    const AddNewPost = (formData: any) => {
        return addNewPost(formData.newPostText);
    };

    return (
        <div>
            <div>
                <h3>Посты</h3>
                <AddPostForm onSubmit={AddNewPost}/>
            </div>
            <div>
                {posts.map(post => <OnePost key={post.id} text={post.text} likes={post.likes}/>)}
            </div>
        </div>
    );
}

export default Posts;