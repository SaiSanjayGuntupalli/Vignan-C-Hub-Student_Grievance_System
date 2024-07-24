package com.example.studentcomplaintmanagement.form;

public class Complaint {

    private String complaintId;
    private String complaintName;
    private String complaintDescription;
    private String complentedBy;
    private String complaintTo;
    private String compaintDate;
    private String complaintStatus;
    private String complaintSolution;

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplentedBy() {
        return complentedBy;
    }

    public void setComplentedBy(String complentedBy) {
        this.complentedBy = complentedBy;
    }

    public String getComplaintTo() {
        return complaintTo;
    }

    public void setComplaintTo(String complaintTo) {
        this.complaintTo = complaintTo;
    }

    public String getCompaintDate() {
        return compaintDate;
    }

    public void setCompaintDate(String compaintDate) {
        this.compaintDate = compaintDate;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getComplaintSolution() {
        return complaintSolution;
    }

    public void setComplaintSolution(String complaintSolution) {
        this.complaintSolution = complaintSolution;
    }



    public static Complaint toStudent(String s) {

        Complaint complaint=new Complaint();

        String[] tokens=s.split(":");

        complaint.setComplaintId(tokens[0]);
        complaint.setComplaintName(tokens[1]);
        complaint.setComplaintDescription(tokens[2]);
        complaint.setComplentedBy(tokens[3]);
        complaint.setComplaintTo(tokens[4]);
        complaint.setCompaintDate(tokens[5]);
        complaint.setComplaintStatus(tokens[6]);
        complaint.setComplaintSolution(tokens[7]);

        return complaint;
    }

    @Override
    public String toString() {
        return complaintId+":"+complaintName+":"+complaintDescription+":"+complentedBy+":"+complaintTo+":"+compaintDate+":"+complaintStatus+":"+complaintSolution;
    }
}
