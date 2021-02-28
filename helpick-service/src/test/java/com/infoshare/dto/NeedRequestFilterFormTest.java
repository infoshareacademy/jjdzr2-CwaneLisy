package com.infoshare.dto;

import com.infoshare.domain.HelpStatuses;
import com.infoshare.domain.TypeOfHelp;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NeedRequestFilterFormTest {
   @Test
void shouldCreateFilteringFormWithCorrectStartDate(){
       //given
       String startDate="2019-02-07";
       //when
       NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
               .withStartDate(startDate)
               .build();
       //then
      assertThat(needRequestFilterForm.getStartDate()).isEqualTo(startDate);
   }
   @Test
   void shouldCreateFilteringFormWithNullStartDate(){
      //given
      String startDate=null;
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withStartDate(startDate)
              .build();
      //then
      assertThat(needRequestFilterForm.getStartDate()).isNull();
   }
   @Test
   void shouldCreateFilteringFormWithEmptyStartDate(){
      //given
      String startDate="";
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withStartDate(startDate)
              .build();
      //then
      assertThat(needRequestFilterForm.getStartDate()).isNull();
   }
   @Test
   void shouldCreateFilteringFormWithCorrectTypesOfHelp(){
      //given
      String typesOfHelp="SHOPPING,WALKING_THE_DOG";
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withTypeOfHelps(typesOfHelp)
              .build();
      //then
      assertThat(needRequestFilterForm.getTypeOfHelps()).hasSize(2).containsExactlyInAnyOrder(TypeOfHelp.SHOPPING,TypeOfHelp.WALKING_THE_DOG);
   }
   @Test
   void shouldCreateFilteringFormWithNullTypesOfHelp(){
      //given
      String typesOfHelp=null;
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withTypeOfHelps(typesOfHelp)
              .build();
      //then
      assertThat(needRequestFilterForm.getTypeOfHelps()).isNull();
   }
   @Test
   void shouldCreateFilteringFormWithEmptyTypeOfHelp(){
      //given
      String typesOfHelp="";
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withTypeOfHelps(typesOfHelp)
              .build();
      //then
      assertThat(needRequestFilterForm.getTypeOfHelps()).isNull();
   }

   @Test
   void shouldCreateFilteringFormWithCorrectEndDate(){
      //given
      String endDate="2019-12-31";
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withEndDate(endDate)
              .build();
      //then
      assertThat(needRequestFilterForm.getEndDate()).isEqualTo(endDate);
   }
   @Test
   void shouldCreateFilteringFormWithNullEndDate(){
      //given
      String endDate=null;
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withEndDate(endDate)
              .build();
      //then
      assertThat(needRequestFilterForm.getEndDate()).isNull();
   }
   @Test
   void shouldCreateFilteringFormWithEmptyEndDate(){
      //given
      String endDate="";
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withEndDate(endDate)
              .build();
      //then
      assertThat(needRequestFilterForm.getEndDate()).isNull();
   }
   @Test
   void shouldCreateFilteringFormWithCorrectHelpStatuses(){
      //given
      String helpStatuses="INPROGRESS,NEW";
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withHelpStatuses(helpStatuses)
              .build();
      //then
      assertThat(needRequestFilterForm.getHelpStatuses()).hasSize(2).containsExactlyInAnyOrder(HelpStatuses.NEW, HelpStatuses.INPROGRESS);
   }
   @Test
   void shouldCreateFilteringFormWithNullHelpStatuses(){
      //given
      String helpStatuses=null;
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withTypeOfHelps(helpStatuses)
              .build();
      //then
      assertThat(needRequestFilterForm.getHelpStatuses()).isNull();
   }
   @Test
   void shouldCreateFilteringFormWithEmptyHelpStatuses(){
      //given
      String helpStatuses="";
      //when
      NeedRequestFilterForm needRequestFilterForm=NeedRequestFilterForm.FilterFormBuilder.aFilterForm()
              .withHelpStatuses(helpStatuses)
              .build();
      //then
      assertThat(needRequestFilterForm.getHelpStatuses()).isNull();
   }

}