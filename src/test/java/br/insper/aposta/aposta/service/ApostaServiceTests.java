package br.insper.aposta.aposta;

import br.insper.aposta.partida.PartidaNaoEncontradaException;
import br.insper.aposta.partida.PartidaNaoRealizadaException;
import br.insper.aposta.partida.PartidaService;
import br.insper.aposta.partida.RetornarPartidaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ApostaServiceTests {

    @InjectMocks
    ApostaService apostaService;

    @Mock
    ApostaRepository apostaRepository;


    @Mock
    PartidaService partidaService;

    @Test
    public void testGetApostaWhenApostaIsNull() {

        Mockito.when(apostaRepository.findById("1"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ApostaNaoEncontradaException.class,
                () -> apostaService.getAposta("1"));
    }

    @Test
    public void testGetApostaWhenApostaIsNotNullStatusRealizada() {

        Aposta aposta = new Aposta();
        aposta.setStatus("GANHOU");

        Mockito.when(apostaRepository.findById("1"))
                .thenReturn(Optional.of(aposta));

        Aposta apostaRetorno = apostaService.getAposta("1");
        Assertions.assertNotNull(apostaRetorno);

    }
    @Test public void testGetApostaWhenApostaIsNotNullStatusPartidaStatusIsNotRealizada() {
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setId("1");

        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);
        partidaDto.getBody().setStatus("Not realizada");
        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));
        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);

        Assertions.assertThrows(PartidaNaoRealizadaException.class, () -> {
            apostaService.getAposta("1");
        });
    }
    @Test
    public void testGetApostaPartidaRealizadaApostaEmpate(){
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setResultado("EMPATE");
        aposta.setId("1");

        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        retornarPartidaDTO.setStatus("REALIZADA");
        retornarPartidaDTO.setPlacarMandante(1);
        retornarPartidaDTO.setPlacarVisitante(1);
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);


        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));
        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);
        Mockito.when(apostaRepository.save(aposta)).thenReturn(aposta);

        Aposta apostaRetorno = apostaService.getAposta("1");

        Assertions.assertEquals("GANHOU", apostaRetorno.getStatus());
        Assertions.assertNotNull(apostaRetorno);
    }
    @Test
    public void testGetApostaPartidaRealizadaApostaVitoriaMandante(){
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setResultado("VITORIA_MANDANTE");
        aposta.setId("1");

        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        retornarPartidaDTO.setStatus("REALIZADA");
        retornarPartidaDTO.setPlacarMandante(2);
        retornarPartidaDTO.setPlacarVisitante(1);
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);


        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));
        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);
        Mockito.when(apostaRepository.save(aposta)).thenReturn(aposta);

        Aposta apostaRetorno = apostaService.getAposta("1");

        Assertions.assertEquals("GANHOU", apostaRetorno.getStatus());
        Assertions.assertNotNull(apostaRetorno);
    }
    @Test
    public void testGetApostaPartidaRealizadaApostaVitoriaVisitante(){
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setResultado("VITORIA_VISITANTE");
        aposta.setId("1");

        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        retornarPartidaDTO.setStatus("REALIZADA");
        retornarPartidaDTO.setPlacarMandante(2);
        retornarPartidaDTO.setPlacarVisitante(3);
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);


        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));
        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);
        Mockito.when(apostaRepository.save(aposta)).thenReturn(aposta);

        Aposta apostaRetorno = apostaService.getAposta("1");

        Assertions.assertEquals("GANHOU", apostaRetorno.getStatus());
        Assertions.assertNotNull(apostaRetorno);
    }
    @Test
    public void testGetApostaPartidaRealizadaApostaPerdidaVS(){
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setResultado("VITORIA_VISITANTE");
        aposta.setId("1");

        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        retornarPartidaDTO.setStatus("REALIZADA");
        retornarPartidaDTO.setPlacarMandante(3);
        retornarPartidaDTO.setPlacarVisitante(3);
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);


        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));
        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);
        Mockito.when(apostaRepository.save(aposta)).thenReturn(aposta);

        Aposta apostaRetorno = apostaService.getAposta("1");

        Assertions.assertEquals("PERDEU", apostaRetorno.getStatus());
        Assertions.assertNotNull(apostaRetorno);
    }
    @Test
    public void testGetApostaPartidaRealizadaApostaPerdidaVM(){
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setResultado("VITORIA_MANDANTE");
        aposta.setId("1");

        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        retornarPartidaDTO.setStatus("REALIZADA");
        retornarPartidaDTO.setPlacarMandante(3);
        retornarPartidaDTO.setPlacarVisitante(3);
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);


        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));
        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);
        Mockito.when(apostaRepository.save(aposta)).thenReturn(aposta);

        Aposta apostaRetorno = apostaService.getAposta("1");

        Assertions.assertEquals("PERDEU", apostaRetorno.getStatus());
        Assertions.assertNotNull(apostaRetorno);
    }
    @Test
    public void testGetApostaPartidaRealizadaApostaPerdidaEmpate(){
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setResultado("EMPATE");
        aposta.setId("1");

        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        retornarPartidaDTO.setStatus("REALIZADA");
        retornarPartidaDTO.setPlacarMandante(2);
        retornarPartidaDTO.setPlacarVisitante(3);
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);


        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));
        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);
        Mockito.when(apostaRepository.save(aposta)).thenReturn(aposta);

        Aposta apostaRetorno = apostaService.getAposta("1");

        Assertions.assertEquals("PERDEU", apostaRetorno.getStatus());
        Assertions.assertNotNull(apostaRetorno);
    }

    @Test
    public void testGetApostaWhenStatusCodeIsNotSuccessful() {
        Aposta aposta = new Aposta();
        aposta.setStatus("REALIZADA");
        aposta.setIdPartida(1);
        aposta.setId("1");
        aposta.setResultado("EMPATE");


        RetornarPartidaDTO retornarPartidaDTO = new RetornarPartidaDTO();
        ResponseEntity<RetornarPartidaDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.NOT_FOUND);

        Mockito.when(partidaService.getPartida(1)).thenReturn(partidaDto);
        Mockito.when(apostaRepository.findById("1")).thenReturn(Optional.of(aposta));

        Assertions.assertThrows(PartidaNaoEncontradaException.class, () -> {
            apostaService.getAposta("1");

        });
    }

    @Test
    public void testSalvarAposta() {
        Aposta aposta = new Aposta();
        aposta.setIdPartida(1);

        RetornarPartidaDTO partidaDTO = new RetornarPartidaDTO();
        ResponseEntity<RetornarPartidaDTO> responseEntity = new ResponseEntity<>(partidaDTO, HttpStatus.OK);

        Mockito.when(partidaService.getPartida(1)).thenReturn(responseEntity);
        Mockito.when(apostaRepository.save(aposta)).thenReturn(aposta);

        Aposta apostaRetorno = apostaService.salvar(aposta);
        Assertions.assertNotNull(apostaRetorno);
        Assertions.assertEquals("REALIZADA", apostaRetorno.getStatus());
        Assertions.assertNotNull(apostaRetorno.getDataAposta());

    }

    @Test
    public void testSalvarApostaNotSuccessful() {
        Aposta aposta = new Aposta();
        aposta.setIdPartida(1);

        RetornarPartidaDTO partidaDTO = new RetornarPartidaDTO();
        ResponseEntity<RetornarPartidaDTO> responseEntity = new ResponseEntity<>(partidaDTO, HttpStatus.NOT_FOUND);

        Mockito.when(partidaService.getPartida(1)).thenReturn(responseEntity);

        Assertions.assertThrows(PartidaNaoEncontradaException.class, () -> {
            apostaService.salvar(aposta);
        });
    }
    @Test
    public void testListarAll() {
        List<Aposta> aposta  = new ArrayList();
        Mockito.when(apostaRepository.findAll()).thenReturn(new ArrayList<>());

        List<Aposta> apostas = apostaService.listar();
        Assertions.assertTrue(apostas.isEmpty());

    }





}
