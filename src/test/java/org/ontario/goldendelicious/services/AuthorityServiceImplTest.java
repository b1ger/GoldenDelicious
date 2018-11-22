package org.ontario.goldendelicious.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
        authority.setName("ROLE_TEST");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);

        // when
        when(authorityService.getAuthorityList()).thenReturn(authorities);

        List<Authority> authoritiesList = authorityService.getAuthorityList();

        // then
        assertThat(authoritiesList, hasItem(authorities.get(0)));
        verify(repository, times(1)).findAllByOrderByName();
    }

    @Test
    public void haveToReturnAuthorityByIdIfItExists() {
        // given
        Authority authority = new Authority();
        authority.setId(1L);
        Optional optional = Optional.of(authority);

        // when
        when(repository.findById(anyLong())).thenReturn(optional);
        Authority returnedAuthority = authorityService.getAuthorityById(1L);

        // then
        assertNotNull("Null ROLE returned", returnedAuthority);
        assertEquals(authority.getId(), returnedAuthority.getId());
    }

    @Test
    public void haveToReturnAuthorityByNameIfItExists() {
        // given
        Authority authority = new Authority();
        authority.setName("ROLE_TEST");
        Optional optional = Optional.of(authority);

        // when
        when(repository.findByName(anyString())).thenReturn(optional);
        Authority returnedAuthority = authorityService.getAuthorityByName("ROLE_TEST");

        // then
        assertNotNull("Null ROLE returned", returnedAuthority);
        assertEquals(authority.getName(), returnedAuthority.getName());
    }

    @Test
    public void haveToSaveNewAuthority() {
        // given
        Authority authority = new Authority();
        authority.setName("ROLE_TEST");

        // when
        when(repository.save(any())).thenReturn(authority);

        Authority saved = authorityService.save(authority);

        // given
        assertEquals("ROLE_TEST", saved.getName());
        verify(repository, times(1)).save(authority);
    }

    @Test(expected = NotFoundException.class)
    public void throwExceptionIfAuthorityNotFoundById() {
        Optional optional = Optional.empty();

        when(repository.findById(anyLong())).thenReturn(optional);

        Authority authority = authorityService.getAuthorityById(100500L);

        // should be boom
    }

    @Test(expected = NotFoundException.class)
    public void throwExceptionIfAuthorityNotFoundByName() {
        Optional optional = Optional.empty();

        when(repository.findByName(anyString())).thenReturn(optional);

        Authority authority = authorityService.getAuthorityByName("ROLE_TEST");

        // should be boom
    }
}