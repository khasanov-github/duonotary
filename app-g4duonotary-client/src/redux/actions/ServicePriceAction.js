import * as app from "../../api/ServicePriceApi";
import * as types from "../types/ServicePriceType";
import {toast} from "react-toastify";
import {getMainServiceWorkTimeList} from "./MainServiceWorkTimeAction";

export const getServicePriceList = (payload) => async (dispatch) => {
    await dispatch({
        api:app.getMinMaxPercent,
        types:[
            types.START_SERVICE_PRICE,
            types.REQ_MIN_MAX_PERCENT_SERVICE_PRICE_LIST,
            types.REQ_ERROR
        ],
        data:payload
    })
}

export const saveOrEditServicePrice = (payload) => async (dispatch) => {
    await dispatch({
        api: payload.id ? app.editServicePrice : app.addServicePrice,
        types: [
            types.START_SERVICE_PRICE,
            types.REQ_SERVICE_PRICE_LIST,
            types.REQ_ERROR
        ],
        data: payload
    }).then(res => {
        if (res.success) {
            dispatch(getServicePriceList())
            toast.success("Successfully saved");
        } else {
            toast.error("You can not save Service Price!")
        }
    }).catch(err => {
        toast.error("Error in saving");
    })
}

export const selectState = () => (dispatch) =>{
    dispatch({
        api: app.getStates,
        types: [
            types.START_SERVICE_PRICE ,
            types.REQ_GET_SERVICE_PRICE_BY_STATE_LIST_SUCCESS,
            types.REQ_ERROR
        ]
    })
}

export const selectCounty = (payload) => (dispatch) =>{
    dispatch({
        api: app.getCountyByState,
        types: [
            types.START_SERVICE_PRICE ,
            types.REQ_GET_SERVICE_PRICE_BY_COUNTY_LIST_SUCCESS,
            types.REQ_ERROR
        ],
        data:payload
    })
}

export const selectZipCode = (payload) => (dispatch) =>{
    dispatch({
        api: app.getZipCodeByCounty,
        types: [
            types.START_SERVICE_PRICE ,
            types.REQ_GET_SERVICE_PRICE_BY_ZIP_CODE_LIST_SUCCESS,
            types.REQ_ERROR
        ],
        data:payload
    })
}
