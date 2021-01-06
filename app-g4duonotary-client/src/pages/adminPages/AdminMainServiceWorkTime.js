import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";
import {
    saveOrEditMainServiceWorkTime,
    getMainServiceWorkTimeList,
    deleteMainServiceWorkTime, changeStatusActive, getMinMaxPercent, selectState, selectCounty, selectZipCode
} from "../../redux/actions/MainServiceWorkTimeAction";
import {getMainServiceList} from "../../redux/actions/MainServiceAction"
import {connect} from "react-redux";
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
import {AvField, AvForm} from "availity-reactstrap-validation";
import StatusModal from "../../components/modal/StatusModal";
import Select from "react-select";
import {login} from "../../redux/actions/AuthActions";
import {getZipCode} from "../../api/MainServiceWorkTimeApi";

class AdminMainServiceWorkTime extends Component {

    componentDidMount() {
        this.props.dispatch(getMainServiceWorkTimeList())
        this.props.dispatch(getMainServiceList())
        this.props.dispatch(getMinMaxPercent())
        this.props.dispatch(selectState())
    }

    render() {
        const {
            dispatch, showModal, mainServiceWorkTimes, mainServices,
            currentItem, active, showDeleteModal, showStatusModal,
            currentMainService, minMaxPercent, all, stateOptions, countyOptions, zipCodeOptions,
            selectZipCodes, selectCounties, selectStates, online
        } = this.props;

        // console.log(stateOptions,"==========")
        // console.log(selectStates,"sdagfg")
        const openModal = (item) => {
            dispatch({
                type: 'updateState',
                payload: {
                    showModal: !showModal,
                    currentItem: item,
                    active: item.active,
                    all: item.all
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

        const saveItem = (e, v) => {
            v.id = currentItem != null ? currentItem.id : null
            v.active = active
            v.online = online
            v.allZipCodes = all
            v.stateIds = selectStates
            v.countyIds = selectCounties
            v.zipCodeIds = selectZipCodes
            console.log(v,"eee kalenga")
            this.props.dispatch(saveOrEditMainServiceWorkTime(v)).then(res=>{
                    this.props.dispatch(getMinMaxPercent())
             window.location.reload()
            })
        };

        const changeActive = () => {
            dispatch({
                type: 'updateState',
                payload: {active: !active}
            })
        };

        const changeOnline = () => {
            dispatch({
                type: 'updateState',
                payload: {online: !online}
            })
        };


        const changeStatusMSWT = () => {
            console.log(currentItem,"````````11111111111")
            let currentMainServiceWorkTime = {...currentItem};
            currentMainServiceWorkTime.active = !currentItem.active;
            this.props.dispatch(changeStatusActive(currentMainServiceWorkTime));
            dispatch({
                type: 'updateState',
                payload: {showStatusModal: !showStatusModal}
            })
        };

        const changeAll = () => {
            // this.props.dispatch(getMinMaxPercent())
            dispatch({
                type: 'updateState',
                payload: {
                    all: !all
                }
            })
        };

        const getCounty = (e, v) => {
            let arr = [];
            this.props.dispatch(selectCounty(v))
            e.map((item) => arr.push(item.value))
            dispatch({
                type: 'updateState',
                payload: {
                    selectStates: arr
                }
            })
        }

        const getZipcode = (e, v) => {
            let arr = [];
            this.props.dispatch(selectZipCode(v))
            e.map((item) => arr.push(item.value))
            dispatch({
                type: 'updateState',
                payload: {
                    selectCounties: arr
                }
            })
        }

        const saveZipCodeForMSWT = (e, v) => {
            let arr=[];
            e.map((item)=> arr.push(item.value))
            dispatch({
                type: 'updateState',
                payload: {
                    selectZipCodes: arr
                }
            })
        }

        return (

            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h2 className="text-center">Main Service Work Time</h2>
                    <Button color="success button5" className="my-2" onClick={() => openModal('')}>+</Button>
                    <h4 className="ml-5">Create new</h4>

                    <Table>
                        <thead>
                        <tr>
                            <th>Main Service</th>
                            <th>Time</th>
                            <th>Percent</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        {minMaxPercent.length > 0 ?
                            <tbody>
                            {minMaxPercent.map((item, i) =>
                                <tr key={item.id}>
                                    <td>{item.mainServiceDto.name}</td>
                                    <td>{item.fromTime}-{item.tillTime}</td>
                                    <td>+{item.maxPercent === item.minPercent ? item.maxPercent + "%" : item.minPercent + "-" + item.maxPercent + "%"}</td>
                                    {/*    <td>{item.mainServiceDto.name}</td>*/}
                                    {/*   <td>{item.fromTime}-{item.tillTime}</td>*/}
                                    {/*    <td>{item.percent?item.percent:''}%</td>*/}
                                    <FormGroup check>
                                        <Label check for="active" className="mt-2">
                                            <Input
                                                type="checkbox" onClick={() => openStatusModal(item)}
                                                id="active"
                                                checked={item.active}/>
                                            {item.active ? "Active" : "Inactive"}
                                        </Label>
                                    </FormGroup>
                                </tr>
                            )}
                            </tbody> :

                            <tbody>
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
                                {currentItem != null ? 'Edit Main Service Work Time' : 'Save Main Service Work Time'}
                            </ModalHeader>
                            <ModalBody>
                                <AvField type="select" name="mainServiceId"
                                         value={currentMainService != null ? (currentMainService.mainServiceDto.name != null ? currentMainService.mainServiceDto.name : currentMainService.mainServiceId) : "0"}
                                         required>
                                    <option value="0" disabled>Select Main Service</option>
                                    {mainServices.map(item =>
                                        <option key={item.id} value={item.id}>{item.name}</option>
                                    )}
                                </AvField>
                                <AvField name="fromTime" label="From Time" required
                                         defaultValue={currentItem != null ? currentItem.fromTime : ""}/>
                                <AvField name="tillTime" label="Till Time" required
                                         defaultValue={currentItem != null ? currentItem.tillTime : ""}/>
                                <AvField name="percent" label="Percent" required
                                         defaultValue={currentItem != null ? currentItem.percent : ""}/>

                                <CustomInput type="checkbox" checked={active}
                                             onChange={changeActive}
                                             label={active ? 'Active' : 'Inactive'} id="mainServiceActive"/>
                                <br/>
                                <CustomInput type="checkbox" checked={online}
                                             onChange={changeOnline} name="online"
                                             label={online ? 'online' : 'offline'} id="mainServiceOnline"/>
                                <br/>
                                <CustomInput type="checkbox" checked={all}
                                             onChange={changeAll} name="allZipCodes"
                                             label={all ? 'Add all zip code' : 'No all zip code'} id="all"/>
                                <br/>

                                <Select
                                    isDisabled={all}
                                    defaultValue="Select State"
                                    isMulti
                                    name="stateIds"
                                    options={stateOptions}
                                    onChange={getCounty}
                                    className="basic-multi-select"
                                    classNamePrefix="select"
                                />

                                {selectStates !== null ?
                                    <Select
                                        isDisabled={all}
                                        defaultValue="Select County"
                                        isMulti
                                        name="countyIds"
                                        options={countyOptions}
                                        onChange={getZipcode}
                                        className="basic-multi-select"
                                        classNamePrefix="select"
                                    /> : ''}

                                {selectCounties !== null ?
                                    <Select
                                        isDisabled={all}
                                        defaultValue="Select Zip Code"
                                        isMulti
                                        name="zipCodeIds"
                                        options={zipCodeOptions}
                                        onChange={saveZipCodeForMSWT}
                                        className="basic-multi-select"
                                        classNamePrefix="select"
                                    />
                                    : ''}


                            </ModalBody>
                            <ModalFooter>
                                <Button type="button" color="secondary" outline onClick={openModal}>Cancel</Button>
                                <Button color="success">Save</Button>
                            </ModalFooter>
                        </AvForm>
                    </Modal>

                    {showStatusModal ? <StatusModal text={currentItem.fromTime +" - "+ currentItem.tillTime}
                                                    showStatusModal={showStatusModal}
                                                    confirm={changeStatusMSWT}
                                                    cancel={openStatusModal}/> : ''}
                </CabinetLayout>
            </div>
        );
    }
}

AdminMainServiceWorkTime.propTypes = {};

export default connect(({
                            mainServiceWorkTime: {
                                mainServiceWorkTimes, showModal, currentItem, active,
                                showDeleteModal, showStatusModal, currentMainService, minMaxPercent, all,
                                stateOptions, countyOptions, zipCodeOptions, selectZipCodes, selectCounties, selectStates, online
                            }, mainService: {mainServices}
                        }) => ({
    mainServiceWorkTimes,
    showModal,
    currentItem,
    active,
    showDeleteModal,
    showStatusModal,
    currentMainService,
    mainServices,
    minMaxPercent,
    all,
    stateOptions,
    countyOptions,
    zipCodeOptions,
    selectZipCodes,
    selectCounties,
    selectStates,
    online
}))
(AdminMainServiceWorkTime);