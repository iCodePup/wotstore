package com.glg204.wotstore.controller;

import com.glg204.wotstore.client.controller.ClientController;
import com.glg204.wotstore.client.dto.ClientDTO;
import com.glg204.wotstore.client.service.ClientService;
import com.glg204.wotstore.webofthing.dto.ThingInStoreDTO;
import com.glg204.wotstore.webofthing.service.WebThingServerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testListClient() {
        List<ClientDTO> clients = new ArrayList<>();
        when(clientService.getClients()).thenReturn(clients);
        ResponseEntity<List<ClientDTO>> response = clientController.listClient();
        verify(clientService).getClients();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(clients, response.getBody());
    }

    @Test
    void testPurchaseThingInStore_Success() {
        Principal principal = mock(Principal.class);
        ThingInStoreDTO thingInStoreDTO = new ThingInStoreDTO();
        thingInStoreDTO.setId(1L);
        when(clientService.purchaseThingInStore(eq(principal), anyLong())).thenReturn(true);
        ResponseEntity<String> response = clientController.purchaseThingInStore(thingInStoreDTO, principal);
        verify(clientService).purchaseThingInStore(principal, thingInStoreDTO.getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Objet connecté acheté", response.getBody());
    }

    @Test
    void testPurchaseThingInStore_Failure() {
        Principal principal = mock(Principal.class);
        ThingInStoreDTO thingInStoreDTO = new ThingInStoreDTO();
        when(clientService.purchaseThingInStore(eq(principal), anyLong())).thenReturn(false);
        ResponseEntity<String> response = clientController.purchaseThingInStore(thingInStoreDTO, principal);
        verify(clientService).purchaseThingInStore(principal, thingInStoreDTO.getId());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Une erreur s'est produite lors de votre achat", response.getBody());
    }

    @Test
    void testGetClientThingsInStore() {
        Principal principal = mock(Principal.class);
        List<ThingInStoreDTO> things = new ArrayList<>();
        when(clientService.getClientThingsInStore(principal)).thenReturn(things);
        ResponseEntity<List<ThingInStoreDTO>> response = clientController.getClientThingsInStore(principal);
        verify(clientService).getClientThingsInStore(principal);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(things, response.getBody());
    }

    @Test
    void testStartThingInStore_Success() {
        ThingInStoreDTO thingInStoreDTO = new ThingInStoreDTO();
        thingInStoreDTO.setId(1L);
        when(clientService.startThingInStore(anyLong())).thenReturn(true);
        ResponseEntity<String> response = clientController.startThingInStore(thingInStoreDTO);
        verify(clientService).startThingInStore(thingInStoreDTO.getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Objet connecté démarré", response.getBody());
    }

    @Test
    void testStartThingInStore_Failure() {
        ThingInStoreDTO thingInStoreDTO = new ThingInStoreDTO();
        thingInStoreDTO.setId(1L);
        when(clientService.startThingInStore(anyLong())).thenReturn(false);
        ResponseEntity<String> response = clientController.startThingInStore(thingInStoreDTO);
        verify(clientService).startThingInStore(thingInStoreDTO.getId());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Objet connecté déja démarré", response.getBody());
    }

    @Test
    void testStopThingInStore_Success() {
        ThingInStoreDTO thingInStoreDTO = new ThingInStoreDTO();
        thingInStoreDTO.setId(1L);
        when(clientService.stopThingInStore(anyLong())).thenReturn(true);
        ResponseEntity<String> response = clientController.stopThingInStore(thingInStoreDTO);
        verify(clientService).stopThingInStore(thingInStoreDTO.getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Objet connecté arreté", response.getBody());
    }

    @Test
    void testStopThingInStore_Failure() {
        ThingInStoreDTO thingInStoreDTO = new ThingInStoreDTO();
        thingInStoreDTO.setId(1L);
        when(clientService.stopThingInStore(anyLong())).thenReturn(false);
        ResponseEntity<String> response = clientController.stopThingInStore(thingInStoreDTO);
        verify(clientService).stopThingInStore(thingInStoreDTO.getId());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Impossible d'arreter cet objet connecté", response.getBody());
    }
}
