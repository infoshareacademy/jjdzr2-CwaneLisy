package com.infoshare.service;

import com.infoshare.TestDataUtils;
import com.infoshare.database.DB;
import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.NeedRequest;
import com.infoshare.domain.TypeOfHelp;
import com.infoshare.dto.NeedRequestFilterForm;
import com.infoshare.dto.NeedRequestListObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class NeedRequestServiceTest {
    private final DB db = mock(DB.class);
    private final NeedRequestService needRequestService = new NeedRequestService(db);
    private final ArgumentCaptor<NeedRequest> needRequestArgumentCaptor = ArgumentCaptor.forClass(NeedRequest.class);

    @Test
    void shouldCreateNeedRequest() {
        // GIVEN
        doNothing().when(db).saveNeedRequest(needRequestArgumentCaptor.capture());
        String name = "Ania";
        String location = "Gdansk";
        String phone = "122122122";
        // WHEN
        needRequestService.createNeedRequest(name, location, phone, TypeOfHelp.HOUSE_HELP);

        // THEN
        NeedRequest needRequest1 = needRequestArgumentCaptor.getValue();
        assertThat(needRequest1.getPersonInNeed().getName()).isEqualTo(name);
        assertThat(needRequest1.getPersonInNeed().getLocation()).isEqualTo(location);
        assertThat(needRequest1.getPersonInNeed().getPhone()).isEqualTo(phone);
    }

    @Test
    void shouldGetAllNeedRequest() {
        //GIVEN
        List<NeedRequestListObject> allNeedRequests = TestDataUtils.getNeedRequestListObject("NeedRequestListObject.json");
        when(db.getNeedRequests()).thenReturn(TestDataUtils.getNeedRequests());
        NeedRequestFilterForm needRequestFilterForm = new NeedRequestFilterForm();

        //WHEN
        List<NeedRequestListObject> needRequestListObjects = needRequestService.getRequestFilteredList(needRequestFilterForm);

        //THEN
        assertThat(needRequestListObjects).hasSize(allNeedRequests.size()).containsAll(allNeedRequests);
    }

    @Test
    void shouldGetNeedRequestsWithLocation() {

        //GIVEN
        List<NeedRequestListObject> allNeedRequests = TestDataUtils.getNeedRequestListObject("locationTestObjects.json");
        when(db.getNeedRequests()).thenReturn(TestDataUtils.getNeedRequests());
        NeedRequestFilterForm needRequestFilterForm = new NeedRequestFilterForm();
        needRequestFilterForm.setLocation("Gdynia");

        //WHEN
        List<NeedRequestListObject> needRequestListObjects = needRequestService.getRequestFilteredList(needRequestFilterForm);

        // THEN
        assertThat(needRequestListObjects).hasSize(1).containsAll(allNeedRequests);
    }

    @Test
    void shouldGetNeedRequestsWithStartAndEndData() {

        //GIVEN
        List<NeedRequestListObject> expectedResult = TestDataUtils.getNeedRequestListObject("datesTestObjects.json");

        when(db.getNeedRequests()).thenReturn(TestDataUtils.getNeedRequests());

        NeedRequestFilterForm needRequestFilterForm = new NeedRequestFilterForm();
        needRequestFilterForm.setStartDate(LocalDate.parse("2021-02-02"));
        needRequestFilterForm.setEndDate(LocalDate.parse("2021-02-03"));

        //WHEN
        List<NeedRequestListObject> needRequestListObjects = needRequestService.getRequestFilteredList(needRequestFilterForm);

        // THEN
        assertThat(needRequestListObjects).hasSize(21).containsAll(expectedResult);
    }

    @Test
    void shouldGetNeedRequestsWithHelpStatuses() {

        //GIVEN
        List<NeedRequestListObject> expectedResult = TestDataUtils.getNeedRequestListObject("helpStatusesTestObjects.json");
        when(db.getNeedRequests()).thenReturn(TestDataUtils.getNeedRequests());
        NeedRequestFilterForm needRequestFilterForm = new NeedRequestFilterForm();
        needRequestFilterForm.setHelpStatuses(Set.of(HelpStatuses.DONE));

        //WHEN
        List<NeedRequestListObject> needRequestListObjects = needRequestService.getRequestFilteredList(needRequestFilterForm);

        //THEN
        assertThat(needRequestListObjects).hasSize(4).containsAll(expectedResult);

    }
    @Test
    void shouldGetNeedRequestsWithTypeOfHelp(){
        //GIVEN
        List<NeedRequestListObject> expectedResult = TestDataUtils.getNeedRequestListObject("typesOfHelpTestObjects.json");
        when(db.getNeedRequests()).thenReturn(TestDataUtils.getNeedRequests());
        NeedRequestFilterForm needRequestFilterForm = new NeedRequestFilterForm();
        needRequestFilterForm.setTypeOfHelps(Set.of(TypeOfHelp.SHOPPING));

        //WHEN
        List<NeedRequestListObject> needRequestListObjects = needRequestService.getRequestFilteredList(needRequestFilterForm);

        //THEN
        assertThat(needRequestListObjects).hasSize(25).containsAll(expectedResult);
    }
}
