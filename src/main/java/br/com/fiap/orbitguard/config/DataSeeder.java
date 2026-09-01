package br.com.fiap.orbitguard.config;

import br.com.fiap.orbitguard.domain.*;
import br.com.fiap.orbitguard.domain.enums.*;
import br.com.fiap.orbitguard.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedData(
            UsuarioRepository usuarioRepository,
            AreaMonitoradaRepository areaRepository,
            FonteDadoRepository fonteRepository,
            ObservacaoOrbitalRepository observacaoRepository,
            AlertaRiscoRepository alertaRepository,
            InscricaoAreaRepository inscricaoRepository) {
        return args -> {
            Usuario admin = usuarioRepository.save(Usuario.builder()
                    .nome("Equipe OrbitGuard")
                    .email("admin@orbitguard.com")
                    .senha(passwordEncoder.encode("orbitguard123"))
                    .perfil(PerfilUsuario.ADMIN)
                    .build());

            AreaMonitorada vilaPrudente = areaRepository.save(AreaMonitorada.builder()
                    .nome("Vila Prudente - Corredor Anhaia Mello")
                    .cidade("Sao Paulo")
                    .uf("SP")
                    .descricao("Regiao urbana com historico de alagamentos e alto fluxo de transporte.")
                    .coordenadas(new Coordenadas(-23.5847, -46.5814))
                    .nivelVulnerabilidade(NivelVulnerabilidade.ALTO)
                    .build());

            AreaMonitorada litoral = areaRepository.save(AreaMonitorada.builder()
                    .nome("Baixada Santista - Area Costeira")
                    .cidade("Santos")
                    .uf("SP")
                    .descricao("Area costeira sujeita a ressacas, chuva intensa e elevacao do nivel do mar.")
                    .coordenadas(new Coordenadas(-23.9608, -46.3336))
                    .nivelVulnerabilidade(NivelVulnerabilidade.CRITICO)
                    .build());

            FonteDado landsat = fonteRepository.save(FonteDado.builder()
                    .nome("Landsat Risk Layer")
                    .provedor("NASA/USGS")
                    .tipo(TipoFonteDado.SATELITE)
                    .ativa(true)
                    .build());

            FonteDado defesaCivil = fonteRepository.save(FonteDado.builder()
                    .nome("Boletim Defesa Civil")
                    .provedor("Defesa Civil SP")
                    .tipo(TipoFonteDado.DEFESA_CIVIL)
                    .ativa(true)
                    .build());

            ObservacaoOrbital observacao = observacaoRepository.save(ObservacaoOrbital.builder()
                    .area(vilaPrudente)
                    .fonteDado(landsat)
                    .capturadaEm(OffsetDateTime.now().minusHours(2))
                    .metricas(new MetricasClimaticas(42.5, 87.0, 24.8, 82.0))
                    .analiseIa("Solo saturado e acumulado de chuva indicam risco de alagamento nas proximas 6 horas.")
                    .build());

            AlertaRisco alerta = new AlertaRisco();
            alerta.setTitulo("Risco alto de alagamento urbano");
            alerta.setDescricao("Cruzamento de dados orbitais e boletins locais indica tendencia de acumulado critico.");
            alerta.setSeveridade(Severidade.ALTA);
            alerta.setArea(vilaPrudente);
            alerta.setObservacaoBase(observacao);
            alerta.setProbabilidadePercentual(84);
            alerta.setRecomendacao("Evitar deslocamento por vias rebaixadas e monitorar rotas alternativas.");
            alerta.setValidoAte(OffsetDateTime.now().plusHours(8));
            alertaRepository.save(alerta);

            inscricaoRepository.save(InscricaoArea.builder()
                    .id(new InscricaoAreaId(admin.getId(), litoral.getId()))
                    .usuario(admin)
                    .area(litoral)
                    .canal(CanalNotificacao.EMAIL)
                    .ativa(true)
                    .build());

            fonteRepository.save(defesaCivil);
        };
    }
}
