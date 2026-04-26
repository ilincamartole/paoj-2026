package com.pao.proiect.SISTEM_LICITATII.exceptions;

public class NuExistaAceastaLicitatie extends RuntimeException {
    public NuExistaAceastaLicitatie(String message) {
        super(message);
    }

    public static class CNPInvalidException extends RuntimeException {
        public CNPInvalidException(String message) {
            super(message);
        }
    }
}
