import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminAdditionalService extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Additional Service</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminAdditionalService.propTypes = {};

export default AdminAdditionalService;