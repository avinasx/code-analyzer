package com.example.app.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.example.app.config.RestDocsControllerSupport;
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
import com.example.app.model.internal.core.FullName;
import com.example.app.model.internal.extra.ActorDetailModel;
import com.example.app.model.internal.extra.FilmDetailModel;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.service.ActorService;
import com.example.app.util.ConstrainedFieldDocumentation;
import com.example.app.util.OpenApiDescriptorTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;

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
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ActorController.class)
class ActorControllerTest extends RestDocsControllerSupport {
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
                    .fullName(FullName.builder().firstName("PENELOPE").lastName("GUINESS").build())
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
            walhberg = ActorModel.builder()
                    .actorId(2)
                    .fullName(FullName.builder().firstName("JENNIFER").lastName("WALHBERG").build())
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
        }

        @Test
        void getActors_success() throws Exception {
            // arrange
            when(actorService.getActors()).thenReturn(List.of(guiness, walhberg));

            // act
            var execute = mockMvc.perform(get("/actors")
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk());
            verify(actorService, times(1)).getActors();

            // docs
            var responseFieldList = List.of(
                    fieldWithPath("_embedded.actors[]." + ActorModel.Fields.actorId).description("Id of the actor"),
                    fieldWithPath("_embedded.actors[]." + FullName.Fields.firstName).description("First name of the actor"),
                    fieldWithPath("_embedded.actors[]." + FullName.Fields.lastName).description("Last name of the actor"),
                    fieldWithPath("_embedded.actors[]." + ActorModel.Fields.lastUpdate).description("Last update of the actor"),
                    subsectionWithPath("_embedded.actors[]._links").description("Links to actor resources"),
                    subsectionWithPath("_links").description("Links to other resources"));
            var linkList = List.of(
                    linkWithRel("self").description("Link to list of actors"));

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseFields(responseFieldList), links(linkList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").summary("Actor list").description("Get list of actor")
                            .responseSchema(Schema.schema("ActorList"))
                            .responseFields(responseFieldList).links(linkList)
                            .build())));
        }

        @Test
        void getActors_trailingSlash() throws Exception {
            // arrange
            when(actorService.getActors()).thenReturn(List.of(guiness, walhberg));

            // act
            var execute = mockMvc.perform(get("/actors/")
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isNotFound());
            verify(actorService, times(0)).getActors();
        }

        @Test
        void getActor_success() throws Exception {
            // arrange
            var actorId = "1";
            when(actorService.getActor(actorId)).thenReturn(Optional.of(guiness));

            // act
            var execute = mockMvc.perform(get("/actors/{actorId}", actorId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(ActorModel.Fields.actorId).value(1))
                    .andExpect(jsonPath(FullName.Fields.firstName).value("PENELOPE"))
                    .andExpect(jsonPath(FullName.Fields.lastName).value("GUINESS"))
                    .andExpect(jsonPath(ActorModel.Fields.lastUpdate).value("2006-02-15T09:34:33"));
            verify(actorService, times(1)).getActor(actorId);

            // docs
            var pathParameterList = List.of(
                    parameterWithName("actorId").description("Id of the actor"));
            var responseFieldList = List.of(
                    fieldWithPath(ActorModel.Fields.actorId).description("Id of the actor"),
                    fieldWithPath(FullName.Fields.firstName).description("First name of the actor"),
                    fieldWithPath(FullName.Fields.lastName).description("Last name of the actor"),
                    fieldWithPath(ActorModel.Fields.lastUpdate).description("Last update of the actor"),
                    subsectionWithPath("_links").description("Links to other resources"));
            var linkList = List.of(
                    linkWithRel("self").description("Self link of the actor"),
                    linkWithRel("update").description("Link to update the actor"),
                    linkWithRel("delete").description("Link to delete the actor"),
                    linkWithRel("details").description("Link to details of the actor"),
                    linkWithRel("add").description("Link to add an actor"),
                    linkWithRel("actors").description("Link to the list of actors"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").summary("Get actor").description("Get actor")
                            .responseSchema(Schema.schema("Actor"))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
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
                    .fullName(FullName.builder()
                            .firstName(payload.get("firstName"))
                            .lastName(payload.get("lastName"))
                            .build())
                    .lastUpdate(LocalDateTime.now())
                    .build();
        }

        @Test
        void addActor_success() throws Exception {
            // arrange
            when(actorService.addActor(requestModel)).thenReturn(newActor);

            // act
            var execute = mockMvc.perform(post("/actors")
                    .accept(MediaTypes.HAL_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)));

            // assert
            execute.andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().string(HttpHeaders.LOCATION, serverUrl + "/actors/" + actorId));
            verify(actorService, times(1)).addActor(requestModel);
            verify(actorService, times(1)).addActor(any(ActorRequestModel.class));

            // docs
            var responseHeaderList = List.of(
                    headerWithName(HttpHeaders.LOCATION).description("The location of the new actor"));
            var requestFieldList = List.of(
                    fieldWithPath(ActorRequestModel.Fields.firstName).description("First name of a new actor"),
                    fieldWithPath(ActorRequestModel.Fields.lastName).description("Last name of a new actor"));
            var openapiResponseHeaderList = OpenApiDescriptorTransformer.transformHeader(responseHeaderList);
            var fieldDescriptors = new ConstrainedFieldDocumentation(ActorRequestModel.class);
            var restdocsRequestFieldList = fieldDescriptors.restdocsFields(requestFieldList);
            var openapiRequestFieldList = fieldDescriptors.openapiFields(requestFieldList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseHeaders(responseHeaderList), requestFields(restdocsRequestFieldList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").summary("Add actor").description("Add actor")
                            .requestSchema(Schema.schema("Actor"))
                            .responseHeaders(openapiResponseHeaderList).requestFields(openapiRequestFieldList)
                            .build())));
        }

        @Test
        void updateActor_success() throws Exception {
            // arrange
            when(actorService.updateActor(actorId, requestModel)).thenReturn(newActor);

            // act
            var execute = mockMvc.perform(put("/actors/{actorId}", actorId)
                    .accept(MediaTypes.HAL_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk());
            verify(actorService, times(1)).updateActor(actorId, requestModel);
            verify(actorService, times(1)).updateActor(anyString(), any(ActorRequestModel.class));

            // docs
            var pathParameterList = List.of(
                    parameterWithName(ActorModel.Fields.actorId).description("Id of the actor to update"));
            var requestFieldList = List.of(
                    fieldWithPath(ActorRequestModel.Fields.firstName).description("New first name of the actor"),
                    fieldWithPath(ActorRequestModel.Fields.lastName).description("New last name of the actor"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);
            var fieldDescriptors = new ConstrainedFieldDocumentation(ActorRequestModel.class);
            var restdocsRequestFieldList = fieldDescriptors.restdocsFields(requestFieldList);
            var openapiRequestFieldList = fieldDescriptors.openapiFields(requestFieldList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), requestFields(restdocsRequestFieldList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").summary("Update actor").description("Update actor")
                            .requestSchema(Schema.schema("Actor"))
                            .pathParameters(openapiPathParameterList).requestFields(openapiRequestFieldList)
                            .build())));
        }

        @Test
        void deleteActor_success() throws Exception {
            // arrange
            doNothing().when(actorService).deleteActor(actorId);

            // act
            var execute = mockMvc.perform(delete("/actors/{actorId}", actorId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isNoContent());
            verify(actorService, times(1)).deleteActor(actorId);

            // docs
            var pathParameterList = List.of(
                    parameterWithName(ActorModel.Fields.actorId).description("The id of the actor to delete"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").summary("Delete actor").description("Delete actor")
                            .pathParameters(openapiPathParameterList)
                            .build())));
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
            var execute = mockMvc.perform(get("/actors/{actorId}/details", actorId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(ActorDetailModel.Fields.actorId).value(1))
                    .andExpect(jsonPath(ActorDetailModel.Fields.firstName).value("PENELOPE"))
                    .andExpect(jsonPath(ActorDetailModel.Fields.lastName).value("GUINESS"))
                    .andExpect(jsonPath(ActorDetailModel.Fields.filmInfo)
                            .value("ACADEMY DINOSAUR, ANACONDA CONFESSIONS, ANGELS LIFE, BULWORTH COMMANDMENTS, " +
                                    "CHEAPER CLYDE, COLOR PHILADELPHIA, ELEPHANT TROJAN, GLEAMING JAWBREAKER, HUMAN GRAFFITI, " +
                                    "KING EVOLUTION, LADY STAGE, LANGUAGE COWBOY, MULHOLLAND BEAST, OKLAHOMA JUMANJI, " +
                                    "RULES HUMAN, SPLASH GUMP, VERTIGO NORTHWEST, WESTWARD SEABISCUIT, WIZARD COLDBLOODED"));
            verify(actorService, times(1)).getActorDetail(actorId);

            // docs
            var pathParameterList = List.of(
                    parameterWithName(ActorModel.Fields.actorId).description("Id of the actor to get details"));
            var responseFieldList = List.of(
                    fieldWithPath(ActorDetailModel.Fields.actorId).description("Id of the actor"),
                    fieldWithPath(ActorDetailModel.Fields.firstName).description("First name of the actor"),
                    fieldWithPath(ActorDetailModel.Fields.lastName).description("Last name of the actor"),
                    fieldWithPath(ActorDetailModel.Fields.filmInfo).description("List of films the actor has been in"),
                    subsectionWithPath("_links").description("Links to other resources"));
            var linkList = List.of(
                    linkWithRel("self").description("Link to self"),
                    linkWithRel("actor").description("Link to the actor"),
                    linkWithRel("actors").description("Link to the list of actors"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").summary("Get details of actor").description("Get details of actor")
                            .responseSchema(Schema.schema("ActorDetailsList"))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
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
            var execute = mockMvc.perform(get("/actors/{actorId}/films", actorId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.filmId).value(1))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.title).value("ACADEMY DINOSAUR"))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.description)
                            .value("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.releaseYear).value("2006-01-01"))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.languageId).value("English"))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.originalLanguageId).value("-"))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.rentalDuration).value(6))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.rentalRate).value(0.99))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.length).value(86))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.replacementCost).value(20.99))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.rating).value("PG"))
                    .andExpect(jsonPath("_embedded.films[0]." + FilmModel.Fields.specialFeatures).value(containsInAnyOrder("Behind the Scenes", "Deleted Scenes")))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.filmId).value(2))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.title).value("ACE GOLDFINGER"))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.description)
                            .value("A Astounding Epistle of a Database Administrator And a Explorer who must Find a Car in Ancient China"))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.releaseYear).value("2006-01-01"))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.languageId).value("English"))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.originalLanguageId).value("-"))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.rentalDuration).value(3))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.rentalRate).value(4.99))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.length).value(48))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.replacementCost).value(12.99))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.rating).value("G"))
                    .andExpect(jsonPath("_embedded.films[1]." + FilmModel.Fields.specialFeatures).value(containsInAnyOrder("Behind the Scenes", "Deleted Scenes")))
                    .andExpect(jsonPath("_links.self.href").value(serverUrl + "/actors/" + actorId + "/films"))
                    .andExpect(jsonPath("_links.actor.href").value(serverUrl + "/actors/" + actorId));
            verify(actorService, times(1)).getActorFilms(actorId);

            // docs
            var pathParameterList = List.of(
                    parameterWithName("actorId").description("Id of the actor"));
            var responseFieldList = List.of(
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.filmId).description("Id of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.title).description("Title of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.description).description("Description of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.releaseYear).description("Release year of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.languageId).description("Language of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.originalLanguageId).description("Original language of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.rentalDuration).description("Rental duration of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.rentalRate).description("Rental rate of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.length).description("Length of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.replacementCost).description("Replacement cost of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.rating).description("Rating of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.specialFeatures).description("Special features of the film"),
                    fieldWithPath("_embedded.films[]." + FilmModel.Fields.lastUpdate).description("Last update of the film"),
                    subsectionWithPath("_embedded.films[]._links").description("Links to film resources"),
                    subsectionWithPath("_links").description("Links to other resources"));
            var linkList = List.of(
                    linkWithRel("self").description("Link to self"),
                    linkWithRel("actor").description("Link to the actor"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").tag("Film").summary("Film list of the actor")
                            .description("Get film list of the actor")
                            .responseSchema(Schema.schema("ActorFilmList"))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }

        @Test
        void getActorFilm_success() throws Exception {
            // arrange
            var actorId = "1";
            var filmId = "1";
            when(actorService.getActorFilm(actorId, filmId)).thenReturn(Optional.ofNullable(academyDinosaur));

            // act
            var execute = mockMvc.perform(get("/actors/{actorId}/films/{filmId}", actorId, filmId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(FilmModel.Fields.filmId).value(1))
                    .andExpect(jsonPath(FilmModel.Fields.title).value("ACADEMY DINOSAUR"))
                    .andExpect(jsonPath(FilmModel.Fields.description)
                            .value("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"))
                    .andExpect(jsonPath(FilmModel.Fields.releaseYear).value("2006-01-01"))
                    .andExpect(jsonPath(FilmModel.Fields.languageId).value("English"))
                    .andExpect(jsonPath(FilmModel.Fields.originalLanguageId).value("-"))
                    .andExpect(jsonPath(FilmModel.Fields.rentalDuration).value(6))
                    .andExpect(jsonPath(FilmModel.Fields.rentalRate).value(0.99))
                    .andExpect(jsonPath(FilmModel.Fields.length).value(86))
                    .andExpect(jsonPath(FilmModel.Fields.replacementCost).value(20.99))
                    .andExpect(jsonPath(FilmModel.Fields.rating).value("PG"))
                    .andExpect(jsonPath(FilmModel.Fields.specialFeatures).value(containsInAnyOrder("Behind the Scenes", "Deleted Scenes")))
                    .andExpect(jsonPath("_links.self.href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId))
                    .andExpect(jsonPath("_links.actor.href").value(serverUrl + "/actors/" + actorId))
                    .andExpect(jsonPath("_links.films.href").value(serverUrl + "/actors/" + actorId + "/films"));
            verify(actorService, times(1)).getActorFilm(actorId, filmId);

            // docs
            var pathParameterList = List.of(
                    parameterWithName(ActorModel.Fields.actorId).description("Id of the actor"),
                    parameterWithName(FilmModel.Fields.filmId).description("Id of the film"));
            var responseFieldList = List.of(
                    fieldWithPath(FilmModel.Fields.filmId).description("Id of the film"),
                    fieldWithPath(FilmModel.Fields.title).description("Title of the film"),
                    fieldWithPath(FilmModel.Fields.description).description("Description of the film"),
                    fieldWithPath(FilmModel.Fields.releaseYear).description("Release year of the film"),
                    fieldWithPath(FilmModel.Fields.languageId).description("Language of the film"),
                    fieldWithPath(FilmModel.Fields.originalLanguageId).description("Original language of the film"),
                    fieldWithPath(FilmModel.Fields.rentalDuration).description("Rental duration of the film"),
                    fieldWithPath(FilmModel.Fields.rentalRate).description("Rental rate of the film"),
                    fieldWithPath(FilmModel.Fields.length).description("Length of the film"),
                    fieldWithPath(FilmModel.Fields.replacementCost).description("Replacement cost of the film"),
                    fieldWithPath(FilmModel.Fields.rating).description("Rating of the film"),
                    fieldWithPath(FilmModel.Fields.specialFeatures).description("Special features of the film"),
                    fieldWithPath(FilmModel.Fields.lastUpdate).description("Last update of the film"),
                    subsectionWithPath("_links").description("Links to other resources"));
            var linkList = List.of(
                    linkWithRel("self").description("Link to self"),
                    linkWithRel("films").description("Link to films of the actor"),
                    linkWithRel("actor").description("Link to actor"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").tag("Film").summary("Film of the actor")
                            .description("Get film of the actor")
                            .responseSchema(Schema.schema("ActorFilm"))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
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
            var execute = mockMvc.perform(post("/actors/{actorId}/films", actorId)
                    .accept(MediaTypes.HAL_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)));
            // assert
            execute.andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(header().string(HttpHeaders.LOCATION, serverUrl + "/actors/" + actorId + "/films/" + filmId));

            verify(actorService, times(1)).addActorFilm(actorId, filmId);

            // docs
            var responseHeaderList = List.of(
                    headerWithName(HttpHeaders.LOCATION).description("Link to the film"));
            var pathParameterList = List.of(
                    parameterWithName(ActorModel.Fields.actorId).description("Id of the actor"));
            var requestFieldList = List.of(
                    fieldWithPath(FilmModel.Fields.filmId).description("Id of the film"));
            var openapiResponseHeaderList = OpenApiDescriptorTransformer.transformHeader(responseHeaderList);
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);
            var fieldDescriptors = new ConstrainedFieldDocumentation(ActorRequestModel.class);
            var restdocsRequestFieldList = fieldDescriptors.restdocsFields(requestFieldList);
            var openapiRequestFieldList = fieldDescriptors.openapiFields(requestFieldList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseHeaders(responseHeaderList), pathParameters(pathParameterList),
                    requestFields(restdocsRequestFieldList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").tag("Film").summary("Add film to film list of actor")
                            .description("Add film to film list of actor")
                            .requestSchema(Schema.schema("ActorFilm"))
                            .responseHeaders(openapiResponseHeaderList).pathParameters(openapiPathParameterList)
                            .requestFields(openapiRequestFieldList)
                            .build())));
        }

        @Test
        void deleteActorFilm_success() throws Exception {
            // arrange
            var actorId = "1";
            var filmId = "1";

            // act
            var execute = mockMvc.perform(delete("/actors/{actorId}/films/{filmId}", actorId, filmId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isNoContent());
            verify(actorService, times(1)).removeActorFilm(actorId, filmId);

            // docs
            var pathParameterList = List.of(
                    parameterWithName(ActorModel.Fields.actorId).description("Id of the actor"),
                    parameterWithName(FilmModel.Fields.filmId).description("Id of the film"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").tag("Film").summary("Remove film from film list of actor")
                            .description("Remove film from film list of actor")
                            .pathParameters(openapiPathParameterList)
                            .build())));
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
            var execute = mockMvc.perform(get("/actors/{actorId}/films/{filmId}/details", actorId, filmId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(FilmDetailModel.Fields.filmId).value(1))
                    .andExpect(jsonPath(FilmDetailModel.Fields.title).value("ACADEMY DINOSAUR"))
                    .andExpect(jsonPath(FilmDetailModel.Fields.description)
                            .value("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies"))
                    .andExpect(jsonPath(FilmDetailModel.Fields.category).value("Documentary"))
                    .andExpect(jsonPath(FilmDetailModel.Fields.price).value(0.99))
                    .andExpect(jsonPath(FilmDetailModel.Fields.length).value(86))
                    .andExpect(jsonPath(FilmDetailModel.Fields.rating).value("PG"))
                    .andExpect(jsonPath(FilmDetailModel.Fields.actors).value("PENELOPE GUINESS"))
                    .andExpect(jsonPath("_links.self.href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId + "/details"))
                    .andExpect(jsonPath("_links.film.href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId))
                    .andExpect(jsonPath("_links.films.href").value(serverUrl + "/actors/" + actorId + "/films"))
                    .andExpect(jsonPath("_links.actor.href").value(serverUrl + "/actors/" + actorId));
            verify(actorService, times(1)).getActorFilmDetail(actorId, filmId);

            // docs
            var pathParameterList = List.of(
                    parameterWithName(ActorModel.Fields.actorId).description("Id of the actor"),
                    parameterWithName(FilmModel.Fields.filmId).description("Id of the film"));
            var responseFieldList = List.of(
                    fieldWithPath(FilmDetailModel.Fields.filmId).description("Id of the film"),
                    fieldWithPath(FilmDetailModel.Fields.title).description("Title of the film"),
                    fieldWithPath(FilmDetailModel.Fields.description).description("Description of the film"),
                    fieldWithPath(FilmDetailModel.Fields.category).description("Category of the film"),
                    fieldWithPath(FilmDetailModel.Fields.price).description("Price of the film"),
                    fieldWithPath(FilmDetailModel.Fields.length).description("Length of the film"),
                    fieldWithPath(FilmDetailModel.Fields.rating).description("Rating of the film"),
                    fieldWithPath(FilmDetailModel.Fields.actors).description("Actors of the film"),
                    subsectionWithPath("_links").description("Links to other resources"));
            var linkList = List.of(
                    linkWithRel("self").description("Link to self"),
                    linkWithRel("film").description("Link to film"),
                    linkWithRel("films").description("Link to films of the actor"),
                    linkWithRel("actor").description("Link to actor"));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            execute.andDo(MockMvcRestDocumentation.document("asciidoctor/{class-name}/{method-name}",
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));
            execute.andDo(MockMvcRestDocumentationWrapper.document("openapi/{class-name}/{method-name}",
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag("Actor").tag("Film").summary("Get film detail of actor")
                            .description("Get film detail of actor")
                            .responseSchema(Schema.schema("ActorFilmDetail"))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }
    }
}
