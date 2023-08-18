import './App.css';
import Navigation from "./Components/Navigation/Navigation";
import Header from "./Components/Header/HeaderContainer";
import Login from "./Components/Login/LoginContainer";
import {withSuspense} from "./HOC/WithSuspense";
import {BrowserRouter, Route} from "react-router-dom";
import {Provider} from "react-redux";
import store from "./Redux/store";
import React from "react";

const MyProfile = React.lazy(() => import("./Components/MyProfile/ProfileInfoContainer"));
const Dialogs = React.lazy(() => import("./Components/Dialog/DialogContainer"));
const Users = React.lazy(() => import("./Components/Users/UsersContainer"));

const AppComponent = (props) => {
    return (
        <div className='my-grid-style'>
            <Header/>
            <Navigation/>
            <div className='content-style'>
                <Route path="/profile/:userID?"
                       render={withSuspense(MyProfile)}/>
                <Route path="/dialogs"
                       render={withSuspense(Dialogs)}/>
                <Route path="/users"
                       render={withSuspense(Users)}/>
                <Route path="/login"
                       render={() => <Login/>}/>
            </div>
        </div>
    );
}

const App = (props) => {
    return (
        <BrowserRouter>
            <Provider store={store}>
                <AppComponent/>
            </Provider>
        </BrowserRouter>
    );
}
export default App;
