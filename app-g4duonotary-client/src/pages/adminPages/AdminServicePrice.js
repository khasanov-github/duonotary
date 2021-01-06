import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CabinetLayout from "../../components/CabinetLayout";
import {connect} from "react-redux";
import {getServicePriceList, saveOrEditServicePrice} from "../../redux/actions/ServicePriceAction";
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
import Select from "react-select";
import {
    selectState,
    selectCounty,
    selectZipCode
} from "../../redux/actions/ServicePriceAction";
import {getServiceList} from "../../redux/actions/ServiceAction";
import {login} from "../../redux/actions/AuthActions";
import {getMinMaxPercent} from "../../redux/actions/MainServiceWorkTimeAction";

class AdminServicePrice extends Component {
    componentDidMount() {
        this.props.dispatch(getServicePriceList())
        this.props.dispatch(getServiceList())
        this.props.dispatch(selectState())
    }

    render() {

        const {dispatch, servicePrices, showModal, showStatusModal ,currentItem, currentService, active, services, all,
            selectZipCodes, selectCounties, selectStates,
            stateOptions, countyOptions, zipCodeOptions} = this.props;

        const openModal = (item) => {
            dispatch({
                type: 'updateState',
                payload: {
                    showModal: !showModal,
                    currentItem: item,
                    active: item.active,
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
            v.allZipCodes = all
            v.stateIds = selectStates
            v.countyIds = selectCounties
            v.zipCodeIds = selectZipCodes

            this.props.dispatch(saveOrEditServicePrice(v)).then(res=>{
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

        const changeAll = () => {
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

        return (
            <div>
                <CabinetLayout pathname={this.props.location.pathname}>
                    <h2 className="text-center">Main Service Work Time</h2>
                    <Button color="success button5" className="my-2" onClick={() => openModal('')}>+</Button>
                    <h4 className="ml-5">Create new</h4>

                    <Table>
                        <thead>
                        <tr>
                            <th>Service</th>
                            <th>Price</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        {servicePrices.length > 0 ?
                            <tbody>
                            {servicePrices.map((item, i) =>
                                <tr key={item.id}>
                                    <td>{item.serviceDto.name}</td>
                                    <td>${item.maxPrice === item.minPrice ? item.maxPrice : item.minPrice + "-" + item.maxPrice}</td>
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
                                {currentItem != null ? 'Edit Service Price' : 'Save Service Price'}
                            </ModalHeader>
                            <ModalBody>
                                <AvField type="select" name="serviceId"
                                         value={currentService != null ? (currentService.serviceDto.name != null ? currentService.serviceDto.name : currentService.serviceId) : "0"}
                                         required>
                                    <option value="0" disabled>Select Service</option>
                                    {services.map(item =>
                                        <option key={item.id} value={item.id}>{item.name}</option>
                                    )}
                                </AvField>
                                <AvField name="price" label="Price" required
                                         defaultValue={currentItem != null ? currentItem.price : ""}/>
                                <AvField name="chargeMinute" label="Charge Minute" required
                                         defaultValue={currentItem != null ? currentItem.chargeMinute : ""}/>
                                <AvField name="chargePercent" label="Charge Percent" required
                                         defaultValue={currentItem != null ? currentItem.chargePercent : ""}/>

                                <CustomInput type="checkbox" checked={active}
                                             onChange={changeActive}
                                             label={active ? 'Active' : 'Inactive'} id="mainServiceActive"/>
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
                                        // onChange={saveZipCodeForMSWT}
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


                </CabinetLayout>
            </div>
        );
    }
}

AdminServicePrice.propTypes = {};

export default connect(({
                            servicePrice: {
                                servicePrices, showModal, currentItem, active,
                                showStatusModal, currentService, all, selectZipCodes, selectCounties, selectStates,
                                stateOptions, countyOptions, zipCodeOptions
                            },  service: {services}
                        }) => ({
    servicePrices, showModal, currentItem, active,
    showStatusModal, currentService,services, all, selectZipCodes, selectCounties, selectStates,
    stateOptions, countyOptions, zipCodeOptions

}))
(AdminServicePrice);