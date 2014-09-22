/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package error;

/**
 * Shows the nature ( type ) of an error event.
 * 
 * @author Alexandar Atanasov
 */
public enum ErrorType {
    NULL_ARGUMENT, 
    INIT_MYSQL_LINK_ERROR ,
    INIT_MYSQL_CLASS_NOT_FOUND ,
    SQL_ERROR ,
    NULL_ARRAY_ELEMENT ,
    ARRAY_DATA_VECTOR_WITH_ZERO_SIZE,
    UNKNOWN_EXCEPTION ,
    DB_CONNECTOR_NEGATIVE_UPDATED_ARR_DATA ,
    INVALID_ARGUMENT ,
    NULL_CLASS_MEMBER ,
    INVALID_METHOD_CALL ,
    FAILED_RESCALING



} // end of enum ErrorType
