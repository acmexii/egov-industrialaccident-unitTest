package industrialaccident.domain;

import industrialaccident.AssessmentApplication;
import industrialaccident.domain.InvestigationDisapproved;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Assessment_table")
@Data
//<<< DDD / Aggregate Root
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accidentId;

    private String businessCode;

    private String employeeId;

    private Long assessorId;

    private String hospitalCode;

    private String doctorNote;

    private String results;

    private Date date;

    private String comments;

    @PostPersist
    public void onPostPersist() {
        InvestigationDisapproved investigationDisapproved = new InvestigationDisapproved(
            this
        );
        investigationDisapproved.publishAfterCommit();
    }

    public static AssessmentRepository repository() {
        AssessmentRepository assessmentRepository = AssessmentApplication.applicationContext.getBean(
            AssessmentRepository.class
        );
        return assessmentRepository;
    }

    public void createInvestigation(
        CreateInvestigationCommand createInvestigationCommand
    ) {
        Assessment assessment = new Assessment();
        
        assessment.setAccidentId(createInvestigationCommand.getAccidentId());
        assessment.setBusinessCode(createInvestigationCommand.getBusinessCode());
        assessment.setEmployeeId(createInvestigationCommand.getEmployeeId());
        assessment.setHospitalCode(createInvestigationCommand.getHospitalCode());
        assessment.setDoctorNote(createInvestigationCommand.getDoctorNote());
        assessment.setComments(createInvestigationCommand.getAccidentType());
        
        repository().save(assessment);

        InvestigationCreated investigationCreated = new InvestigationCreated(this);
        investigationCreated.publishAfterCommit();
    }

    public void updateInvestigation(
        UpdateInvestigationCommand updateInvestigationCommand
    ) {
        //implement business logic here:

        InvestigationApproved investigationApproved = new InvestigationApproved(
            this
        );
        investigationApproved.publishAfterCommit();
    }

}
//>>> DDD / Aggregate Root
