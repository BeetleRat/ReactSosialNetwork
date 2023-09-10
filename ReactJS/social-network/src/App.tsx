import './App.css';
import HeaderContainer from "./Components/Header/HeaderContainer";
import {withSuspense} from "./HOC/WithSuspense";
import {HashRouter, Route, Routes} from "react-router-dom";
import {Provider} from "react-redux";
import store from "./Redux/store";
import NotFoundPage from "./Components/CommonComponents/NotFoundPage/NotFoundPage";
import React from 'react'
import {Layout} from 'antd';
import BreadcrumbContainer from "./Components/CommonComponents/BreadcrumbComponent/BreadcrumbContainer";
import MenuNavigation from "./Components/Navigation/MenuNavigation";


const MyProfile = React.lazy(() => import("./Components/ProfileInfo/ProfileInfoContainer"));
const Dialogs = React.lazy(() => import("./Components/Dialog/DialogContainer"));
const Users = React.lazy(() => import("./Components/Users/UsersContainer"));
const LoginContainer = React.lazy(() => import("./Components/Login/LoginContainer"));
const ChatPage = React.lazy(() => import("./Pages/Chat/ChatPage"));

const {Content} = Layout;

const AppComponent: React.FC = () => {

    return (
        <Layout>
            <HeaderContainer/>
            <Layout>
                <MenuNavigation/>
                <Layout style={{padding: '0 24px 24px'}}>
                    <BreadcrumbContainer/>
                    <Content
                        style={{
                            padding: 24,
                            margin: 0,
                            minHeight: 280,
                            background: "white",
                        }}
                    >
                        <Routes>
                            <Route path="/profile"
                                   Component={withSuspense(MyProfile)}/>
                            <Route path="/dialogs"
                                   Component={withSuspense(Dialogs)}/>
                            <Route path="/users"
                                   Component={withSuspense(Users)}/>
                            <Route path="/login"
                                   Component={withSuspense(LoginContainer)}/>
                            <Route path="/chat"
                                   Component={withSuspense(ChatPage)}/>
                            <Route path="/*"
                                   Component={NotFoundPage}/>
                        </Routes>
                    </Content>
                </Layout>
            </Layout>
        </Layout>
    );
};

const App: React.FC = () => {
    return (
        <React.StrictMode>
            <HashRouter>
                <Provider store={store}>
                    <AppComponent/>
                </Provider>
            </HashRouter>
        </React.StrictMode>
    );
}

export default App;


