import React, {Component} from 'react';
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {connect} from "react-redux";

export default ({text, showDeleteModal,confirm, cancel}) =>{
    return <Modal isOpen={showDeleteModal} toggle={cancel}>
        <ModalHeader toggle={cancel}
                     charCode="x">Delete Main Service</ModalHeader>
        <ModalBody>
            Are you sure delete {text} ?
        </ModalBody>
        <ModalFooter>
            <Button onClick={cancel} color="primary">No</Button>
            <Button type="button" onClick={confirm}
                    color="">Yes</Button>
        </ModalFooter>
    </Modal>
}