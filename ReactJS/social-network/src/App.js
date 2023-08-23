import './App.css';
import Navigation from "./Components/Navigation/Navigation";
import Header from "./Components/Header/HeaderContainer";
import {withSuspense} from "./HOC/WithSuspense";
import {HashRouter, Route, Switch} from "react-router-dom";
import {Provider} from "react-redux";
import store from "./Redux/store";
import React from "react";
import NotFoundPage from "./Components/CommonComponents/NotFoundPage/NotFoundPage";

const MyProfile = React.lazy(() => import("./Components/ProfileInfo/ProfileInfoContainer"));
const Dialogs = React.lazy(() => import("./Components/Dialog/DialogContainer"));
const Users = React.lazy(() => import("./Components/Users/UsersContainer"));
const Login = React.lazy(() => import("./Components/Login/LoginContainer"));

const AppComponent = (props) => {
    return (
        <div className='my-grid-style'>
            <Header/>
            <Navigation/>
            <div className='content-style'>
                <Switch>
                    <Route exact path="/profile/:userID?"
                           render={withSuspense(MyProfile)}/>
                    <Route exact path="/dialogs"
                           render={withSuspense(Dialogs)}/>
                    <Route exact path="/users"
                           render={withSuspense(Users)}/>
                    <Route exact path="/login"
                           render={withSuspense(Login)}/>
                    <Route path="/*"
                           render={() => <NotFoundPage/>}/>
                </Switch>
            </div>
        </div>
    );
}

const App = (props) => {
    return (
        <HashRouter>
            <Provider store={store}>
                <AppComponent/>
            </Provider>
        </HashRouter>
    );
}

export default App;
