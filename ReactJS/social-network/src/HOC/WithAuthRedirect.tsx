import React from "react";
import {connect} from "react-redux";
import {GlobalStateType} from "../Redux/store";
import {getIsAuth} from "../Redux/Selectors/AuthSelectors";
import { Navigate } from "react-router-dom";



export function withAuthRedirect<T extends object>(WrappedComponent: React.ComponentType<T>) {

    type MapStateToPropsType = {
        isAuth: boolean
    };
    type MapDispatchToPropsType = {};
    type OwnType = {};

    type PropsType = MapStateToPropsType & MapDispatchToPropsType & OwnType;

    // Создаваемый контейнерный компонент
    const RedirectComponent: React.FC<PropsType> =
        React.memo(
            (props) => {
                // Избавляемся от лишнего props isAuth
                let {isAuth, ...restProps} = props;

                if (!isAuth) {
                    return (<Navigate to='/login'/>);
                }

                // Передаем в оборачиваемую компоненту
                return <WrappedComponent {...restProps as T}/>;
            }
        );

    let MapStateToProps = (state: GlobalStateType) => {
        return {
            isAuth: getIsAuth(state)
        };
    };


    // Возвращаем созданный
    return connect<MapStateToPropsType, MapDispatchToPropsType, OwnType, GlobalStateType>(
        MapStateToProps,
        {}
    )(RedirectComponent);
}