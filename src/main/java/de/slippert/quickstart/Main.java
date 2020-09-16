package de.slippert.quickstart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.slippert.quickstart.controller.dto.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:8080/customers";
        ResponseEntity<String> getResponse1 = restTemplate.getForEntity(baseUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();

        CustomerDto cd = new CustomerDto();
        cd.setFirstname("Hans");
        cd.setLastname("Maulwurf");

        String json = mapper.writeValueAsString(cd);
        cd = mapper.readValue(json, CustomerDto.class);

        ResponseEntity<String> poseResponse = restTemplate.postForEntity(baseUrl, cd, String.class);

        ResponseEntity<CustomerDto> getResponse2 = restTemplate.getForEntity(baseUrl + "/1", CustomerDto.class);

        CustomerDto getResponse3 = restTemplate.getForObject(baseUrl + "/1", CustomerDto.class);
        ResponseEntity<CustomerDto[]> getResponse4 = restTemplate.getForEntity(baseUrl, CustomerDto[].class);

        System.out.println(getResponse2);
    }
}
