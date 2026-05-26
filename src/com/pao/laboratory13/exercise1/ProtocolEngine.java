package com.pao.laboratory13.exercise1;

public class ProtocolEngine {


        private SessionState state;
        private int historyCount;
        private String currentUser;

        public ProtocolEngine() {
            state = SessionState.INIT;
            historyCount = 0;
            currentUser = "";
        }

        public String processCommand(String line) {
            line = line.trim();

            if (line.isEmpty()) {
                return "";
            }

            String[] tokens = line.split("\\s+");
            String command = tokens[0];

            switch (command) {
                case "AUTH":
                    return handleAuth(tokens);

                case "OPEN":
                    return handleOpen(tokens);

                case "SEND":
                    return handleSend(tokens);

                case "BROADCAST":
                    return handleBroadcast(tokens);

                case "HISTORY":
                    return handleHistory(tokens);

                case "CLOSE":
                    return handleClose(tokens);

                default:
                    return "ERR E_PARSE UNKNOWN_COMMAND";
            }
        }

        private String handleAuth(String[] tokens) {
            if (tokens.length < 2) {
                return "ERR E_PARSE AUTH";
            }

            if (state == SessionState.CLOSED) {
                return "ERR E_STATE CLOSED";
            }

            currentUser = tokens[1];
            state = SessionState.AUTH;

            historyCount = 0;

            return "OK AUTH user=" + currentUser;
        }

        private String handleOpen(String[] tokens) {
            if (tokens.length != 1) {
                return "ERR E_PARSE OPEN";
            }

            if (state == SessionState.CLOSED) {
                return "ERR E_STATE CLOSED";
            }

            if (state == SessionState.OPEN) {
                return "ERR E_STATE ALREADY_OPEN";
            }

            if (state != SessionState.AUTH) {
                return "ERR E_STATE NOT_OPEN";
            }

            state = SessionState.OPEN;
            return "OK OPEN";
        }

        private String handleSend(String[] tokens) {

            if (tokens.length < 2) {
                return "ERR E_PARSE SEND";
            }

            if (state == SessionState.CLOSED) {
                return "ERR E_STATE CLOSED";
            }

            if (state != SessionState.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            historyCount++;
            return "OK OPEN sent";
        }

        private String handleBroadcast(String[] tokens) {
            if (tokens.length < 2) {
                return "ERR E_PARSE BROADCAST";
            }

            if (state == SessionState.CLOSED) {
                return "ERR E_STATE CLOSED";
            }

            if (state != SessionState.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            historyCount++;
            return "OK OPEN broadcast";
        }

        private String handleHistory(String[] tokens) {
            if (tokens.length != 1) {
                return "ERR E_PARSE HISTORY";
            }

            if (state == SessionState.CLOSED) {
                return "ERR E_STATE CLOSED";
            }

            if (state != SessionState.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            return "OK OPEN history=" + historyCount;
        }

        private String handleClose(String[] tokens) {
            if (tokens.length != 1) {
                return "ERR E_PARSE CLOSE";
            }

            if (state == SessionState.CLOSED) {
                return "ERR E_STATE CLOSED";
            }

            if (state != SessionState.OPEN) {
                return "ERR E_STATE NOT_OPEN";
            }

            state = SessionState.CLOSED;
            return "OK CLOSED";
        }
    }

