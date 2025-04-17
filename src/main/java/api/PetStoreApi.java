package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.pet.FindById;
import model.pet.FindByStatus;
import model.pet.Pet;
import utils.ApiResponse;
import utils.ConfigManager;
import utils.LogUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PetStoreApi {

        private final String baseUrl = ConfigManager.get("base_url");

        public ApiResponse<Pet> createPet(Map<String, Object> body) {
                Response response = RestAssured.given()
                                .baseUri(baseUrl)
                                .header("accept", "application/json")
                                .header("Content-Type", "application/json")
                                .body(body)
                                .post("/pet");

                LogUtil.logRequest("Create Pet", "POST", baseUrl + "/pet", null, null, body);
                LogUtil.logResponse("Create Pet", response);

                Pet pet = response.as(Pet.class);
                return new ApiResponse<>(pet, response);
        }

        public ApiResponse<List<FindByStatus>> findByStatus(Map<String, Object> params) {
                Response response = RestAssured.given()
                                .baseUri(baseUrl)
                                .header("accept", "application/json")
                                .params(params)
                                .get("/pet/findByStatus");

                LogUtil.logRequest("Finds Pet By Status", "GET", baseUrl + "/pet/findByStatus", null, params, null);
                LogUtil.logResponse("Finds Pet By Status", response);

                List<FindByStatus> findByStatusList = Arrays.asList(response.as(FindByStatus[].class));
                return new ApiResponse<>(findByStatusList, response);
        }

        public ApiResponse<FindById> findById(int id) {
                Response response = RestAssured.given()
                                .baseUri(baseUrl)
                                .header("accept", "application/json")
                                .get("/pet/" + id);

                LogUtil.logRequest("Finds Pet By Id", "GET", baseUrl + "/pet/" + id, null, null, null);
                LogUtil.logResponse("Finds Pet By Id", response);

                FindById findById = response.as(FindById.class);
                return new ApiResponse<>(findById, response);
        }

}
