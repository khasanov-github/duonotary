import React, {Component} from 'react';
import CabinetLayout from "../../components/CabinetLayout";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import {
    changeActive,
    getAgent,
    changeDocumentStatus,
    getSchedule,
    getHourOff, editAgent
} from "../../redux/actions/AdminAgentsActions";
import {config} from "../../utills/config";
import StatusModal from "../../components/modal/StatusModal";
import {FormGroup, Input, Label, Button, Table,Modal,ModalHeader,ModalBody,ModalFooter } from "reactstrap";
import {AvField, AvForm} from "availity-reactstrap-validation";
import ChangeStatusModal from "../../components/modal/ChangeStatusModal";
import {toast} from "react-toastify";



class AdminAgent extends Component {
    componentDidMount() {
        this.props.dispatch(getAgent(window.location.pathname.substring(window.location.pathname.lastIndexOf("/") + 1)))
        this.props.dispatch(getSchedule(window.location.pathname.substring(window.location.pathname.lastIndexOf("/") + 1)))
        this.props.dispatch(getHourOff(window.location.pathname.substring(window.location.pathname.lastIndexOf("/") + 1)))
    }

    render() {
        const {currentAgent, showStatusModal, statusEnums, showChangeStatusModal, currentItem, schedule, hourOff,showModal} = this.props

        const openStatusModal = () => {
            this.props.dispatch({
                type: 'updateState',
                payload: {
                    showStatusModal: !showStatusModal
                }
            })
        };
        const changeAgentActive = () => {
            let agent = {...currentAgent};
            this.props.dispatch(changeActive(agent))
            window.location.reload(false)
        }

        const openChangeStatusModal = (item) => {
            this.props.dispatch(
                {
                    type: "updateState",
                    payload: {
                        showChangeStatusModal: true,
                        currentItem: item
                    }
                })
        }

        const changeStatus = (e, v) => {

            if (v.statusEnum === "PENDING")
                toast.error("You cannot select Pending")
            else if (v.statusEnum === "REJECTED" && v.description.length < 5) {
                toast.error("description is empty")
            } else {
                v.documentId = currentItem.id;
                this.props.dispatch(changeDocumentStatus(v))
                this.props.dispatch({type: "updateState", payload: {showStatusModal: false}})
            }
        }
        const openModal = () => {
            this.props.dispatch({
                type: 'updateState',
                payload: {
                    showModal: !showModal,
                }
            })
        };
        const saveItem = (e, v) => {
            v.id = currentAgent != null ? currentAgent.id : null
            v.photoId= currentAgent != null ? currentAgent.photoId : null
            console.log(v)
            this.props.dispatch(editAgent(v))
            openModal()
            window.location.reload()
        }

        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h1>Duo Notary Agent Info</h1>

                    <div className="container-fluid">
                        {currentAgent ?
                            <div>
                                <div className="row">
                                    <div className="col-md-4">
                                        <img src={config.BASE_URL + "/attachment/" + currentAgent.photoId} alt=""
                                             className="img-fluid rounded-circle"/>
                                    </div>
                                    <div className="col-md-8">
                                        <p>firstName: {currentAgent.firstName}</p>
                                        <p>lastName: {currentAgent.lastName}</p>
                                        <p>Phone Number : {currentAgent.phoneNumber}</p>
                                        <p>Email : {currentAgent.email}</p>
                                        <FormGroup check>
                                            <Label check>
                                                <Input type="checkbox" onClick={() => openStatusModal()} id="active"
                                                       checked={currentAgent.active}/>
                                                {currentAgent.active ? "Active" : "Inactive"}
                                            </Label>
                                        </FormGroup>
                                        <Button color="warning" outline onClick={() => openModal()}>Edit</Button>
                                    </div>
                                </div>
                                <div className="row"><h1>Passport Info</h1></div>

                                {currentAgent.passportDto!=null?
                                    <div className="row">
                                        <div className="col-md-4">
                                            <img
                                                src={config.BASE_URL + "/attachment/" + currentAgent.passportDto.attachmentId}
                                                alt=""
                                                className="img-fluid "/>
                                        </div>
                                        <div className="col-md-8">
                                            <p>Issue Date : {currentAgent.passportDto.issueDate}</p>
                                            <p>Expire Date : {currentAgent.passportDto.expireDate}</p>
                                            <p>Status : {currentAgent.passportDto.statusEnum}</p>
                                            <p>Expired : {currentAgent.passportDto.expired ? "true" : "false"}</p>
                                        </div>
                                    </div>:''
                                }

                                <div className="row"><h1>Certificate Info</h1></div>
                                {currentAgent.certificateDtoList ? currentAgent.certificateDtoList.map((item) =>
                                    <div key={item.id} className="row">
                                        <div className="col-md-4">
                                            <img
                                                src={config.BASE_URL + "/attachment/" + item.attachmentId}
                                                alt=""
                                                className="img-fluid "/>
                                        </div>
                                        <div className="col-md-8">
                                            <p>Issue Date : {item.issueDate}</p>
                                            <p>Expire Date : {item.expireDate}</p>
                                            <p>Expired : {item.expired ? "true" : "false"}</p>
                                            <p>State : </p>
                                            <p>Status : {item.statusEnum}</p>
                                            <Button color="secondary" onClick={() => openChangeStatusModal(item)}
                                            > Change status</Button>
                                        </div>
                                    </div>
                                ) : ''}

                            </div>


                            : ''}
                        <h1>Agent Schedule</h1>
                        <Table>
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Week day</th>
                                <th>Day off</th>
                                <th>Working hours</th>
                            </tr>
                            </thead>
                            {schedule ? schedule.map((item, i) =>
                                <tbody>
                                <tr>
                                    <th>{i + 1}</th>
                                    <td>{item.day}</td>
                                    <td>{item.dayoff ? 'day off' : 'working day'}</td>
                                    {item.hourList.map((hour, h) =>
                                        <td className="d-flex">{hour.fromTime + "-" + hour.tillTime}</td>
                                    )}
                                </tr>
                                </tbody>
                            ) : ''}
                        </Table>

                        <h1>Agent Hour off</h1>
                        <Table>
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Date</th>
                                <th>Hour</th>
                            </tr>
                            </thead>
                            {hourOff ? hourOff.map((item, i) =>
                                <tbody>
                                <tr>
                                    <th>{i + 1}</th>
                                    <td>{item.fromTime.substring(0, 10)}</td>
                                    <td>{item.fromTime.substring(12, 16)}-{item.tillTime.substring(12, 16)}</td>
                                </tr>
                                </tbody>
                            ) : "not hour off"}


                        </Table>


                    </div>

                    <Modal isOpen={showModal} toggle={openModal}>
                        <AvForm onValidSubmit={saveItem}>
                            <ModalHeader toggle={openModal}
                                         charCode="x">Edit Agent</ModalHeader>
                            <ModalBody>

                                <AvField name="firstName" label="firstName" required
                                          defaultValue={currentAgent!=null?currentAgent.firstName:''}
                                />
                                <AvField name="lastName" label="lastName" required
                                         defaultValue={currentAgent!=null?currentAgent.lastName:''}
                                />
                                <AvField name="email" label="email" required
                                         defaultValue={currentAgent!=null?currentAgent.email:''}
                                />
                                <AvField name="phoneNumber" label="PhoneNumber" required
                                         defaultValue={currentAgent!=null?currentAgent.phoneNumber:''}
                                />
                            </ModalBody>
                            <ModalFooter>
                                <Button type="button" color="secondary" outline onClick={openModal}>Cancel</Button>{' '}
                                <Button type="submit" color="success">Save</Button>
                            </ModalFooter>
                        </AvForm>
                    </Modal>


                    {showStatusModal && <StatusModal text={currentAgent.firstName + " " + currentAgent.lastName}
                                                     showStatusModal={showStatusModal}
                                                     confirm={changeAgentActive}
                                                     cancel={openStatusModal}/>}


                    <ChangeStatusModal
                        submit={changeStatus}
                        headerText={"Certificate and Passport"}
                        showModal={showChangeStatusModal}
                        statusEnums={statusEnums}
                    />


                </CabinetLayout>
            </div>
        );
    }
}

AdminAgent.propTypes = {};

export default withRouter(
    connect(
        ({agent: {currentAgent, showStatusModal, statusEnums, showChangeStatusModal, currentItem, schedule, hourOff,showModal}}) => ({
            currentAgent, showStatusModal, statusEnums, showChangeStatusModal, currentItem, schedule, hourOff,showModal
        })
    )(AdminAgent)
);