import HttpClient from "../utills/HttpClient";
import {api} from './api'

export const addServicePrice = (data) => {
    return HttpClient.doPost(api.servicePrice, data)
}
export const editServicePrice = (data) => {
    return HttpClient.doPost(api.servicePrice, data)
}

export const getMinMaxPercent = (data) => {
    return HttpClient.doGet(api.servicePrice + "minOrMax", data)
}

export const getStates = () => {
    return HttpClient.doGet(api.state + "getStatePage")
}

export const getCountyByState = (param = {}) => {
    return HttpClient.doGet(api.servicePrice + "byState" + "?stateId=" + param.option.value, param)
}

export const getZipCodeByCounty = (param = {}) => {
    return HttpClient.doGet(api.servicePrice + "byCounty" + "?countyId=" + param.option.value, param)
}