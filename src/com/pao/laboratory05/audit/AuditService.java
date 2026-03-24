package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Angajat;

import java.util.Arrays;

public class AuditService {


        private Angajat[] angajati;
        private AuditEntry[] auditLog;

        private AuditService() {
            this.angajati = new Angajat[0];
            this.auditLog= new AuditEntry[0];
        }

        private static class Holder {
            private static final AuditService INSTANCE = new AuditService();
        }

        public static AuditService getInstance() {
            return AuditService.Holder.INSTANCE;
        }


        private void logAction(String action, String target) {
        String timestamp = java.time.LocalDateTime.now().toString();
        AuditEntry entry = new AuditEntry(action, target, timestamp);

            AuditEntry[] tmp = new AuditEntry[auditLog.length + 1];
            System.arraycopy(auditLog, 0, tmp, 0, auditLog.length);
            tmp[tmp.length - 1] = entry;
            auditLog = tmp;
        }

        void addAngajat(Angajat a) {
            Angajat[] temp = new Angajat[angajati.length + 1];
            System.arraycopy(angajati, 0, temp, 0, angajati.length);
            temp[temp.length - 1] = a;
            angajati = temp;
            logAction("ADD", a.getNume());

            System.out.println("Angajat adaugat: " + a);
        }

        void printAll() {
            for (Angajat a : angajati) {
                System.out.println(a);
            }
        }

        void listBySalary() {
            Angajat[] copy = angajati.clone();
            Arrays.sort(copy); // presupune Comparable

            for (Angajat a : copy) {
                System.out.println(a);
            }
        }

        void findByDepartament(String numeDept) {
            boolean found = false;
            logAction("FIND_BY_DEPT", numeDept);

            for (Angajat a : angajati) {
                if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                    System.out.println(a);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Niciun angajat în departamentul: " + numeDept);
            }
        }

        void printAuditLog(){
            for (AuditEntry ae : auditLog) {
                System.out.println(ae);
            }

        }
    }

