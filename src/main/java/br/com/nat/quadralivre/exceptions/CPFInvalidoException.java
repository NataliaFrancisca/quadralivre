package br.com.nat.quadralivre.exceptions;

public class CPFInvalidoException extends IllegalArgumentException{
    public CPFInvalidoException(String message){
        super(message);
    }
}
