import './App.css';
import {Route} from "react-router-dom";
import Navigation from "./Components/Navigation/Navigation";
import Header from "./Components/Header/HeaderContainer";
import Dialogs from "./Components/Dialog/DialogContainer";
import Users from "./Components/Users/UsersContainer";
import TotalProgress from "./Components/TotalProgress/TotalProgressContainer";
import MyProfile from "./Components/MyProfile/ProfileInfoContainer";
import Login from "./Components/Login/LoginContainer";


const App = (props) => {
    return (
        <div className='my-grid-style'>
            <Header/>
            <Navigation/>
            <div className='content-style'>
                {/*Switch*/}
                    {/*Маршрутизатор по адресу http://localhost:3000/profile
			отрисовать компоненту ProfileInfo*/}
                    <Route path="/profile/:userID?" render={()=><MyProfile/>}/>
                    <Route path="/dialogs" render={()=><Dialogs/>}/>
                    <Route path="/users" render={()=><Users/>}/>
                    <Route path="/progress" render={()=><TotalProgress/>}/>
                <Route path="/login" render={()=><Login/>}/>
            </div>
        </div>
    );
}
export default App;
