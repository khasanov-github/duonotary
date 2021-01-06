import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminOrder extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Order</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminOrder.propTypes = {};

export default AdminOrder;