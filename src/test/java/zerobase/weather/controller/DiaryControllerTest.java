package zerobase.weather.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

@WebMvcTest(DiaryController.class)
class DiaryControllerTest {

    @MockBean
    private DiaryService diaryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void successCreateDiary() throws Exception {
        // Given
        String text = "Diary text";
        LocalDate date = LocalDate.of(2023,10,20);

        // When
        // Then
        mockMvc.perform(
            post("/create/diary")
                .param("date", date.toString())
                .content(text)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void successReadDiary() throws Exception {
        // Given
        LocalDate date = LocalDate.parse("2023-10-20");

        List<Diary> diaries = Arrays.asList(
            Diary.builder()
                .date(LocalDate.parse("2023-10-20"))
                .text("1")
                .build(),
            Diary.builder()
                .date(LocalDate.parse("2023-10-20"))
                .text("2")
                .build(),
            Diary.builder()
                .date(LocalDate.parse("2023-10-20"))
                .text("3")
                .build()
        );

        // When
        when(diaryService.readDiary(date)).thenReturn(diaries);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/read/diary")
                .param("date", date.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].date", is("2023-10-20")))
            .andExpect(jsonPath("$[0].text", is("1")))
            .andExpect(jsonPath("$[1].date", is("2023-10-20")))
            .andExpect(jsonPath("$[1].text", is("2")))
            .andExpect(jsonPath("$[2].date", is("2023-10-20")))
            .andExpect(jsonPath("$[2].text", is("3")));

        verify(diaryService, times(1)).readDiary(date);
    }


    @Test
    void successReadDiaries() throws Exception {
        // Given
        LocalDate startDate = LocalDate.parse("2023-10-23");
        LocalDate endDate = LocalDate.parse("2023-10-30");

        List<Diary> diaries = Arrays.asList(
            Diary.builder()
                .date(LocalDate.parse("2023-10-20"))
                .text("1")
                .build(),
            Diary.builder()
                .date(LocalDate.parse("2023-10-25"))
                .text("2")
                .build(),
            Diary.builder()
                .date(LocalDate.parse("2023-10-30"))
                .text("3")
                .build()
        );

        // When
        when(diaryService.readDiaries(startDate, endDate)).thenReturn(diaries);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/read/diaries")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].date", is("2023-10-20")))
            .andExpect(jsonPath("$[0].text", is("1")))
            .andExpect(jsonPath("$[1].date", is("2023-10-25")))
            .andExpect(jsonPath("$[1].text", is("2")))
            .andExpect(jsonPath("$[2].date", is("2023-10-30")))
            .andExpect(jsonPath("$[2].text", is("3")))
        ;

        verify(diaryService, times(1)).readDiaries(startDate, endDate);
    }


    @Test
    void successUpdateDiary() throws Exception {
        // Given
        LocalDate date = LocalDate.parse("2023-10-20");
        String text = "Updated Diary Text";

        // When
        mockMvc.perform(put("/update/diary")
                .param("date", date.toString())
                .content(text)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        // Then
        verify(diaryService, times(1)).updateDiary(date, text);
    }

    @Test
    void successDeleteDiary() throws Exception {
        // Given
        LocalDate date = LocalDate.parse("2023-10-23");

        // When
        // Then
        mockMvc.perform(delete("/delete/diary")
                .param("date", date.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}