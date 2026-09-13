package acsanfrancisco.invoice_system.entity.enums;

import acsanfrancisco.invoice_system.exception.InvalidDocumentTypeException;

public enum DocumentType {

    CPF,
    CNPJ;

    public static DocumentType validateDocumentType(String document){
        if(document.length() == 11){
            return DocumentType.CPF;
        }
        if(document.length() == 14){
            return DocumentType.CNPJ;
        }
        throw new InvalidDocumentTypeException("Inform a valid document pattern");
    }
}
