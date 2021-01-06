import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminZipCode extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Zip Code County  And State CRUD</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminZipCode.propTypes = {};

export default AdminZipCode;