package com.kidzcubicle.uaps.repository;

import com.kidzcubicle.uaps.entity.Parent;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParentRepositoryTest {


    @Mock
    private ParentRepository parentRepository;

    @Test
    public void testExistsBySubId() {
        MockitoAnnotations.openMocks(this);
        String existingSubId = "uniqueSubId123";
        String nonExistingSubId = "nonexistentSubId";
        Mockito.when(parentRepository.existsBySubId(existingSubId)).thenReturn(true);
        Mockito.when(parentRepository.existsBySubId(nonExistingSubId)).thenReturn(false);
        boolean exists = parentRepository.existsBySubId(existingSubId);
        assertTrue(exists);
        exists = parentRepository.existsBySubId(nonExistingSubId);
        assertFalse(exists);
    }

    @Test
    public void testFindBySubId() {
        MockitoAnnotations.openMocks(this);
        String existingSubId = "uniqueSubId123";
        String nonExistingSubId = "nonexistentSubId";
        Parent parent = new Parent();
        parent.setName("Parent Name");
        parent.setSubId(existingSubId);
        Mockito.when(parentRepository.findBySubId(existingSubId)).thenReturn(parent);
        Mockito.when(parentRepository.findBySubId(nonExistingSubId)).thenReturn(null);
        Parent foundParent = parentRepository.findBySubId(existingSubId);
        Parent nonExistentParent = parentRepository.findBySubId(nonExistingSubId);
        assertNotNull(foundParent);
        assertNull(nonExistentParent);
    }
}