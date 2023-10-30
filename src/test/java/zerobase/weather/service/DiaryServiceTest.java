package zerobase.weather.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DiaryRepository;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private DiaryService diaryService;

    @Test
    void readDiarySuccess() {
        // Given
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

        given(diaryRepository.findAllByDate(any()))
            .willReturn(diaries);

        // When
        List<Diary> diaryList = diaryService.readDiary(LocalDate.now());

        // Then
        assertEquals("2023-10-20", diaryList.get(0).getDate().toString());
        assertEquals("2023-10-20", diaryList.get(1).getDate().toString());
        assertEquals("2023-10-20", diaryList.get(2).getDate().toString());
        assertEquals("1", diaryList.get(0).getText());
        assertEquals("2", diaryList.get(1).getText());
        assertEquals("3", diaryList.get(2).getText());
    }

    @Test
    void readDiariesSuccess() {
        // Given
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

        given(diaryRepository.findAllByDateBetween(any(), any()))
            .willReturn(diaries);

        // When
        List<Diary> diaryList = diaryService.readDiaries(LocalDate.now(), LocalDate.now().plusDays(3));

        // Then
        assertEquals("2023-10-20", diaryList.get(0).getDate().toString());
        assertEquals("2023-10-25", diaryList.get(1).getDate().toString());
        assertEquals("2023-10-30", diaryList.get(2).getDate().toString());
        assertEquals("1", diaryList.get(0).getText());
        assertEquals("2", diaryList.get(1).getText());
        assertEquals("3", diaryList.get(2).getText());
    }

}