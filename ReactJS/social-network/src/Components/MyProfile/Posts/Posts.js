import styleClass from "./Posts.module.css"
// Подключение компонентов
import OnePost from "./OnePost/OnePost";
import AddPostForm from "./PostForm/AddPostForm";

const Posts=(props)=>{
    const AddNewPost=(formData)=>{
        debugger;
        return props.addNewPost(formData.newPostText);
    };

    return(
        <div>
            <div>
                <h3>Посты</h3>
                <AddPostForm onSubmit={AddNewPost} />
            </div>
            <div>
                {props.posts.map(post=><OnePost key={post.id} text={post.text} likes={post.likes}/>)}
            </div>
        </div>
    );
}
export default Posts;