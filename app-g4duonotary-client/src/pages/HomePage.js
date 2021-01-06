import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {AvField, AvForm} from 'availity-reactstrap-validation'
import Button from "reactstrap/es/Button";
import * as authAction from '../redux/actions/AuthActions'
import {withRouter} from "react-router";
import {connect} from "react-redux";


class HomePage extends Component {
    constructor(props) {
        super(props);
        this.state={
            test:''
        }
    }
    render() {
        const {loading,dispatch,history}=this.props
        const login=(e,v)=>{
            e.preventDefault();
            console.log(v,"VALUES")
          dispatch(authAction.login({v,history}))
        }
        return (
            <div>
                {loading?
                <h1>Loading ...</h1>
                    :
                    <AvForm onValidSubmit={login}>
                        <AvField
                            className="modal-input"
                            name="userName"
                            label="Name"
                            required
                            placeholder="Enter Main Service name"
                        />
                        <AvField
                            type="password"
                            className="modal-input"
                            name="password"
                            label="Name"
                            required
                            placeholder="Enter Main Service name"
                        />
                        <Button type="submit">Login</Button>
                    </AvForm>
                }
            </div>
        );
    }
}

HomePage.propTypes = {};

export default withRouter(
    connect(
        ({ auth: { loading } }) => ({
           loading
        })
    )(HomePage)
);