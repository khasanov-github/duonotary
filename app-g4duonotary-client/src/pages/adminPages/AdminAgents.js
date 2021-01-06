import React, {Component} from 'react';
import CabinetLayout from "../../components/CabinetLayout";
import {Row, Col} from "reactstrap";
import {config} from "../../utills/config";
import {getAgents,getCurrentAgentLocationAction, getLoc, searchAgents} from "../../redux/actions/AdminAgentsActions";
import {logout} from "../../redux/actions/AuthActions";
import {connect} from "react-redux";
import {withRouter} from "react-router";
import {AvField, AvForm} from "availity-reactstrap-validation";
import notification from "../../assets/notification.png"
import logoutIcon from "../../assets/logout.png"
import axios from "axios";


class AdminAgents extends Component {



    componentDidMount() {
        this.props.dispatch(getAgents({
                currentPage: this.props.currentPage,
                pageSize: this.props.pageSize
            })
        )


    }


    logout = () => {
        this.props.dispatch(logout());
    };


    changePage = (p) => {
        this.props.dispatch({
            type: 'updateState',
            payload: {currentPage: p}
        })
        this.props.dispatch(getAgents({
            currentPage: p,
            pageSize: this.props.pageSize
        }))
    };

    search = (e) => {
        console.log(e.target.value, "val")
        this.props.dispatch(searchAgents({
            searchValue: e.target.value
        }))
    };

    goAgent = (id) => {
        const {push} = this.props.history;
        push("/admin/agent/" + id)
    };

    getCurrentAgentLocation = (obj) => {
        console.log(obj,"OBJ")

        this.props.agents[obj.index].agentLocation.locName=obj.locName
        // console.log(tempAgent,"asdfasdfasdf")
        // this.props.dispatch(getCurrentAgentLocationAction({arr:tempAgentArray}))
    }


    render() {


        const {agents, totalElements, currentPage, pageSize, searchedAgents} = this.props


        let pagesCount = totalElements / pageSize;
        let pages;
        pages = [];
        for (let i = 0; i <= pagesCount; i++) {
            pages.push(i)
        }

        return (

            <div>
                <CabinetLayout pathname={this.props.location.pathname}>


                    <div className="container-fluid  agents">
                        <div className="row mb-5">
                            <div className="col-md-6 mt-5">
                                <h1>Agents</h1>
                            </div>
                            <div className="col-md-6 mt-5">
                                <Row>
                                    <Col className="offset-1 col-md-8">
                                        <AvForm>
                                            <AvField
                                                onChange={e => {
                                                    this.search(e);
                                                }}
                                                type="text"
                                                className="modal-input bord"
                                                name="search"
                                                placeholder="Search..."
                                            />
                                        </AvForm>
                                    </Col>
                                    <div className="iconBack">
                                        <img className="ic" src={notification} alt="Img"/>
                                    </div>
                                    <div className="iconBack">
                                        <a href="/"><img className="ic" src={logoutIcon} alt="Img"
                                                         onClick={this.logout}/></a>
                                    </div>
                                </Row>
                            </div>
                        </div>

                        {pages.map((p) =>
                            <div key={p} className="d-inline ml-2">
                                <button onClick={() => {
                                    this.changePage(p)
                                }}
                                        className={currentPage === p ? "selectedPage" : "defaultPage"}>
                                    {p + 1}
                                </button>
                            </div>
                        )}


                        {searchedAgents.length > 0 ? searchedAgents.map((item) =>
                            <Row key={item.id} className="mt-2 agentsList">
                                <Col md={1}>
                                    <img src={config.BASE_URL + "/attachment/" + item.photoId} alt=""
                                         className="img-fluid rounded-circle"/>
                                </Col>
                                <Col md={3}>
                                    <h4>Agent</h4>
                                    <p>{item.firstName + " " + item.lastName}</p>
                                </Col>
                                <Col md={3}>
                                    <h4>Location</h4>
                                    <p>Addres with zip code</p>
                                </Col>
                                <Col md={2}>
                                    <h4>Online time</h4>
                                    <p>{item.onOffTime}</p>
                                </Col>
                                <Col md={2}>
                                    <h4>Orders</h4>
                                    <p>{item.orderCount}</p>
                                </Col>
                                <Col md={1}>
                                    <a href=""> > </a>
                                </Col>
                            </Row>
                        ) : agents&&agents.map((item,index) =>
                            <Row key={item.id} className="mt-2 agentsList">
                                <Col md={1}>
                                    <img src={config.BASE_URL + "/attachment/" + item.photoId} alt=""
                                         className="img-fluid rounded-circle"/>
                                </Col>
                                <Col md={3}>
                                    <h4>Agent</h4>
                                    <p>{item.firstName + " " + item.lastName}</p>
                                </Col>
                                <Col md={3}>
                                    <h4>Location</h4>
                                    <p>{item.agentLocation?item.agentLocation.locName:""}</p>
                                </Col>
                                <Col md={2}>
                                    <h4>Online time</h4>
                                    <p>{item.onOffTime}</p>
                                </Col>
                                <Col md={2}>
                                    <h4>Orders</h4>
                                    <p>{item.orderCount}</p>
                                </Col>
                                <Col md={1} className="seeMore" onClick={() => {
                                    this.goAgent(item.id)
                                }}>
                                    <h4 className="m-3"> > </h4>
                                </Col>
                            </Row>
                        )
                        }
                        <div className="col-xs-12 text-center">

                        </div>


                    </div>


                </CabinetLayout>
            </div>
        );
    }
}

AdminAgents.propTypes = {};

export default withRouter(
    connect(
        ({agent: {agents, agentId, currentPage, pageSize, totalElements, pages, searchedAgents}}) => ({
            agents, agentId, currentPage, pageSize, totalElements, pages, searchedAgents
        })
    )(AdminAgents)
);


