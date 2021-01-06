import React from 'react';
import {Route} from 'react-router-dom';
// import PublicHeader from "../components/Header/PublicHeader";

const PublicRoute = ({component: Component, ...rest}) => {

    window.onhashchange = null;

    return (<Route
            {...rest}
            render={props =>
                <Component {...props}/>
            }
        />
    )
};
export default PublicRoute;
