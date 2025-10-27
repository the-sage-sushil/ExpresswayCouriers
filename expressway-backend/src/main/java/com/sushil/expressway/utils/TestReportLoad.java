package com.sushil.expressway.utils;

import java.io.InputStream;

public class TestReportLoad {
    public static void main(String[] args) {
        try {
            InputStream stream = TestReportLoad.class.getClassLoader().getResourceAsStream("reports/invoice_sample.jrxml");
            if (stream != null) {
                System.out.println("File found! Size: " + stream.available());
                stream.close();
            } else {
                System.out.println("File not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}