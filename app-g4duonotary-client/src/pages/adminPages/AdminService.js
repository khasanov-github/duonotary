import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";
import {getServiceList} from "../../redux/actions/ServiceAction";
import {connect} from "react-redux";

class AdminService extends Component {

    componentDidMount() {
        this.props.dispatch(getServiceList())
    }

    render() {

        const {dispatch} = this.props;
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Service</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminService.propTypes = {};

export default connect(({
                           service: {services}
                        }) => ({
 services

}))
(AdminService);