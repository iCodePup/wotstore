package com.glg204.wotstore.service;

import com.glg204.wotstore.client.dao.ClientDAO;
import com.glg204.wotstore.client.domain.Client;
import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.client.service.ClientDTOMapper;
import com.glg204.wotstore.client.service.ClientServiceImpl;
import com.glg204.wotstore.webofthing.dao.ThingInStoreDAO;
import com.glg204.wotstore.webofthing.domain.ThingInStore;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import com.glg204.wotstore.webofthing.service.ThingInStoreDTOMapper;
import com.glg204.wotstore.webofthing.service.WebThingServerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientDAO clientDAO;

    @Mock
    private ClientDTOMapper clientDTOMapper;

    @Mock
    private ThingInStoreDTOMapper thingInStoreDTOMapper;

    @Mock
    private WebThingServerService webThingServerService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ThingInStoreDAO thingInStoreDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClients() {
        List<Optional<Client>> clients = new ArrayList<>();
        when(clientDAO.getClients()).thenReturn(clients);
        List<ClientDTO> result = clientService.getClients();
        verify(clientDAO).getClients();
        List<ClientDTO> expectedClientDTOs = clients.stream().filter(Optional::isPresent).map(client -> {
            if (client.isPresent()) {
                return clientDTOMapper.toDTO(client.get());
            }
            return null;
        }).toList();

        assertEquals(expectedClientDTOs, result);
    }

    @Test
    void testGetClientThingsInStore_Success() {
        Principal principal = mock(Principal.class);
        List<Optional<ThingInStore>> things = new ArrayList<>();
        when(clientDAO.getClientByEmail(principal.getName())).thenReturn(Optional.of(new Client()));
        when(thingInStoreDAO.getClientThingsInStore(any(Client.class))).thenReturn(things);
        List<ThingInStoreDTO> result = clientService.getClientThingsInStore(principal);
        verify(clientDAO).getClientByEmail(principal.getName());
        verify(thingInStoreDAO).getClientThingsInStore(any(Client.class));
        List<ThingInStoreDTO> exceptedthingInStoreDTOS = things.stream().filter(Optional::isPresent).map(thing -> {
            if (thing.isPresent()) {
                return thingInStoreDTOMapper.toDTO(thing.get());
            }
            return null;
        }).toList();

        assertEquals(exceptedthingInStoreDTOS, result);
    }

    @Test
    void testGetClientThingsInStore_ClientNotFound() {
        Principal principal = mock(Principal.class);
        when(clientDAO.getClientByEmail(principal.getName())).thenReturn(Optional.empty());
        List<ThingInStoreDTO> result = clientService.getClientThingsInStore(principal);
        verify(clientDAO).getClientByEmail(principal.getName());
        verifyNoInteractions(thingInStoreDAO);
        assertTrue(result.isEmpty());
    }

    @Test
    void testPurchaseThingInStore_Success() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@lecnam.net"); //?
        Client client = new Client();
        when(clientDAO.getClientByEmail(principal.getName())).thenReturn(Optional.of(client));
        Long thingInStoreId = 123L;
        when(thingInStoreDAO.getThingInStoreById(thingInStoreId)).thenReturn(Optional.of(new ThingInStore()));
        when(clientDAO.setClientToThingInStore(thingInStoreId, client.getId())).thenReturn(true);
        boolean result = clientService.purchaseThingInStore(principal, thingInStoreId);
        verify(clientDAO).getClientByEmail(principal.getName());
        verify(clientDAO).setClientToThingInStore(thingInStoreId, client.getId());
        assertTrue(result);
    }

    @Test
    void testPurchaseThingInStore_Failure() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@lecnam.net");
        Client client = new Client();
        when(clientDAO.getClientByEmail(principal.getName())).thenReturn(Optional.of(client));
        Long thingInStoreId = 123L;
        when(thingInStoreDAO.getThingInStoreById(thingInStoreId)).thenReturn(Optional.empty()); // echec
        boolean result = clientService.purchaseThingInStore(principal, thingInStoreId);
        verify(clientDAO).getClientByEmail(principal.getName());
        assertFalse(result);
    }

    @Test
    void testStartThingInStore_Success() {
        Long thingInStoreId = 123L;
        ThingInStore thingInStore = mock(ThingInStore.class);
        when(thingInStore.getId()).thenReturn(thingInStoreId);
        when(thingInStoreDAO.getThingInStoreById(thingInStoreId)).thenReturn(Optional.of(thingInStore));
        when(webThingServerService.startThing(thingInStoreId, thingInStore.getThing())).thenReturn(true);
        when(clientDAO.setStatusToThingInStore(thingInStoreId, true)).thenReturn(true);
        boolean result = clientService.startThingInStore(thingInStoreId);
        verify(thingInStoreDAO).getThingInStoreById(thingInStoreId);
        verify(webThingServerService).startThing(thingInStoreId, thingInStore.getThing());
        verify(clientDAO).setStatusToThingInStore(thingInStoreId, true);

        assertTrue(result);
    }

    @Test
    void testStartThingInStore_Failure() {
        Long thingInStoreId = 123L;
        ThingInStore thingInStore = mock(ThingInStore.class);
        when(thingInStore.getId()).thenReturn(thingInStoreId);
        when(thingInStoreDAO.getThingInStoreById(thingInStoreId)).thenReturn(Optional.of(thingInStore));
        when(webThingServerService.startThing(thingInStoreId, thingInStore.getThing())).thenReturn(false); //echec
        boolean result = clientService.startThingInStore(thingInStoreId);
        verify(thingInStoreDAO).getThingInStoreById(thingInStoreId);
        verify(webThingServerService).startThing(thingInStoreId, thingInStore.getThing());
        assertFalse(result);
    }

    @Test
    void testStartThingInStore_ThingNotFound() {
        Long thingInStoreId = 123L;
        when(thingInStoreDAO.getThingInStoreById(thingInStoreId)).thenReturn(Optional.empty());
        boolean result = clientService.startThingInStore(thingInStoreId);
        verify(thingInStoreDAO).getThingInStoreById(thingInStoreId);
        assertFalse(result);
    }
}
