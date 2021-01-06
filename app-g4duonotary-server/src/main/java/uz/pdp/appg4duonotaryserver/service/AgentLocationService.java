package uz.pdp.appg4duonotaryserver.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.AgentLocation;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.AgentLocationDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.repository.AgentLocationRepository;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;
import uz.pdp.appg4duonotaryserver.utils.JsonReader;

import java.io.IOException;
import java.util.Optional;

@Service
public class AgentLocationService {
    @Autowired
    AgentLocationRepository agentLocationRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse addLoc(AgentLocationDto dto) {
        try {
            User user = userRepository.findById(dto.getAgentId()).orElseThrow(() -> new ResourceNotFoundException("agnet", "agentId", dto.getAgentId()));
            agentLocationRepository.save(new AgentLocation(user, dto.getLan(), dto.getLat(),getLocName(dto.getLan(), dto.getLat())));
            return new ApiResponse("location added",true);
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }
            }

    private String getLocName(Float lan, Float lat) throws IOException, JSONException {
        String linkStart = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
        String linkEnd = "&sensor=false&key=AIzaSyBOfWcaw_Kz0ABY4JxXO9Hd7Nq3_pkIbmI";
        String url= linkStart +lan+","+lat+linkEnd;
        final JSONObject response = JsonReader.read(url);
        final JSONObject location = response.getJSONArray("results").getJSONObject(0);
        final String formattedAddress = location.getString("formatted_address");
        return formattedAddress;
    }
}
