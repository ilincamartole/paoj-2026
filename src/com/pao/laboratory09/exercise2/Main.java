package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {

    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());

        new File("output").mkdirs();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {

            for (int i = 0; i < N; i++) {
                String[] parts = sc.nextLine().trim().split("\\s+");

                int id = Integer.parseInt(parts[0]);
                double suma = Double.parseDouble(parts[1]);
                String data = parts[2];
                TipTranzactie tip = TipTranzactie.valueOf(parts[3]);

                dos.write(toLittleEndianInt(id));

                dos.write(toLittleEndianDouble(suma));

                byte[] dataBytes = Arrays.copyOf(data.getBytes(), 10);
                dos.write(dataBytes);

                dos.write(tip == TipTranzactie.CREDIT ? 0 : 1);

                dos.write(0);

                dos.write(new byte[8]);
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");

                switch (parts[0]) {

                    case "READ": {
                        int idx = Integer.parseInt(parts[1]);
                        System.out.println(readRecord(raf, idx));
                        break;
                    }

                    case "UPDATE": {
                        int idx = Integer.parseInt(parts[1]);
                        String statusStr = parts[2];

                        byte status = statusToByte(statusStr);

                        raf.seek(idx * RECORD_SIZE + 23);
                        raf.write(status);

                        System.out.println("Updated [" + idx + "]: " + statusStr);
                        break;
                    }

                    case "PRINT_ALL": {
                        for (int i = 0; i < N; i++) {
                            System.out.println(readRecord(raf, i));
                        }
                        break;
                    }
                }
            }
        }
    }

    private static String readRecord(RandomAccessFile raf, int idx) throws IOException {

        raf.seek(idx * RECORD_SIZE);

        byte[] buffer = new byte[RECORD_SIZE];
        raf.readFully(buffer);

        ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);

        int id = bb.getInt();
        double suma = bb.getDouble();

        byte[] dataBytes = new byte[10];
        bb.get(dataBytes);
        String data = new String(dataBytes).trim();

        byte tipByte = bb.get();
        byte statusByte = bb.get();

        String tip = (tipByte == 0) ? "CREDIT" : "DEBIT";
        String status = byteToStatus(statusByte);

        return String.format("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s",
                idx, id, data, tip, suma, status);
    }

    // 🔹 Helpers

    private static byte[] toLittleEndianInt(int value) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
    }

    private static byte[] toLittleEndianDouble(double value) {
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(value).array();
    }

    private static byte statusToByte(String status) {
        switch (status) {
            case "PENDING": return 0;
            case "PROCESSED": return 1;
            case "REJECTED": return 2;
            default: throw new IllegalArgumentException("Invalid status");
        }
    }

    private static String byteToStatus(byte b) {
        switch (b) {
            case 0: return "PENDING";
            case 1: return "PROCESSED";
            case 2: return "REJECTED";
            default: return "UNKNOWN";
        }
    }
}