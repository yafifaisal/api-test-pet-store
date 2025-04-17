package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import api.PetStoreApi;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.pet.FindById;
import model.pet.FindByStatus;
import model.pet.Pet;
import utils.ApiResponse;
import utils.RequestBuilder;

@Epic("Pet Store API")
@Feature("Pet Store Tests")
public class PetStoreTests {

    private final PetStoreApi petStoreApi = new PetStoreApi();

    @ParameterizedTest(name = "Scenario: {0}")
    @CsvFileSource(resources = "/data/pet/create-pet.csv", numLinesToSkip = 1)
    @Story("Create Pet")
    void testCreatePet(String scenario, int expectedStatus, String petName) {
        RequestBuilder request = new RequestBuilder("requestTemplates/pet/createPetRequest.json")
                .putPayload("name", petName);

        // API Call
        ApiResponse<Pet> response = petStoreApi.createPet(request.getPayload());

        // Assertion
        assertEquals(expectedStatus, response.getStatusCode());
        assertNotNull(response.getData().getId());
        assertEquals(petName, response.getData().getName());

        // Schema Validation
        response.getResponse().then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/pet/createPetSchemaSuccess.json"));
    }

    @ParameterizedTest(name = "Scenario: {0}")
    @CsvFileSource(resources = "/data/pet/find-pet-by-status.csv", numLinesToSkip = 1)
    @Story("Finds Pet by Status")
    void testFindPetByStatus(String scenario, int expectedStatus, String status) {
        RequestBuilder request = new RequestBuilder("")
                .putParam("status", status);

        // API Call
        ApiResponse<List<FindByStatus>> response = petStoreApi.findByStatus(request.getParams());

        // Assertion
        assertEquals(expectedStatus, response.getStatusCode());
        List<FindByStatus> pets = response.getData();
        for (FindByStatus pet : pets) {
            assertNotNull(pet.getId());
            assertEquals(status, pet.getStatus());
            // assertNotNull(pet.getName());
            assertNotNull(pet.getPhotoUrls());
        }

        // Schema Validation
        response.getResponse().then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/pet/findByStatusSchemaSuccess.json"));
    }

    @ParameterizedTest(name = "Scenario: {0}")
    @CsvFileSource(resources = "/data/pet/find-pet-by-id.csv", numLinesToSkip = 1)
    @Story("Finds Pet by Id")
    void testFindPetById(String scenario, int expectedStatus, int id) {

        // API Call
        ApiResponse<FindById> response = petStoreApi.findById(id);

        // Assertion
        assertEquals(expectedStatus, response.getStatusCode());
        assertNotNull(response.getData().getId());
        assertNotNull(response.getData().getPhotoUrls());
        assertEquals(id, response.getData().getId());

        // Schema Validation
        response.getResponse().then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/pet/findByIdSchemaSuccess.json"));
    }

}
