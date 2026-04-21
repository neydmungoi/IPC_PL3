package com.ipc.sentinela.domain

import com.ipc.sentinela.data.model.RiskLevel

object DecisionSupport {

    /**
     * Retorna o nível de risco baseado no Standardized Precipitation Index (SPI)
     * Valores padrão para monitorização de seca agrícola.
     */
    fun getRiskLevelFromSPI(spi: Float): RiskLevel {
        return when {
            spi <= -2.0f -> RiskLevel.CRITICAL
            spi <= -1.5f -> RiskLevel.SEVERE
            spi <= -1.0f -> RiskLevel.MODERATE
            else -> RiskLevel.NORMAL
        }
    }

    /**
     * Retorna uma recomendação textual simples baseada na cultura e no risco.
     * Esta lógica será expandida com o "Modo Linguagem Simples".
     */
    fun getRecommendation(riskLevel: RiskLevel, crop: String, isSimpleLanguage: Boolean): String {
        return if (isSimpleLanguage) {
            getSimpleRecommendation(riskLevel, crop)
        } else {
            getTechnicalRecommendation(riskLevel, crop)
        }
    }

    private fun getSimpleRecommendation(riskLevel: RiskLevel, crop: String): String {
        return when (riskLevel) {
            RiskLevel.CRITICAL -> "A situação é muito grave. Não plante agora e tente poupar o máximo de água possível."
            RiskLevel.SEVERE -> "A terra está muito seca. Se puder, mude a rega para a noite e adie novas plantações."
            RiskLevel.MODERATE -> "Atenção à falta de chuva. Vigie as suas plantas com mais cuidado."
            RiskLevel.NORMAL -> "A situação está normal. Continue o seu trabalho habitual."
        }
    }

    private fun getTechnicalRecommendation(riskLevel: RiskLevel, crop: String): String {
        return when (riskLevel) {
            RiskLevel.CRITICAL -> "Índice SPI crítico (<= -2.0). Défice hídrico extremo. Recomendação: Suspensão de novas sementeiras."
            RiskLevel.SEVERE -> "Seca severa detectada. Redução significativa da disponibilidade hídrica no solo."
            RiskLevel.MODERATE -> "Seca moderada. Monitorizar índices de evapotranspiração (SPEI)."
            RiskLevel.NORMAL -> "Índices climáticos dentro dos parâmetros de normalidade."
        }
    }
}
