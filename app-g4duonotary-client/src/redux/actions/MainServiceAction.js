import * as app from "../../api/MainServiceApi";
import * as types from "../types/MainServiceType";
import {toast} from "react-toastify";


export const getMainServiceList = () => (dispatch) => {
    dispatch({
        api: app.getMainServices,
        types: [
            types.START,
            types.REQ_GET_MAIN_SERVICE_LIST,
            types.REQ_ERROR
        ]
    })
};

export const saveOrEditMainService = (payload) => (dispatch) => {
    dispatch({
        api: payload.id ? app.editMainService : app.addMainService,
        types: [
            types.START,
            types.REQ_SUCCESS,
            types.REQ_ERROR
        ],
        data: payload
    }).then(res => {
        if (res.success) {
            dispatch(getMainServiceList())
            toast.success(payload.id ? "Main Service edit successfully!" : "Main Service saved successfully!");
        } else {
            toast.error("You can not save Main Service!")
        }
    }).catch(err => {
        toast.error("Error in save");
    })
}

export const deleteMainService = (payload) => (dispatch) => {
    dispatch({
        api:app.deleteMainService,
        types:[
            types.START,
            types.REQ_SUCCESS,
            types.REQ_ERROR
        ],
        data:payload
    }).then(res =>{
        dispatch(getMainServiceList())
        dispatch({
            types: types.REQ_SUCCESS
        })
        toast.success("Successfully deleted");
    }).catch(err=>{
        toast.success("Error in deleted")
    })
}