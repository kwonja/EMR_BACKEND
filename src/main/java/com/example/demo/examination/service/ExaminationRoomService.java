package com.example.demo.examination.service;

import com.example.demo.examination.domain.ExaminationRoom;
import com.example.demo.examination.dto.ExaminationRoomCreateRequest;
import com.example.demo.examination.dto.ExaminationRoomResponse;
import com.example.demo.examination.repository.ExaminationRoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExaminationRoomService {

    private final ExaminationRoomRepository examinationRoomRepository;

    public ExaminationRoomService(
            ExaminationRoomRepository examinationRoomRepository
    ) {
        this.examinationRoomRepository = examinationRoomRepository;
    }

    public ExaminationRoomResponse create(ExaminationRoomCreateRequest request) {
        ExaminationRoom examinationRoom = new ExaminationRoom(
                request.getName(),
                request.getRoomNo(),
                request.getLocation(),
                request.getDescription()
        );

        ExaminationRoom savedExaminationRoom =
                examinationRoomRepository.save(examinationRoom);

        return ExaminationRoomResponse.from(savedExaminationRoom);
    }

    public List<ExaminationRoomResponse> findAll(
            String location,
            String roomName,
            String roomNo
    ) {
        List<ExaminationRoom> examinationRooms =
                examinationRoomRepository.findAllByFilters(
                        emptyToNull(location),
                        emptyToNull(roomName),
                        emptyToNull(roomNo)
                );

        List<ExaminationRoomResponse> responses = new ArrayList<>();

        for (ExaminationRoom examinationRoom : examinationRooms) {
            responses.add(ExaminationRoomResponse.from(examinationRoom));
        }

        return responses;
    }

    private String emptyToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value;
    }
}
