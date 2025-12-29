
package jobinterviewpreparationsystem.models;

import java.util.Date;

public class Interview {

    private int interviewId;
    private int jobSeekerId;
    private int mentorId;
    private String interviewStatus;

    public Interview(int interviewId, int jobSeekerId, int mentorId, String interviewStatus) {
        this.interviewId = interviewId;
        this.jobSeekerId = jobSeekerId;
        this.mentorId = mentorId;
        this.interviewStatus = interviewStatus;
    }

}



