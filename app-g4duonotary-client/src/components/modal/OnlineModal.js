import React, {Component} from 'react';
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {connect} from "react-redux";

export default ({text, showOnlineModal, confirm, cancel}) => {
    console.log('modalga keldi',showOnlineModal);
    return <Modal isOpen={showOnlineModal} toggle={cancel}>
        <ModalHeader toggle={cancel}
                     charCode="x">Change Online</ModalHeader>
        <ModalBody>
            Are you sure change online {text} ?
        </ModalBody>
        <ModalFooter>
            <Button onClick={cancel} color="primary">No</Button>
            <Button type="button" onClick={confirm}
                    color="">Yes</Button>
        </ModalFooter>
    </Modal>
}