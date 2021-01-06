import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminDiscount extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Discount</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminDiscount.propTypes = {};

export default AdminDiscount;