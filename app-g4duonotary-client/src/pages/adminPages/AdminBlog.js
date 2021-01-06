import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";

class AdminBlog extends Component {
    render() {
        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Admin Blog</h1>
                </CabinetLayout>
            </div>
        );
    }
}

AdminBlog.propTypes = {};

export default AdminBlog;