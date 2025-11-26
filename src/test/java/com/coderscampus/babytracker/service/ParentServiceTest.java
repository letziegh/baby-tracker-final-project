package com.coderscampus.babytracker.service;

import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.repository.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ParentServiceTest {

    @Mock
    private ParentRepository parentRepository;

    @InjectMocks
    private ParentService parentService;

    private static final String EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        // no-op, kept for future customization
    }

    @Test
    void saveParent_shouldCreateNewParentWhenEmailNotFound() {
        given(parentRepository.findByEmail(EMAIL)).willReturn(Optional.empty());
        given(parentRepository.save(any(Parent.class))).willAnswer(invocation -> invocation.getArgument(0));

        Parent result = parentService.saveParent(EMAIL, "Test User");

        ArgumentCaptor<Parent> parentCaptor = ArgumentCaptor.forClass(Parent.class);
        verify(parentRepository).save(parentCaptor.capture());

        Parent savedParent = parentCaptor.getValue();
        assertThat(savedParent.getEmail()).isEqualTo(EMAIL);
        assertThat(savedParent.getName()).isEqualTo("Test User");
        assertThat(savedParent.getChildren()).isNotNull();

        assertThat(result).isSameAs(savedParent);
    }

    @Test
    void saveParent_shouldUpdateExistingParentNameWhenChanged() {
        Parent existing = new Parent();
        existing.setId(1L);
        existing.setEmail(EMAIL);
        existing.setName("Old Name");

        given(parentRepository.findByEmail(EMAIL)).willReturn(Optional.of(existing));
        given(parentRepository.save(existing)).willReturn(existing);

        Parent result = parentService.saveParent(EMAIL, "New Name");

        assertThat(existing.getName()).isEqualTo("New Name");
        assertThat(result).isSameAs(existing);
        verify(parentRepository).save(existing);
    }

    @Test
    void saveParent_shouldReturnExistingParentWhenNameUnchanged() {
        Parent existing = new Parent();
        existing.setId(1L);
        existing.setEmail(EMAIL);
        existing.setName("Same Name");

        given(parentRepository.findByEmail(EMAIL)).willReturn(Optional.of(existing));

        Parent result = parentService.saveParent(EMAIL, "Same Name");

        assertThat(result).isSameAs(existing);
    }

    @Test
    void saveParent_shouldThrowWhenEmailIsNullOrEmpty() {
        assertThatThrownBy(() -> parentService.saveParent(null, "Name"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email cannot be null or empty");

        assertThatThrownBy(() -> parentService.saveParent("  ", "Name"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email cannot be null or empty");
    }

    @Test
    void getParentById_shouldReturnParentWhenFound() {
        Parent parent = new Parent();
        parent.setId(5L);
        given(parentRepository.findById(5L)).willReturn(Optional.of(parent));

        Parent result = parentService.getParentById(5L);

        assertThat(result).isEqualTo(parent);
    }

    @Test
    void getParentById_shouldReturnNullWhenNotFound() {
        given(parentRepository.findById(5L)).willReturn(Optional.empty());

        Parent result = parentService.getParentById(5L);

        assertThat(result).isNull();
    }

    @Test
    void findByEmail_shouldReturnParentWhenFound() {
        Parent parent = new Parent();
        parent.setEmail(EMAIL);
        given(parentRepository.findByEmail(EMAIL)).willReturn(Optional.of(parent));

        Parent result = parentService.findByEmail(EMAIL);

        assertThat(result).isEqualTo(parent);
    }

    @Test
    void findByEmail_shouldReturnNullWhenNotFound() {
        given(parentRepository.findByEmail(EMAIL)).willReturn(Optional.empty());

        Parent result = parentService.findByEmail(EMAIL);

        assertThat(result).isNull();
    }
}


