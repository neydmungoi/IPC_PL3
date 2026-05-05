# SENTINELA – VoxNoti 🍇💧

**Sistema de Monitorização de Secas e Apoio à Decisão para a Região do Douro.**

O **VoxNoti** é um MVP desenvolvido para democratizar o acesso a dados climáticos complexos para agricultores, focando-se na inclusão digital e acessibilidade multimodal.

## 🚀 Motivação e Impacto
A região do Douro enfrenta desafios crescentes devido às alterações climáticas e períodos de seca prolongados. Muitos agricultores possuem literacia digital variada. O SENTINELA colmata esta falha ao traduzir métricas técnicas (SPI/SPEI) em feedback auditivo e visual simples, permitindo decisões rápidas sobre rega e preservação de culturas (Vinha, Olival, Milho).

## 🛠️ Stack Técnica
- **Linguagem:** Kotlin 1.9+
- **UI:** Jetpack Compose (Declarativa e Acessível)
- **Arquitetura:** MVVM (Model-View-ViewModel)
- **Navegação:** Compose Navigation com persistência de estado.
- **Áudio:** 
    - **Spearcons:** SoundPool para alertas de voz imediatos.
    - **Earcons/Podcasts:** Media3 ExoPlayer para sons de ambiente e educação.
- **Acessibilidade:** Suporte total a TalkBack, Contraste WCAG e Vibração Hática Extra.

## 📦 Dependências Principais
- `androidx.navigation:navigation-compose`: Gestão de ecrãs.
- `androidx.media3:media3-exoplayer`: Motor de áudio avançado.
- `androidx.lifecycle:lifecycle-viewmodel-compose`: Lógica de negócio reativa.
- `androidx.compose.material3`: Design System moderno e adaptável.

## 👥 Equipa de Desenvolvimento
- **Membro 1:** UI, Navegação e Modo de Linguagem Simples.
- **Membro 2:** Lógica de Dados, Mapas de Calor e Gráficos Customizados.
- **Membro 3:** Motor de Áudio, Acessibilidade Hática e Podcasts.
- **Membro 4:** Integração de Sistemas, QA e Documentação Técnica.

---
*Projeto desenvolvido para a unidade curricular de IPC - MVP v1.0*
