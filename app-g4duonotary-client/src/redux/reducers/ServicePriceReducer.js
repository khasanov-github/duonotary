import * as types from "../types/ServicePriceType";
import {createReducer} from "../../utills/StoreUtils";

const initState = {
    loading: false,
    active: false,
    servicePrices: [],
    stateOptions: [],
    countyOptions: [],
    zipCodeOptions: [],
    showModal: false,
    currentItem: null,
    currentService: null,
    showStatusModal: false,
    page: '',
    size: '',
    totalPages: '',
    totalElements: '',
    text: '',
    all: false,
    selectZipCodes: null,
    selectCounties: null,
    selectStates: null,
    minMaxPercent: []
};

const reducers = {
    [types.START_SERVICE_PRICE](state) {
        state.loading = true;
        state.text = '';
    },

    [types.REQ_MIN_MAX_PERCENT_SERVICE_PRICE_LIST](state, data) {
        console.log(data,"qweqeqe")
        state.servicePrices = data.payload.object
    },

    [types.REQ_SUCCESS_SERVICE_PRICE](state){
        state.loading = false;
        state.showStatusModal = false;
        state.showModal = false;
        state.currentItem = null;
        state.currentService = null;
        state.selectCounties = null;
        state.selectZipCodes = null;
        state.selectStates = null;
    },

    [types.REQ_ERROR](state) {
        state.selectCounties = null;
        state.selectionState = null;
        state.selectZipCodes = null;
    },

    [types.REQ_GET_SERVICE_PRICE_BY_STATE_LIST_SUCCESS](state,payload){
        // console.log('States',payload.payload.object)
        // state.stateOptions = payload.payload.object;
        state.stateOptions.push(...payload.payload.object.map(item => {
            return {value: item.id, label: item.name}
        }));
    },

    [types.REQ_GET_SERVICE_PRICE_BY_COUNTY_LIST_SUCCESS](state,payload){
        // console.log('County',payload)
        state.countyOptions.push(...payload.payload.map(item => {
            return {value: item.id, label: item.name}
        }));
    },

    [types.REQ_GET_SERVICE_PRICE_BY_ZIP_CODE_LIST_SUCCESS](state,payload){
        // console.log('ZipCode',payload)
        state.zipCodeOptions.push(...payload.payload.map(item => {
            return {value: item.id, label: item.code}
        }));
    },

    updateState(state, {payload}) {
        return {
            ...state,
            ...payload
        }
    },
}

export default createReducer(initState, reducers);
