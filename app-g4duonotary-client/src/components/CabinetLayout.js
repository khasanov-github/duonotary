import React, {Component} from 'react';
import {withRouter} from "react-router";
import {connect} from "react-redux";
import {Link} from "react-router-dom";
import Collapse from "reactstrap/lib/Collapse";

class CabinetLayout extends Component {
    render() {
        const {dispatch,loading,superAdmin,admin,agent,customer,isOpen}=this.props

        const showServices=()=>{
            dispatch({type:"updateState",payload:{
                    isOpen:!isOpen
                }})
        }
        return (
            loading?
                <h1>Loading ... </h1>
                :
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-2">
                            <div className="img-fluid m-5">
                                <img src="/assets/duo_no 1logo.png" alt=""/>
                            </div>
                            {superAdmin||admin?
                                <div>
                                    <Link to="/admin"
                                          className={
                                              this.props.pathname === "/admin" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin" ? "/assets/Vector.png" : "/assets/icons/Vector.svg"}/></span>

                                            Main
                                        </div>
                                    </Link>
                                    <Link to="/admin/calendar"
                                          className={
                                              this.props.pathname === "/admin/calendar" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/calendar" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/calendar" ? "/assets/calendar.png" : "/assets/icons/calendar.svg"}/></span>

                                            Calendar
                                        </div>
                                    </Link>
                                    {superAdmin?
                                        <Link to="/admin/admins"
                                              className={
                                                  this.props.pathname === "/admin/admins" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/admins" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/admins" ? "/assets/Agents.png" : "/assets/icons/Agents.svg"}/></span>

                                                Admins
                                            </div>
                                        </Link>:''
                                    }
                                    <Link to="/admin/agent"
                                          className={
                                              this.props.pathname === "/admin/agent" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/agent" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/agent" ? "/assets/Agents.png" : "/assets/icons/Agents.svg"}/></span>
                                            Agents
                                        </div>
                                    </Link>
                                    <div onClick={showServices}>
                                        <Link to="/admin/mainService"
                                              className={
                                                  this.props.pathname === "/admin/mainService" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/mainService" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/mainService" ? "/assets/services.png" : "/assets/icons/services.svg"}/></span>
                                                Services
                                            </div>
                                        </Link>
                                    </div>
                                    <Collapse isOpen={isOpen}>
                                        <Link to="/admin/mainService"
                                              className={
                                                  this.props.pathname === "/admin/mainService" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/mainService" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>
                                                Main Service
                                            </div>
                                        </Link>
                                        <Link to="/admin/service"
                                              className={
                                                  this.props.pathname === "/admin/service" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/service" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>

                                                Service

                                            </div>
                                        </Link>
                                        <Link to="/admin/servicePrice"
                                              className={
                                                  this.props.pathname === "/admin/servicePrice" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/servicePrice" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>

                                                ServicePrice

                                            </div>
                                        </Link>

                                        <Link to="/admin/additionalService"
                                              className={
                                                  this.props.pathname === "/admin/additionalService" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/additionalService" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>

                                                Additional Service

                                            </div>
                                        </Link>
                                        <Link to="/admin/additionalServicePrice"
                                              className={
                                                  this.props.pathname === "/admin/additionalServicePrice" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/additionalServicePrice" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>

                                                Additional Service Price

                                            </div>
                                        </Link>
                                        <Link to="/admin/pricing"
                                              className={
                                                  this.props.pathname === "/admin/pricing" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/pricing" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>

                                                Pricing

                                            </div>
                                        </Link>
                                        <Link to="/admin/mainServiceWorkTime"
                                              className={
                                                  this.props.pathname === "/admin/mainServiceWorkTime" ?
                                                      "active-tab" : "default-tab"
                                              }>
                                            <div className={
                                                this.props.pathname === "/admin/mainServiceWorkTime" ?
                                                    "active-tab m-2" : "default-tab m-2"
                                            }>

                                                Main Service Work Time

                                            </div>
                                        </Link>

                                    </Collapse>
                                    <Link to="/admin/order"
                                          className={
                                              this.props.pathname === "/admin/order" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/order" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/order" ? "/assets/order.png" : "/assets/icons/order.svg"}/></span>
                                            Order
                                        </div>
                                    </Link>
                                    <Link to="/admin/zipCode"
                                          className={
                                              this.props.pathname === "/admin/zipCode" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/zipCode" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/zipCode" ? "/assets/zip.png" : "/assets/icons/zip.svg"}/></span>
                                            ZipCode
                                        </div>
                                    </Link>
                                    <Link to="/admin/discount"
                                          className={
                                              this.props.pathname === "/admin/discount" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/discount" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/discount" ? "/assets/Vector.png" : "/assets/icons/Vector.svg"}/></span>
                                            Discount
                                        </div>
                                    </Link>
                                    <Link to="/admin/customer"
                                          className={
                                              this.props.pathname === "/admin/customer" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/customer" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/customer" ? "/assets/Vector.png" : "/assets/icons/Vector.svg"}/></span>
                                            Customer
                                        </div>
                                    </Link>
                                    <Link to="/admin/blog"
                                          className={
                                              this.props.pathname === "/admin/blog" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/blog" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/blog" ? "/assets/Vector.png" : "/assets/icons/Vector.svg"}/></span>
                                            Blog
                                        </div>
                                    </Link>
                                    <Link to="/admin/country"
                                          className={
                                              this.props.pathname === "/admin/country" ?
                                                  "active-tab" : "default-tab"
                                          }>
                                        <div className={
                                            this.props.pathname === "/admin/country" ?
                                                "active-tab m-2" : "default-tab m-2"
                                        }>
                                                    <span style={{marginLeft: 25}}><img
                                                        src={this.props.pathname === "/admin/country" ? "/assets/Vector.png" : "/assets/icons/Vector.svg"}/></span>
                                            Country
                                        </div>
                                    </Link>
                                </div>
                                :agent?
                                    <div>

                                    </div>
                                    :customer?
                                        <div>

                                        </div>
                                        :null

                            }

                        </div>


                        <div className="col-md-10">
                            {this.props.children}
                        </div>
                    </div>
                </div>

        );
    }
}

CabinetLayout.propTypes = {};

export default withRouter(
    connect(
        ({ auth: { loading,
            superAdmin,
            admin,
            agent,
            customer,
            isOpen
        } }) => ({
            loading,
            superAdmin,
            admin,
            agent,
            customer,
            isOpen
        })
    )(CabinetLayout)
);