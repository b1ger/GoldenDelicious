package org.ontario.goldendelicious.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.repositories.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorityServiceImplTest {

    private AuthorityServiceImpl authorityService;

    @Mock
    private AuthorityRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authorityService = new AuthorityServiceImpl(repository);
    }

    @Test
    public void haveToReturnAuthorityListIfExists() {
        // given
        Authority authority = new Authority();
        authority.setName("TestAuthority");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);

        // when
        when(authorityService.getAuthorityList()).thenReturn(authorities);

        List<Authority> authoritiesList = authorityService.getAuthorityList();

        // then
        assertThat(authoritiesList, hasItem(authorities.get(0)));
        verify(repository, times(1)).findAllByOrderByName();
    }
}