import HttpClient from "../utills/HttpClient";
import {api} from './api'

export const loginUser = (data = {userName: null, password: null}) => {
    return HttpClient.doPost(api.login, data);
}
export const me = () => {
    return HttpClient.doGet(api.me);
}