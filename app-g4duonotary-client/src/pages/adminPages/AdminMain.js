import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminMain extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Main</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminMain.propTypes = {};

export default AdminMain;