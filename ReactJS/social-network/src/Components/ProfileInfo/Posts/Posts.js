import styleClass from "./Posts.module.css"
import OnePost from "./OnePost/OnePost";
import AddPostForm from "./PostForm/AddPostForm";

const Posts = ({addNewPost, posts}) => {
    const AddNewPost = (formData) => {
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