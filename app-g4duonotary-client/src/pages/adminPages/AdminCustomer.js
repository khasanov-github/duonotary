import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminCustomer extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Customer</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminCustomer.propTypes = {};

export default AdminCustomer;