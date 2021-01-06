import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class Admins extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admins</h1>
                </CabinetLayout>
            </div>
        );
    }
}

Admins.propTypes = {};

export default Admins;