package com.example.app.controller;

import com.example.app.config.RestDocsTestControllerSupport;
import com.example.app.hateoas.assembler.ActorDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.ActorRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmRepresentationModelAssembler;
import com.example.app.model.constant.Category;
import com.example.app.model.constant.FilmRating;
import com.example.app.model.constant.Language;
import com.example.app.model.constant.SpecialFeature;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.ActorDetailModel;
import com.example.app.model.internal.extra.FilmDetailModel;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.service.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ActorController.class)
class ActorControllerTest extends RestDocsTestControllerSupport {
    @MockBean
    private ActorService actorService;
    @SpyBean
    private ActorRepresentationModelAssembler actorAssembler;
    @SpyBean
    private ActorDetailRepresentationModelAssembler actorDetailAssembler;
    @SpyBean
    private FilmRepresentationModelAssembler filmAssembler;
    @SpyBean
    private FilmDetailRepresentationModelAssembler filmDetailAssembler;

    @Nested
    class ActorListTests {
        private ActorModel guiness;
        private ActorModel walhberg;

        @BeforeEach
        void setUp() {
            guiness = ActorModel.builder()
                    .actorId(1)
                    .firstName("PENELOPE")
                    .lastName("GUINESS")
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
            walhberg = ActorModel.builder()
                    .actorId(2)
                    .firstName("NICK")
                    .lastName("WAHLBERG")
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
        }

        @Test
        void getActors_success() throws Exception {
            // arrange
            when(actorService.getActors()).thenReturn(List.of(guiness, walhberg));

            // act
            mockMvc.perform(get("/actors")
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(restDocsHandler.document(
                            responseFields(
                                    fieldWithPath("_embedded.actors[].actorId").description("Id of the actor"),
                                    fieldWithPath("_embedded.actors[].firstName").description("First name of the actor"),
                                    fieldWithPath("_embedded.actors[].lastName").description("Last name of the actor"),
                                    fieldWithPath("_embedded.actors[].lastUpdate").description("Last update of the actor"),
                                    fieldWithPath("_embedded.actors[]._links.self.href").description("Link to the actor's own resource"),
                                    fieldWithPath("_embedded.actors[]._links.self.title").ignored(),
                                    fieldWithPath("_embedded.actors[]._links.self.type").description("HTTP method of the link"),
                                    fieldWithPath("_embedded.actors[]._links.update.href").description("Link to update the actor"),
                                    fieldWithPath("_embedded.actors[]._links.update.title").ignored(),
                                    fieldWithPath("_embedded.actors[]._links.update.type").description("HTTP method of the link"),
                                    fieldWithPath("_embedded.actors[]._links.delete.href").description("Link to delete the actor"),
                                    fieldWithPath("_embedded.actors[]._links.delete.title").ignored(),
                                    fieldWithPath("_embedded.actors[]._links.delete.type").description("HTTP method of the link"),
                                    fieldWithPath("_embedded.actors[]._links.details.href").description("Link to details of the actor"),
                                    fieldWithPath("_embedded.actors[]._links.details.title").ignored(),
                                    fieldWithPath("_embedded.actors[]._links.details.type").description("HTTP method of the link"),
                                    fieldWithPath("_embedded.actors[]._links.add.href").description("Link to add an actor"),
                                    fieldWithPath("_embedded.actors[]._links.add.title").ignored(),
                                    fieldWithPath("_embedded.actors[]._links.add.type").description("HTTP method of the link"),
                                    fieldWithPath("_embedded.actors[]._links.actors.href").description("Link to the list of actors"),
                                    fieldWithPath("_embedded.actors[]._links.actors.title").ignored(),
                                    fieldWithPath("_embedded.actors[]._links.actors.type").description("HTTP method of the link"),
                                    fieldWithPath("_links.self.href").description("Self link of the actors"),
                                    fieldWithPath("_links.self.title").ignored(),
                                    fieldWithPath("_links.self.type").description("HTTP method of the link")
                            )));

            // assert
            verify(actorService, times(1)).getActors();
        }

        @Test
        void getActors_trailingSlash() throws Exception {
            // arrange
            when(actorService.getActors()).thenReturn(List.of(guiness, walhberg));

            // act
            mockMvc.perform(get("/actors/")
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            // assert
            verify(actorService, times(0)).getActors();
        }

        @Test
        void getActor_success() throws Exception {
            // arrange
            var actorId = "1";
            when(actorService.getActor(actorId)).thenReturn(Optional.of(guiness));

            // act
            mockMvc.perform(get("/actors/{actorId}", actorId)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("actorId").value(1))
                    .andExpect(jsonPath("firstName").value("PENELOPE"))
                    .andExpect(jsonPath("lastName").value("GUINESS"))
                    .andExpect(jsonPath("lastUpdate").value("2006-02-15T09:34:33"))
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("Id of the actor")),
                            responseFields(
                                    fieldWithPath("actorId").description("Id of the actor"),
                                    fieldWithPath("firstName").description("First name of the actor"),
                                    fieldWithPath("lastName").description("Last name of the actor"),
                                    fieldWithPath("lastUpdate").description("Last update of the actor"),
                                    fieldWithPath("_links.self.href").description("Link to the actor's own resource"),
                                    fieldWithPath("_links.self.title").ignored(),
                                    fieldWithPath("_links.self.type").description("HTTP method of the link"),
                                    fieldWithPath("_links.update.href").description("Link to update the actor"),
                                    fieldWithPath("_links.update.title").ignored(),
                                    fieldWithPath("_links.update.type").description("HTTP method of the link"),
                                    fieldWithPath("_links.delete.href").description("Link to delete the actor"),
                                    fieldWithPath("_links.delete.title").ignored(),
                                    fieldWithPath("_links.delete.type").description("HTTP method of the link"),
                                    fieldWithPath("_links.details.href").description("Link to details of the actor"),
                                    fieldWithPath("_links.details.title").ignored(),
                                    fieldWithPath("_links.details.type").description("HTTP method of the link"),
                                    fieldWithPath("_links.add.href").description("Link to add an actor"),
                                    fieldWithPath("_links.add.title").ignored(),
                                    fieldWithPath("_links.add.type").description("HTTP method of the link"),
                                    fieldWithPath("_links.actors.href").description("Link to the list of actors"),
                                    fieldWithPath("_links.actors.title").ignored(),
                                    fieldWithPath("_links.actors.type").description("HTTP method of the link")
                            )
                    ));

