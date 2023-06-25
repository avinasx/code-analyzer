package com.example.app.app.catalog.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.example.app.app.catalog.assembler.ActorDetailsRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.ActorRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.FilmDetailsRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.FilmRepresentationModelAssembler;
import com.example.app.app.catalog.domain.dto.ActorDetailsDto;
import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.app.catalog.service.ActorService;
import com.example.app.common.constant.*;
import com.example.app.common.domain.dto.FullName;
import com.example.app.config.RestDocsControllerSupport;
import com.example.app.util.ConstrainedFieldDocumentation;
import com.example.app.util.OpenApiDescriptorTransformer;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Pageable;
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
    private ActorDetailsRepresentationModelAssembler actorDetailsAssembler;
    @SpyBean
    private FilmRepresentationModelAssembler filmAssembler;
    @SpyBean
    private FilmDetailsRepresentationModelAssembler filmDetailsAssembler;

    @Nested
    class ActorListTests {
        private ActorDto.Actor guiness;
        private ActorDto.Actor walhberg;

        @BeforeEach
        void setUp() {
            guiness = ActorDto.Actor.builder()
                    .actorId(1)
                    .fullName(FullName.builder().firstName("PENELOPE").lastName("GUINESS").build())
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
            walhberg = ActorDto.Actor.builder()
                    .actorId(2)
                    .fullName(FullName.builder().firstName("JENNIFER").lastName("WALHBERG").build())
                    .lastUpdate(LocalDateTime.of(2006, 2, 15, 9, 34, 33))
                    .build();
        }

        @Test
        void getActorList_success() throws Exception {
            // arrange
            var pageable = Pageable.ofSize(10).withPage(0);
            when(actorService.getActorList(pageable)).thenReturn(List.of(guiness, walhberg));

            // act
            var execute = mockMvc.perform(get("/actors")
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.self + ".href").value(serverUrl + "/actors"));
            verify(actorService, times(1)).getActorList(pageable);

            // descriptors
            var actorBase = HalRelation.Fields.actor + "." + HalRelation.Fields.read + ".";
            var actorLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.item + ".";
            var actorListLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.collection + ".";
            var embeddedBase = HalRelation.Fields._embedded + "." + HalRelation.Fields.actorList + "[].";

            var responseFieldList = List.of(
                    fieldWithPath(embeddedBase + ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorBase + "responseField." + ActorDto.Actor.Fields.actorId)),
                    fieldWithPath(embeddedBase + FullName.Fields.firstName).description(getMessageSourceMessage(actorBase + "responseField." + FullName.Fields.firstName)),
                    fieldWithPath(embeddedBase + FullName.Fields.lastName).description(getMessageSourceMessage(actorBase + "responseField." + FullName.Fields.lastName)),
                    fieldWithPath(embeddedBase + ActorDto.Actor.Fields.lastUpdate).description(getMessageSourceMessage(actorBase + "responseField." + ActorDto.Actor.Fields.lastUpdate)),
                    subsectionWithPath(embeddedBase + HalRelation.Fields._links).description(getMessageSourceMessage(actorListLinkBase + HalRelation.Fields._links)),
                    subsectionWithPath(HalRelation.Fields._links).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields._links)));
            var linkList = List.of(
                    linkWithRel(HalRelation.Fields.self).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.self)));

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseFields(responseFieldList), links(linkList)));

            // openapi
            var codeBase = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.getActorList;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(codeBase + ".tag"))
                            .summary(getMessageSourceMessage(codeBase + ".summary"))
                            .description(getMessageSourceMessage(codeBase + ".description"))
                            .responseSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.actorList)))
                            .responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }

        @Test
        void getActorList_trailingSlash() throws Exception {
            // arrange
            var pageable = Pageable.ofSize(10).withPage(0);
            when(actorService.getActorList(pageable)).thenReturn(List.of(guiness, walhberg));

            // act
            var execute = mockMvc.perform(get("/actors/")
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isNotFound());
            verify(actorService, times(0)).getActorList(pageable);
        }

        @Test
        void getActor_success() throws Exception {
            // arrange
            var actorId = 1;
            when(actorService.getActor(actorId)).thenReturn(Optional.of(guiness));

            // act
            var execute = mockMvc.perform(get("/actors/{actorId}", actorId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(ActorDto.Actor.Fields.actorId).value(1))
                    .andExpect(jsonPath(FullName.Fields.firstName).value("PENELOPE"))
                    .andExpect(jsonPath(FullName.Fields.lastName).value("GUINESS"))
                    .andExpect(jsonPath(ActorDto.Actor.Fields.lastUpdate).value("2006-02-15T09:34:33"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.self + ".href").value(serverUrl + "/actors/" + actorId))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.update + ".href").value(serverUrl + "/actors/" + actorId))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.delete + ".href").value(serverUrl + "/actors/" + actorId))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.details + ".href").value(serverUrl + "/actors/" + actorId + "/details"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.create + ".href").value(serverUrl + "/actors"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.actorList + ".href").value(serverUrl + "/actors"));
            verify(actorService, times(1)).getActor(actorId);

            // descriptors
            var actorBase = HalRelation.Fields.actor + "." + HalRelation.Fields.read + ".";
            var actorLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.item + ".";
            var actorListLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.collection + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorBase + "pathParameter." + ActorDto.Actor.Fields.actorId)));
            var responseFieldList = List.of(
                    fieldWithPath(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorBase + "responseField." + ActorDto.Actor.Fields.actorId)),
                    fieldWithPath(FullName.Fields.firstName).description(getMessageSourceMessage(actorBase + "responseField." + FullName.Fields.firstName)),
                    fieldWithPath(FullName.Fields.lastName).description(getMessageSourceMessage(actorBase + "responseField." + FullName.Fields.lastName)),
                    fieldWithPath(ActorDto.Actor.Fields.lastUpdate).description(getMessageSourceMessage(actorBase + "responseField." + ActorDto.Actor.Fields.lastUpdate)),
                    subsectionWithPath(HalRelation.Fields._links).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields._links)));
            var linkList = List.of(
                    linkWithRel(HalRelation.Fields.self).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.update).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.update)),
                    linkWithRel(HalRelation.Fields.delete).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.delete)),
                    linkWithRel(HalRelation.Fields.details).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.details)),
                    linkWithRel(HalRelation.Fields.create).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.create)),
                    linkWithRel(HalRelation.Fields.actorList).description(getMessageSourceMessage(actorListLinkBase + HalRelation.Fields.self)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));

            // openapi
            var codeBase = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.getActor;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(codeBase + ".tag"))
                            .summary(getMessageSourceMessage(codeBase + ".summary"))
                            .description(getMessageSourceMessage(codeBase + ".description"))
                            .responseSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.actor)))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }
    }

    @Nested
    class ActorUpdateTests {
        private int actorId;
        private HashMap<String, String> payload;
        private ActorDto.ActorRequest requestModel;
        private ActorDto.Actor newActor;

        @BeforeEach
        void setUp() {
            actorId = 1;
            payload = new HashMap<>();
            payload.put("firstName", "HELLO");
            payload.put("lastName", "WORLD");
            requestModel = new ActorDto.ActorRequest(
                    payload.get(ActorDto.ActorRequest.Fields.firstName),
                    payload.get(ActorDto.ActorRequest.Fields.lastName)
            );
            newActor = ActorDto.Actor.builder()
                    .actorId(actorId)
                    .fullName(FullName.builder()
                            .firstName(payload.get(FullName.Fields.firstName))
                            .lastName(payload.get(FullName.Fields.lastName))
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
            verify(actorService, times(1)).addActor(any(ActorDto.ActorRequest.class));

            // descriptors
            var actorBase = HalRelation.Fields.actor + "." + HalRelation.Fields.create + ".";

            var requestFieldList = List.of(
                    fieldWithPath(ActorDto.ActorRequest.Fields.firstName).description(getMessageSourceMessage(actorBase + "requestField." + ActorDto.ActorRequest.Fields.firstName)),
                    fieldWithPath(ActorDto.ActorRequest.Fields.lastName).description(getMessageSourceMessage(actorBase + "requestField." + ActorDto.ActorRequest.Fields.lastName)));
            var responseHeaderList = List.of(
                    headerWithName(HttpHeaders.LOCATION).description(getMessageSourceMessage(actorBase + "responseHeader." + HttpHeaders.LOCATION)));
            var openapiResponseHeaderList = OpenApiDescriptorTransformer.transformHeader(responseHeaderList);
            var fieldDescriptors = new ConstrainedFieldDocumentation(ActorDto.ActorRequest.class);
            var restdocsRequestFieldList = fieldDescriptors.restdocsFields(requestFieldList);
            var openapiRequestFieldList = fieldDescriptors.openapiFields(requestFieldList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseHeaders(responseHeaderList), requestFields(restdocsRequestFieldList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.addActor;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .requestSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.actor + "Request")))
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
            verify(actorService, times(1)).updateActor(anyInt(), any(ActorDto.ActorRequest.class));

            // descriptors
            var actorBase = HalRelation.Fields.actor + "." + HalRelation.Fields.update + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorBase + "pathParameter." + ActorDto.Actor.Fields.actorId)));
            var requestFieldList = List.of(
                    fieldWithPath(ActorDto.ActorRequest.Fields.firstName).description(getMessageSourceMessage(actorBase + "requestField." + ActorDto.ActorRequest.Fields.firstName)),
                    fieldWithPath(ActorDto.ActorRequest.Fields.lastName).description(getMessageSourceMessage(actorBase + "requestField." + ActorDto.ActorRequest.Fields.lastName)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);
            var fieldDescriptors = new ConstrainedFieldDocumentation(ActorDto.ActorRequest.class);
            var restdocsRequestFieldList = fieldDescriptors.restdocsFields(requestFieldList);
            var openapiRequestFieldList = fieldDescriptors.openapiFields(requestFieldList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), requestFields(restdocsRequestFieldList)));

            // openapi
            var codeBase = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.updateActor;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(codeBase + ".tag"))
                            .summary(getMessageSourceMessage(codeBase + ".summary"))
                            .description(getMessageSourceMessage(codeBase + ".description"))
                            .requestSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.actor + "Request")))
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

            // descriptors
            var actorBase = HalRelation.Fields.actor + "." + HalRelation.Fields.delete + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(
                            getMessageSourceMessage(actorBase + "pathParameter." + ActorDto.Actor.Fields.actorId)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.deleteActor;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .pathParameters(openapiPathParameterList)
                            .build())));
        }
    }

    @Nested
    class ActorDetailsTests {
        @Test
        void getActorDetails_success() throws Exception {
            // arrange
            var actorId = 1;
            var model = ActorDetailsDto.ActorDetails.builder()
                    .actorId(actorId)
                    .firstName("PENELOPE")
                    .lastName("GUINESS")
                    .filmInfo("ACADEMY DINOSAUR, ANACONDA CONFESSIONS, ANGELS LIFE, BULWORTH COMMANDMENTS, " +
                            "CHEAPER CLYDE, COLOR PHILADELPHIA, ELEPHANT TROJAN, GLEAMING JAWBREAKER, HUMAN GRAFFITI, " +
                            "KING EVOLUTION, LADY STAGE, LANGUAGE COWBOY, MULHOLLAND BEAST, OKLAHOMA JUMANJI, " +
                            "RULES HUMAN, SPLASH GUMP, VERTIGO NORTHWEST, WESTWARD SEABISCUIT, WIZARD COLDBLOODED")
                    .build();
            when(actorService.getActorDetails(actorId)).thenReturn(Optional.of(model));

            // act
            var execute = mockMvc.perform(get("/actors/{actorId}/details", actorId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(ActorDetailsDto.ActorDetails.Fields.actorId).value(1))
                    .andExpect(jsonPath(ActorDetailsDto.ActorDetails.Fields.firstName).value("PENELOPE"))
                    .andExpect(jsonPath(ActorDetailsDto.ActorDetails.Fields.lastName).value("GUINESS"))
                    .andExpect(jsonPath(ActorDetailsDto.ActorDetails.Fields.filmInfo)
                            .value("ACADEMY DINOSAUR, ANACONDA CONFESSIONS, ANGELS LIFE, BULWORTH COMMANDMENTS, " +
                                    "CHEAPER CLYDE, COLOR PHILADELPHIA, ELEPHANT TROJAN, GLEAMING JAWBREAKER, HUMAN GRAFFITI, " +
                                    "KING EVOLUTION, LADY STAGE, LANGUAGE COWBOY, MULHOLLAND BEAST, OKLAHOMA JUMANJI, " +
                                    "RULES HUMAN, SPLASH GUMP, VERTIGO NORTHWEST, WESTWARD SEABISCUIT, WIZARD COLDBLOODED"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.self + ".href").value(serverUrl + "/actors/" + actorId + "/details"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.actor + ".href").value(serverUrl + "/actors/" + actorId))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.actorList + ".href").value(serverUrl + "/actors"));
            verify(actorService, times(1)).getActorDetails(actorId);

            // descriptors
            var actorBase = HalRelation.Fields.actor + "." + HalRelation.Fields.details + ".";
            var actorLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.item + ".";
            var actorListLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.collection + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorBase + "pathParameter." + ActorDto.Actor.Fields.actorId)));
            var responseFieldList = List.of(
                    fieldWithPath(ActorDetailsDto.ActorDetails.Fields.actorId).description(getMessageSourceMessage(actorBase + "responseField." + ActorDetailsDto.ActorDetails.Fields.actorId)),
                    fieldWithPath(ActorDetailsDto.ActorDetails.Fields.firstName).description(getMessageSourceMessage(actorBase + "responseField." + ActorDetailsDto.ActorDetails.Fields.firstName)),
                    fieldWithPath(ActorDetailsDto.ActorDetails.Fields.lastName).description(getMessageSourceMessage(actorBase + "responseField." + ActorDetailsDto.ActorDetails.Fields.lastName)),
                    fieldWithPath(ActorDetailsDto.ActorDetails.Fields.filmInfo).description(getMessageSourceMessage(actorBase + "responseField." + ActorDetailsDto.ActorDetails.Fields.filmInfo)),
                    subsectionWithPath(HalRelation.Fields._links).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields._links)));
            var linkList = List.of(
                    linkWithRel(HalRelation.Fields.self).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.actor).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.actorList).description(getMessageSourceMessage(actorListLinkBase + HalRelation.Fields.self)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.getActorDetails;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .responseSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.actorDetails)))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }
    }

    @Nested
    class ActorFilmTests {
        private FilmDto.Film academyDinosaur;
        private FilmDto.Film aceGoldfinger;

        @BeforeEach
        void setUp() {
            academyDinosaur = FilmDto.Film.builder()
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
            aceGoldfinger = FilmDto.Film.builder()
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
        void getActorFilmList_success() throws Exception {
            // arrange
            var actorId = 1;
            var pageable = Pageable.ofSize(10).withPage(0);
            when(actorService.getActorFilmList(actorId, FilmDto.Film.builder().build(), pageable)).thenReturn(List.of(academyDinosaur, aceGoldfinger));

            // act
            var execute = mockMvc.perform(get("/actors/{actorId}/films", actorId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.self + ".href").value(serverUrl + "/actors/" + actorId + "/films{?releaseYear,rating}"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.actor + ".href").value(serverUrl + "/actors/" + actorId));
            verify(actorService, times(1)).getActorFilmList(actorId, FilmDto.Film.builder().build(), pageable);

            // descriptors
            var actorFilmBase = HalRelation.Fields.actor + "." + HalRelation.Fields.film + "." + HalRelation.Fields.read + ".";
            var actorLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.item + ".";
            var filmLinkBase = HalRelation.Fields.film + ".hal." + HalRelation.Fields.item + ".";
            var filmListLinkBase = HalRelation.Fields.film + ".hal." + HalRelation.Fields.collection + ".";
            var embeddedBase = HalRelation.Fields._embedded + "." + HalRelation.Fields.filmList + "[].";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + ActorDto.Actor.Fields.actorId)));
            var responseFieldList = List.of(
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.filmId).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.filmId)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.title).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.title)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.description).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.description)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.releaseYear).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.releaseYear)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.languageId).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.languageId)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.originalLanguageId).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.originalLanguageId)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.rentalDuration).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.rentalDuration)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.rentalRate).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.rentalRate)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.length).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.length)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.replacementCost).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.replacementCost)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.rating).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.rating)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.specialFeatures).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.specialFeatures)),
                    fieldWithPath(embeddedBase + FilmDto.Film.Fields.lastUpdate).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.lastUpdate)),
                    subsectionWithPath(embeddedBase + HalRelation.Fields._links).description(getMessageSourceMessage(filmListLinkBase + HalRelation.Fields._links)),
                    subsectionWithPath(HalRelation.Fields._links).description(getMessageSourceMessage(filmLinkBase + HalRelation.Fields._links)));
            var linkList = List.of(
                    linkWithRel(HalRelation.Fields.self).description(getMessageSourceMessage(filmLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.actor).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields._links)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.getActorFilmList;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .responseSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.filmList)))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }

        @Test
        void getActorFilm_success() throws Exception {
            // arrange
            var actorId = 1;
            var filmId = 1;
            when(actorService.getActorFilm(actorId, filmId)).thenReturn(Optional.ofNullable(academyDinosaur));

            // act
            var execute = mockMvc.perform(get("/actors/{actorId}/films/{filmId}", actorId, filmId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.self + ".href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.actor + ".href").value(serverUrl + "/actors/" + actorId))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.filmList + ".href").value(serverUrl + "/actors/" + actorId + "/films{?releaseYear,rating}"));
            verify(actorService, times(1)).getActorFilm(actorId, filmId);

            // descriptors
            var actorFilmBase = HalRelation.Fields.actor + "." + HalRelation.Fields.film + "." + HalRelation.Fields.read + ".";
            var actorLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.item + ".";
            var filmLinkBase = HalRelation.Fields.film + ".hal." + HalRelation.Fields.item + ".";
            var filmListLinkBase = HalRelation.Fields.film + ".hal." + HalRelation.Fields.collection + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + ActorDto.Actor.Fields.actorId)),
                    parameterWithName(FilmDto.Film.Fields.filmId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + FilmDto.Film.Fields.filmId)));
            var responseFieldList = List.of(
                    fieldWithPath(FilmDto.Film.Fields.filmId).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.filmId)),
                    fieldWithPath(FilmDto.Film.Fields.title).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.title)),
                    fieldWithPath(FilmDto.Film.Fields.description).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.description)),
                    fieldWithPath(FilmDto.Film.Fields.releaseYear).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.releaseYear)),
                    fieldWithPath(FilmDto.Film.Fields.languageId).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.languageId)),
                    fieldWithPath(FilmDto.Film.Fields.originalLanguageId).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.originalLanguageId)),
                    fieldWithPath(FilmDto.Film.Fields.rentalDuration).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.rentalDuration)),
                    fieldWithPath(FilmDto.Film.Fields.rentalRate).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.rentalRate)),
                    fieldWithPath(FilmDto.Film.Fields.length).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.length)),
                    fieldWithPath(FilmDto.Film.Fields.replacementCost).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.replacementCost)),
                    fieldWithPath(FilmDto.Film.Fields.rating).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.rating)),
                    fieldWithPath(FilmDto.Film.Fields.specialFeatures).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.specialFeatures)),
                    fieldWithPath(FilmDto.Film.Fields.lastUpdate).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDto.Film.Fields.lastUpdate)),
                    subsectionWithPath(HalRelation.Fields._links).description(getMessageSourceMessage(filmLinkBase + HalRelation.Fields._links)));
            var linkList = List.of(
                    linkWithRel(HalRelation.Fields.self).description(getMessageSourceMessage(filmLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.filmList).description(getMessageSourceMessage(filmListLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.actor).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.self)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.getActorFilm;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .responseSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.film)))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }

        @Test
        void addActorFilm_success() throws Exception {
            // arrange
            var actorId = 1;
            var filmId = 1;
            var payload = Maps.<String, String>newHashMap();
            payload.put(FilmDto.Film.Fields.filmId, String.valueOf(filmId));
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

            // descriptors
            var actorFilmBase = HalRelation.Fields.actor + "." + HalRelation.Fields.film + "." + HalRelation.Fields.create + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + ActorDto.Actor.Fields.actorId)));
            var requestFieldList = List.of(
                    fieldWithPath(FilmDto.Film.Fields.filmId).description(getMessageSourceMessage(actorFilmBase + "requestField." + FilmDto.Film.Fields.filmId)));
            var responseHeaderList = List.of(
                    headerWithName(HttpHeaders.LOCATION).description(getMessageSourceMessage(actorFilmBase + "responseHeader." + HttpHeaders.LOCATION)));
            var openapiResponseHeaderList = OpenApiDescriptorTransformer.transformHeader(responseHeaderList);
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);
            var fieldDescriptors = new ConstrainedFieldDocumentation(ActorDto.ActorRequest.class);
            var restdocsRequestFieldList = fieldDescriptors.restdocsFields(requestFieldList);
            var openapiRequestFieldList = fieldDescriptors.openapiFields(requestFieldList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    responseHeaders(responseHeaderList), pathParameters(pathParameterList), requestFields(restdocsRequestFieldList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.addActorFilm;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .requestSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.film + "Request")))
                            .responseHeaders(openapiResponseHeaderList)
                            .pathParameters(openapiPathParameterList)
                            .requestFields(openapiRequestFieldList)
                            .build())));
        }

        @Test
        void deleteActorFilm_success() throws Exception {
            // arrange
            var actorId = 1;
            var filmId = 1;

            // act
            var execute = mockMvc.perform(delete("/actors/{actorId}/films/{filmId}", actorId, filmId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isNoContent());
            verify(actorService, times(1)).removeActorFilm(actorId, filmId);

            // descriptors
            var actorFilmBase = HalRelation.Fields.actor + "." + HalRelation.Fields.film + "." + HalRelation.Fields.delete + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + ActorDto.Actor.Fields.actorId)),
                    parameterWithName(FilmDto.Film.Fields.filmId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + FilmDto.Film.Fields.filmId)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), pathParameters(pathParameterList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.deleteActorFilm;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .pathParameters(openapiPathParameterList)
                            .build())));
        }
    }

    @Nested
    class ActorFilmDetailsTests {
        @Test
        void getActorFilmDetails_success() throws Exception {
            // arrange
            var actorId = 1;
            var filmId = 1;
            var model = Optional.of(FilmDetailsDto.FilmDetails.builder()
                    .filmId(1)
                    .title("ACADEMY DINOSAUR")
                    .description("A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies")
                    .category(Category.DOCUMENTARY)
                    .price(BigDecimal.valueOf(0.99))
                    .length(86)
                    .rating(FilmRating.PG)
                    .actors("PENELOPE GUINESS")
                    .build());
            when(actorService.getActorFilmDetails(actorId, filmId)).thenReturn(model);

            // act
            var execute = mockMvc.perform(get("/actors/{actorId}/films/{filmId}/details", actorId, filmId)
                    .accept(MediaTypes.HAL_JSON));

            // assert
            execute.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.self + ".href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId + "/details"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.film + ".href").value(serverUrl + "/actors/" + actorId + "/films/" + filmId))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.filmList + ".href").value(serverUrl + "/actors/" + actorId + "/films{?releaseYear,rating}"))
                    .andExpect(jsonPath(HalRelation.Fields._links + "." + HalRelation.Fields.actor + ".href").value(serverUrl + "/actors/" + actorId));
            verify(actorService, times(1)).getActorFilmDetails(actorId, filmId);

            // descriptors
            var actorFilmBase = HalRelation.Fields.actor + "." + HalRelation.Fields.film + "." + HalRelation.Fields.details + ".";
            var actorLinkBase = HalRelation.Fields.actor + ".hal." + HalRelation.Fields.item + ".";
            var filmLinkBase = HalRelation.Fields.film + ".hal." + HalRelation.Fields.item + ".";
            var filmListLinkBase = HalRelation.Fields.film + ".hal." + HalRelation.Fields.collection + ".";

            var pathParameterList = List.of(
                    parameterWithName(ActorDto.Actor.Fields.actorId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + ActorDto.Actor.Fields.actorId)),
                    parameterWithName(FilmDto.Film.Fields.filmId).description(getMessageSourceMessage(actorFilmBase + "pathParameter." + FilmDto.Film.Fields.filmId)));
            var responseFieldList = List.of(
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.filmId).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.filmId)),
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.title).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.title)),
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.description).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.description)),
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.category).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.category)),
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.price).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.price)),
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.length).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.length)),
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.rating).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.rating)),
                    fieldWithPath(FilmDetailsDto.FilmDetails.Fields.actors).description(getMessageSourceMessage(actorFilmBase + "responseField." + FilmDetailsDto.FilmDetails.Fields.actors)),
                    subsectionWithPath(HalRelation.Fields._links).description(getMessageSourceMessage(filmLinkBase + HalRelation.Fields._links)));
            var linkList = List.of(
                    linkWithRel(HalRelation.Fields.self).description(getMessageSourceMessage(filmLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.film).description(getMessageSourceMessage(filmLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.filmList).description(getMessageSourceMessage(filmListLinkBase + HalRelation.Fields.self)),
                    linkWithRel(HalRelation.Fields.actor).description(getMessageSourceMessage(actorLinkBase + HalRelation.Fields.self)));
            var openapiPathParameterList = OpenApiDescriptorTransformer.transformParameter(pathParameterList);

            // restdocs
            execute.andDo(MockMvcRestDocumentation.document(RESTDOCS_DOCUMENT_IDENTIFIER,
                    preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                    pathParameters(pathParameterList), responseFields(responseFieldList), links(linkList)));

            // openapi
            var base = HalRelation.Fields.actor + ".controller." + HalRelation.Fields.getActorFilmDetails;
            execute.andDo(MockMvcRestDocumentationWrapper.document(OPENAPI_DOCUMENT_IDENTIFIER,
                    ResourceDocumentation.resource(ResourceSnippetParameters.builder()
                            .tag(getMessageSourceMessage(base + ".tag"))
                            .summary(getMessageSourceMessage(base + ".summary"))
                            .description(getMessageSourceMessage(base + ".description"))
                            .responseSchema(Schema.schema(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, HalRelation.Fields.filmDetails)))
                            .pathParameters(openapiPathParameterList).responseFields(responseFieldList)
                            .links(linkList)
                            .build())));
        }
    }
}
