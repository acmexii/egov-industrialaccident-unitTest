package industrialaccident.external;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class RequestSickLeaveBenefitCommand {

    private Long sickLeaveId;
    private String employeeId;
    private String businessCode;
    private Integer period;
}
