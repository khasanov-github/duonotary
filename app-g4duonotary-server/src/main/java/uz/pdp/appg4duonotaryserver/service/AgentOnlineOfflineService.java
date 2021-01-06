package uz.pdp.appg4duonotaryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.AgentOnlineOffline;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.AgentOnlineOfflineDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.repository.AgentOnlineOfflineRepository;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;

@Service
public class AgentOnlineOfflineService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AgentOnlineOfflineRepository agentOnlineOfflineRepository;

    public ApiResponse addOnOff(AgentOnlineOfflineDto dto) {
        try {
            User user = userRepository.findById(dto.getAgentId()).orElseThrow(() -> new ResourceNotFoundException("agnet", "agentId", dto.getAgentId()));
            agentOnlineOfflineRepository.save(new AgentOnlineOffline(user, dto.isOnline()));
            return new ApiResponse("added",true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }
}
