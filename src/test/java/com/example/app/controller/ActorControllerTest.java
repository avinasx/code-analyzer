package com.example.app.controller;

import com.example.app.config.RestDocsTestControllerSupport;
import com.example.app.hateoas.assembler.ActorDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.ActorRepresentationModelAssembler;
import com.example.app.model.internal.ActorDetailModel;
import com.example.app.model.internal.ActorModel;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.service.ActorService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.hateoas.MediaTypes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

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

    @Test
    void getAllActors() {
    }

    @Test
    void findActorByName() {
    }

    @Nested
    class AddActorTests {
        @Test
        void addActor_success() throws Exception {
            // arrange
            var id = "1";
            var payload = new HashMap<String, String>();
            payload.put("firstName", "HELLO");
            payload.put("lastName", "WORLD");
            var requestModel = new ActorRequestModel(
                    payload.get("firstName"),
                    payload.get("lastName")
            );
            when(actorService.addActor(requestModel))
                    .thenReturn(ActorModel.builder()
                            .actorId(Integer.valueOf(id))
                            .firstName(payload.get("firstName"))
                            .lastName(payload.get("lastName"))
                            .lastUpdate(LocalDateTime.now())
                            .build());

            // act
            mockMvc.perform(post("/actors")
                            .contentType(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(payload)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", serverUrl + "/actors/" + id))
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
    }

    @Nested
    class GetActorTests {
        @Test
        void getActor_success() throws Exception {
            // arrange
            var id = "1";
            var model = ActorModel.builder()
                    .actorId(Integer.valueOf(id))
                    .firstName("PENELOPE")
                    .lastName("GUINESS")
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
            when(actorService.getActorById(id)).thenReturn(Optional.of(model));

            // act
            mockMvc.perform(get("/actors/{id}", id)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("actorId").value(1))
                    .andExpect(jsonPath("firstName").value("PENELOPE"))
                    .andExpect(jsonPath("lastName").value("GUINESS"))
                    .andExpect(jsonPath("lastUpdate").value("2006-02-15T09:34:33"))
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("id").description("The id of the actor")),
                            responseFields(
                                    fieldWithPath("actorId").description("The id of the actor"),
                                    fieldWithPath("firstName").description("The first name of the actor"),
                                    fieldWithPath("lastName").description("The last name of the actor"),
                                    fieldWithPath("lastUpdate").description("The last update of the actor"),
                                    fieldWithPath("_links.self.href").description("Link to the actor's own resource"),
                                    fieldWithPath("_links.actors.href").description("Link to the list of actors")
                            )));

            // assert
            verify(actorService, times(1)).getActorById(id);
        }
    }

    @Nested
    class UpdateActorTests {
        @Test
        void updateActor_success() throws Exception {
            // arrange
            var id = "1";
            var payload = new HashMap<String, String>();
            payload.put("firstName", "HELLO");
            payload.put("lastName", "WORLD");
            var requestModel = new ActorRequestModel(
                    payload.get("firstName"),
                    payload.get("lastName")
            );
            when(actorService.updateActor(id, requestModel)).thenReturn(
                    ActorModel.builder()
                            .actorId(Integer.valueOf(id))
                            .firstName(payload.get("firstName"))
                            .lastName(payload.get("lastName"))
                            .lastUpdate(LocalDateTime.now())
                            .build());

            // act
            mockMvc.perform(put("/actors/{id}", id)
                            .contentType(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(payload)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("id").description("The id of the actor to update")),
                            requestFields(
                                    fieldWithPath("firstName").description("The new first name of the actor"),
                                    fieldWithPath("lastName").description("The new last name of the actor")
                            )
                    ));

            // assert
            verify(actorService, times(1)).updateActor(id, requestModel);
            verify(actorService, times(1)).updateActor(anyString(), any(ActorRequestModel.class));
        }
    }

    @Nested
    class DeleteActorTests {
        @Test
        void deleteActor_success() throws Exception {
            // arrange
            var id = "1";
            doNothing().when(actorService).deleteActorById(id);

            // act
            mockMvc.perform(delete("/actors/{id}", id))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andDo(restDocsHandler.document(
                            pathParameters(
                                    parameterWithName("id").description("The id of the actor to delete")
                            )
                    ));

            // assert
            verify(actorService, times(1)).deleteActorById(id);
        }
    }

    @Nested
    class GetActorDetailTests {
        @Test
        void getActorDetail_success() throws Exception {
            // arrange
            var id = "1";
            var model = ActorDetailModel.builder()
                    .actorId(Integer.valueOf(id))
                    .firstName("PENELOPE")
                    .lastName("GUINESS")
                    .filmInfo("ACADEMY DINOSAUR, ANACONDA CONFESSIONS, ANGELS LIFE, BULWORTH COMMANDMENTS, " +
                            "CHEAPER CLYDE, COLOR PHILADELPHIA, ELEPHANT TROJAN, GLEAMING JAWBREAKER, HUMAN GRAFFITI, " +
                            "KING EVOLUTION, LADY STAGE, LANGUAGE COWBOY, MULHOLLAND BEAST, OKLAHOMA JUMANJI, " +
                            "RULES HUMAN, SPLASH GUMP, VERTIGO NORTHWEST, WESTWARD SEABISCUIT, WIZARD COLDBLOODED")
                    .build();
            when(actorService.getActorDetailById(id)).thenReturn(Optional.of(model));

            // act
            mockMvc.perform(get("/actors/{id}/details", id)
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
                                    parameterWithName("id").description("The id of the actor")),
                            responseFields(
                                    fieldWithPath("actorId").description("The id of the actor"),
                                    fieldWithPath("firstName").description("The first name of the actor"),
                                    fieldWithPath("lastName").description("The last name of the actor"),
                                    fieldWithPath("filmInfo").description("The film info of the actor"),
                                    fieldWithPath("_links.self.href").description("Link to the actor's own resource"),
                                    fieldWithPath("_links.actor.href").description("Link to the actor's resource"),
                                    fieldWithPath("_links.actors.href").description("Link to the list of actors")
                            )));

            // assert
            verify(actorService, times(1)).getActorDetailById(id);
        }
    }
}
