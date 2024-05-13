package industrialaccident.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestSickLeaveBenefitCommand {

    private Long sickLeaveId;
    private String employeeId;
    private String businessCode;
    private Integer period;
}
