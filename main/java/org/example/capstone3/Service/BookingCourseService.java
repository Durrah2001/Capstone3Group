package org.example.capstone3.Service;

import lombok.AllArgsConstructor;
import org.example.capstone3.ApiResponse.ApiException;
import org.example.capstone3.Model.BookingCourse;
import org.example.capstone3.Model.Course;
import org.example.capstone3.Model.User;
import org.example.capstone3.OutDTO.BookingCourseDTO;
import org.example.capstone3.Repository.BookingCourseRepository;
import org.example.capstone3.Repository.CourseRepository;
import org.example.capstone3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingCourseService {

    private final BookingCourseRepository bookingCourseRepository;
    private final UserRepository UserRepository;
    private final CourseRepository courseRepository;

    public List<BookingCourseDTO> getAllBookingCourses() {
        List<BookingCourse> bookingCourses = bookingCourseRepository.findAll();
        List<BookingCourseDTO> bookingCourseOutDTOs = new ArrayList<>();
        for (BookingCourse bookingCourse : bookingCourses) {
            bookingCourseOutDTOs.add(new BookingCourseDTO(bookingCourse.getBookingDate(),bookingCourse.getCourseStartDate(),bookingCourse.getCourseEndDate()));
        }
        return bookingCourseOutDTOs;
    }

    public void addBookingCourse(Integer user_id,Integer course_id, BookingCourse bookingCourse) {
        User user = UserRepository.findUserById(user_id);
        Course course = courseRepository.findCourseById(course_id);
        bookingCourse.setUser(user);
        bookingCourse.setCourse(course);
        bookingCourseRepository.save(bookingCourse);
    }

    public void updateBookingCourse(Integer bookingCourse_id, BookingCourse bookingCourse) {
        BookingCourse bookingCourse1 = bookingCourseRepository.findBookingCourseById(bookingCourse_id);
        if (bookingCourse1 == null) {
            throw new ApiException("Booking course not found");
        }
        bookingCourse1.setCourseStartDate(bookingCourse.getCourseStartDate());
        bookingCourse1.setCourseEndDate(bookingCourse.getCourseEndDate());
        bookingCourseRepository.save(bookingCourse1);
    }

    public void deleteBookingCourse(Integer bookingCourse_id) {
        BookingCourse bookingCourse1 = bookingCourseRepository.findBookingCourseById(bookingCourse_id);
        if (bookingCourse1 == null) {
            throw new ApiException("Booking course not found");
        }
        bookingCourseRepository.delete(bookingCourse1);
    }


}
