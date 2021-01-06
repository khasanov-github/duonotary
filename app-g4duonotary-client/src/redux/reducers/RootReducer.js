import {combineReducers} from "redux";
import auth from "./AuthReducer";
import mainService from './MainServiceReducer'
import mainServiceWorkTime from './MainServiceWorkTimeReducer'
import servicePrice from './ServicePriceReducer'
import agent from "./AdminAgentsReducer";
import service from "./ServiceReducer"
import {routerReducer} from "react-router-redux";


export const rootReducer = combineReducers({
    router:routerReducer,
    mainService,
    mainServiceWorkTime,
    servicePrice,
    auth,
    agent,
    service
})