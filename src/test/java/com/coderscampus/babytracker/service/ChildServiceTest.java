package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Activity;
import com.coderscampus.babytracker.domain.Child;
import com.coderscampus.babytracker.domain.ChildWithLastActivity;
import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.repository.ChildRepository;
import com.coderscampus.babytracker.repository.ParentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChildServiceTest {

    @Mock
    private ChildRepository childRepository;

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ChildService childService;

    @Test
    void addChild_shouldPersistChildWithResolvedParentAndInitializedActivities() {
        Parent parent = new Parent();
        parent.setId(1L);

        Child child = new Child();
        child.setName("Alice");
        child.setBirthdate(LocalDate.of(2020, 1, 1));
        child.setGender("FEMALE");
        child.setParent(parent);

        given(parentRepository.findById(1L)).willReturn(Optional.of(parent));
        given(childRepository.save(any(Child.class))).willAnswer(invocation -> invocation.getArgument(0));

        Child result = childService.addChild(child);

        verify(parentRepository).findById(1L);
        verify(childRepository).save(child);

        assertThat(result.getParent()).isEqualTo(parent);
        assertThat(result.getActivities()).isNotNull();
    }

    @Test
    void addChild_shouldThrowWhenParentNotFound() {
        Parent parent = new Parent();
        parent.setId(99L);

        Child child = new Child();
        child.setParent(parent);

        given(parentRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> childService.addChild(child))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Parent not found");
    }

    @Test
    void getChildren_shouldReturnAllChildren() {
        Child child1 = new Child();
        Child child2 = new Child();
        given(childRepository.findAll()).willReturn(List.of(child1, child2));

        List<Child> children = childService.getChildren();

        assertThat(children).containsExactly(child1, child2);
    }

    @Test
    void getChild_shouldReturnChildWhenFound() {
        Child child = new Child();
        child.setId(10L);
        given(childRepository.findById(10L)).willReturn(Optional.of(child));

        Child result = childService.getChild(10L);

        assertThat(result).isEqualTo(child);
    }

    @Test
    void getChild_shouldReturnNullWhenNotFound() {
        given(childRepository.findById(10L)).willReturn(Optional.empty());

        Child result = childService.getChild(10L);

        assertThat(result).isNull();
    }

    @Test
    void getChildrenByParent_shouldDelegateToRepository() {
        Child child = new Child();
        given(childRepository.findByParentId(1L)).willReturn(List.of(child));

        List<Child> result = childService.getChildrenByParent(1L);

        assertThat(result).containsExactly(child);
    }

    @Test
    void updateChild_shouldUpdateFieldsAndSave() {
        Child existing = new Child();
        existing.setId(1L);
        existing.setName("Old");
        existing.setBirthdate(LocalDate.of(2019, 1, 1));
        existing.setGender("MALE");

        Child update = new Child();
        update.setName("New");
        update.setBirthdate(LocalDate.of(2020, 2, 2));
        update.setGender("FEMALE");

        given(childRepository.findById(1L)).willReturn(Optional.of(existing));
        given(childRepository.save(existing)).willReturn(existing);

        Child result = childService.updateChild(1L, update);

        assertThat(existing.getName()).isEqualTo("New");
        assertThat(existing.getBirthdate()).isEqualTo(LocalDate.of(2020, 2, 2));
        assertThat(existing.getGender()).isEqualTo("FEMALE");
        assertThat(result).isSameAs(existing);
    }

    @Test
    void updateChild_shouldThrowWhenChildNotFound() {
        given(childRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> childService.updateChild(1L, new Child()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Child not found");
    }

    @Test
    void deleteChild_shouldDelegateToRepository() {
        childService.deleteChild(3L);

        verify(childRepository).deleteById(3L);
    }

    @Test
    void getChildrenByParentWithLastActivity_shouldMapChildrenToChildWithLastActivity() {
        Child child1 = new Child();
        child1.setId(1L);
        child1.setName("Child1");

        Child child2 = new Child();
        child2.setId(2L);
        child2.setName("Child2");

        Activity lastActivity1 = new Activity();
        Activity lastActivity2 = new Activity();

        given(childRepository.findByParentId(10L)).willReturn(List.of(child1, child2));
        given(activityService.getLastActivityByChildId(1L)).willReturn(lastActivity1);
        given(activityService.getLastActivityByChildId(2L)).willReturn(lastActivity2);

        List<ChildWithLastActivity> result = childService.getChildrenByParentWithLastActivity(10L);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getChild()).isEqualTo(child1);
        assertThat(result.get(0).getLastActivity()).isEqualTo(lastActivity1);
        assertThat(result.get(1).getChild()).isEqualTo(child2);
        assertThat(result.get(1).getLastActivity()).isEqualTo(lastActivity2);
    }
}


