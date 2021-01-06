import React from 'react';
import {connect} from 'react-redux';
import {Redirect, Route} from 'react-router-dom';
import {TOKEN} from "./constants";
// import {userMe} from "../redux/actions/AuthActions";


const PrivateRoute = ({dispatch, component: Component, ...rest})=> {
    // dispatch(userMe())
    return (<Route
            {...rest}
            render={(props) =>
                localStorage.getItem(TOKEN) != null ? (
                    <Component {...props} />
                ) : (
                    <Redirect
                        to={{
                            pathname: '/',
                            state: {from: props.location}
                        }}
                    />
                )
            }
        />
    )
}
export default connect(({privateRoute, auth}) => ({privateRoute, auth}))(
    PrivateRoute
);
