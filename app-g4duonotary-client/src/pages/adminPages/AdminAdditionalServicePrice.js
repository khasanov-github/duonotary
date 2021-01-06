import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminAdditionalServicePrice extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Additional Service Price</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminAdditionalServicePrice.propTypes = {};

export default AdminAdditionalServicePrice;