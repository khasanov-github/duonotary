import * as app from "../../api/ServiceApi";
import * as types from "../types/ServiceType";
import {toast} from "react-toastify";

export const getServiceList = (payload) => (dispatch) => {
    dispatch({
        api: app.getService,
        types: [
            types.START_SERVICE,
            types.REQ_SERVICE_LIST,
            types.REQ_ERROR
        ],
        data: payload
    })
}