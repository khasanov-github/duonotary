import React, {Component} from 'react';
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {connect} from "react-redux";

export default ({text, showStatusModal,confirm, cancel}) =>{
    return <Modal isOpen={showStatusModal} toggle={cancel}>
        <ModalHeader toggle={cancel}
                     charCode="x">Change status</ModalHeader>
        <ModalBody>
            Are you sure change status {text} ?
        </ModalBody>
        <ModalFooter>
            <Button onClick={cancel} color="primary">No</Button>
            <Button type="button" onClick={confirm}
                    color="">Yes</Button>
        </ModalFooter>
    </Modal>
}