package com.example.app.controller;

import com.example.app.config.RestDocsTestControllerSupport;
import com.example.app.hateoas.assembler.ActorDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.ActorRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmRepresentationModelAssembler;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.extra.ActorDetailModel;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.service.ActorService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Description;
import org.springframework.hateoas.MediaTypes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    @SpyBean
    private FilmRepresentationModelAssembler filmAssembler;
    @SpyBean
    private FilmDetailRepresentationModelAssembler filmDetailAssembler;

    @Nested
    @Description("GET /actors")
    class GetActorsTests {
        @Test
        void getActors_success() throws Exception {
            // arrange
            when(actorService.getActors()).thenReturn(
                    List.of(
                            ActorModel.builder()
                                    .actorId(1)
                                    .firstName("PENELOPE")
                                    .lastName("GUINESS")
                                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                                    .build(),
                            ActorModel.builder()
                                    .actorId(2)
                                    .firstName("NICK")
                                    .lastName("WAHLBERG")
                                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                                    .build()
                    )
            );

            // act
            mockMvc.perform(get("/actors")
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andDo(restDocsHandler.document(
                            responseFields(
                                    fieldWithPath("_embedded.actors[].actorId").description("The id of the actor"),
                                    fieldWithPath("_embedded.actors[].firstName").description("The first name of the actor"),
                                    fieldWithPath("_embedded.actors[].lastName").description("The last name of the actor"),
                                    fieldWithPath("_embedded.actors[].lastUpdate").description("The last update of the actor"),
                                    fieldWithPath("_embedded.actors[]._links.self.href").description("Link to the actor's own resource"),
                                    fieldWithPath("_embedded.actors[]._links.actorDetails.href").description("Link to details of the actor"),
                                    fieldWithPath("_embedded.actors[]._links.actors.href").description("Link to the list of actors"),
                                    fieldWithPath("_links.self.href").description("Actors self link")
                            )
                    ));

            // assert
            verify(actorService, times(1)).getActors();
        }
    }

    @Nested
    @Description("POST /actors")
    class AddActorTests {
        @Test
        void addActor_success() throws Exception {
            // arrange
            var actorId = "1";
            var payload = new HashMap<String, String>();
            payload.put("firstName", "HELLO");
            payload.put("lastName", "WORLD");
            var requestModel = new ActorRequestModel(
                    payload.get("firstName"),
                    payload.get("lastName")
            );
            when(actorService.addActor(requestModel)).thenReturn(
                    ActorModel.builder()
                            .actorId(Integer.valueOf(actorId))
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
    }

    @Nested
    @Description("GET /actors/{actorId}")
    class GetActorTests {
        @Test
        void getActor_success() throws Exception {
            // arrange
            var actorId = "1";
            var model = ActorModel.builder()
                    .actorId(Integer.valueOf(actorId))
                    .firstName("PENELOPE")
                    .lastName("GUINESS")
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
            when(actorService.getActor(actorId)).thenReturn(Optional.of(model));

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
                                    parameterWithName("actorId").description("The id of the actor")),
                            responseFields(
                                    fieldWithPath("actorId").description("The id of the actor"),
                                    fieldWithPath("firstName").description("The first name of the actor"),
                                    fieldWithPath("lastName").description("The last name of the actor"),
                                    fieldWithPath("lastUpdate").description("The last update of the actor"),
                                    fieldWithPath("_links.self.href").description("Link to the actor's own resource"),
                                    fieldWithPath("_links.actorDetails.href").description("Link to details of the actor"),
                                    fieldWithPath("_links.actors.href").description("Link to the list of actors")
                            )));

            // assert
            verify(actorService, times(1)).getActor(actorId);
        }
    }

    @Nested
    @Description("PUT /actors/{actorId}")
    class UpdateActorTests {
        @Test
        void updateActor_success() throws Exception {
            // arrange
            var actorId = "1";
            var payload = new HashMap<String, String>();
            payload.put("firstName", "HELLO");
            payload.put("lastName", "WORLD");
            var requestModel = new ActorRequestModel(
                    payload.get("firstName"),
                    payload.get("lastName")
            );
            when(actorService.updateActor(actorId, requestModel)).thenReturn(
                    ActorModel.builder()
                            .actorId(Integer.valueOf(actorId))
                            .firstName(payload.get("firstName"))
                            .lastName(payload.get("lastName"))
                            .lastUpdate(LocalDateTime.now())
                            .build());

            // act
            mockMvc.perform(put("/actors/{actorId}", actorId)
                            .contentType(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(payload)))
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
    }

    @Nested
    @Description("DELETE /actors/{actorId}")
    class DeleteActorTests {
        @Test
        void deleteActor_success() throws Exception {
            // arrange
            var actorId = "1";
            doNothing().when(actorService).deleteActor(actorId);

            // act
            mockMvc.perform(delete("/actors/{actorId}", actorId))
                    .andExpect(status().isNoContent())
                    .andDo(print())
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
    @Description("GET /actors/{actorId}/details")
    class GetActorDetailTests {
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
                                    parameterWithName("actorId").description("The id of the actor")),
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
            verify(actorService, times(1)).getActorDetail(actorId);
        }
    }
}
