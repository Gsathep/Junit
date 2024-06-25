package com.kidzcubicle.uaps.repository;

import com.kidzcubicle.uaps.entity.ChildrenDetails;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class ChildrenDetailsRepositoryTest {

    @Mock
    private ChildrenDetailsRepository childrenDetailsRepository;

    @Test
    void findByParentId() {
        Long parentId = 1L;
        List<ChildrenDetails> childrenDetails = new ArrayList<>();
        childrenDetails.add(new ChildrenDetails());
        childrenDetails.add(new ChildrenDetails());
        childrenDetails.add(new ChildrenDetails());
        Mockito.when(childrenDetailsRepository.findByParentId(parentId)).thenReturn(childrenDetails);
        List<ChildrenDetails> foundChildList = childrenDetailsRepository.findByParentId(parentId);
        Assert.assertEquals(3, foundChildList.size());
    }
}