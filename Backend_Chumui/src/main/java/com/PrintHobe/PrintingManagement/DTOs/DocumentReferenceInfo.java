package com.PrintHobe.PrintingManagement.DTOs;

import com.PrintHobe.PrintingManagement.Entity.Document;

public class DocumentReferenceInfo {
    private Long documentId;
    private String referenceId;
    private Document.Color color;
    private String filePath;
    private String originalFileName;
    private int copies;


    public DocumentReferenceInfo(Long documentId, String referenceId, Document.Color color, String filePath, String originalFileName, int copies) {
        this.documentId = documentId;
        this.referenceId = referenceId;
        this.color = color;
        this.filePath = filePath;
        this.originalFileName = originalFileName;
        this.copies = copies;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Document.Color getColor() {
        return color;
    }

    public void setColor(Document.Color color) {
        this.color = color;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
