import HttpClient from "../utills/HttpClient";
import {api} from './api'

export const getService = (data) => {
    return HttpClient.doGet(api.service + "getPage", data)
}