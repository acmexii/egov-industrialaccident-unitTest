package industrialaccident.external;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SickLeaveServiceFallback extends EgovAbstractServiceImpl implements SickLeaveService {

    @Override
    public void requestSickLeaveBenefit(Long id, RequestSickLeaveBenefitCommand requestSickLeaveBenefitCommand) throws Exception {
        
        // Some workround Code here, When the called service is disabled        
        System.out.println("#### 휴업급여 신청정보가 임시 저장되었습니다. ####");  
        System.out.println("#### 해당 시스템이 정상화되면, 정상 처리될 예정입니다. ####");  
    }

}
