import * as app from "../../api/MainServiceWorkTimeApi";
import * as types from "../types/MainServiceWorkTimeType";
import {toast} from "react-toastify";


export const getMainServiceWorkTimeList = () => (dispatch) => {
    dispatch({
        api: app.getMainServiceWorkTimes,
        types: [
            types.START_MAIN_SERVICE_WORK_TIME,
            types.REQ_GET_MAIN_SERVICE_WORK_TIME_LIST,
            types.REQ_ERROR
        ]
    })
};

export const saveOrEditMainServiceWorkTime = (payload) => async (dispatch) => {
   await dispatch({
        api: payload.id ? app.editMainServiceWorkTime : app.addMainServiceWorkTime,
        types: [
            types.START_MAIN_SERVICE_WORK_TIME,
            types.REQ_SUCCESS_MAIN_SERVICE_WORK_TIME,
            types.REQ_ERROR
        ],
        data: payload
    }).then(res => {
        if (res.success) {
            dispatch(getMainServiceWorkTimeList())
            toast.success("Successfully saved");
        } else {
            toast.error("You can not save Main Service Work Time!")
        }
    }).catch(err => {
        toast.error("Error in saving");
    })
}

export const changeStatusActive = (payload) => (dispatch) => {
    dispatch({
        api: app.changeActive,
        types: [
            "",
            types.CHANGE_ACTIVE,
            types.REQ_ERROR
        ],
        data: payload
    }).then(res => {
        if (res.success) {
            dispatch(getMinMaxPercent())
            toast.success("Successfully changed status");
        } else {
            toast.error("You can not change status Main Service Work Time!")
        }
    }).catch(err => {
        toast.error("Error in changing status");
    })
}

export const getMinMaxPercent = () => (dispatch) => {
    dispatch({
        api: app.getMinMaxPercent,
        types: [
            "",
            types.GET_MIN_MAX_PERCENT,
            types.REQ_ERROR
        ],
    })
}

export const deleteMainServiceWorkTime = (payload) => (dispatch) => {
    dispatch({
        api: app.deleteMainServiceWorkTime,
        types: [
            types.START_MAIN_SERVICE_WORK_TIME,
            types.REQ_SUCCESS_MAIN_SERVICE_WORK_TIME,
            types.REQ_ERROR
        ],
        data: payload
    }).then(res => {
        dispatch(getMainServiceWorkTimeList())
        dispatch({
            types: types.REQ_SUCCESS_MAIN_SERVICE_WORK_TIME
        })
        toast.success("Successfully deleted");
    }).catch(err => {
        toast.success("Error in deleting")
    })
}

export const selectState = () => (dispatch) =>{
    dispatch({
        api: app.getStates,
        types: [
            types.START_MAIN_SERVICE_WORK_TIME ,
            types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_STATE_LIST_SUCCESS,
            types.REQ_ERROR
        ]
    })
}

export const selectCounty = (payload) => (dispatch) =>{
    dispatch({
        api: app.getCountyByState,
        types: [
            types.START_MAIN_SERVICE_WORK_TIME ,
            types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_COUNTY_LIST_SUCCESS,
            types.REQ_ERROR
        ],
        data:payload
    })
}

export const selectZipCode = (payload) => (dispatch) =>{
    dispatch({
        api: app.getZipCodeByCounty,
        types: [
            types.START_MAIN_SERVICE_WORK_TIME ,
            types.REQ_GET_MAIN_SERVICE_WORK_TIME_BY_ZIP_CODE_LIST_SUCCESS,
            types.REQ_ERROR
        ],
        data:payload
    })
}