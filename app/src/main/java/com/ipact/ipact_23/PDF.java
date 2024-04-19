package com.ipact.ipact_23;

public class PDF {

    String pdfTitle, pdfLink;

    public PDF() {
    }

    public PDF(String pdfTitle, String pdfLink) {
        this.pdfTitle = pdfTitle;
        this.pdfLink = pdfLink;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public String getPdfLink() {
        return pdfLink;
    }
}
