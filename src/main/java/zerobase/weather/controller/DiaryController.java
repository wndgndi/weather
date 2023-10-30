package zerobase.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @ApiOperation("일기 텍스트와 날씨를 이용해서 DB에 일기를 저장합니다.")
    @PostMapping("/create/diary")
    public void createDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "일기를 저장할 날짜", example = "2023-10-20") LocalDate date,
        @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    @ApiOperation("선택한 날짜의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diary")
    public List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "조회하고자 하는 일기의 날짜", example = "2023-10-20") LocalDate date) {
        return diaryService.readDiary(date);
    }

    @ApiOperation("선택한 기간 내의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diaries")
    public List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "조회할 기간의 첫번째 날", example = "2023-10-23") LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "조회할 기간의 마지막 날", example = "2023-10-30") LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @ApiOperation("선택한 날짜의 일기 데이터를 수정합니다.")
    @PutMapping("/update/diary")
    public void updateDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "수정하고자 하는 일기의 날짜", example = "2023-10-20") LocalDate date, @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    @ApiOperation("선택한 날짜의 일기 데이터를 삭제합니다.")
    @DeleteMapping("/delete/diary")
    public void deleteDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "삭제하고자 하는 일기의 날짜", example = "2023-10-20") LocalDate date) {
        diaryService.deleteDiary(date);
    }
}
