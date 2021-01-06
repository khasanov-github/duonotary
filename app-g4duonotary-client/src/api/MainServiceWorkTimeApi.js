import HttpClient from "../utills/HttpClient";
import {api} from './api'

export const getMainServiceWorkTimes = async () => {
    return await HttpClient.doGet(api.mainServiceWorkTime)
}
export const addMainServiceWorkTime = (data) => {
    return HttpClient.doPost(api.mainServiceWorkTime, data)
}
export const editMainServiceWorkTime = (data) => {
    return HttpClient.doPost(api.mainServiceWorkTime, data)
}

export const changeActive = (data) => {
    return HttpClient.doPut(api.mainServiceWorkTime + "changeActive/" + data.id)
}

export const getMinMaxPercent = (data) => {
    return HttpClient.doGet(api.mainServiceWorkTime + "minMaxPercent", data)
}

export const deleteMainServiceWorkTime = (data) => {
    return HttpClient.doDelete(api.mainServiceWorkTime + "remove/" + data.id)
}

export const getStates = () => {
    return HttpClient.doGet(api.state + "getStatePage")
}

export const getCounty = () => {
    return HttpClient.doGet(api.county + "allByPageable")
}

export const getZipCode = () => {
    return HttpClient.doGet(api.zipCode + "getPage")
}

export const getCountyByState = (param = {}) => {
    return HttpClient.doGet(api.mainServiceWorkTime + "byState" + "?stateId=" + param.option.value, param)
}

export const getZipCodeByCounty = (param = {}) => {
    return HttpClient.doGet(api.mainServiceWorkTime + "byCounty" + "?countyId=" + param.option.value, param)
}