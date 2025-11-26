package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.repository.ActivityRepository;
import com.coderscampus.babytracker.repository.ChildRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ChildRepository childRepository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void createActivity_shouldValidateChildAndSave() {
        Child child = new Child();
        child.setId(1L);

        Activity activity = new Activity();
        activity.setChild(child);

        given(childRepository.findById(1L)).willReturn(Optional.of(child));
        given(activityRepository.save(any(Activity.class))).willAnswer(invocation -> invocation.getArgument(0));

        Activity result = activityService.createActivity(activity);

        verify(childRepository).findById(1L);
        verify(activityRepository).save(activity);
        assertThat(result.getChild()).isEqualTo(child);
    }

    @Test
    void createActivity_shouldThrowWhenChildNotFound() {
        Child child = new Child();
        child.setId(99L);

        Activity activity = new Activity();
        activity.setChild(child);

        given(childRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.createActivity(activity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Child not found");
    }

    @Test
    void getActivitiesByChildId_shouldDelegateToRepository() {
        Activity a1 = new Activity();
        Activity a2 = new Activity();
        given(activityRepository.findByChildId(5L)).willReturn(List.of(a1, a2));

        List<Activity> result = activityService.getActivitiesByChildId(5L);

        assertThat(result).containsExactly(a1, a2);
    }

    @Test
    void deleteActivity_shouldDelegateToRepository() {
        activityService.deleteActivity(3L);

        verify(activityRepository).deleteById(3L);
    }

    @Test
    void updateActivity_shouldUpdateFieldsAndSave() {
        Activity existing = new Activity();
        existing.setId(1L);
        existing.setStartTime(LocalDateTime.now().minusHours(1));

        Activity updated = new Activity();
        updated.setActivityType(existing.getActivityType());
        updated.setStartTime(LocalDateTime.now());
        updated.setEndTime(LocalDateTime.now().plusHours(1));
        updated.setNotes("Updated notes");
        updated.setDiaperCondition("WET");

        given(activityRepository.findById(1L)).willReturn(Optional.of(existing));
        given(activityRepository.save(existing)).willReturn(existing);

        Activity result = activityService.updateActivity(1L, updated);

        assertThat(existing.getStartTime()).isEqualTo(updated.getStartTime());
        assertThat(existing.getEndTime()).isEqualTo(updated.getEndTime());
        assertThat(existing.getNotes()).isEqualTo("Updated notes");
        assertThat(existing.getDiaperCondition()).isEqualTo("WET");
        assertThat(result).isSameAs(existing);
    }

    @Test
    void updateActivity_shouldThrowWhenNotFound() {
        given(activityRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> activityService.updateActivity(1L, new Activity()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Activity not found");
    }

    @Test
    void getLastActivityByChildId_shouldReturnNullWhenNoActivities() {
        given(activityRepository.findByChildId(10L)).willReturn(List.of());

        Activity result = activityService.getLastActivityByChildId(10L);

        assertThat(result).isNull();
    }

    @Test
    void getLastActivityByChildId_shouldReturnFirstActivityFromList() {
        Activity first = new Activity();
        Activity second = new Activity();
        given(activityRepository.findByChildId(10L)).willReturn(List.of(first, second));

        Activity result = activityService.getLastActivityByChildId(10L);

        assertThat(result).isSameAs(first);
    }
}


