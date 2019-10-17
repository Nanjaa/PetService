package com.stephanieolfert.petservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stephanieolfert.petservice.data.PetRepository;
import com.stephanieolfert.petservice.pet.Pet;
import com.stephanieolfert.petservice.util.CreateRequest;
import com.stephanieolfert.petservice.util.DeleteRequest;
import com.stephanieolfert.petservice.util.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PetServiceUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenInvalidInput_thenThrowErrorOnCreate() throws Exception {

        List<Pet> pets = TestUtils.newInvalidPetList();
        CreateRequest create = new CreateRequest();
        create.setPets(pets);

        MvcResult result = this.mockMvc
                .perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(TestUtils.toJson(create)))
                .andExpect(status().isBadRequest()).andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content, containsString("pets[0].name\":\"First name required"));
        assertThat(content, containsString("pets[1].type\":\"Not a valid type"));
        assertThat(content, containsString("pets[2].age\":\"Age cannot be a negative number"));
        assertThat(content, containsString("pets[3].sex\":\"Sex can only be values 1 (M) or 2 (F)"));
        assertThat(content, containsString("pets[4].description\":\"Description required"));
        assertThat(content, containsString("pets[5].owner_email\":\"Email must be a valid format"));
        assertThat(content, containsString("pets[6].image_url\":\"Image URL must be a valid format"));

        petRepository.deleteAll();

    } // whenInvalidInput_thenThrowErrorOnCreate

    @Test
    public void whenValidInput_thenCreatePets() throws Exception {

        List<Pet> pets = TestUtils.newValidPetList();
        CreateRequest create = new CreateRequest();
        create.setPets(pets);

        this.mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(TestUtils.toJson(create)))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Iterable<Pet> found = petRepository.findAll();
        assertNotNull(found);
        assertThat(found).isNotEmpty();
        assertThat(found).hasSize(4);

        int count = 0;

        for (Pet foundPet : found) {
            testFields(pets.get(count), foundPet);
            count++;
        }

        petRepository.deleteAll();

    } // whenValidInput_thenCreatePets()

    @Test
    public void whenNoInput_thenGetAllPets() throws Exception {

        List<Pet> pets = TestUtils.newValidPetList();
        petRepository.saveAll(pets);

        List<Pet> found = searchResults("");
        assertThat(found).hasSize(4);

        int count = 0;
        for (Pet foundPet : found) {
            testFields(pets.get(count), foundPet);
            count++;
        }

        petRepository.deleteAll();

    } // whenNoInput_thenGetAllPets()

    @Test
    public void whenSearchInput_thenSearchPets() throws Exception {

        List<Pet> randomPets = TestUtils.newValidPetList();
        petRepository.saveAll(randomPets);
        Pet myCat = petRepository.save(TestUtils.newCat());
        Pet myDog = petRepository.save(TestUtils.newDog());
        Pet myBird = petRepository.save(TestUtils.newBird());
        Pet myFish = petRepository.save(TestUtils.newFish());

        // id
        List<Pet> found = searchResults("{\"search\": { \"id\": \"" + myCat.getId() + "\"}}");
        assertThat(found).hasSize(1);
        testFields(myCat, found.get(0));
        // name
        found = searchResults("{\"search\": { \"name\": \"" + myDog.getName() + "\"}}");
        assertThat(found).hasSize(2);
        // type
        found = searchResults("{\"search\": { \"type\": \"" + myBird.getType() + "\"}}");
        assertThat(found).hasSize(2);
        // age
        found = searchResults("{\"search\": { \"age\": \"" + myFish.getAge() + "\"}}");
        assertThat(found).hasSize(2);
        // sex
        found = searchResults("{\"search\": { \"sex\": \"" + Pet.SEX_F + "\"}}");
        assertThat(found).hasSize(4);
        // description
        found = searchResults("{\"search\": { \"description\": \"The cutest %\"}}");
        assertThat(found).hasSize(8);
        // owner_email
        found = searchResults("{\"search\": { \"owner_email\": \"" + myCat.getOwner_email() + "\"}}");
        assertThat(found).hasSize(8);
        // image_url
        found = searchResults("{\"search\": { \"image_url\": \"" + myDog.getImage_url() + "\"}}");
        assertThat(found).hasSize(8);
        // multiple queries
        found = searchResults(
                "{\"search\": { \"type\": \"" + myCat.getType() + "\", \"name\": \"" + myCat.getName() + "\"}}");
        assertThat(found).hasSize(2);

        petRepository.deleteAll();
    } // whenSearchInput_thenSearchPets();

    @Test
    public void whenMissingId_thenDontUpdate() throws Exception {

        List<Pet> pets = TestUtils.newValidPetList();
        petRepository.saveAll(pets);

        String json = "{\"pets\": [{ \"name\": \"Ducky\"}]}";

        MvcResult result = this.mockMvc
                .perform(put("/pets").contentType(MediaType.APPLICATION_JSON).content(json.getBytes()))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertThat(content, containsString("ID required to update pet"));

        Response response = objectMapper.readValue(content, Response.class);
        assertNotNull(response);
        assertNotNull(response.getResponse());

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) response.getResponse().get("pets");
        assertNull(objects);

        petRepository.deleteAll();

    } // whenMissingId_thenDontUpdate();

    @Test
    public void whenNoChanges_thenDontUpdate() throws Exception {

        List<Pet> pets = TestUtils.newValidPetList();
        petRepository.saveAll(pets);
        Pet myCat = petRepository.save(TestUtils.newCat());

        String json = "{\"pets\": [{" + "\"id\": \"" + myCat.getId() + "\", " + "\"name\": \"" + myCat.getName()
                + "\", " + "\"type\": " + myCat.getType() + ", " + "\"age\": " + myCat.getAge() + ", " + "\"sex\": "
                + myCat.getSex() + ", " + "\"description\": \"" + myCat.getDescription() + "\", "
                + "\"owner_email\": \"" + myCat.getOwner_email() + "\", " + "\"image_url\": \"" + myCat.getImage_url()
                + "\"}]}";

        MvcResult result = this.mockMvc
                .perform(put("/pets").contentType(MediaType.APPLICATION_JSON).content(json.getBytes()))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertThat(content, containsString("No changes made to pet, no update required"));

        Response response = objectMapper.readValue(content, Response.class);
        assertNotNull(response);
        assertNotNull(response.getResponse());

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) response.getResponse().get("pets");
        assertNull(objects);

        Optional<Pet> found = petRepository.findById(myCat.getId());
        assertNotNull(found);
        assertEquals(myCat, found.get());

        petRepository.deleteAll();

    } // whenNoChanges_thenDontUpdate();

    @Test
    public void whenInvalidField_thenDontUpdate() throws Exception {

        List<Pet> pets = TestUtils.newValidPetList();
        petRepository.saveAll(pets);
        Pet myCat = petRepository.save(TestUtils.newCat());

        String json = "{\"pets\": [{" + "\"id\": \"" + myCat.getId() + "\", " + "\"type\": 11 }]}";

        MvcResult result = this.mockMvc
                .perform(put("/pets").contentType(MediaType.APPLICATION_JSON).content(json.getBytes()))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertThat(content, containsString("Not a valid type"));

        Response response = objectMapper.readValue(content, Response.class);
        assertNotNull(response);
        assertNotNull(response.getResponse());

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) response.getResponse().get("pets");
        assertNull(objects);

        Optional<Pet> found = petRepository.findById(myCat.getId());
        assertNotNull(found);
        assertEquals(myCat, found.get());

        petRepository.deleteAll();

    } // whenInvalidField_thenDontUpdate();

    @Test
    public void whenValidFields_thenUpdate() throws Exception {

        List<Pet> pets = TestUtils.newValidPetList();
        petRepository.saveAll(pets);
        Pet myCat = petRepository.save(TestUtils.newCat());

        Pet updated = new Pet();
        updated.setId(myCat.getId());
        updated.setName("TEST");
        updated.setType(Pet.TYPE_DOG);
        updated.setAge(11);
        updated.setSex(Pet.SEX_F);
        updated.setDescription("THIS IS A TEST!");
        updated.setOwner_email("test@stephanieolfert.com");
        updated.setImage_url("/newImage.png");

        String json = "{\"pets\": [{" + "\"id\": \"" + updated.getId() + "\", " + "\"name\": \"" + updated.getName()
                + "\", " + "\"type\": " + updated.getType() + ", " + "\"age\": " + updated.getAge() + ", " + "\"sex\": "
                + updated.getSex() + ", " + "\"description\": \"" + updated.getDescription() + "\", "
                + "\"owner_email\": \"" + updated.getOwner_email() + "\", " + "\"image_url\": \""
                + updated.getImage_url() + "\"}]}";

        MvcResult result = this.mockMvc
                .perform(put("/pets").contentType(MediaType.APPLICATION_JSON).content(json.getBytes()))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Response response = objectMapper.readValue(result.getResponse().getContentAsString(), Response.class);
        assertNotNull(response);
        assertNotNull(response.getResponse());

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) response.getResponse().get("updatedPets");
        List<Pet> updatedPets = new ArrayList<Pet>();
        objects.forEach((object) -> {
            ObjectMapper mapper = new ObjectMapper();
            updatedPets.add(mapper.convertValue(object, Pet.class));
        });

        assertThat(updatedPets).hasSize(1);

        Optional<Pet> found = petRepository.findById(myCat.getId());
        assertNotNull(found);
        assertEquals(updated, found.get());

        petRepository.deleteAll();

    } // whenValidFields_thenUpdate();

    @Test
    public void whenInvalidId_thenDontDelete() throws Exception {

        Pet myCat = petRepository.save(TestUtils.newCat());

        List<Long> ids = new ArrayList<Long>();
        ids.add(myCat.getId() + 1);
        DeleteRequest delete = new DeleteRequest();
        delete.setIds(ids);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/pets").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(delete)))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertThat(content, containsString("Invalid id"));

        Response response = objectMapper.readValue(content, Response.class);
        assertNotNull(response);
        assertNotNull(response.getResponse());

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) response.getResponse().get("deletedIds");
        assertTrue(objects.isEmpty());

        Iterable<Pet> allPets = petRepository.findAll();
        assertThat(allPets).hasSize(1);

        petRepository.deleteAll();
    } // whenInvalidId_thenDontDelete();

    @Test
    public void whenValidId_thenDelete() throws Exception {

        Pet myCat = petRepository.save(TestUtils.newCat());

        List<Long> ids = new ArrayList<Long>();
        ids.add(myCat.getId());
        DeleteRequest delete = new DeleteRequest();
        delete.setIds(ids);

        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/pets").contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.toJson(delete)))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Response response = objectMapper.readValue(result.getResponse().getContentAsString(), Response.class);
        assertNotNull(response);
        assertNotNull(response.getResponse());

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) response.getResponse().get("deletedIds");
        assertFalse(objects.isEmpty());
        Long objectId = Long.parseLong(objects.get(0).toString());
        assertEquals(myCat.getId(), objectId);

        Iterable<Pet> allPets = petRepository.findAll();
        assertThat(allPets).hasSize(0);
    } // whenValidId_thenDelete();

    private List<Pet> searchResults(String json) throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/pets").contentType(MediaType.APPLICATION_JSON).content(json.getBytes()))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Response response = objectMapper.readValue(result.getResponse().getContentAsString(), Response.class);

        assertNotNull(response);
        assertNotNull(response.getResponse());

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) response.getResponse().get("pets");
        List<Pet> found = new ArrayList<Pet>();
        objects.forEach((object) -> {
            ObjectMapper mapper = new ObjectMapper();
            found.add(mapper.convertValue(object, Pet.class));
        });

        return found;
    }

    private void testFields(Pet expected, Pet actual) {
        assertNotNull(actual);

        assertNotNull(actual.getId());
        assertThat(actual.getId()).isPositive();

        assertNotNull(actual.getType());
        assertEquals(expected.getType(), actual.getType());

        assertNotNull(actual.getAge());
        assertEquals(expected.getAge(), actual.getAge());

        assertNotNull(actual.getSex());
        assertEquals(expected.getSex(), actual.getSex());

        assertNotNull(actual.getDescription());
        assertEquals(expected.getDescription(), actual.getDescription());

        assertNotNull(actual.getOwner_email());
        assertEquals(expected.getOwner_email(), actual.getOwner_email());

        assertNotNull(actual.getImage_url());
        assertEquals(expected.getImage_url(), actual.getImage_url());

    }
}