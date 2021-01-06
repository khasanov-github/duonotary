import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminPricing extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Pricing</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminPricing.propTypes = {};

export default AdminPricing;