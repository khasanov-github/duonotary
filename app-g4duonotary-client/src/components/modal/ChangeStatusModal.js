import React from 'react'
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {AvField, AvForm} from "availity-reactstrap-validation";

export default ({headerText, showModal, submit, statusEnums}) => {
    return <Modal isOpen={showModal}>

        <AvForm onValidSubmit={submit}>
            <ModalHeader
                charCode="x">{headerText}
            </ModalHeader>
            <ModalBody>
                <AvField type="select" name="statusEnum" required label="Select status">
                    {statusEnums.map(item =>
                        <option key={item} value={item}>{item}</option>
                    )}
                </AvField>
                <AvField name="description" placeholder="Enter Description" height="100px"/>
            </ModalBody>
            <ModalFooter>
                <Button type="button" onClick={()=>window.location.reload()}>Close</Button>
                <Button type="submit" outline
                        color="secondary">Submit</Button>
            </ModalFooter>
        </AvForm>
    </Modal>
}