package com.papercutlabs.jobstar.model;

import java.io.Serializable;

/**
 * Created by ritwik on 11-05-2018.
 */

public class Job implements Serializable {

    private String jobID;
    private String jobTitle;
    private String jobLocation;
    private String jobDescription;
    private String jobResponsibility;
    private String resumeType;
    private String contractType;
    private String category;
    private boolean isRelocationAvailable;
    private String dateForFilling;
    private String dateOfPosting;
    private String keySkills;
    private String employerID;
    private String userID;
    public Employer employer;
    private String datePositionFilling;
    private String jobPerks;
    private int backgroundDrawable;
    private String companyName = "";
    private String aboutCompany = "";

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobResponsibility() {
        return jobResponsibility;
    }

    public void setJobResponsibility(String jobResponsibility) {
        this.jobResponsibility = jobResponsibility;
    }

    public String getResumeType() {
        return resumeType;
    }

    public void setResumeType(String resumeType) {
        this.resumeType = resumeType;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRelocationAvailable() {
        return isRelocationAvailable;
    }

    public void setRelocationAvailable(boolean relocationAvailable) {
        isRelocationAvailable = relocationAvailable;
    }

    public String getDateForFilling() {
        return dateForFilling;
    }

    public void setDateForFilling(String dateForFilling) {
        this.dateForFilling = dateForFilling;
    }

    public String getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(String dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public String getKeySkills() {
        return keySkills;
    }

    public void setKeySkills(String keySkills) {
        this.keySkills = keySkills;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDatePositionFilling() {
        return datePositionFilling;
    }

    public void setDatePositionFilling(String datePositionFilling) {
        this.datePositionFilling = datePositionFilling;
    }

    public String getJobPerks() {
        return jobPerks;
    }

    public void setJobPerks(String jobPerks) {
        this.jobPerks = jobPerks;
    }

    public int getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(int backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }
}
