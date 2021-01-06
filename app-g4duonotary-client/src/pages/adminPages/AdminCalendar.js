import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminCalendar extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Calendar</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminCalendar.propTypes = {};

export default AdminCalendar;