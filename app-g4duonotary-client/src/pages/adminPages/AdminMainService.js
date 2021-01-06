import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";
import {saveOrEditMainService, getMainServiceList, deleteMainService} from "../../redux/actions/MainServiceAction";
import {
    Button,
    CustomInput,
    FormGroup,
    Input,
    Label,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader,
    Table
} from "reactstrap";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import {AvField, AvForm} from "availity-reactstrap-validation";
import DeleteModal from "../../components/modal/DeleteModal";
import StatusModal from "../../components/modal/StatusModal";
import {login} from "../../redux/actions/AuthActions";
import OnlineModal from "../../components/modal/OnlineModal";

class AdminMainService extends Component {
    componentDidMount() {
        this.props.dispatch(getMainServiceList())
    }

    render() {

        const {dispatch, showModal, mainServices, currentItem, active, online, showDeleteModal, showStatusModal, showOnlineModal} = this.props;
        const openModal = (item) => {
            dispatch({
                type: 'updateState',
                payload: {
                    showModal: !showModal,
                    currentItem: item,
                    active: item.active,
                    online: item.online
                }
            })
        }

        const saveItem = (e, v) => {
            v.id = currentItem != null ? currentItem.id : null
            v.active = active
            v.online = online
            this.props.dispatch(saveOrEditMainService(v))
        };

        const openDeleteModal = (item) => {
            dispatch({
                type: 'updateState',
                payload: {
                    showDeleteModal: !showDeleteModal,
                    currentItem: item
                }
            })
        };

        const openStatusModal = (item) => {
            dispatch({
                type: 'updateState',
                payload: {
                    showStatusModal: !showStatusModal,
                    currentItem: item
                }
            })
        };

        const openOnlineModal = (item) => {
            dispatch({
                type: 'updateState',
                payload: {
                    showOnlineModal: !showOnlineModal,
                    currentItem: item
                }
            })
        };

        const deleteFunction = () => {
            this.props.dispatch(deleteMainService(currentItem))
        };

        const changeActive = () => {
            dispatch({
                type: 'updateState',
                payload: {
                    active: !active
                }
            })
        };

        const changeStatusMS = () => {
            let currentMainService = {...currentItem};
            currentMainService.active = !currentItem.active;
            this.props.dispatch(saveOrEditMainService(currentMainService));
        };

        const changeOnlineMS = () => {
            let currentMainServiceOnline = {...currentItem};
            currentMainServiceOnline.online = !currentItem.online;
            this.props.dispatch(saveOrEditMainService(currentMainServiceOnline));
        };

        const changeOnline = () => {
            dispatch({
                type: 'updateState',
                payload: {
                    online: !online
                }
            })
        };

        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h2 className="text-center">MainService</h2>
                    <Button color="success" outline className="my-2" onClick={() => openModal('')}>+ New add</Button>
                    <Table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>From time</th>
                            <th>Till time</th>
                            <th>Description</th>
                            <th>Order Number</th>
                            <th>Status</th>
                            <th>Online</th>
                            <th colSpan="2" className="text-danger">Operation</th>
                        </tr>
                        </thead>
                        {mainServices.length > 0 ?
                            <tbody>
                            {mainServices.map((item, i) =>
                                <tr key={item.id}>
                                    <td>{i + 1}</td>
                                    <td>{item.name}</td>
                                    <td>{item.fromTime}</td>
                                    <td>{item.tillTime}</td>
                                    <td>{item.description}</td>
                                    <td>{item.orderNumber}</td>
                                    <td>
                                        <FormGroup check>
                                            <Label check for="active">
                                                <Input
                                                    type="checkbox" onClick={() => openStatusModal(item)} id="active"
                                                    checked={item.active}/>
                                                {item.active ? "Active" : "Inactive"}
                                            </Label>
                                        </FormGroup>
                                    </td>
                                    <td>
                                        <FormGroup check>
                                            <Label check for="online">
                                                <Input type="checkbox" onClick={() => openOnlineModal(item)} id="online"
                                                       checked={item.online}/>
                                                {item.online ? "Online" : "Offline"}
                                            </Label>
                                        </FormGroup>
                                    </td>
                                    <td><Button color="warning" outline onClick={() => openModal(item)}>Edit</Button>
                                    </td>
                                    <td><Button color="danger" outline
                                                onClick={() => openDeleteModal(item)}>Delete</Button>
                                    </td>
                                </tr>
                            )}
                            </tbody>

                            : <tbody>
                            <tr>
                                <td colSpan="4">
                                    <h3 className="text-center mx-auto"> Sorry no info </h3>
                                </td>
                            </tr>
                            </tbody>
                        }
                    </Table>

                    <Modal isOpen={showModal} toggle={openModal}>
                        <AvForm onValidSubmit={saveItem}>
                            <ModalHeader toggle={openModal} charCode="x">
                                {currentItem != null ? 'Edit Main Service' : 'Save Main Service'}
                            </ModalHeader>
                            <ModalBody>
                                <AvField name="name" label="Name" required
                                         defaultValue={currentItem != null ? currentItem.name : ""}
                                         placeholder="Enter Main Service Name"/>
                                <AvField name="fromTime" label="From" required
                                         defaultValue={currentItem != null ? currentItem.fromTime : ""}
                                         placeholder="Enter From Time"/>
                                <AvField name="tillTime" label="To" required
                                         defaultValue={currentItem != null ? currentItem.tillTime : ""}
                                         placeholder="Enter Till Time"/>
                                <AvField name="description" label="Description"
                                         defaultValue={currentItem != null ? currentItem.description : ""}
                                         placeholder="Enter Description"/>
                                <AvField name="orderNumber" label="Order Number" required
                                         defaultValue={currentItem != null ? currentItem.orderNumber : ""}
                                         placeholder="Enter Order Number"/>
                                <CustomInput type="checkbox" checked={active}
                                             onChange={changeActive}
                                             label={active ? 'Active' : 'Inactive'} id="mainServiceActive"/>
                                <br/>
                                <CustomInput type="checkbox" checked={online}
                                             onChange={changeOnline}
                                             label={online ? 'Online' : 'Offline'} id="mainServiceOnline"/>
                                <br/>

                            </ModalBody>
                            <ModalFooter>
                                <Button type="button" color="secondary" outline onClick={openModal}>Cancel</Button>
                                <Button color="success">Save</Button>
                            </ModalFooter>
                        </AvForm>
                    </Modal>

                    {showDeleteModal && <DeleteModal text={currentItem.name}
                                                     showDeleteModal={showDeleteModal}
                                                     confirm={deleteFunction}
                                                     cancel={openDeleteModal}/>}

                    {showStatusModal? <StatusModal text={currentItem.name}
                                                     showStatusModal={showStatusModal}
                                                     confirm={changeStatusMS}
                                                     cancel={openStatusModal}/>:''}

                    {showOnlineModal? <OnlineModal text={currentItem.name}
                                                     showOnlineModal={showOnlineModal}
                                                     confirm={changeOnlineMS}
                                                     cancel={openOnlineModal}/>:''}

                </CabinetLayout>
            </div>
        );
    }
}

AdminMainService.propTypes = {};

export default withRouter(
    connect(({
                 mainService: {
                     mainServices, showModal, currentItem, active, online,
                     showDeleteModal, showStatusModal, showOnlineModal
                 }
             }) => ({
        mainServices, showModal, currentItem, active, online, showDeleteModal, showStatusModal, showOnlineModal
    }))
    (AdminMainService));