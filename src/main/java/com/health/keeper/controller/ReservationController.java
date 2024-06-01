package com.health.keeper.controller;

import com.health.keeper.entity.ReservationEntity;
import com.health.keeper.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/book")
    public boolean bookLesson(@RequestParam Long lessonId, @RequestParam Long userId, @RequestParam LocalDate reservationDate) {
        return reservationService.bookLesson(lessonId, userId, reservationDate);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationEntity> getReservationsByUserId(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @GetMapping("/{id}")
    public String reservation(@PathVariable("id") String id){
        System.out.println("id = " + id);

        return "lessonReservation";
    }
}
