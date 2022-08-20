package com.stella.rememberall.datelog;

import com.stella.rememberall.datelog.dto.DateLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DateLogController {

    private final DateLogService dateLogService;

    @PostMapping("/triplog/{id}/datelog/new")
    public Long create(@PathVariable("id") Long tripLogId, @RequestBody @Valid DateLogSaveRequestDto saveRequestDto) {
         return dateLogService.createDateLog(tripLogId, saveRequestDto);
    }

}
