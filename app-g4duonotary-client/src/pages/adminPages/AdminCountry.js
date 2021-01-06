import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminCountry extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Country Appostl and Embassy</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminCountry.propTypes = {};

export default AdminCountry;