            // assert
            verify(actorService, times(1)).getActor(actorId);
        }
    }

    @Nested
    class ActorUpdateTests {
        private String actorId;
        private HashMap<String, String> payload;
        private ActorRequestModel requestModel;
        private ActorModel newActor;

        @BeforeEach
        void setUp() {
            actorId = "1";
            payload = new HashMap<String, String>();
            payload.put("firstName", "HELLO");
            payload.put("lastName", "WORLD");
            requestModel = new ActorRequestModel(
                    payload.get("firstName"),
                    payload.get("lastName")
            );
            newActor = ActorModel.builder()
                    .actorId(Integer.valueOf(actorId))
                    .firstName(payload.get("firstName"))
                    .lastName(payload.get("lastName"))
                    .lastUpdate(LocalDateTime.now())
                    .build();
        }

        @Test
        void addActor_success() throws Exception {
            // arrange
            when(actorService.addActor(requestModel)).thenReturn(newActor);

            // act
            mockMvc.perform(post("/actors")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(payload))
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", serverUrl + "/actors/" + actorId))
                    .andDo(restDocsHandler.document(
                            responseHeaders(
                                    headerWithName("Location").description("The location of the new actor")
                            ),
                            requestFields(
                                    fieldWithPath("firstName").description("The first name of a new actor"),
                                    fieldWithPath("lastName").description("The last name of a new actor")
                            )
                    ));

            // assert
            verify(actorService, times(1)).addActor(requestModel);
            verify(actorService, times(1)).addActor(any(ActorRequestModel.class));
        }

        @Test
        void updateActor_success() throws Exception {
            // arrange
            when(actorService.updateActor(actorId, requestModel)).thenReturn(newActor);

            // act
            mockMvc.perform(put("/actors/{actorId}", actorId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(payload))
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("The id of the actor to update")),
                            requestFields(
                                    fieldWithPath("firstName").description("The new first name of the actor"),
                                    fieldWithPath("lastName").description("The new last name of the actor")
                            )
                    ));

            // assert
            verify(actorService, times(1)).updateActor(actorId, requestModel);
            verify(actorService, times(1)).updateActor(anyString(), any(ActorRequestModel.class));
        }

        @Test
        void deleteActor_success() throws Exception {
            // arrange
            doNothing().when(actorService).deleteActor(actorId);

            // act
            mockMvc.perform(delete("/actors/{actorId}", actorId)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("The id of the actor to delete")
                            )
                    ));

            // assert
            verify(actorService, times(1)).deleteActor(actorId);
        }
    }

    @Nested
    class ActorDetailTests {
        @Test
        void getActorDetail_success() throws Exception {
            // arrange
            var actorId = "1";
            var model = ActorDetailModel.builder()
                    .actorId(Integer.valueOf(actorId))
                    .firstName("PENELOPE")
                    .lastName("GUINESS")
                    .filmInfo("ACADEMY DINOSAUR, ANACONDA CONFESSIONS, ANGELS LIFE, BULWORTH COMMANDMENTS, " +
                            "CHEAPER CLYDE, COLOR PHILADELPHIA, ELEPHANT TROJAN, GLEAMING JAWBREAKER, HUMAN GRAFFITI, " +
                            "KING EVOLUTION, LADY STAGE, LANGUAGE COWBOY, MULHOLLAND BEAST, OKLAHOMA JUMANJI, " +
                            "RULES HUMAN, SPLASH GUMP, VERTIGO NORTHWEST, WESTWARD SEABISCUIT, WIZARD COLDBLOODED")
                    .build();
            when(actorService.getActorDetail(actorId)).thenReturn(Optional.of(model));

            // act
            mockMvc.perform(get("/actors/{actorId}/details", actorId)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("actorId").value(1))
                    .andExpect(jsonPath("firstName").value("PENELOPE"))
                    .andExpect(jsonPath("lastName").value("GUINESS"))
                    .andExpect(jsonPath("filmInfo").value("ACADEMY DINOSAUR, ANACONDA CONFESSIONS, ANGELS LIFE, BULWORTH COMMANDMENTS, " +
                            "CHEAPER CLYDE, COLOR PHILADELPHIA, ELEPHANT TROJAN, GLEAMING JAWBREAKER, HUMAN GRAFFITI, " +
                            "KING EVOLUTION, LADY STAGE, LANGUAGE COWBOY, MULHOLLAND BEAST, OKLAHOMA JUMANJI, " +
                            "RULES HUMAN, SPLASH GUMP, VERTIGO NORTHWEST, WESTWARD SEABISCUIT, WIZARD COLDBLOODED"))
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("Id of the actor to get details")),
                            responseFields(
                                    fieldWithPath("actorId").description("Id of the actor"),
                                    fieldWithPath("firstName").description("First name of the actor"),
                                    fieldWithPath("lastName").description("Last name of the actor"),
                                    fieldWithPath("filmInfo").description("List of films the actor has been in"),
                                    fieldWithPath("_links.self.href").description("Link to the actor's own resource"),
                                    fieldWithPath("_links.actor.href").description("Link to the actor's resource"),
                                    fieldWithPath("_links.actors.href").description("Link to the list of actors")
                            )
                    ));

            // assert
            verify(actorService, times(1)).getActorDetail(actorId);
        }
    }

    @Nested
    class ActorFilmTests {
        private FilmModel academyDinosaur;
        private FilmModel aceGoldfinger;

        @BeforeEach
        void setUp() {
            academyDinosaur = FilmModel.builder()
                    .filmId(1)
                    .title("ACADEMY DINOSAUR")
                    .description("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies")
                    .releaseYear(LocalDate.ofYearDay(2006, 1))
                    .languageId(Language.ENGLISH)
                    .originalLanguageId(Language.NONE)
                    .rentalDuration(6)
                    .rentalRate(BigDecimal.valueOf(0.99))
                    .length(86)
                    .replacementCost(BigDecimal.valueOf(20.99))
                    .rating(FilmRating.PG)
                    .specialFeatures(EnumSet.of(SpecialFeature.BEHIND_THE_SCENES, SpecialFeature.DELETED_SCENES))
                    .lastUpdate(LocalDateTime.now())
                    .build();
            aceGoldfinger = FilmModel.builder()
                    .filmId(2)
                    .title("ACE GOLDFINGER")
                    .description("A Astounding Epistle of a Database Administrator And a Explorer who must Find a Car in Ancient China")
                    .releaseYear(LocalDate.ofYearDay(2006, 1))
                    .languageId(Language.ENGLISH)
                    .originalLanguageId(Language.NONE)
                    .rentalDuration(3)
                    .rentalRate(BigDecimal.valueOf(4.99))
                    .length(48)
                    .replacementCost(BigDecimal.valueOf(12.99))
                    .rating(FilmRating.G)
                    .specialFeatures(EnumSet.of(SpecialFeature.BEHIND_THE_SCENES, SpecialFeature.DELETED_SCENES))
                    .lastUpdate(LocalDateTime.now())
                    .build();
        }

        @Test
        void getActorFilms_success() throws Exception {
            // arrange
            var actorId = "1";
            when(actorService.getActorFilms(actorId)).thenReturn(List.of(academyDinosaur, aceGoldfinger));

            // act
            mockMvc.perform(get("/actors/{actorId}/films", actorId)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("_embedded.films[0].filmId").value(1))
                    .andExpect(jsonPath("_embedded.films[0].title").value("ACADEMY DINOSAUR"))
                    .andExpect(jsonPath("_embedded.films[0].description").value("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"))
                    .andExpect(jsonPath("_embedded.films[0].releaseYear").value("2006-01-01"))
                    .andExpect(jsonPath("_embedded.films[0].languageId").value("English"))
                    .andExpect(jsonPath("_embedded.films[0].originalLanguageId").value("-"))
                    .andExpect(jsonPath("_embedded.films[0].rentalDuration").value(6))
                    .andExpect(jsonPath("_embedded.films[0].rentalRate").value(0.99))
                    .andExpect(jsonPath("_embedded.films[0].length").value(86))
                    .andExpect(jsonPath("_embedded.films[0].replacementCost").value(20.99))
                    .andExpect(jsonPath("_embedded.films[0].rating").value("PG"))
                    .andExpect(jsonPath("_embedded.films[0].specialFeatures").value(containsInAnyOrder("Behind the Scenes", "Deleted Scenes")))
                    .andExpect(jsonPath("_embedded.films[1].filmId").value(2))
                    .andExpect(jsonPath("_embedded.films[1].title").value("ACE GOLDFINGER"))
                    .andExpect(jsonPath("_embedded.films[1].description").value("A Astounding Epistle of a Database Administrator And a Explorer who must Find a Car in Ancient China"))
                    .andExpect(jsonPath("_embedded.films[1].releaseYear").value("2006-01-01"))
                    .andExpect(jsonPath("_embedded.films[1].languageId").value("English"))
                    .andExpect(jsonPath("_embedded.films[1].originalLanguageId").value("-"))
                    .andExpect(jsonPath("_embedded.films[1].rentalDuration").value(3))
                    .andExpect(jsonPath("_embedded.films[1].rentalRate").value(4.99))
                    .andExpect(jsonPath("_embedded.films[1].length").value(48))
                    .andExpect(jsonPath("_embedded.films[1].replacementCost").value(12.99))
                    .andExpect(jsonPath("_embedded.films[1].rating").value("G"))
                    .andExpect(jsonPath("_embedded.films[1].specialFeatures").value(containsInAnyOrder("Behind the Scenes", "Deleted Scenes")))
                    .andExpect(jsonPath("_links.self.href").value(serverUrl + "/actors/" + actorId + "/films"))
                    .andExpect(jsonPath("_links.actor.href").value(serverUrl + "/actors/" + actorId))
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("Id of the actor")
                            ),
                            responseFields(
                                    fieldWithPath("_embedded.films[].filmId").description("Id of the film"),
                                    fieldWithPath("_embedded.films[].title").description("Title of the film"),
                                    fieldWithPath("_embedded.films[].description").description("Description of the film"),
                                    fieldWithPath("_embedded.films[].releaseYear").description("Release year of the film"),
                                    fieldWithPath("_embedded.films[].languageId").description("Language of the film"),
                                    fieldWithPath("_embedded.films[].originalLanguageId").description("Original language of the film"),
                                    fieldWithPath("_embedded.films[].rentalDuration").description("Rental duration of the film"),
                                    fieldWithPath("_embedded.films[].rentalRate").description("Rental rate of the film"),
                                    fieldWithPath("_embedded.films[].length").description("Length of the film"),
                                    fieldWithPath("_embedded.films[].replacementCost").description("Replacement cost of the film"),
                                    fieldWithPath("_embedded.films[].rating").description("Rating of the film"),
                                    fieldWithPath("_embedded.films[].specialFeatures").description("Special features of the film"),
                                    fieldWithPath("_embedded.films[].lastUpdate").description("Last update of the film"),
                                    fieldWithPath("_embedded.films[]._links.self.href").description("Link to the film"),
                                    fieldWithPath("_embedded.films[]._links.films.href").description("Link to films"),
                                    fieldWithPath("_links.self.href").description("Link to self"),
                                    fieldWithPath("_links.actor.href").description("Link to the actor")
                            )
                    ));

            // assert
            verify(actorService, times(1)).getActorFilms(actorId);
        }

        @Test
        void getActorFilm_success() throws Exception {
            // arrange
            var actorId = "1";
            var filmId = "1";
            when(actorService.getActorFilm(actorId, filmId)).thenReturn(Optional.ofNullable(academyDinosaur));

            // act
            mockMvc.perform(get("/actors/{actorId}/films/{filmId}", actorId, filmId)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("filmId").value(1))
                    .andExpect(jsonPath("title").value("ACADEMY DINOSAUR"))
                    .andExpect(jsonPath("description").value("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"))
                    .andExpect(jsonPath("releaseYear").value("2006-01-01"))
                    .andExpect(jsonPath("languageId").value("English"))
                    .andExpect(jsonPath("originalLanguageId").value("-"))
                    .andExpect(jsonPath("rentalDuration").value(6))
                    .andExpect(jsonPath("rentalRate").value(0.99))
                    .andExpect(jsonPath("length").value(86))
                    .andExpect(jsonPath("replacementCost").value(20.99))
                    .andExpect(jsonPath("rating").value("PG"))
                    .andExpect(jsonPath("specialFeatures").value(containsInAnyOrder("Behind the Scenes", "Deleted Scenes")))
                    .andExpect(jsonPath("_links.self.href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId))
                    .andExpect(jsonPath("_links.actor.href").value(serverUrl + "/actors/" + actorId))
                    .andExpect(jsonPath("_links.films.href").value(serverUrl + "/actors/" + actorId + "/films"))
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("Id of the actor"),
                                    parameterWithName("filmId").description("Id of the film")
                            ),
                            responseFields(
                                    fieldWithPath("filmId").description("Id of the film"),
                                    fieldWithPath("title").description("Title of the film"),
                                    fieldWithPath("description").description("Description of the film"),
                                    fieldWithPath("releaseYear").description("Release year of the film"),
                                    fieldWithPath("languageId").description("Language of the film"),
                                    fieldWithPath("originalLanguageId").description("Original language of the film"),
                                    fieldWithPath("rentalDuration").description("Rental duration of the film"),
                                    fieldWithPath("rentalRate").description("Rental rate of the film"),
                                    fieldWithPath("length").description("Length of the film"),
                                    fieldWithPath("replacementCost").description("Replacement cost of the film"),
                                    fieldWithPath("rating").description("Rating of the film"),
                                    fieldWithPath("specialFeatures").description("Special features of the film"),
                                    fieldWithPath("lastUpdate").description("Last update of the film"),
                                    fieldWithPath("_links.self.href").description("Link to self"),
                                    fieldWithPath("_links.films.href").description("Link to films of the actor"),
                                    fieldWithPath("_links.actor.href").description("Link to actor")
                            )
                    ));

            // assert
            verify(actorService, times(1)).getActorFilm(actorId, filmId);
        }

        @Test
        void addActorFilm_success() throws Exception {
            // arrange
            var actorId = "1";
            var filmId = "1";
            var payload = new HashMap<String, String>();
            payload.put("filmId", filmId);
            when(actorService.addActorFilm(actorId, filmId)).thenReturn(academyDinosaur);

            // act
            mockMvc.perform(post("/actors/{actorId}/films", actorId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(payload))
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", serverUrl + "/actors/" + actorId + "/films/" + filmId))
                    .andDo(restDocsHandler.document(
                            responseHeaders(
                                    headerWithName("Location").description("Link to the film")
                            ),
                            pathParameters(
                                    parameterWithName("actorId").description("Id of the actor")
                            ),
                            requestFields(
                                    fieldWithPath("filmId").description("Id of the film")
                            )
                    ));

            // assert
            verify(actorService, times(1)).addActorFilm(actorId, filmId);
        }

        @Test
        void deleteActorFilm_success() throws Exception {
            // arrange
            var actorId = "1";
            var filmId = "1";

            // act
            mockMvc.perform(delete("/actors/{actorId}/films/{filmId}", actorId, filmId)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("Id of the actor"),
                                    parameterWithName("filmId").description("Id of the film")
                            )
                    ));

            // assert
            verify(actorService, times(1)).removeActorFilm(actorId, filmId);
        }
    }

    @Nested
    class ActorFilmDetailTests {
        @Test
        void getActorFilmDetail_success() throws Exception {
            // arrange
            var actorId = "1";
            var filmId = "1";
            var model = Optional.of(FilmDetailModel.builder()
                    .filmId(1)
                    .title("ACADEMY DINOSAUR")
                    .description("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies")
                    .category(Category.DOCUMENTARY)
                    .price(BigDecimal.valueOf(0.99))
                    .length(86)
                    .rating(FilmRating.PG)
                    .actors("PENELOPE GUINESS")
                    .build());
            when(actorService.getActorFilmDetail(actorId, filmId)).thenReturn(model);

            // act
            mockMvc.perform(get("/actors/{actorId}/films/{filmId}/details", actorId, filmId)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("filmId").value(1))
                    .andExpect(jsonPath("title").value("ACADEMY DINOSAUR"))
                    .andExpect(jsonPath("description").value("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"))
                    .andExpect(jsonPath("category").value("Documentary"))
                    .andExpect(jsonPath("price").value(0.99))
                    .andExpect(jsonPath("length").value(86))
                    .andExpect(jsonPath("rating").value("PG"))
                    .andExpect(jsonPath("actors").value("PENELOPE GUINESS"))
                    .andExpect(jsonPath("_links.self.href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId + "/details"))
                    .andExpect(jsonPath("_links.film.href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId))
                    .andExpect(jsonPath("_links.films.href").value(serverUrl + "/actors/" + actorId + "/films"))
                    .andExpect(jsonPath("_links.actor.href").value(serverUrl + "/actors/" + actorId))
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("actorId").description("Id of the actor"),
                                    parameterWithName("filmId").description("Id of the film")
                            ),
                            responseFields(
                                    fieldWithPath("filmId").description("Id of the film"),
                                    fieldWithPath("title").description("Title of the film"),
                                    fieldWithPath("description").description("Description of the film"),
                                    fieldWithPath("category").description("Category of the film"),
                                    fieldWithPath("price").description("Price of the film"),
                                    fieldWithPath("length").description("Length of the film"),
                                    fieldWithPath("rating").description("Rating of the film"),
                                    fieldWithPath("actors").description("Actors of the film"),
                                    fieldWithPath("_links.self.href").description("Link to self"),
                                    fieldWithPath("_links.film.href").description("Link to film"),
                                    fieldWithPath("_links.films.href").description("Link to films of the actor"),
                                    fieldWithPath("_links.actor.href").description("Link to actor")
                            )
                    ));

            // assert
            verify(actorService, times(1)).getActorFilmDetail(actorId, filmId);
        }
    }
}
